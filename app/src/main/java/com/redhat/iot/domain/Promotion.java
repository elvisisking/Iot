package com.redhat.iot.domain;

/**
 * Represents a promotion.
 */
public class Promotion {

    public static final Promotion[] NO_PROMOTIONs = new Promotion[ 0 ];

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

        if ( this.productId != that.productId ) {
            return false;
        }

        return ( this.id == that.id );
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
