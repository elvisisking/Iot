package com.redhat.iot.auth;

import com.redhat.iot.IotConstants;
import com.redhat.iot.domain.Customer;

/**
 * Provides customer information.
 */
public class CustomerProvider {

    /**
     * The ID of an unknown user.
     */
    public static final int UNKNOWN_USER = -1;

    private static CustomerProvider _shared = null;

    /**
     * @return the shared provider (never <code>null</code>)
     */
    public static CustomerProvider get() {
        if ( _shared == null ) {
            _shared = new CustomerProvider();
        }

        return _shared;
    }

    /**
     * Don't allow construction outside of this class.
     */
    private CustomerProvider() {
        // nothing to do
    }

    /**
     * @param email the email of the user being requested (cannot be empty)
     * @return the user or <code>null</code> if not found
     */
    public Customer getUser( final String email ) {
        for ( final Customer customer : IotConstants.TestData.CUSTOMERs ) {
            if ( customer.getEmail().equals( email ) ) {
                return customer;
            }
        }

        return null;
    }

    /**
     * @param email the user's email (cannot be empty)
     * @return the user ID or {@link CustomerProvider#UNKNOWN_USER}
     */
    public int getUserId( final String email ) {
        for ( final Customer customer : IotConstants.TestData.CUSTOMERs ) {
            if ( customer.getEmail().equals( email ) ) {
                return customer.getId();
            }
        }

        return UNKNOWN_USER;
    }

}
