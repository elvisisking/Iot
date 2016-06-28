package com.redhat.iot.json;

import com.redhat.iot.IotException;
import com.redhat.iot.domain.Department;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Converts to/from a JSON string and a {@link Department} object.
 */
public class DepartmentMarshaller implements IotMarshaller< Department > {

    /**
     * The JSON names that may have mappings.
     */
    public interface Name {

        String DESCRIPTION = "departmentDescription";
        String ID = "departmentCode";
        String NAME = "departmentName";

    }

    private static DepartmentMarshaller _shared;

    /**
     * @return the shared {@link Department} marshaller (never <code>null</code>)
     */
    public static DepartmentMarshaller get() {
        if ( _shared == null ) {
            _shared = new DepartmentMarshaller();
        }

        return _shared;
    }

    /**
     * Don't allow construction outside of this class.
     */
    private DepartmentMarshaller() {
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
    public Department toIot( final String json ) throws IotException {
        try {
            final JSONObject dept = new JSONObject( json );

            // required
            final long id = dept.getLong( Name.ID );
            final String name = dept.getString( Name.NAME );

            // optional
            final String description = ( dept.has( Name.DESCRIPTION ) ? dept.getString( Name.DESCRIPTION ) : "" );

            // TODO what about dimension

            return new Department( id, name, description );
        } catch ( final Exception e ) {
            throw new IotException( e );
        }
    }

    @Override
    public String toJson( final Department department ) throws IotException {
        final Map< String, Object > map = new HashMap<>();
        map.put( Name.ID, department.getId() );
        map.put( Name.NAME, department.getName() );

        if ( department.getDescription() != null ) {
            map.put( Name.DESCRIPTION, department.getDescription() );
        }

        final JSONObject jDept = new JSONObject( map );
        return jDept.toString();
    }

}
