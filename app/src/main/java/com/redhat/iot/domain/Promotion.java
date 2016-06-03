package com.redhat.iot.domain;

import com.redhat.iot.DataProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Comparator;

/**
 * Represents a promotion.
 */
public class Promotion implements IotObject {

    /**
     * An empty collection of {@link Promotion}s.
     */
    public static final Promotion[] NO_PROMOTIONS = new Promotion[ 0 ];

    /**
     * Sorts {@link Promotion promotions} be {@link Department department} name.
     */
    public static final Comparator< Promotion > DEPT__NAME_SORTER = new Comparator< Promotion >() {

        @Override
        public int compare( final Promotion thisPromo,
                            final Promotion thatPromo ) {
            final DataProvider store = DataProvider.get();
            final Product thisProduct = store.findProduct( thisPromo.getProductId() );
            final Product thatProduct = store.findProduct( thatPromo.getProductId() );
            final Department thisDept = store.findDepartment( thisProduct.getDepartmentId() );
            final Department thatDept = store.findDepartment( thatProduct.getDepartmentId() );
            return Department.NAME_SORTER.compare( thisDept, thatDept );
        }
    };

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
