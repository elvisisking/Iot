package com.redhat.iot.json;

import com.redhat.iot.TestUtils;
import com.redhat.iot.domain.OrderDetail;
import com.redhat.iot.json.OrderDetailMarshaller.Name;

import org.json.JSONObject;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * A test class for the {@link OrderDetailMarshaller} class.
 */
public final class OrderDetailMarshallerTest {

    private static final int DISCOUNT = 17;
    private static final double MSRP = 9.0;
    private static final int ORDER_ID = 1;
    private static final int PRODUCT_ID = 1959;
    private static final int QUANTITY = 1;

    private static final OrderDetail IOT = new OrderDetail( ORDER_ID, PRODUCT_ID, QUANTITY, MSRP, DISCOUNT );
    private static final String JSON = "{ "
        + TestUtils.quote( Name.ORDER_ID ) + " : " + ORDER_ID + ", "
        + TestUtils.quote( Name.PRODUCT_ID ) + " : " + PRODUCT_ID + ", "
        + TestUtils.quote( Name.DISCOUNT ) + " : " + DISCOUNT + ", "
        + TestUtils.quote( Name.MSRP ) + " : " + MSRP + ", "
        + TestUtils.quote( Name.QUANTITY ) + " : " + QUANTITY
        + " }";

    private static final OrderDetailMarshaller MARSHALLER = OrderDetailMarshaller.get();

    @Test
    public void shouldConvertToIot() throws Exception {
        final OrderDetail inventory = MARSHALLER.toIot( JSON );
        assertThat( inventory, is( notNullValue() ) );
        assertThat( inventory.getOrderId(), is( ORDER_ID ) );
        assertThat( inventory.getProductId(), is( PRODUCT_ID ) );
        assertThat( inventory.getDiscount(), is( DISCOUNT ) );
        assertThat( inventory.getMsrp(), is( MSRP ) );
        assertThat( inventory.getQuantity(), is( QUANTITY ) );
    }

    @Test
    public void shouldConvertToJson() throws Exception {
        final String json = MARSHALLER.toJson( IOT );
        final JSONObject jOrderDetail = new JSONObject( json );

        assertThat( jOrderDetail.has( Name.ORDER_ID ), is( true ) );
        assertThat( jOrderDetail.getInt( Name.ORDER_ID ), is( ORDER_ID ) );

        assertThat( jOrderDetail.has( Name.PRODUCT_ID ), is( true ) );
        assertThat( jOrderDetail.getInt( Name.PRODUCT_ID ), is( PRODUCT_ID ) );

        assertThat( jOrderDetail.has( Name.QUANTITY ), is( true ) );
        assertThat( jOrderDetail.getInt( Name.QUANTITY ), is( QUANTITY ) );

        assertThat( jOrderDetail.has( Name.MSRP ), is( true ) );
        assertThat( jOrderDetail.getDouble( Name.MSRP ), is( MSRP ) );

        assertThat( jOrderDetail.has( Name.DISCOUNT ), is( true ) );
        assertThat( jOrderDetail.getInt( Name.DISCOUNT ), is( DISCOUNT ) );
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
