package com.redhat.iot.domain;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Comparator;

/**
 * Represents a line item from the order.
 */
public class OrderDetail implements IotObject {

    /**
     * Sorts {@link OrderDetail}s by purchased products with the biggest discounts.
     */
    public static final Comparator< OrderDetail > SORTER = new Comparator< OrderDetail >() {

        @Override
        public int compare( final OrderDetail thisDetail,
                            final OrderDetail thatDetail ) {
            int result = Integer.compare( thisDetail.getOrderId(), thatDetail.getOrderId() );

            if ( result == 0 ) {
                return Integer.compare( thatDetail.getDiscount(), thisDetail.getDiscount() );
            }

            return result;
        }
    };

    /**
     * An empty collection of {@link OrderDetail}s.
     */
    public static final OrderDetail[] NO_DETAILS = new OrderDetail[ 0 ];

    private final int orderId;
    private final int productId;
    private final int quantity;
    private final double msrp;
    private final int discount; // percentage

    /**
     * @param orderId   the ID of the order
     * @param productId the ID of the product
     * @param quantity  the number of the products ordered
     * @param msrp      the price for each product
     * @param discount  the percentage of discount off the MSRP that the product was purchased
     */
    public OrderDetail( final int orderId,
                        final int productId,
                        final int quantity,
                        final double msrp,
                        final int discount ) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.msrp = msrp;
        this.discount = discount;
    }

    /**
     * @param json a JSON representation of a order line item (cannot be empty)
     * @throws JSONException if there is a problem parsing the JSON
     */
    public OrderDetail( final String json ) throws JSONException {
        final JSONObject orderDetail = new JSONObject( json );

        // required
        this.orderId = orderDetail.getInt( "orderNumber" ); // must have an order ID
        this.productId = orderDetail.getInt( "productCode" ); // must have a product ID
        this.msrp = orderDetail.getDouble( "msrp" );
        this.discount = orderDetail.getInt( "discount" );

        // optional
        this.quantity = ( orderDetail.has( "quantityOrdered" ) ? orderDetail.getInt( "quantityOrdered" ) : 1 );
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

        if ( Double.compare( that.msrp, this.msrp ) != 0 ) {
            return false;
        }

        return ( ( this.discount == that.discount ) && ( this.productId == that.productId ) );

    }

    /**
     * @return the percentage discount off the MSRP the product was purchased for
     */
    public int getDiscount() {
        return this.discount;
    }

    /**
     * @return the price of each product ordered
     */
    public double getMsrp() {
        return this.msrp;
    }

    /**
     * @return the ID of the order this product was ordered
     */
    public int getOrderId() {
        return this.orderId;
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
        temp = Double.doubleToLongBits( this.msrp );
        result = 31 * result + ( int )( temp ^ ( temp >>> 32 ) );
        result = 31 * result + this.discount;
        return result;
    }

    @Override
    public String toString() {
        return ( "OrderDetail: order=" + this.orderId + ", product=" + this.productId );
    }

}
