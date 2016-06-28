package com.redhat.iot.json;

import com.redhat.iot.TestUtils;
import com.redhat.iot.domain.Product;
import com.redhat.iot.json.ProductMarshaller.Name;

import org.json.JSONObject;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * A test class for the {@link ProductMarshaller} class.
 */
public final class ProductMarshallerTest {

    private static final double BUY_PRICE = 96.00;
    private static final long DEPARTMENT_ID = 1001;
    private static final String DESCRIPTION = "A Beach sling by manufacturer Izod";
    private static final int ID = 1653;
    private static final double MSRP = 18.00;
    private static final String NAME = "Beach sling";
    private static final String SIZE = "X-Large";
    private static final String VENDOR = "Izod";

    private static final Product IOT = new Product( ID,
                                                    DEPARTMENT_ID,
                                                    DESCRIPTION,
                                                    MSRP,
                                                    BUY_PRICE,
                                                    SIZE,
                                                    NAME,
                                                    VENDOR );
    private static final String JSON = "{ "
        + TestUtils.quote( Name.ID ) + " : " + ID + ", "
        + TestUtils.quote( Name.DEPARTMENT_ID ) + " : " + DEPARTMENT_ID + ", "
        + TestUtils.quote( Name.DESCRIPTION ) + " : " + TestUtils.quote( DESCRIPTION ) + ", "
        + TestUtils.quote( Name.MSRP ) + " : " + MSRP + ", "
        + TestUtils.quote( Name.BUY_PRICE ) + " : " + BUY_PRICE + ", "
        + TestUtils.quote( Name.SIZE ) + " : " + TestUtils.quote( SIZE ) + ", "
        + TestUtils.quote( Name.NAME ) + " : " + TestUtils.quote( NAME ) + ", "
        + TestUtils.quote( Name.VENDOR ) + " : " + TestUtils.quote( VENDOR )
        + " }";

    private static final ProductMarshaller MARSHALLER = ProductMarshaller.get();

    @Test
    public void shouldConvertToIot() throws Exception {
        final Product product = MARSHALLER.toIot( JSON );
        assertThat( product, is( notNullValue() ) );
        assertThat( product.getId(), is( ID ) );
        assertThat( product.getDepartmentId(), is( DEPARTMENT_ID ) );
        assertThat( product.getDescription(), is( DESCRIPTION ) );
        assertThat( product.getMsrp(), is( MSRP ) );
        assertThat( product.getBuyPrice(), is( BUY_PRICE ) );
        assertThat( product.getSize(), is( SIZE ) );
        assertThat( product.getName(), is( NAME ) );
        assertThat( product.getVendor(), is( VENDOR ) );
    }

    @Test
    public void shouldConvertToJson() throws Exception {
        final String json = MARSHALLER.toJson( IOT );
        final JSONObject jProduct = new JSONObject( json );

        assertThat( jProduct.has( Name.ID ), is( true ) );
        assertThat( jProduct.getInt( Name.ID ), is( ID ) );

        assertThat( jProduct.has( Name.DEPARTMENT_ID ), is( true ) );
        assertThat( jProduct.getLong( Name.DEPARTMENT_ID ), is( DEPARTMENT_ID ) );

        assertThat( jProduct.has( Name.DESCRIPTION ), is( true ) );
        assertThat( jProduct.getString( Name.DESCRIPTION ), is( DESCRIPTION ) );

        assertThat( jProduct.has( Name.MSRP ), is( true ) );
        assertThat( jProduct.getDouble( Name.MSRP ), is( MSRP ) );

        assertThat( jProduct.has( Name.BUY_PRICE ), is( true ) );
        assertThat( jProduct.getDouble( Name.BUY_PRICE ), is( BUY_PRICE ) );

        assertThat( jProduct.has( Name.SIZE ), is( true ) );
        assertThat( jProduct.getString( Name.SIZE ), is( SIZE ) );

        assertThat( jProduct.has( Name.NAME ), is( true ) );
        assertThat( jProduct.getString( Name.NAME ), is( NAME ) );

        assertThat( jProduct.has( Name.VENDOR ), is( true ) );
        assertThat( jProduct.getString( Name.VENDOR ), is( VENDOR ) );
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
