package com.redhat.iot.json;

import com.redhat.iot.domain.Store;
import com.redhat.iot.TestUtils;
import com.redhat.iot.json.StoreMarshaller.Name;

import org.json.JSONObject;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * A test class for the {@link StoreMarshaller} class.
 */
public final class StoreMarshallerTest {

    private static final String ADDRESS_LINE_1 = "5 Thackeray Way";
    private static final String ADDRESS_LINE_2 = "c/o Sledge Hammer";
    private static final String CITY = "Columbus";
    private static final String COUNTRY = "USA";
    private static final int ID = 9000;
    private static final String PHONE = "(614)613-7123";
    private static final String POSTAL_CODE = "43231";
    private static final String STATE = "Ohio";

    private static final Store IOT = new Store( ID, ADDRESS_LINE_1, ADDRESS_LINE_2,
                                                CITY, STATE, POSTAL_CODE, COUNTRY, PHONE );
    private static final String JSON = "{ "
        + TestUtils.quote( Name.ID ) + " : " + ID + ", "
        + TestUtils.quote( Name.ADDRESS_LINE_1 ) + " : " + TestUtils.quote( ADDRESS_LINE_1 ) + ", "
        + TestUtils.quote( Name.ADDRESS_LINE_2 ) + " : " + TestUtils.quote( ADDRESS_LINE_2 ) + ", "
        + TestUtils.quote( Name.CITY ) + " : " + TestUtils.quote( CITY ) + ", "
        + TestUtils.quote( Name.STATE ) + " : " + TestUtils.quote( STATE ) + ", "
        + TestUtils.quote( Name.POSTAL_CODE ) + " : " + TestUtils.quote( POSTAL_CODE ) + ", "
        + TestUtils.quote( Name.COUNTRY ) + " : " + TestUtils.quote( COUNTRY ) + ", "
        + TestUtils.quote( Name.PHONE ) + " : " + TestUtils.quote( PHONE )
        + " }";

    private static final StoreMarshaller MARSHALLER = StoreMarshaller.get();

    @Test
    public void shouldConvertToIot() throws Exception {
        final Store store = MARSHALLER.toIot( JSON );
        assertThat( store, is( notNullValue() ) );
        assertThat( store.getId(), is( ID ) );
        assertThat( store.getAddressLine1(), is( ADDRESS_LINE_1 ) );
        assertThat( store.getAddressLine2(), is( ADDRESS_LINE_2 ) );
        assertThat( store.getCity(), is( CITY ) );
        assertThat( store.getCountry(), is( COUNTRY ) );
        assertThat( store.getPhone(), is( PHONE ) );
        assertThat( store.getPostalCode(), is( POSTAL_CODE ) );
        assertThat( store.getState(), is( STATE ) );
    }

    @Test
    public void shouldConvertToJson() throws Exception {
        final String json = MARSHALLER.toJson( IOT );
        final JSONObject jCust = new JSONObject( json );

        assertThat( jCust.has( Name.ID ), is( true ) );
        assertThat( jCust.getInt( Name.ID ), is( ID ) );

        assertThat( jCust.has( Name.ADDRESS_LINE_1 ), is( true ) );
        assertThat( jCust.getString( Name.ADDRESS_LINE_1 ), is( ADDRESS_LINE_1 ) );

        assertThat( jCust.has( Name.ADDRESS_LINE_2 ), is( true ) );
        assertThat( jCust.getString( Name.ADDRESS_LINE_2 ), is( ADDRESS_LINE_2 ) );

        assertThat( jCust.has( Name.CITY ), is( true ) );
        assertThat( jCust.getString( Name.CITY ), is( CITY ) );

        assertThat( jCust.has( Name.COUNTRY ), is( true ) );
        assertThat( jCust.getString( Name.COUNTRY ), is( COUNTRY ) );

        assertThat( jCust.has( Name.PHONE ), is( true ) );
        assertThat( jCust.getString( Name.PHONE ), is( PHONE ) );

        assertThat( jCust.has( Name.POSTAL_CODE ), is( true ) );
        assertThat( jCust.getString( Name.POSTAL_CODE ), is( POSTAL_CODE ) );

        assertThat( jCust.has( Name.STATE ), is( true ) );
        assertThat( jCust.getString( Name.STATE ), is( STATE ) );
    }

    @Test
    public void shouldRoundTripIot() throws Exception {
        assertThat( MARSHALLER.toIot( MARSHALLER.toJson( IOT ) ), is( IOT ) );
    }

    @Test
    public void shouldRoundTripJson() throws Exception {
        final String json = MARSHALLER.toJson( MARSHALLER.toIot( JSON ) );
        final JSONObject actual = new JSONObject( json );
        final JSONObject expected = new JSONObject( JSON );
        assertThat( actual.toString(), is( expected.toString() ) );
    }

}
