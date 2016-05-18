package com.redhat.iot.domain;

import com.redhat.iot.IotConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Represents an order.
 */
public class Order {

    /**
     * An empty collection of {@link Order}s.
     */
    public static final Order[] NO_ORDERS = new Order[ 0 ];

    private final String comments;
    private final int customerId;
    private final int id;
    private int[] productIds;
    private final Calendar orderDate;
    private final double price;
    private final Calendar requiredDate;
    private final Calendar shippedDate;
    private final String status;

    public Order( final int id,
                  final int customerId,
                  final Calendar orderDate,
                  final double price,
                  final String comments,
                  final String status,
                  final Calendar shippedDate,
                  final Calendar requiredDate ) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.price = price;
        this.comments = comments;
        this.status = status;
        this.shippedDate = shippedDate;
        this.requiredDate = requiredDate;
    }

    /**
     * @param json a JSON representation of an order (cannot be empty)
     * @throws JSONException if there is a problem parsing the JSON
     */
    public Order( final String json ) throws JSONException, ParseException {
        final JSONObject order = new JSONObject( json );

        // required
        this.id = order.getInt( "orderNumber" ); // must have an ID
        this.customerId = order.getInt( "customerNumber" ); // must have a customer ID

        // optional
        this.comments = ( order.has( "comments" ) ? order.getString( "comments" ) : "" );
        this.price = ( order.has( "price" ) ? order.getDouble( "price" ) : -1 );
        this.status = ( order.has( "status" ) ? order.getString( "status" ) : "" );

        if ( order.has( "productIds" ) ) {
            final JSONArray jarray = order.getJSONArray( "productIds" );
            this.productIds = new int[ jarray.length() ];

            for ( int i = 0;
                  i < jarray.length();
                  ++i ) {
                this.productIds[ i ] = jarray.getInt( i );
            }
        } else {
            this.productIds = new int[ 0 ];
        }

        if ( order.has( "orderDate" ) ) {
            this.orderDate = parseDate( order.getString( "orderDate" ) );
        } else {
            this.orderDate = null;
        }

        if ( order.has( "requiredDate" ) ) {
            this.requiredDate = parseDate( order.getString( "requiredDate" ) );
        } else {
            this.requiredDate = null;
        }

        if ( order.has( "shippedDate" ) ) {
            this.shippedDate = parseDate( order.getString( "shippedDate" ) );
        } else {
            this.shippedDate = null;
        }
    }

    /**
     * @return the order comments (can be empty)
     */
    public String getComments() {
        return this.comments;
    }

    /**
     * @return the customer ID
     */
    public int getCustomerId() {
        return this.customerId;
    }

    /**
     * @return the order ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return the IDs of the products bought with this order (never <code>null</code>)
     */
    public int[] getProducts() {
        return this.productIds;
    }

    /**
     * @return the date of the order (never <code>null</code>)
     */
    public Calendar getOrderDate() {
        return this.orderDate;
    }

    /**
     * @return the order price
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * @return the required date of the order (never <code>null</code>)
     */
    public Calendar getRequiredDate() {
        return this.requiredDate;
    }

    /**
     * @return the shipped date of the order (never <code>null</code>)
     */
    public Calendar getShippedDate() {
        return this.shippedDate;
    }

    /**
     * @return the order status (never empty)
     */
    public String getStatus() {
        return this.status;
    }

    private Calendar parseDate( final String dateString ) throws ParseException {
        // need to strip off "/Date(" from beginning and ")/" from end
        final String temp = dateString.substring( 6, dateString.length() - 2 );
        final long orderDate = Long.parseLong( temp );
        final Calendar cal = Calendar.getInstance();
        cal.setTime( new Date( orderDate ) );
        return cal;
    }

    /**
     * @param productIds the IDs of the products bought with this order (never <code>null</code>)
     */
    public void setProducts( final int[] productIds ) {
        this.productIds = productIds;
    }

    @Override
    public String toString() {
        return ( "Order: " + this.id );
    }

}
