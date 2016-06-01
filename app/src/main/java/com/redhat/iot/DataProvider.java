package com.redhat.iot;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.redhat.iot.domain.Customer;
import com.redhat.iot.domain.Department;
import com.redhat.iot.domain.Order;
import com.redhat.iot.domain.OrderDetail;
import com.redhat.iot.domain.Product;
import com.redhat.iot.domain.Promotion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provider for domain objects.
 */
public class DataProvider {

    private static final boolean USE_REAL_DATA = true;
    private static final String HOST = "10.0.2.2"; // when DV is running locally (use localhost in browser)
    private static final String PORT = "8080";

    private static final String CUSTOMERS_URL;
    private static final String DEPARTMENTS_URL;
    private static final String ORDER_DETAILS_URL;
    private static final String ORDER_HISTORY_URL;
    private static final String ORDERS_URL;
    private static final String PRODUCTS_URL;
    private static final String PROMOTIONS_URL;

    static {
        final String jsonFormat = "?$format=json";
        final String urlPattern = new StringBuilder( "http://" ).append( HOST )
            .append( ':' )
            .append( PORT )
            .append( "/odata/customer_iot/%s" )
            .toString();

        CUSTOMERS_URL = String.format( urlPattern, "Customer" ) + jsonFormat;
        DEPARTMENTS_URL = String.format( urlPattern, "FUSE.Department" ) + jsonFormat;
        ORDER_DETAILS_URL = String.format( urlPattern, "PostgreSQL_Sales_Promotions.Order(%s)/OrderDetail" ) + jsonFormat;
        ORDER_HISTORY_URL = String.format( urlPattern, "getSalesHistory?customerNumber=%s&$format=json" );
        ORDERS_URL = String.format( urlPattern, "PostgreSQL_Sales_Promotions.Customer(%s)/Order" ) + jsonFormat;
        PRODUCTS_URL = String.format( urlPattern, "PostgreSQL_Sales_Promotions.Product" ) + jsonFormat;
        PROMOTIONS_URL = String.format( urlPattern, "PostgreSQL_Sales_Promotions.Promotion" ) + jsonFormat;
    }

    private static DataProvider _shared = null;

    /**
     * @return the shared provider (never <code>null</code>)
     */
    public static DataProvider get() {
        if ( _shared == null ) {
            _shared = new DataProvider();
        }

        return _shared;
    }

    private final Map< Integer, Customer > customers = new HashMap<>();
    private final Map< Long, Department > departments = new HashMap<>();
    private final Map< Long, Integer > deptColors = new HashMap<>();
    private final Map< Integer, Product > products = new HashMap<>();
    private final Map< Integer, Promotion > promotions = new HashMap<>();

    /**
     * Don't allow construction outside of this class.
     */
    private DataProvider() {
        getDepartments(); // load departments
        getCustomers(); // load customer data
    }

    private String executeHttpGet( final String urlAsString,
                                   final String user,
                                   final String pswd ) throws Exception {
        final URL url = new URL( urlAsString );
        final String userCredentials = ( user + ':' + pswd );
        final String encoding = new String( Base64.encode( userCredentials.getBytes(), Base64.DEFAULT ) ).replaceAll( "\\s+", "" );

        final HttpURLConnection urlConnection = ( HttpURLConnection )url.openConnection();
        urlConnection.setRequestProperty( "Authorization", "Basic " + encoding );
        urlConnection.setRequestMethod( "GET" );
        urlConnection.setRequestProperty( "ACCEPT-LANGUAGE", "en-US,en;0.5" );

        try {
            final int code = urlConnection.getResponseCode();
            InputStream is;

            if ( code == HttpURLConnection.HTTP_OK ) {
                Log.d( IotConstants.LOG_TAG, ( "HTTP GET SUCCESS for URL: " + urlAsString ) );
                is = urlConnection.getInputStream();
            } else {
                Log.e( IotConstants.LOG_TAG, ( "HTTP GET FAILED for URL: " + urlAsString ) );
                is = urlConnection.getErrorStream();
            }

            final BufferedReader reader = new BufferedReader( new InputStreamReader( is ) );
            final StringBuilder builder = new StringBuilder();
            String line;

            while ( ( line = reader.readLine() ) != null ) {
                builder.append( line ).append( "\n" );
            }

            reader.close();
            return builder.toString();
        } finally {
            urlConnection.disconnect();
        }
    }

    /**
     * @param deptId the ID of the department being requested
     * @return the department or <code>null</code> if not found
     */
    public Department findDepartment( final long deptId ) {
        for ( final Department dept : getDepartments() ) {
            if ( deptId == dept.getId() ) {
                return dept;
            }
        }

        return null;
    }

    /**
     * @param productId the ID of the product being requested
     * @return the product or <code>null</code> if not found
     */
    public Product findProduct( final int productId ) {
        if ( this.products.isEmpty() ) {
            getProducts(); // load products
        }

        return this.products.get( productId );
    }

    /**
     * @param deptIds the IDs of the departments whose promotions are being requested
     * @return the requested promotions (never <code>null</code>)
     */
    public Promotion[] findPromotions( final Long... deptIds ) {
        if ( ( deptIds == null ) || ( deptIds.length == 0 ) ) {
            return Promotion.NO_PROMOTIONS;
        }

        final List< Long > requestedDepts = Arrays.asList( deptIds );
        final Promotion[] promotions = getPromotions();
        final List< Promotion > result = new ArrayList<>();

        for ( final Promotion promo : promotions ) {
            final int productId = promo.getProductId();
            final Product product = findProduct( productId );

            if ( product == null ) {
                Log.e( IotConstants.LOG_TAG, "Product " + productId + " was not found" );
            } else if ( requestedDepts.contains( product.getDepartmentId() ) ) {
                result.add( promo );
            }
        }

        return result.toArray( new Promotion[ result.size() ] );
    }

    /**
     * @param userId the ID of the user being requested
     * @return the user or <code>null</code> if not found
     */
    public Customer getCustomer( final int userId ) {
        for ( final Customer customer : getCustomers() ) {
            if ( customer.getId() == userId ) {
                return customer;
            }
        }

        return null;
    }

    /**
     * @param userId the ID of the customer whose name is being requested
     * @return the name or <code>null</code> if not found
     */
    public String getCustomerName( final int userId ) {
        if ( this.customers.isEmpty() ) {
            getCustomers();
        }

        final Customer customer = this.customers.get( userId );

        if ( customer != null ) {
            return customer.getName();
        }

        return null;
    }

    private Customer[] getCustomers() {
        if ( this.customers.isEmpty() ) {
            try {
                String json;

                if ( USE_REAL_DATA ) {
                    json = new GetData( CUSTOMERS_URL ).execute().get();
                } else {
                    final InputStream is = IotApp.getContext().getResources().openRawResource( R.raw.customer );
                    final BufferedReader streamReader = new BufferedReader( new InputStreamReader( is, "UTF-8" ) );
                    final StringBuilder builder = new StringBuilder();
                    String inputStr;

                    while ( ( inputStr = streamReader.readLine() ) != null ) {
                        builder.append( inputStr );
                    }

                    json = builder.toString();
                }

                final JSONObject jobj = new JSONObject( json );
                final JSONObject d = jobj.getJSONObject( "d" );
                final JSONArray jarray = d.getJSONArray( "results" );

                for ( int i = 0;
                      i < jarray.length();
                      ++i ) {
                    final JSONObject jcust = jarray.getJSONObject( i );
                    final Customer cust = new Customer( jcust.toString() );
                    this.customers.put( cust.getId(), cust );
                }
            } catch ( final Exception e ) {
                Log.e( IotConstants.LOG_TAG, e.getLocalizedMessage() );
                return Customer.NO_CUSTOMERs;
            }
        }

        return this.customers.values().toArray( new Customer[ this.customers.size() ] );
    }

    /**
     * @param deptId the ID of the department whose color is being requested (cannot be <code>null</code>)
     * @return the ID of the color
     */
    public int getDepartmentColor( final long deptId ) {
        // populate department cache if necessary
        if ( this.deptColors.isEmpty() ) {
            getDepartments();
        }

        final Department dept = this.departments.get( deptId );

        if ( dept != null ) {
            return this.deptColors.get( deptId );
        }

        Log.e( IotConstants.LOG_TAG, "No department found in cache for department " + deptId );
        return -1;
    }

    /**
     * @param dept the department whose color is being requested (cannot be <code>null</code>)
     * @return the ID of the color
     */
    public int getDepartmentColor( final Department dept ) {
        return getDepartmentColor( dept.getId() );
    }

    /**
     * @return all store departments (never <code>null</code>)
     */
    public Department[] getDepartments() {
        if ( this.departments.isEmpty() ) {
            try {
                String json;

                // TODO fix this
//                if ( USE_REAL_DATA ) {
//                    json = new GetData( DEPARTMENTS_URL ).execute().get();
//                } else {
                json = IotConstants.TestData.DEPARTMENTS_JSON;
//                }

                final JSONObject jobj = new JSONObject( json );
                final JSONObject d = jobj.getJSONObject( "d" );
                final JSONArray jarray = d.getJSONArray( "results" );

                final Resources res = IotApp.getContext().getResources();
                final TypedArray deptColors = res.obtainTypedArray( R.array.dept_colors );

                for ( int i = 0;
                      i < jarray.length();
                      ++i ) {
                    final JSONObject jdept = jarray.getJSONObject( i );
                    final Department dept = new Department( jdept.toString() );
                    this.departments.put( dept.getId(), dept );

                    // populate department colors map
                    final int colorId = deptColors.getColor( i, 0 );
                    this.deptColors.put( dept.getId(), colorId );
                }

                deptColors.recycle(); // call after done with TypeArray
            } catch ( final Exception e ) {
                Log.e( IotConstants.LOG_TAG, e.getLocalizedMessage() );
                return Department.NO_DEPARTMENTS;
            }
        }

        final Department[] result = this.departments.values().toArray( new Department[ this.departments.size() ] );
        Arrays.sort( result, Department.NAME_SORTER );
        return result;
    }

    public String getNotification() {
        // TODO all of this...
        // 1. find out department there in
        // 2. find out if roaming or focused
        // 3. if focused see if they ordered anything in that department in the past
        // 4. if yes, get promotions for that department
        // 5. Notify them of first promotion
        final int userId = IotApp.getUserId();

        if ( Customer.UNKNOWN_USER == userId ) {
            return null;
        }

        return "10% all clothing in the Boy's department";
    }

    /**
     * @param orderId the ID of the order whose order details are being requested
     * @return the order details (never <code>null</code>)
     */
    private OrderDetail[] getOrderDetails( final int orderId ) {
        try {
            String json;

            if ( USE_REAL_DATA ) {
                final String url = String.format( ORDER_DETAILS_URL, orderId );
                json = new GetData( url ).execute().get();
            } else {
                switch ( orderId ) {
                    case IotConstants.TestData.ORDER_1010_ID:
                        json = IotConstants.TestData.ORDER_1010_DETAILS_JSON;
                        break;
                    case IotConstants.TestData.ORDER_2020_ID:
                        json = IotConstants.TestData.ORDER_2020_DETAILS_JSON;
                        break;
                    case IotConstants.TestData.ORDER_3030_ID:
                        json = IotConstants.TestData.ORDER_3030_DETAILS_JSON;
                        break;
                    case IotConstants.TestData.ORDER_4040_ID:
                        json = IotConstants.TestData.ORDER_4040_DETAILS_JSON;
                        break;
                    case IotConstants.TestData.ORDER_5050_ID:
                        json = IotConstants.TestData.ORDER_5050_DETAILS_JSON;
                        break;
                    case IotConstants.TestData.ORDER_6060_ID:
                        json = IotConstants.TestData.ORDER_6060__DETAILS_JSON;
                        break;
                    default:
                        return OrderDetail.NO_DETAILS;
                }
            }

            final JSONObject jobj = new JSONObject( json );
            final JSONObject d = jobj.getJSONObject( "d" );
            final JSONArray jarray = d.getJSONArray( "results" );
            final List< OrderDetail > details = new ArrayList<>();

            for ( int i = 0;
                  i < jarray.length();
                  ++i ) {
                final JSONObject jdetail = jarray.getJSONObject( i );
                final OrderDetail detail = new OrderDetail( jdetail.toString() );
                details.add( detail );
            }

            return details.toArray( new OrderDetail[ details.size() ] );
        } catch ( final Exception e ) {
            Log.e( IotConstants.LOG_TAG, e.getLocalizedMessage() );
            return OrderDetail.NO_DETAILS;
        }
    }

    /**
     * @param customerId the ID of the customer whose orders are being requested
     * @return the orders (never <code>null</code>)
     */
    public Order[] getOrders( final int customerId ) {
        try {
            String json;

            if ( USE_REAL_DATA ) {
                final String url = String.format( ORDERS_URL, customerId );
                json = new GetData( url ).execute().get();
            } else {
                switch ( customerId ) {
                    case IotConstants.TestData.ELVIS_ID:
                        json = IotConstants.TestData.ELVIS_ORDERS_JSON;
                        break;
                    case IotConstants.TestData.RINGO_ID:
                        json = IotConstants.TestData.RINGO_ORDERS_JSON;
                        break;
                    case IotConstants.TestData.SLEDGE_ID:
                        json = IotConstants.TestData.SLEDGE_ORDERS_JSON;
                        break;
                    default:
                        return Order.NO_ORDERS;
                }
            }

            final JSONObject jobj = new JSONObject( json );
            final JSONObject d = jobj.getJSONObject( "d" );
            final JSONArray jarray = d.getJSONArray( "results" );
            final Order[] orders = new Order[ jarray.length() ];

            for ( int i = 0;
                  i < jarray.length();
                  ++i ) {
                final JSONObject jorder = jarray.getJSONObject( i );
                final Order order = new Order( jorder.toString() );
                final OrderDetail[] details = getOrderDetails( order.getId() );
                order.setDetails( details );
                orders[ i ] = order;
            }

            return orders;
        } catch ( final Exception e ) {
            Log.e( IotConstants.LOG_TAG, e.getLocalizedMessage() );
            return Order.NO_ORDERS;
        }
    }

    private Product[] getProducts() {
        if ( this.products.isEmpty() ) {
            try {
                String json;

                if ( USE_REAL_DATA ) {
                    json = new GetData( PRODUCTS_URL ).execute().get();
                } else {
                    json = IotConstants.TestData.PRODUCTS_JSON;
                }

                final JSONObject jobj = new JSONObject( json );
                final JSONObject d = jobj.getJSONObject( "d" );
                final JSONArray jarray = d.getJSONArray( "results" );

                for ( int i = 0;
                      i < jarray.length();
                      ++i ) {
                    final JSONObject jproduct = jarray.getJSONObject( i );
                    final Product product = new Product( jproduct.toString() );
                    this.products.put( product.getId(), product );
                }
            } catch ( final Exception e ) {
                Log.e( IotConstants.LOG_TAG, e.getLocalizedMessage() );
                return Product.NO_PRODUCTS;
            }
        }

        return this.products.values().toArray( new Product[ this.products.size() ] );
    }

    /**
     * @return all promotions (never <code>null</code> but can be empty)
     */
    public Promotion[] getPromotions() {
        if ( this.promotions.isEmpty() ) {
            try {
                String json;

                if ( USE_REAL_DATA ) {
                    json = new GetData( PROMOTIONS_URL ).execute().get();
                } else {
                    json = IotConstants.TestData.PROMOTIONS_JSON;
                }

                final JSONObject jobj = new JSONObject( json );
                final JSONObject d = jobj.getJSONObject( "d" );
                final JSONArray jarray = d.getJSONArray( "results" );

                for ( int i = 0;
                      i < jarray.length();
                      ++i ) {
                    final JSONObject jpromo = jarray.getJSONObject( i );
                    final Promotion promo = new Promotion( jpromo.toString() );
                    this.promotions.put( promo.getId(), promo );
                }
            } catch ( final Exception e ) {
                Log.e( IotConstants.LOG_TAG, e.getLocalizedMessage() );
                return Promotion.NO_PROMOTIONS;
            }
        }

        final Promotion[] result = this.promotions.values().toArray( new Promotion[ this.promotions.size() ] );
        Arrays.sort( result, Promotion.DEPT__NAME_SORTER );
        return result;
    }

    private class GetData extends AsyncTask< Void, Void, String > {

        private static final boolean I_AM_TED = true;
        private static final String USER = "teiidUser";
        private static final String PSWD = ( I_AM_TED ? "TbJ01221991$" : "4teiid$admin" );

        private final String url;

        public GetData( final String url ) {
            this.url = url;
        }

        @Override
        protected String doInBackground( final Void... params ) {
            try {
                return executeHttpGet( this.url, USER, PSWD );
            } catch ( final Exception e ) {
                Log.e( IotConstants.LOG_TAG, "Error in GetData AsyncTask. URL:  " + params[ 0 ] );
                return null;
            }
        }

    }

}
