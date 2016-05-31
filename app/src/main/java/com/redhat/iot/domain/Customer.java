package com.redhat.iot.domain;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represents a customer of the online store.
 */
public class Customer {

    /**
     * An empty collection of {@link Customer}s.
     */
    public static final Customer[] NO_CUSTOMERs = new Customer[ 0 ];

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

    /**
     * @param json a JSON representation of a customer (cannot be empty)
     * @throws JSONException if there is a problem parsing the JSON
     */
    public Customer( final String json ) throws JSONException {
        final JSONObject cust = new JSONObject( json );

        // required
        this.id = cust.getInt( "id" ); // must have an ID
        this.name = cust.getString( "name" ); // must have a name

        // optional
        this.addressLine1 = ( cust.has( "addressLine1" ) ? cust.getString( "addressLine1" ) : "" );
        this.addressLine2 = ( cust.has( "addressLine2" ) ? cust.getString( "addressLine2" ) : "" );
        this.city = ( cust.has( "city" ) ? cust.getString( "city" ) : "" );
        this.country = ( cust.has( "country" ) ? cust.getString( "country" ) : "" );
        this.creditLimit = ( cust.has( "creditLimit" ) ? cust.getInt( "creditLimit" ) : -1 );
        this.email = ( cust.has( "email" ) ? cust.getString( "email" ) : "" );
        this.phone = ( cust.has( "phone" ) ? cust.getString( "phone" ) : "" );
        this.postalCode = ( cust.has( "postalCode" ) ? cust.getString( "postalCode" ) : "" );
        this.pswd = ( cust.has( "pswd" ) ? cust.getString( "pswd" ) : "" );
        this.state = ( cust.has( "state" ) ? cust.getString( "state" ) : "" );
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
    public String toString() {
        return ( "Customer: " + this.id );
    }

}
