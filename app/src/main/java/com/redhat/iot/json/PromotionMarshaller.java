package com.redhat.iot.json;

import com.redhat.iot.IotException;
import com.redhat.iot.domain.Promotion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Converts to/from a JSON string and a {@link Promotion} object.
 */
public class PromotionMarshaller implements IotMarshaller< Promotion > {

    /**
     * The JSON names that may have mappings.
     */
    public interface Name {

        String DISCOUNT = "discount";
        String ID = "id";
        String PRODUCT_ID = "productId";

    }

    private static PromotionMarshaller _shared;

    /**
     * @return the shared {@link com.redhat.iot.domain.Product} marshaller (never <code>null</code>)
     */
    public static PromotionMarshaller get() {
        if ( _shared == null ) {
            _shared = new PromotionMarshaller();
        }

        return _shared;
    }

    /**
     * Don't allow construction outside of this class.
     */
    private PromotionMarshaller() {
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
    public Promotion toIot( final String json ) throws IotException {
        try {
            final JSONObject cust = new JSONObject( json );

            // required
            final int id = cust.getInt( Name.ID );
            final int productId = cust.getInt( Name.PRODUCT_ID );
            final double discount = cust.getDouble( Name.DISCOUNT );

            return new Promotion( id, productId, discount );
        } catch ( final Exception e ) {
            throw new IotException( e );
        }
    }

    @Override
    public String toJson( final Promotion promotion ) throws IotException {
        final Map< String, Object > map = new HashMap<>();
        map.put( Name.ID, promotion.getId() );
        map.put( Name.PRODUCT_ID, promotion.getProductId() );
        map.put( Name.DISCOUNT, promotion.getDiscount() );

        final JSONObject jPromo = new JSONObject( map );
        return jPromo.toString();
    }

}
