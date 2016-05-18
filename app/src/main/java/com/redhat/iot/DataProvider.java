package com.redhat.iot;

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
import java.util.List;

/**
 * Provider for domain objects.
 */
public class DataProvider {

    private static final boolean USE_REAL_DATA = true;

    private static final String CUSTOMERS_URL = "http://10.0.2.2:8081/odata/customer_iot/Customer?$format=json";
    private static final String DEPARTMENTS_URL = "http://10.0.2.2:8081/odata/customer_iot/FUSE.Department?$format=json";
    private static final String ORDER_DETAILS_URL =
        " http://localhost:8081/odata/customer_iot/PostgreSQL_Sales_Promotions.Order(%s)/OrderDetail?$format=json";
    private static final String ORDERS_URL =
        "http://10.0.2.2:8081/odata/customer_iot/PostgreSQL_Sales_Promotions.Customer(%s)/Order?$format=json";
    private static final String PRODUCTS_URL = "http://10.0.2.2:8081/odata/customer_iot/Product?$format=json";
    private static final String PROMOTIONS_URL = "http://10.0.2.2:8081/odata/customer_iot/Promotion?$format=json";

    /**
     * The ID of an unknown user.
     */
    public static final int UNKNOWN_USER = -1;

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

    /**
     * Don't allow construction outside of this class.
     */
    private DataProvider() {
        // nothing to do
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
     * @param productId the ID of the product being requested
     * @return the product or <code>null</code> if not found
     */
    public Product findProduct( final int productId ) {
        for ( final Product product : getProductsFromJson() ) {
            if ( productId == product.getId() ) {
                return product;
            }
        }

        return null;
    }

    /**
     * @param userId the ID of the user being requested
     * @return the user or <code>null</code> if not found
     */
    public Customer getCustomer( final int userId ) {
        for ( final Customer customer : getCustomersFromJson() ) {
            if ( customer.getId() == userId ) {
                return customer;
            }
        }

        return null;
    }

    public Customer[] getCustomersFromJson() {
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
            final Customer[] customers = new Customer[ jarray.length() ];

            for ( int i = 0;
                  i < jarray.length();
                  ++i ) {
                final JSONObject jcust = jarray.getJSONObject( i );
                final Customer cust = new Customer( jcust.toString() );
                customers[ i ] = cust;
            }

            return customers;
        } catch ( final Exception e ) {
            Log.e( IotConstants.LOG_TAG, e.getLocalizedMessage() );
            return Customer.NO_CUSTOMERs;
        }
    }

    /**
     * @return all store departments (never <code>null</code>)
     */
    public Department[] getDepartmentsFromJson() {
        try {
            String json;

//            if ( USE_REAL_DATA ) {
//                json = new GetData( DEPARTMENTS_URL ).execute().get();
//            } else {
                json = IotConstants.TestData.DEPARTMENTS_JSON;
//            }

            final JSONObject jobj = new JSONObject( json );
            final JSONObject d = jobj.getJSONObject( "d" );
            final JSONArray jarray = d.getJSONArray( "results" );
            final Department[] departments = new Department[ jarray.length() ];

            for ( int i = 0;
                  i < jarray.length();
                  ++i ) {
                final JSONObject jdept = jarray.getJSONObject( i );
                final Department dept = new Department( jdept.toString() );
                departments[ i ] = dept;
            }

            return departments;
        } catch ( final Exception e ) {
            Log.e( IotConstants.LOG_TAG, e.getLocalizedMessage() );
            return Department.NO_DEPARTMENTS;
        }
    }

    public String getNotification() {
        // TODO all of this...
        // 1. find out department there in
        // 2. find out if roaming or focused
        // 3. if focused see if they ordered anything in that department in the past
        // 4. if yes, get promotions for that department
        // 5. Notify them of first promotion
        final int userId = IotApp.getUserId();

        if ( DataProvider.UNKNOWN_USER == userId ) {
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
                        json = IotConstants.TestData.ORDER_1010_JSON;
                        break;
                    case IotConstants.TestData.ORDER_2020_ID:
                        json = IotConstants.TestData.ORDER_2020_JSON;
                        break;
                    case IotConstants.TestData.ORDER_3030_ID:
                        json = IotConstants.TestData.ORDER_3030_JSON;
                        break;
                    case IotConstants.TestData.ORDER_4040_ID:
                        json = IotConstants.TestData.ORDER_4040_JSON;
                        break;
                    case IotConstants.TestData.ORDER_5050_ID:
                        json = IotConstants.TestData.ORDER_5050_JSON;
                        break;
                    case IotConstants.TestData.ORDER_6060_ID:
                        json = IotConstants.TestData.ORDER_6060_JSON;
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
                final int[] productIds = new int[ details.length ];

                for ( int j = 0;
                      j < details.length;
                      ++j ) {
                    productIds[ j ] = details[ j ].getProductId();
                }

                order.setProducts( productIds );
                orders[ i ] = order;
            }

            return orders;
        } catch ( final Exception e ) {
            Log.e( IotConstants.LOG_TAG, e.getLocalizedMessage() );
            return Order.NO_ORDERS;
        }
    }

    private Product[] getProductsFromJson() {
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
            final Product[] products = new Product[ jarray.length() ];

            for ( int i = 0;
                  i < jarray.length();
                  ++i ) {
                final JSONObject jproduct = jarray.getJSONObject( i );
                final Product product = new Product( jproduct.toString() );
                products[ i ] = product;
            }

            return products;
        } catch ( final Exception e ) {
            Log.e( IotConstants.LOG_TAG, e.getLocalizedMessage() );
            return Product.NO_PRODUCTS;
        }
    }

    /**
     * @return all promotions (never <code>null</code> but can be empty)
     */
    public Promotion[] getPromotionsFromJson() {
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
            final Promotion[] promotions = new Promotion[ jarray.length() ];

            for ( int i = 0;
                  i < jarray.length();
                  ++i ) {
                final JSONObject jpromo = jarray.getJSONObject( i );
                final Promotion promo = new Promotion( jpromo.toString() );
                promotions[ i ] = promo;
            }

            return promotions;
        } catch ( final Exception e ) {
            Log.e( IotConstants.LOG_TAG, e.getLocalizedMessage() );
            return Promotion.NO_PROMOTIONS;
        }
    }

    /**
     * @param departmentIds the IDs of the departments whose promotions are being requested (can be <code>null</code>)
     * @return the promotions (never <code>null</code> but can be empty)
     */
    public Promotion[] getPromotions( final Long... departmentIds ) {
        if ( ( departmentIds == null ) || ( departmentIds.length == 0 ) ) {
            return Promotion.NO_PROMOTIONS;
        }

        final List< Promotion > promotions = new ArrayList<>();
        final List< Long > deptIds = Arrays.asList( departmentIds );

        for ( final Promotion promotion : getPromotionsFromJson() ) {
            final int productId = promotion.getProductId();
            final Product product = DataProvider.get().findProduct( productId );

            if ( product == null ) {
                Log.e( IotConstants.LOG_TAG,
                       "Product " + productId + " was not found for promotion " + promotion.getId() );
                continue;
            }

            if ( deptIds.contains( product.getDepartmentId() ) ) {
                promotions.add( promotion );
            }
        }

        return promotions.toArray( new Promotion[ promotions.size() ] );
    }

    private class GetData extends AsyncTask< Void, Void, String > {

        private static final String USER = "teiidUser";
        private static final String PSWD = "TbJ01221991$";

        private final String url;

        public GetData( final String url ) {
            this.url = url;
        }

        @Override
        protected String doInBackground( final Void... params ) {
            try {
                return executeHttpGet( this.url, USER, PSWD );
            } catch ( final Exception e ) {
                Log.e( IotConstants.LOG_TAG,
                       "Error in GetData AsyncTask. URL:  " + params[ 0 ] );
                return null;
            }
        }

    }

}
