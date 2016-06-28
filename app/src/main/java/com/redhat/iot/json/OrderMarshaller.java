package com.redhat.iot.json;

import com.redhat.iot.IotException;
import com.redhat.iot.domain.Order;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Converts to/from a JSON string and a {@link Order} object.
 */
public class OrderMarshaller implements IotMarshaller< Order > {

    /**
     * The JSON names that may have mappings.
     */
    public interface Name {

        String CUSTOMER_ID = "customerId";
        String COMMENTS = "comments";
        String STATUS = "status";
        String ORDER_DATE = "orderDate";
        String ID = "id";
        String REQUIRED_DATE = "requiredDate";
        String SHIPPED_DATE = "shippedDate";

    }

    private static OrderMarshaller _shared;

    /**
     * @return the shared {@link Order} marshaller (never <code>null</code>)
     */
    public static OrderMarshaller get() {
        if ( _shared == null ) {
            _shared = new OrderMarshaller();
        }

        return _shared;
    }

    /**
     * Don't allow construction outside of this class.
     */
    private OrderMarshaller() {
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
    public Order toIot( final String json ) throws IotException {
        try {
            final JSONObject order = new JSONObject( json );

            // required
            final int id = order.getInt( Name.ID );
            final int customerId = order.getInt( Name.CUSTOMER_ID );
            final Calendar orderDate = JsonUtils.parseDate( order.getString( Name.ORDER_DATE ) );

            // optional
            final String comments = ( order.has( Name.COMMENTS ) ? order.getString( Name.COMMENTS ) : "" );
            final String status = ( order.has( Name.STATUS ) ? order.getString( Name.STATUS ) : "" );

            final Calendar requiredDate;

            if ( order.has( Name.REQUIRED_DATE ) ) {
                requiredDate = JsonUtils.parseDate( order.getString( Name.REQUIRED_DATE ) );
            } else {
                requiredDate = null;
            }

            final Calendar shippedDate;

            if ( order.has( Name.SHIPPED_DATE ) ) {
                shippedDate = JsonUtils.parseDate( order.getString( Name.SHIPPED_DATE ) );
            } else {
                shippedDate = null;
            }

            return new Order( id, comments, customerId, orderDate, requiredDate, shippedDate, status );
        } catch ( final Exception e ) {
            throw new IotException( e );
        }
    }

    @Override
    public String toJson( final Order order ) throws IotException {
        final Map< String, Object > map = new HashMap<>();
        map.put( Name.ID, order.getId() );
        map.put( Name.CUSTOMER_ID, order.getCustomerId() );
        map.put( Name.ORDER_DATE, JsonUtils.toJson( order.getOrderDate() ) );

        if ( order.getComments() != null ) {
            map.put( Name.COMMENTS, order.getComments() );
        }

        if ( order.getStatus() != null ) {
            map.put( Name.STATUS, order.getStatus() );
        }

        if ( order.getRequiredDate() != null ) {
            map.put( Name.REQUIRED_DATE, JsonUtils.toJson( order.getRequiredDate() ) );
        }

        if ( order.getShippedDate() != null ) {
            map.put( Name.SHIPPED_DATE, JsonUtils.toJson( order.getShippedDate() ) );
        }

        final JSONObject jProduct = new JSONObject( map );
        return jProduct.toString();
    }

}
