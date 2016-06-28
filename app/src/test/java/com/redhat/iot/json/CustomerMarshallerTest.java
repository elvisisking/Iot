package com.redhat.iot.json;

import com.redhat.iot.domain.Customer;
import com.redhat.iot.TestUtils;
import com.redhat.iot.json.CustomerMarshaller.Name;

import org.json.JSONObject;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * A test class for the {@link CustomerMarshaller} class.
 */
public final class CustomerMarshallerTest {

    private static final String ADDRESS_LINE_1 = "5 Thackeray Way";
    private static final String ADDRESS_LINE_2 = "c/o Sledge Hammer";
    private static final String CITY = "Columbus";
    private static final String COUNTRY = "USA";
    private static final int CREDIT_LIMIT = 6282;
    private static final String EMAIL = "alvera@ondricka.com";
    private static final int ID = 10000;
    private static final String NAME = "Alvera Ondricka";
    private static final String PHONE = "(614)613-7123";
    private static final String POSTAL_CODE = "43231";
    private static final String PASSWORD = "pswd";
    private static final String STATE = "Ohio";

    private static final Customer IOT = new Customer( ID, EMAIL, PASSWORD, NAME, ADDRESS_LINE_1, ADDRESS_LINE_2,
                                                      CITY, STATE, POSTAL_CODE, COUNTRY, PHONE, CREDIT_LIMIT );
    private static final String JSON = "{ "
        + TestUtils.quote( Name.ID ) + " : " + ID + ", "
        + TestUtils.quote( Name.EMAIL ) + " : " + TestUtils.quote( EMAIL ) + ", "
        + TestUtils.quote( Name.PASSWORD ) + " : " + TestUtils.quote( PASSWORD ) + ", "
        + TestUtils.quote( Name.NAME ) + " : " + TestUtils.quote( NAME ) + ", "
        + TestUtils.quote( Name.ADDRESS_LINE_1 ) + " : " + TestUtils.quote( ADDRESS_LINE_1 ) + ", "
        + TestUtils.quote( Name.ADDRESS_LINE_2 ) + " : " + TestUtils.quote( ADDRESS_LINE_2 ) + ", "
        + TestUtils.quote( Name.CITY ) + " : " + TestUtils.quote( CITY ) + ", "
        + TestUtils.quote( Name.STATE ) + " : " + TestUtils.quote( STATE ) + ", "
        + TestUtils.quote( Name.POSTAL_CODE ) + " : " + TestUtils.quote( POSTAL_CODE ) + ", "
        + TestUtils.quote( Name.COUNTRY ) + " : " + TestUtils.quote( COUNTRY ) + ", "
        + TestUtils.quote( Name.PHONE ) + " : " + TestUtils.quote( PHONE ) + ", "
        + TestUtils.quote( Name.CREDIT_LIMIT ) + " : " + CREDIT_LIMIT
        + " }";

    private static final CustomerMarshaller MARSHALLER = CustomerMarshaller.get();

    @Test
    public void shouldConvertToIot() throws Exception {
        final Customer customer = MARSHALLER.toIot( JSON );
        assertThat( customer, is( notNullValue() ) );
        assertThat( customer.getId(), is( ID ) );
        assertThat( customer.getAddressLine1(), is( ADDRESS_LINE_1 ) );
        assertThat( customer.getAddressLine2(), is( ADDRESS_LINE_2 ) );
        assertThat( customer.getCity(), is( CITY ) );
        assertThat( customer.getCountry(), is( COUNTRY ) );
        assertThat( customer.getCreditLimit(), is( CREDIT_LIMIT ) );
        assertThat( customer.getEmail(), is( EMAIL ) );
        assertThat( customer.getName(), is( NAME ) );
        assertThat( customer.getPhone(), is( PHONE ) );
        assertThat( customer.getPostalCode(), is( POSTAL_CODE ) );
        assertThat( customer.getPswd(), is( PASSWORD ) );
        assertThat( customer.getState(), is( STATE ) );
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

        assertThat( jCust.has( Name.CREDIT_LIMIT ), is( true ) );
        assertThat( jCust.getInt( Name.CREDIT_LIMIT ), is( CREDIT_LIMIT ) );

        assertThat( jCust.has( Name.EMAIL ), is( true ) );
        assertThat( jCust.getString( Name.EMAIL ), is( EMAIL ) );

        assertThat( jCust.has( Name.NAME ), is( true ) );
        assertThat( jCust.getString( Name.NAME ), is( NAME ) );

        assertThat( jCust.has( Name.PHONE ), is( true ) );
        assertThat( jCust.getString( Name.PHONE ), is( PHONE ) );

        assertThat( jCust.has( Name.POSTAL_CODE ), is( true ) );
        assertThat( jCust.getString( Name.POSTAL_CODE ), is( POSTAL_CODE ) );

        assertThat( jCust.has( Name.PASSWORD ), is( true ) );
        assertThat( jCust.getString( Name.PASSWORD ), is( PASSWORD ) );

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
