package com.redhat.iot.json;

import com.redhat.iot.IotException;
import com.redhat.iot.domain.Store;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Converts to/from a JSON string and a {@link Store} object.
 */
public class StoreMarshaller implements IotMarshaller< Store > {

    /**
     * The JSON names that may have mappings.
     */
    public interface Name {

        String ADDRESS_LINE_1 = "addressLine1";
        String ADDRESS_LINE_2 = "addressLine2";
        String CITY = "city";
        String COUNTRY = "country";
        String ID = "id";
        String PHONE = "phone";
        String POSTAL_CODE = "postalCode";
        String STATE = "state";

    }

    private static StoreMarshaller _shared;

    /**
     * @return the shared {@link Store} marshaller (never <code>null</code>)
     */
    public static StoreMarshaller get() {
        if ( _shared == null ) {
            _shared = new StoreMarshaller();
        }

        return _shared;
    }

    /**
     * Don't allow construction outside of this class.
     */
    private StoreMarshaller() {
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
    public Store toIot( final String json ) throws IotException {
        try {
            final JSONObject store = new JSONObject( json );

            // required
            final int id = store.getInt( Name.ID );

            // optional
            final String addressLine1 = ( store.has( Name.ADDRESS_LINE_1 ) ? store.getString( Name.ADDRESS_LINE_1 ) : "" );
            final String addressLine2 = ( store.has( Name.ADDRESS_LINE_2 ) ? store.getString( Name.ADDRESS_LINE_2 ) : "" );
            final String city = ( store.has( Name.CITY ) ? store.getString( Name.CITY ) : "" );
            final String country = ( store.has( Name.COUNTRY ) ? store.getString( Name.COUNTRY ) : "" );
            final String phone = ( store.has( Name.PHONE ) ? store.getString( Name.PHONE ) : "" );
            final String postalCode = ( store.has( Name.POSTAL_CODE ) ? store.getString( Name.POSTAL_CODE ) : "" );
            final String state = ( store.has( Name.STATE ) ? store.getString( Name.STATE ) : "" );

            return new Store( id,
                              addressLine1,
                              addressLine2,
                              city,
                              state,
                              postalCode,
                              country,
                              phone );
        } catch ( final Exception e ) {
            throw new IotException( e );
        }
    }

    @Override
    public String toJson( final Store store ) throws IotException {
        final Map< String, Object > map = new HashMap<>();
        map.put( Name.ID, store.getId() );

        if ( store.getAddressLine1() != null ) {
            map.put( Name.ADDRESS_LINE_1, store.getAddressLine1() );
        }

        if ( store.getAddressLine2() != null ) {
            map.put( Name.ADDRESS_LINE_2, store.getAddressLine2() );
        }

        if ( store.getCity() != null ) {
            map.put( Name.CITY, store.getCity() );
        }

        if ( store.getCountry() != null ) {
            map.put( Name.COUNTRY, store.getCountry() );
        }

        if ( store.getPhone() != null ) {
            map.put( Name.PHONE, store.getPhone() );
        }

        if ( store.getPostalCode() != null ) {
            map.put( Name.POSTAL_CODE, store.getPostalCode() );
        }

        if ( store.getState() != null ) {
            map.put( Name.STATE, store.getState() );
        }

        final JSONObject jCust = new JSONObject( map );
        return jCust.toString();
    }

}
