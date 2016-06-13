package com.redhat.iot.domain;

import java.util.Objects;

/**
 * Represents a customer of the online store.
 */
public class Customer implements IotObject {

    /**
     * An empty collection of {@link Customer}s.
     */
    public static final Customer[] NO_CUSTOMERS = new Customer[ 0 ];

    /**
     * The ID of an unknown user.
     */
    public static final int UNKNOWN_USER = -1;

    private final String addressLine1;
    private final String addressLine2;
    private final String city;
    private final String country;
    private final int creditLimit;
    private final String email;
    private final int id;
    private final String name;
    private final String phone;
    private final String postalCode;
    private final String pswd;
    private final String state;

    /**
     * @param id           the unique ID of the customer
     * @param email        the customer email (can be empty)
     * @param pswd         the customer password (can be empty)
     * @param name         the customer name (cannot be empty)
     * @param addressLine1 the first line of the customer address (can be empty)
     * @param addressLine2 the second line of the customer address (can be empty)
     * @param city         the city of the customer address (can be empty)
     * @param state        the state of the customer address (can be empty)
     * @param postalCode   the zipcode of the customer address (can be empty)
     * @param country      the country of the customer address (can be empty)
     * @param phone        the customer phone number (can be empty)
     * @param creditLimit  the first line of the customer address
     */
    public Customer( final int id,
                     final String email,
                     final String pswd,
                     final String name,
                     final String addressLine1,
                     final String addressLine2,
                     final String city,
                     final String state,
                     final String postalCode,
                     final String country,
                     final String phone,
                     final int creditLimit ) {
        this.id = id;
        this.email = email;
        this.pswd = pswd;
        this.name = name;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.phone = phone;
        this.creditLimit = creditLimit;
    }

    @Override
    public boolean equals( final Object o ) {
        if ( this == o ) {
            return true;
        }

        if ( ( o == null ) || ( getClass() != o.getClass() ) ) {
            return false;
        }

        final Customer that = ( Customer )o;

        if ( this.creditLimit != that.creditLimit ) {
            return false;
        }

        if ( this.id != that.id ) {
            return false;
        }

        if ( !Objects.equals( this.addressLine1, that.addressLine1 ) ) {
            return false;
        }

        if ( !Objects.equals( this.addressLine2, that.addressLine2 ) ) {
            return false;
        }

        if ( !Objects.equals( this.city, that.city ) ) {
            return false;
        }

        if ( !Objects.equals( this.country, that.country ) ) {
            return false;
        }

        if ( !Objects.equals( this.email, that.email ) ) {
            return false;
        }

        if ( !Objects.equals( this.name, that.name ) ) {
            return false;
        }

        if ( !Objects.equals( this.phone, that.phone ) ) {
            return false;
        }

        if ( !Objects.equals( this.postalCode, that.postalCode ) ) {
            return false;
        }

        if ( !Objects.equals( this.pswd, that.pswd ) ) {
            return false;
        }

        return Objects.equals( this.state, that.state );
    }

    /**
     * @return the address line 1 (can be empty)
     */
    public String getAddressLine1() {
        return this.addressLine1;
    }

    /**
     * @return the address line 2 (can be empty)
     */
    public String getAddressLine2() {
        return this.addressLine2;
    }

    /**
     * @return the address city (can be empty)
     */
    public String getCity() {
        return this.city;
    }

    /**
     * @return the address country (can be empty)
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * @return the credit limit or -1 if not set
     */
    public int getCreditLimit() {
        return this.creditLimit;
    }

    /**
     * @return the user email (can be empty)
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @return the customer ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return the customer name (never empty)
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the password (can be empty)
     */
    public String getPswd() {
        return this.pswd;
    }

    /**
     * @return the customer phone number (can be empty)
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * @return the address postal code (can be empty)
     */
    public String getPostalCode() {
        return this.postalCode;
    }

    /**
     * @return the address state (can be empty)
     */
    public String getState() {
        return this.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash( this.addressLine1,
                             this.addressLine2,
                             this.city,
                             this.country,
                             this.creditLimit,
                             this.email,
                             this.id,
                             this.name,
                             this.phone,
                             this.postalCode,
                             this.pswd,
                             this.state );
    }

    @Override
    public String toString() {
        return ( "Customer: id = " + this.id + ", name = " + this.name );
    }

}
