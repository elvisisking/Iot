package com.redhat.iot.domain;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represents a line item from the order.
 */
public class OrderDetail {

    /**
     * An empty collection of {@link OrderDetail}s.
     */
    public static final OrderDetail[] NO_DETAILS = new OrderDetail[ 0 ];

    private final int orderId;
    private final int productId;
    private final int quantity;
    private final double priceEach;
    private final int orderLineNumber;

    /**
     * @param orderId         the ID of the order
     * @param productId       the ID of the product
     * @param quantity        the number of the products ordered
     * @param priceEach       the price for each product
     * @param orderLineNumber the line number on the order where this product was purchased
     */
    public OrderDetail( final int orderId,
                        final int productId,
                        final int quantity,
                        final double priceEach,
                        final int orderLineNumber ) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.priceEach = priceEach;
        this.orderLineNumber = orderLineNumber;
    }

    /**
     * @param json a JSON representation of a order line item (cannot be empty)
     * @throws JSONException if there is a problem parsing the JSON
     */
    public OrderDetail( final String json ) throws JSONException {
        final JSONObject orderDetail = new JSONObject( json );

        // required
        this.orderId = orderDetail.getInt( "orderId" ); // must have an order ID
        this.productId = orderDetail.getInt( "productId" ); // must have a product ID
        this.priceEach = orderDetail.getDouble( "priceEach" ); // must have a item price

        // optional
        this.quantity = ( orderDetail.has( "quantity" ) ? orderDetail.getInt( "quantity" ) : 1 );
        this.orderLineNumber = ( orderDetail.has( "orderLineNumber" ) ? orderDetail.getInt( "orderLineNumber" ) : -1 );
    }

    @Override
    public boolean equals( final Object o ) {
        if ( this == o ) {
            return true;
        }

        if ( ( o == null ) || ( getClass() != o.getClass() ) ) {
            return false;
        }

        final OrderDetail that = ( OrderDetail )o;

        if ( this.orderId != that.orderId ) {
            return false;
        }

        if ( this.quantity != that.quantity ) {
            return false;
        }

        if ( Double.compare( that.priceEach, this.priceEach ) != 0 ) {
            return false;
        }

        if ( this.orderLineNumber != that.orderLineNumber ) {
            return false;
        }

        return ( this.productId == that.productId );

    }

    /**
     * @return the ID of the order this product was ordered
     */
    public int getOrderId() {
        return this.orderId;
    }

    /**
     * @return the line number of the order that pertains to this product ordered
     */
    public int getOrderLineNumber() {
        return this.orderLineNumber;
    }

    /**
     * @return the price of each product ordered
     */
    public double getPriceEach() {
        return this.priceEach;
    }

    /**
     * @return the ID of the product ordered
     */
    public int getProductId() {
        return this.productId;
    }

    /**
     * @return the quantity ordered (a number greater than zero)
     */
    public int getQuantity() {
        return this.quantity;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = this.orderId;
        result = 31 * result + this.productId;
        result = 31 * result + this.quantity;
        temp = Double.doubleToLongBits( this.priceEach );
        result = 31 * result + ( int )( temp ^ ( temp >>> 32 ) );
        result = 31 * result + this.orderLineNumber;
        return result;
    }

    @Override
    public String toString() {
        return ( "OrderDetail: " + this.orderLineNumber );
    }

}
