package com.redhat.iot.domain;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represents a promotion.
 */
public class Promotion {

    public static final Promotion[] NO_PROMOTIONS = new Promotion[ 0 ];

    private final double discount;
    private final int productId;
    private final int id;

    public Promotion( final int id,
                      final int productId,
                      final double discount ) {
        this.id = id;
        this.productId = productId;
        this.discount = discount;
    }

    /**
     * @param json a JSON representation of a promotion (cannot be empty)
     * @throws JSONException if there is a problem parsing the JSON
     */
    public Promotion( final String json ) throws JSONException {
        final JSONObject cust = new JSONObject( json );

        // required
        this.id = cust.getInt( "id" ); // must have an ID
        this.productId = cust.getInt( "productCode" ); // must have a product ID
        this.discount = cust.getDouble( "discount" ); // must have a discount
    }

    @Override
    public boolean equals( final Object o ) {
        if ( this == o ) {
            return true;
        }

        if ( ( o == null ) || ( getClass() != o.getClass() ) ) {
            return false;
        }

        final Promotion that = ( Promotion )o;

        if ( Double.compare( that.discount, this.discount ) != 0 ) {
            return false;
        }

        return ( ( this.productId == that.productId ) && ( this.id == that.id ) );

    }

    /**
     * @return the promotional percentage discount
     */
    public double getDiscount() {
        return this.discount;
    }

    /**
     * @return the ID of the promotion
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return the ID of the product which is being discounted
     */
    public int getProductId() {
        return this.productId;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits( discount );
        result = ( int )( temp ^ ( temp >>> 32 ) );
        result = 31 * result + productId;
        result = 31 * result + id;
        return result;
    }

    @Override
    public String toString() {
        return ( "Promotion: " + this.id );
    }

}
