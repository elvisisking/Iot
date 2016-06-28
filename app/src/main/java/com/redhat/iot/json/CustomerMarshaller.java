package com.redhat.iot.json;

import com.redhat.iot.IotException;
import com.redhat.iot.domain.Customer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Converts to/from a JSON string and a {@link Customer} object.
 */
public class CustomerMarshaller implements IotMarshaller< Customer > {

    /**
     * The JSON names that may have mappings.
     */
    public interface Name {

        String ADDRESS_LINE_1 = "addressLine1";
        String ADDRESS_LINE_2 = "addressLine2";
        String CITY = "city";
        String COUNTRY = "country";
        String CREDIT_LIMIT = "creditLimit";
        String EMAIL = "email";
        String ID = "id";
        String NAME = "name";
        String PASSWORD = "pswd";
        String PHONE = "phone";
        String POSTAL_CODE = "postalCode";
        String STATE = "state";

    }

    private static CustomerMarshaller _shared;

    /**
     * @return the shared {@link Customer} marshaller (never <code>null</code>)
     */
    public static CustomerMarshaller get() {
        if ( _shared == null ) {
            _shared = new CustomerMarshaller();
        }

        return _shared;
    }

    /**
     * Don't allow construction outside of this class.
     */
    private CustomerMarshaller() {
        // nothing to do
    }

    @Override
    public JSONArray parseJsonArray( final String json ) throws IotException {
        try {
            final JSONObject jobj = new JSONObject( json );
            final JSONObject d = jobj.getJSONObject( JsonUtils.RESULTS_ARRAY_PARENT );
            return d.getJSONArray( JsonUtils.RESULTS_ARRAY );
        } catch ( final Exception e ) {
            throw new IotException( e );
        }
    }

    @Override
    public Customer toIot( final String json ) throws IotException {
        try {
            final JSONObject cust = new JSONObject( json );

            // required
            final int id = cust.getInt( Name.ID );
            final String name = cust.getString( Name.NAME );

            // optional
            final String addressLine1 = ( cust.has( Name.ADDRESS_LINE_1 ) ? cust.getString( Name.ADDRESS_LINE_1 ) : "" );
            final String addressLine2 = ( cust.has( Name.ADDRESS_LINE_2 ) ? cust.getString( Name.ADDRESS_LINE_2 ) : "" );
            final String city = ( cust.has( Name.CITY ) ? cust.getString( Name.CITY ) : "" );
            final String country = ( cust.has( Name.COUNTRY ) ? cust.getString( Name.COUNTRY ) : "" );
            final int creditLimit = ( cust.has( Name.CREDIT_LIMIT ) ? cust.getInt( Name.CREDIT_LIMIT ) : -1 );
            final String email = ( cust.has( Name.EMAIL ) ? cust.getString( Name.EMAIL ) : "" );
            final String phone = ( cust.has( Name.PHONE ) ? cust.getString( Name.PHONE ) : "" );
            final String postalCode = ( cust.has( Name.POSTAL_CODE ) ? cust.getString( Name.POSTAL_CODE ) : "" );
            final String pswd = ( cust.has( Name.PASSWORD ) ? cust.getString( Name.PASSWORD ) : "" );
            final String state = ( cust.has( Name.STATE ) ? cust.getString( Name.STATE ) : "" );

            return new Customer( id,
                                 email,
                                 pswd,
                                 name,
                                 addressLine1,
                                 addressLine2,
                                 city,
                                 state,
                                 postalCode,
                                 country,
                                 phone,
                                 creditLimit );
        } catch ( final Exception e ) {
            throw new IotException( e );
        }
    }

    @Override
    public String toJson( final Customer customer ) throws IotException {
        final Map< String, Object > map = new HashMap<>();
        map.put( Name.ID, customer.getId() );
        map.put( Name.NAME, customer.getName() );

        if ( customer.getAddressLine1() != null ) {
            map.put( Name.ADDRESS_LINE_1, customer.getAddressLine1() );
        }

        if ( customer.getAddressLine2() != null ) {
            map.put( Name.ADDRESS_LINE_2, customer.getAddressLine2() );
        }

        if ( customer.getCity() != null ) {
            map.put( Name.CITY, customer.getCity() );
        }

        if ( customer.getCountry() != null ) {
            map.put( Name.COUNTRY, customer.getCountry() );
        }

        if ( customer.getCreditLimit() != -1 ) {
            map.put( Name.CREDIT_LIMIT, customer.getCreditLimit() );
        }

        if ( customer.getEmail() != null ) {
            map.put( Name.EMAIL, customer.getEmail() );
        }

        if ( customer.getPhone() != null ) {
            map.put( Name.PHONE, customer.getPhone() );
        }

        if ( customer.getPostalCode() != null ) {
            map.put( Name.POSTAL_CODE, customer.getPostalCode() );
        }

        if ( customer.getPswd() != null ) {
            map.put( Name.PASSWORD, customer.getPswd() );
        }

        if ( customer.getState() != null ) {
            map.put( Name.STATE, customer.getState() );
        }

        final JSONObject jCust = new JSONObject( map );
        return jCust.toString();
    }

}
