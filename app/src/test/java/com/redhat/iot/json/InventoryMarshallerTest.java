package com.redhat.iot.json;

import com.redhat.iot.domain.Inventory;
import com.redhat.iot.TestUtils;
import com.redhat.iot.json.InventoryMarshaller.Name;

import org.json.JSONObject;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * A test class for the {@link InventoryMarshaller} class.
 */
public final class InventoryMarshallerTest {

    private static final int PRODUCT_ID = 1504;
    private static final int QUANTITY = 813;
    private static final int STORE_ID = 9014;

    private static final Inventory IOT = new Inventory( STORE_ID, PRODUCT_ID, QUANTITY );
    private static final String JSON = "{ "
        + TestUtils.quote( Name.STORE_ID ) + " : " + STORE_ID + ", "
        + TestUtils.quote( Name.PRODUCT_ID ) + " : " + PRODUCT_ID + ", "
        + TestUtils.quote( Name.QUANTITY ) + " : " + QUANTITY
        + " }";

    private static final InventoryMarshaller MARSHALLER = InventoryMarshaller.get();

    @Test
    public void shouldConvertToIot() throws Exception {
        final Inventory inventory = MARSHALLER.toIot( JSON );
        assertThat( inventory, is( notNullValue() ) );
        assertThat( inventory.getStoreId(), is( STORE_ID ) );
        assertThat( inventory.getProductId(), is( PRODUCT_ID ) );
        assertThat( inventory.getQuantity(), is( QUANTITY ) );
    }

    @Test
    public void shouldConvertToJson() throws Exception {
        final String json = MARSHALLER.toJson( IOT );
        final JSONObject jInventory = new JSONObject( json );

        assertThat( jInventory.has( Name.STORE_ID ), is( true ) );
        assertThat( jInventory.getInt( Name.STORE_ID ), is( STORE_ID ) );

        assertThat( jInventory.has( Name.PRODUCT_ID ), is( true ) );
        assertThat( jInventory.getInt( Name.PRODUCT_ID ), is( PRODUCT_ID ) );

        assertThat( jInventory.has( Name.QUANTITY ), is( true ) );
        assertThat( jInventory.getInt( Name.QUANTITY ), is( QUANTITY ) );
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
