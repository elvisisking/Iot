package com.redhat.iot.json;

import com.redhat.iot.domain.Promotion;
import com.redhat.iot.TestUtils;
import com.redhat.iot.json.PromotionMarshaller.Name;

import org.json.JSONObject;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * A test class for the {@link PromotionMarshaller} class.
 */
public final class PromotionMarshallerTest {

    private static final double DISCOUNT = 10.0;
    private static final int ID = 1;
    private static final int PROD_ID = 100;

    private static final Promotion IOT = new Promotion( ID, PROD_ID, DISCOUNT );
    private static final String JSON = "{ "
        + TestUtils.quote( Name.ID ) + " : " + ID + ", "
        + TestUtils.quote( Name.PRODUCT_ID ) + " : " + PROD_ID + ", "
        + TestUtils.quote( Name.DISCOUNT ) + " : " + DISCOUNT
        + " }";

    private static final PromotionMarshaller MARSHALLER = PromotionMarshaller.get();

    @Test
    public void shouldConvertToIot() throws Exception {
        final Promotion promo = MARSHALLER.toIot( JSON );
        assertThat( promo, is( notNullValue() ) );
        assertThat( promo.getId(), is( ID ) );
        assertThat( promo.getProductId(), is( PROD_ID ) );
        assertThat( promo.getDiscount(), is( DISCOUNT ) );
    }

    @Test
    public void shouldConvertToJson() throws Exception {
        final String json = MARSHALLER.toJson( IOT );
        final JSONObject jPromo = new JSONObject( json );

        assertThat( jPromo.has( Name.ID ), is( true ) );
        assertThat( jPromo.getInt( Name.ID ), is( ID ) );

        assertThat( jPromo.has( Name.PRODUCT_ID ), is( true ) );
        assertThat( jPromo.getInt( Name.PRODUCT_ID ), is( PROD_ID ) );

        assertThat( jPromo.has( Name.DISCOUNT ), is( true ) );
        assertThat( jPromo.getDouble( Name.DISCOUNT ), is( DISCOUNT ) );
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
