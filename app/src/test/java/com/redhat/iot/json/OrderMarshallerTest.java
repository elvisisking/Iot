package com.redhat.iot.json;

import com.redhat.iot.TestUtils;
import com.redhat.iot.domain.Order;
import com.redhat.iot.json.OrderMarshaller.Name;

import org.json.JSONObject;
import org.junit.Test;

import java.util.Calendar;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * A test class for the {@link OrderMarshaller} class.
 */
public final class OrderMarshallerTest {

    private static final String COMMENTS = "comments";
    private static final int CUSTOMER_ID = 13107;
    private static final int ID = 999;
    private static final Calendar ORDER_DATE;
    private static final Calendar REQUIRED_DATE;
    private static final Calendar SHIPPED_DATE;
    private static final String STATUS = "status";

    static {
        ORDER_DATE = Calendar.getInstance();
        ORDER_DATE.set( 2016, 1, 1, 1, 1, 1 );

        REQUIRED_DATE = Calendar.getInstance();
        REQUIRED_DATE.set( 2016, 2, 2, 2, 2, 2 );

        SHIPPED_DATE = Calendar.getInstance();
        SHIPPED_DATE.set( 2016, 3, 3, 3, 3, 3 );
    }

    private static final Order IOT = new Order( ID, COMMENTS, CUSTOMER_ID, ORDER_DATE, REQUIRED_DATE, SHIPPED_DATE, STATUS );
    private static final OrderMarshaller MARSHALLER = OrderMarshaller.get();

    private static final String JSON = "{ "
        + TestUtils.quote( Name.ID ) + " : " + ID + ", "
        + TestUtils.quote( Name.COMMENTS ) + " : " + TestUtils.quote( COMMENTS ) + ", "
        + TestUtils.quote( Name.CUSTOMER_ID ) + " : " + CUSTOMER_ID + ", "
        + TestUtils.quote( Name.ORDER_DATE ) + " : " + TestUtils.quote( ORDER_DATE ) + ", "
        + TestUtils.quote( Name.REQUIRED_DATE ) + " : " + TestUtils.quote( REQUIRED_DATE ) + ", "
        + TestUtils.quote( Name.SHIPPED_DATE ) + " : " + TestUtils.quote( SHIPPED_DATE ) + ", "
        + TestUtils.quote( Name.STATUS ) + " : " + TestUtils.quote( STATUS )
        + " }";

    @Test
    public void shouldConvertToIot() throws Exception {
        final Order order = MARSHALLER.toIot( JSON );
        assertThat( order, is( notNullValue() ) );
        assertThat( order.getId(), is( ID ) );
        assertThat( order.getComments(), is( COMMENTS ) );
        assertThat( order.getCustomerId(), is( CUSTOMER_ID ) );
        assertThat( order.getOrderDate(), is( ORDER_DATE ) );
        assertThat( order.getRequiredDate(), is( REQUIRED_DATE ) );
        assertThat( order.getShippedDate(), is( SHIPPED_DATE ) );
        assertThat( order.getStatus(), is( STATUS ) );
    }

    @Test
    public void shouldConvertToJson() throws Exception {
        final String json = MARSHALLER.toJson( IOT );
        final JSONObject jOrder = new JSONObject( json );

        assertThat( jOrder.has( Name.ID ), is( true ) );
        assertThat( jOrder.getInt( Name.ID ), is( ID ) );

        assertThat( jOrder.has( Name.COMMENTS ), is( true ) );
        assertThat( jOrder.getString( Name.COMMENTS ), is( COMMENTS ) );

        assertThat( jOrder.has( Name.CUSTOMER_ID ), is( true ) );
        assertThat( jOrder.getInt( Name.CUSTOMER_ID ), is( CUSTOMER_ID ) );

        assertThat( jOrder.has( Name.ORDER_DATE ), is( true ) );
        assertThat( jOrder.getString( Name.ORDER_DATE ), is( JsonUtils.toJson( ORDER_DATE ) ) );

        assertThat( jOrder.has( Name.REQUIRED_DATE ), is( true ) );
        assertThat( jOrder.getString( Name.REQUIRED_DATE ), is( JsonUtils.toJson( REQUIRED_DATE ) ) );

        assertThat( jOrder.has( Name.SHIPPED_DATE ), is( true ) );
        assertThat( jOrder.getString( Name.SHIPPED_DATE ), is( JsonUtils.toJson( SHIPPED_DATE ) ) );

        assertThat( jOrder.has( Name.STATUS ), is( true ) );
        assertThat( jOrder.getString( Name.STATUS ), is( STATUS ) );
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
