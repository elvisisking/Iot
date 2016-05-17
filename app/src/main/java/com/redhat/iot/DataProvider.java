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

    private static final String CUSTOMER_URL = "http://localhost:8080/odata/customer_iot";

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
        urlConnection.setDoOutput( true );

        try {
            final int code = urlConnection.getResponseCode();
            InputStream is = null;

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
        for ( final Customer customer : getCustomersFromJsonFile() ) {
            if ( customer.getId() == userId ) {
                return customer;
            }
        }

        return null;
    }

    public Customer[] getCustomersFromJsonFile() {
        final boolean useRealData = false;

        try {
            String jcustomers = null;

            if ( !useRealData ) {
                final InputStream is = IotApp.getContext().getResources().openRawResource( R.raw.customer );
                final BufferedReader streamReader = new BufferedReader( new InputStreamReader( is, "UTF-8" ) );
                final StringBuilder builder = new StringBuilder();

                String inputStr;
                while ( ( inputStr = streamReader.readLine() ) != null ) {
                    builder.append( inputStr );
                }

                jcustomers = builder.toString();
            } else {
                final String url = null; // TODO fill in Ted
                final String user = null; // TODO fill in Ted
                final String pswd = null; // TODO fill in Ted
                jcustomers = new GetData( url, user, pswd ).execute().get();
            }

            final JSONObject jobj = new JSONObject( jcustomers );
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

    public Customer[] getCustomersFromJson() {
        final String json = IotConstants.TestData.CUSTOMERS_JSON; // TODO replace with call to get actual customers JSON string

        try {
            final JSONObject jobj = new JSONObject( json );
            final JSONArray jarray = jobj.getJSONArray( "customers" );
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
        final String json = IotConstants.TestData.DEPARTMENTS_JSON; // TODO replace with call to get actual departments JSON string

        try {
            final JSONObject jobj = new JSONObject( json );
            final JSONArray jarray = jobj.getJSONArray( "departments" );
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
    public OrderDetail[] getOrderDetails( final int orderId ) {
        final List< OrderDetail > details = new ArrayList<>();

        for ( final OrderDetail detail : IotConstants.TestData.ORDER_DETAILS ) {
            if ( orderId == detail.getOrderId() ) {
                details.add( detail );
            }
        }

        return details.toArray( new OrderDetail[ details.size() ] );
    }

    private Order[] getOrdersFromJson() {
        final String json = IotConstants.TestData.ORDERS_JSON; // TODO replace with call to get actual Orders JSON string

        try {
            final JSONObject jobj = new JSONObject( json );
            final JSONArray jarray = jobj.getJSONArray( "orders" );
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

    /**
     * @param customerId the ID of the customer whose orders are being requested
     * @return the orders (never <code>null</code>)
     */
    public Order[] getOrders( final int customerId ) {
        final List< Order > orders = new ArrayList<>();

        for ( final Order order : getOrdersFromJson() ) {
            if ( customerId == order.getCustomerId() ) {
                final OrderDetail[] items = getOrderDetails( order.getId() );
                final int[] productIds = new int[ items.length ];
                int i = 0;

                for ( final OrderDetail item : items ) {
                    productIds[ i++ ] = item.getProductId();
                }

                order.setProducts( productIds );
                orders.add( order );
            }
        }

        return orders.toArray( new Order[ orders.size() ] );
    }

    private Product[] getProductsFromJson() {
        final String json = IotConstants.TestData.PRODUCTS_JSON;

        try {
            final JSONObject jobj = new JSONObject( json );
            final JSONArray jarray = jobj.getJSONArray( "products" );
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
        final String json = IotConstants.TestData.PROMOTIONS_JSON; // TODO replace with call to get actual promotions JSON string

        try {
            final JSONObject jobj = new JSONObject( json );
            final JSONArray jarray = jobj.getJSONArray( "promotions" );
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

        private final String url;
        private final String user;
        private final String pswd;

        public GetData( final String url,
                        final String user,
                        final String pswd ) {
            this.url = url;
            this.user = user;
            this.pswd = pswd;
        }

        @Override
        protected String doInBackground( final Void... params ) {
            try {
                return executeHttpGet( this.url, this.user, this.pswd );
            } catch ( final Exception e ) {
                Log.e( IotConstants.LOG_TAG,
                       "Error in GetData AsyncTask. URL:  " + params[ 0 ] );
                return null;
            }
        }

    }

}
