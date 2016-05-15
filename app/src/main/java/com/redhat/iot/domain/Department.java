package com.redhat.iot.domain;

import com.redhat.iot.R;

/**
 * Represents a store department.
 */
public class Department {

    /**
     * An empty collection of departments.
     */
    public static final Department[] NO_DEPARTMENTS = new Department[0];

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
