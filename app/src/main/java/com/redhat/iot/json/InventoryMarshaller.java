package com.redhat.iot.json;

import com.redhat.iot.IotException;
import com.redhat.iot.domain.Inventory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Converts to/from a JSON string and an {@link Inventory} object.
 */
public class InventoryMarshaller implements IotMarshaller< Inventory > {

    /**
     * The JSON names that may have mappings.
     */
    public interface Name {

        String PRODUCT_ID = "productId";
        String QUANTITY = "quantity";
        String STORE_ID = "storeId";

    }

    private static InventoryMarshaller _shared;

    /**
     * @return the shared {@link Inventory} marshaller (never <code>null</code>)
     */
    public static InventoryMarshaller get() {
        if ( _shared == null ) {
            _shared = new InventoryMarshaller();
        }

        return _shared;
    }

    /**
     * Don't allow construction outside of this class.
     */
    private InventoryMarshaller() {
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
    public Inventory toIot( final String json ) throws IotException {
        try {
            final JSONObject cust = new JSONObject( json );

            // required
            final int storeId = cust.getInt( Name.STORE_ID );
            final int productId = cust.getInt( Name.PRODUCT_ID );
            final int quantity = cust.getInt( Name.QUANTITY );

            return new Inventory( storeId, productId, quantity );
        } catch ( final Exception e ) {
            throw new IotException( e );
        }
    }

    @Override
    public String toJson( final Inventory inventory ) throws IotException {
        final Map< String, Object > map = new HashMap<>();
        map.put( Name.STORE_ID, inventory.getStoreId() );
        map.put( Name.PRODUCT_ID, inventory.getProductId() );
        map.put( Name.QUANTITY, inventory.getQuantity() );

        final JSONObject jInventory = new JSONObject( map );
        return jInventory.toString();
    }

}
