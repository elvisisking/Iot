package com.redhat.iot.domain;

import com.redhat.iot.IotApp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represents a product from the store.
 */
public class Product {

    public static final Product[] NO_PRODUCTS = new Product[ 0 ];

    private final double buyPrice;
    private final long departmentId;
    private final String description;
    private final int id;
    private final double msrp;
    private final String size;
    private final String name;
    private final String vendor;

    /**
     * @param id           the product's unique ID
     * @param departmentId the ID of the product's department
     * @param description  the description (cannot be empty)
     * @param msrp         the product MSRP
     * @param buyPrice     the product buy price
     * @param size         the product size
     * @param name         the product name
     * @param vendor       the product vendor
     */
    public Product( final int id,
                    final long departmentId,
                    final String description,
                    final double msrp,
                    final double buyPrice,
                    final String size,
                    final String name,
                    final String vendor ) {
        this.id = id;
        this.departmentId = departmentId;
        this.description = description;
        this.msrp = msrp;
        this.buyPrice = buyPrice;
        this.size = size;
        this.name = name;
        this.vendor = vendor;
    }

    /**
     * @param json a JSON representation of a product (cannot be empty)
     * @throws JSONException if there is a problem parsing the JSON
     */
    public Product( final String json ) throws JSONException {
        final JSONObject product = new JSONObject( json );

        // required
        this.id = product.getInt( "productCode" ); // must have an ID
        this.departmentId = product.getInt( "departmentCode" ); // must have a department ID

        // optional
        this.description = ( product.has( "productDescription" ) ? product.getString( "productDescription" ) : "" );
        this.size = ( product.has( "productSize" ) ? product.getString( "productSize" ) : "" );
        this.name = ( product.has( "productName" ) ? product.getString( "productName" ) : "" );
        this.vendor = ( product.has( "productVendor" ) ? product.getString( "productVendor" ) : "" );
        this.buyPrice = ( product.has( "buyPrice" ) ? product.getDouble( "buyPrice" ) : -1 );
        this.msrp = ( product.has( "MSRP" ) ? product.getDouble( "MSRP" ) : -1 );

        // TODO need quantityInStock
    }

    @Override
    public boolean equals( final Object o ) {
        if ( this == o ) {
            return true;
        }

        if ( ( o == null ) || ( getClass() != o.getClass() ) ) {
            return false;
        }

        final Product that = ( Product )o;

        if ( Double.compare( that.buyPrice, this.buyPrice ) != 0 ) {
            return false;
        }

        if ( this.departmentId != that.departmentId ) {
            return false;
        }

        if ( this.id != that.id ) {
            return false;
        }

        if ( Double.compare( that.msrp, this.msrp ) != 0 ) {
            return false;
        }

        if ( ( this.description != null ) ? !description.equals( that.description ) : ( that.description != null ) ) {
            return false;
        }

        if ( ( size != null ) ? !size.equals( that.size ) : ( that.size != null ) ) {
            return false;
        }

        if ( ( name != null ) ? !name.equals( that.name ) : ( that.name != null ) ) {
            return false;
        }

        return ( vendor != null ) ? vendor.equals( that.vendor ) : ( that.vendor == null );
    }

    /**
     * @return the buy price
     */
    public double getBuyPrice() {
        return this.buyPrice;
    }

    /**
     * @return the product's department identifier
     */
    public long getDepartmentId() {
        return this.departmentId;
    }

    /**
     * @return the product description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @return the product identifier
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return the product's image identifier
     */
    public int getImageId() {
        return IotApp.getImageId( this );
    }

    /**
     * @return the product msrp
     */
    public double getMsrp() {
        return this.msrp;
    }

    /**
     * @return the product name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the product size
     */
    public String getSize() {
        return this.size;
    }

    /**
     * @return the product vendor
     */
    public String getVendor() {
        return this.vendor;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits( this.buyPrice );
        result = ( int )( temp ^ ( temp >>> 32 ) );
        result = 31 * result + ( int )this.departmentId;
        result = 31 * result + ( ( this.description != null ) ? this.description.hashCode() : 0 );
        result = 31 * result + this.id;
        temp = Double.doubleToLongBits( this.msrp );
        result = 31 * result + ( int )( temp ^ ( temp >>> 32 ) );
        result = 31 * result + ( ( this.size != null ) ? this.size.hashCode() : 0 );
        result = 31 * result + ( ( this.name != null ) ? this.name.hashCode() : 0 );
        result = 31 * result + ( ( this.vendor != null ) ? this.vendor.hashCode() : 0 );
        return result;
    }

}
