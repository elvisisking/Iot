package com.redhat.iot.domain;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represents a store department.
 */
public class Department {

    /**
     * An empty collection of departments.
     */
    public static final Department[] NO_DEPARTMENTS = new Department[ 0 ];

    private final String description;
    private final long id;
    private final String name;

    /**
     * @param id          the unique ID of the department
     * @param name        the department name (cannot be empty)
     * @param description the department description (cannot be empty)
     */
    public Department( final long id,
                       final String name,
                       final String description ) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * @param json a JSON representation of a department (cannot be empty)
     * @throws JSONException if there is a problem parsing the JSON
     */
    public Department( final String json ) throws JSONException {
        final JSONObject dept = new JSONObject( json );

        // required
        this.id = dept.getLong( "departmentCode" ); // must have an ID
        this.name = dept.getString( "departmentName" ); // must have a name

        // optional
        this.description = ( dept.has( "departmentDescription" ) ? dept.getString( "departmentDescription" ) : "" );

        // TODO need dimension
    }

    /**
     * @return the department ID
     */
    public long getId() {
        return this.id;
    }

    /**
     * @return the department description (never empty)
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @return the department name (never empty)
     */
    public String getName() {
        return this.name;
    }

}
