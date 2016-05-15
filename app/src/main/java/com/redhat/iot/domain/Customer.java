package com.redhat.iot.domain;

/**
 * Represents a customer of the online store.
 */
public class Customer {

    /**
     * An empty collection of {@link Customer}s.
     */
    public static final Customer[] NO_CUSTOMERs = new Customer[ 0 ];

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

    public String getAddressLine1() {
        return this.addressLine1;
    }

    public String getAddressLine2() {
        return this.addressLine2;
    }

    public String getCity() {
        return this.city;
    }

    public String getCountry() {
        return this.country;
    }

    public int getCreditLimit() {
        return this.creditLimit;
    }

    /**
     * @return the user email (never empty)
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
     * @return the customer name
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
     * @return the customer phone number
     */
    public String getPhone() {
        return this.phone;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public String getState() {
        return this.state;
    }

    @Override
    public String toString() {
        return ( "Customer: " + this.id );
    }

}
