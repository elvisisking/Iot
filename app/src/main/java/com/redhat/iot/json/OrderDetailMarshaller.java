package com.redhat.iot.json;

import com.redhat.iot.IotException;
import com.redhat.iot.domain.OrderDetail;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Converts to/from a JSON string and a {@link OrderDetail} object.
 */
public class OrderDetailMarshaller implements IotMarshaller< OrderDetail > {

    /**
     * The JSON names that may have mappings.
     */
    public interface Name {

        String DISCOUNT = "discount";
        String MSRP = "msrp";
        String ORDER_ID = "orderId";
        String PRODUCT_ID = "productId";
        String QUANTITY = "quantityOrdered";

    }

    private static OrderDetailMarshaller _shared;

    /**
     * @return the shared {@link OrderDetail} marshaller (never <code>null</code>)
     */
    public static OrderDetailMarshaller get() {
        if ( _shared == null ) {
            _shared = new OrderDetailMarshaller();
        }

        return _shared;
    }

    /**
     * Don't allow construction outside of this class.
     */
    private OrderDetailMarshaller() {
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
    public OrderDetail toIot( final String json ) throws IotException {
        try {
            final JSONObject orderDetail = new JSONObject( json );

            // required
            final int orderId = orderDetail.getInt( Name.ORDER_ID );
            final int productId = orderDetail.getInt( Name.PRODUCT_ID );
            final double msrp = orderDetail.getDouble( Name.MSRP );
            final int discount = orderDetail.getInt( Name.DISCOUNT );

            // optional
            final int quantity = ( orderDetail.has( Name.QUANTITY ) ? orderDetail.getInt( Name.QUANTITY ) : 1 );

            return new OrderDetail( orderId, productId, quantity, msrp, discount );
        } catch ( final Exception e ) {
            throw new IotException( e );
        }
    }

    @Override
    public String toJson( final OrderDetail detail ) throws IotException {
        final Map< String, Object > map = new HashMap<>();
        map.put( Name.ORDER_ID, detail.getOrderId() );
        map.put( Name.PRODUCT_ID, detail.getProductId() );
        map.put( Name.MSRP, detail.getMsrp() );
        map.put( Name.DISCOUNT, detail.getDiscount() );
        map.put( Name.QUANTITY, detail.getQuantity() );

        final JSONObject jDetail = new JSONObject( map );
        return jDetail.toString();
    }

}
