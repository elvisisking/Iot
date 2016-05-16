package com.redhat.iot;

import android.util.Log;

import com.redhat.iot.domain.Customer;
import com.redhat.iot.domain.Department;
import com.redhat.iot.domain.Order;
import com.redhat.iot.domain.OrderDetail;
import com.redhat.iot.domain.Product;
import com.redhat.iot.domain.Promotion;

import org.json.JSONArray;
import org.json.JSONObject;

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
     * @param email the email of the user being requested (cannot be empty)
     * @return the user or <code>null</code> if not found
     */
    public Customer getCustomer( final String email ) {
        for ( final Customer customer : getCustomersFromJson() ) {
            if ( customer.getEmail().equals( email ) ) {
                return customer;
            }
        }

        return null;
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
            Log.e( IotConstants.LOG__TAG, e.getLocalizedMessage() );
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
            Log.e( IotConstants.LOG__TAG, e.getLocalizedMessage() );
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
            Log.e( IotConstants.LOG__TAG, e.getLocalizedMessage() );
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
            Log.e( IotConstants.LOG__TAG, e.getLocalizedMessage() );
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
            Log.e( IotConstants.LOG__TAG, e.getLocalizedMessage() );
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
                Log.e( IotConstants.LOG__TAG,
                       "Product " + productId + " was not found for promotion " + promotion.getId() );
                continue;
            }

            if ( deptIds.contains( product.getDepartmentId() ) ) {
                promotions.add( promotion );
            }
        }

        return promotions.toArray( new Promotion[ promotions.size() ] );
    }

}
