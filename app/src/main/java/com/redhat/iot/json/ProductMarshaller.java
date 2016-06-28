package com.redhat.iot.json;

import com.redhat.iot.IotException;
import com.redhat.iot.domain.Product;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Converts to/from a JSON string and a {@link Product} object.
 */
public class ProductMarshaller implements IotMarshaller< Product > {

    /**
     * The JSON names that may have mappings.
     */
    public interface Name {

        String BUY_PRICE = "buyPrice";
        String DEPARTMENT_ID = "departmentCode";
        String DESCRIPTION = "productDescription";
        String ID = "id";
        String MSRP = "msrp";
        String NAME = "productName";
        String SIZE = "productSize";
        String VENDOR = "productVendor";

    }

    private static ProductMarshaller _shared;

    /**
     * @return the shared {@link Product} marshaller (never <code>null</code>)
     */
    public static ProductMarshaller get() {
        if ( _shared == null ) {
            _shared = new ProductMarshaller();
        }

        return _shared;
    }

    /**
     * Don't allow construction outside of this class.
     */
    private ProductMarshaller() {
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
    public Product toIot( final String json ) throws IotException {
        try {
            final JSONObject product = new JSONObject( json );

            // required
            final int id = product.getInt( Name.ID ); // must have an ID
            final int departmentId = product.getInt( Name.DEPARTMENT_ID ); // must have a department ID

            // optional
            final String description = ( product.has( Name.DESCRIPTION ) ? product.getString( Name.DESCRIPTION ) : "" );
            final String size = ( product.has( Name.SIZE ) ? product.getString( Name.SIZE ) : "" );
            final String name = ( product.has( Name.NAME ) ? product.getString( Name.NAME ) : "" );
            final String vendor = ( product.has( Name.VENDOR ) ? product.getString( Name.VENDOR ) : "" );
            final double buyPrice = ( product.has( Name.BUY_PRICE ) ? product.getDouble( Name.BUY_PRICE ) : -1 );
            final double msrp = ( product.has( Name.MSRP ) ? product.getDouble( Name.MSRP ) : -1 );

            return new Product( id, departmentId, description, msrp, buyPrice, size, name, vendor );
        } catch ( final Exception e ) {
            throw new IotException( e );
        }
    }

    @Override
    public String toJson( final Product product ) throws IotException {
        final Map< String, Object > map = new HashMap<>();
        map.put( Name.ID, product.getId() );
        map.put( Name.DEPARTMENT_ID, product.getDepartmentId() );
        map.put( Name.BUY_PRICE, product.getBuyPrice() );
        map.put( Name.MSRP, product.getMsrp() );

        if ( product.getDescription() != null ) {
            map.put( Name.DESCRIPTION, product.getDescription() );
        }

        if ( product.getSize() != null ) {
            map.put( Name.SIZE, product.getSize() );
        }

        if ( product.getName() != null ) {
            map.put( Name.NAME, product.getName() );
        }

        if ( product.getVendor() != null ) {
            map.put( Name.VENDOR, product.getVendor() );
        }

        final JSONObject jProduct = new JSONObject( map );
        return jProduct.toString();
    }

}
