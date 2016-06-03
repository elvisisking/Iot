package com.redhat.iot.domain;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Represents an order.
 */
public class Order implements IotObject {

    /**
     * An empty collection of {@link Order}s.
     */
    public static final Order[] NO_ORDERS = new Order[ 0 ];

    private final String comments;
    private final int customerId;
    private final int id;
    private OrderDetail[] details = OrderDetail.NO_DETAILS;
    private final Calendar orderDate;
    private double price = 0;
    private final Calendar requiredDate;
    private final Calendar shippedDate;
    private final String status;

    /**
     * @param json a JSON representation of an order (cannot be empty)
     * @throws JSONException if there is a problem parsing the JSON
     */
    public Order( final String json ) throws JSONException {
        final JSONObject order = new JSONObject( json );

        // required
        this.id = order.getInt( "orderNumber" ); // must have an ID
        this.customerId = order.getInt( "customerNumber" ); // must have a customer ID

        // optional
        this.comments = ( order.has( "comments" ) ? order.getString( "comments" ) : "" );
        this.status = ( order.has( "status" ) ? order.getString( "status" ) : "" );

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

    @Override
    public boolean equals( final Object o ) {
        if ( this == o ) {
            return true;
        }

        if ( o == null || ( getClass() != o.getClass() ) ) {
            return false;
        }

        final Order that = ( Order )o;

        if ( this.customerId != that.customerId ) {
            return false;
        }
        if ( this.id != that.id ) {
            return false;
        }

        if ( Double.compare( that.price, this.price ) != 0 ) {
            return false;
        }

        if ( ( this.comments != null ) ? !this.comments.equals( that.comments ) : ( that.comments != null ) ) {
            return false;
        }

        if ( !Arrays.equals( this.details, that.details ) ) {
            return false;
        }

        if ( ( this.orderDate != null ) ? !this.orderDate.equals( that.orderDate ) : ( that.orderDate != null ) ) {
            return false;
        }

        if ( ( this.requiredDate != null ) ? !this.requiredDate.equals( that.requiredDate ) : ( that.requiredDate != null ) ) {
            return false;
        }

        if ( ( this.shippedDate != null ) ? !this.shippedDate.equals( that.shippedDate ) : ( that.shippedDate != null ) ) {
            return false;
        }

        return ( ( this.status != null ) ? this.status.equals( that.status ) : ( that.status == null ) );

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
     * @return the order details (never <code>null</code> but can be empty)
     */
    public OrderDetail[] getDetails() {
        return this.details;
    }

    /**
     * @return the order ID
     */
    public int getId() {
        return this.id;
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

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = ( ( comments != null ) ? comments.hashCode() : 0 );
        result = ( 31 * result + customerId );
        result = ( 31 * result + id );
        result = ( 31 * result + Arrays.hashCode( details ) );
        result = ( 31 * result + ( ( orderDate != null ) ? orderDate.hashCode() : 0 ) );
        temp = Double.doubleToLongBits( price );
        result = ( 31 * result + ( int )( temp ^ ( temp >>> 32 ) ) );
        result = ( 31 * result + ( ( requiredDate != null ) ? requiredDate.hashCode() : 0 ) );
        result = ( 31 * result + ( ( shippedDate != null ) ? shippedDate.hashCode() : 0 ) );
        result = ( 31 * result + ( ( status != null ) ? status.hashCode() : 0 ) );
        return result;
    }

    private Calendar parseDate( final String dateString ) {
        // need to strip off "/Date(" from beginning and ")/" from end
        final String temp = dateString.substring( 6, dateString.length() - 2 );
        final long orderDate = Long.parseLong( temp );
        final Calendar cal = Calendar.getInstance();
        cal.setTime( new Date( orderDate ) );
        return cal;
    }

    /**
     * @param details the order details (can be <code>null</code>) sorted by line number
     */
    public void setDetails( final OrderDetail[] details ) {
        this.details = ( ( details == null ) ? OrderDetail.NO_DETAILS : details );
        this.price = 0;

        if ( this.details.length != 0 ) {
            Arrays.sort( this.details, OrderDetail.SORTER );

            for ( final OrderDetail detail : this.details ) {
                this.price += ( ( detail.getMsrp() - ( detail.getMsrp() * detail.getDiscount() / 100 ) ) * detail.getQuantity() );
            }
        }
    }

    @Override
    public String toString() {
        return ( "Order: " + this.id );
    }

}
