package com.redhat.iot.product;

import com.redhat.iot.IotConstants;
import com.redhat.iot.domain.Department;

import java.util.ArrayList;
import java.util.List;

/**
 * A provider for store {@link com.redhat.iot.domain.Department}s.
 */
public class DepartmentProvider {

    private static DepartmentProvider _shared;

    /**
     * @return the shared provider (never <code>null</code>)
     */
    public static DepartmentProvider get() {
        if ( _shared == null ) {
            _shared = new DepartmentProvider();
        }

        return _shared;
    }

    /**
     * Don't allow construction outside of this class.
     */
    private DepartmentProvider() {
        // nothing to do
    }

    /**
     * @param deptId the ID of the department being requested
     * @return the department or <code>null</code> if not found
     */
    public Department findDepartment( final long deptId ) {
        for ( final Department dept : IotConstants.TestData.DEPARTMENTS ) {
            if ( dept.getId() == deptId ) {
                return dept;
            }
        }

        return null;
    }

    /**
     * @return all store departments (never <code>null</code>)
     */
    public Department[] getDepartments() {
        return IotConstants.TestData.DEPARTMENTS;
    }

    /**
     * @param deptIds the IDs of the departments being requested
     * @return the store departments (never <code>null</code> but can be empty)
     */
    public Department[] getDepartments( final Long... deptIds ) {
        if ( ( deptIds == null ) || ( deptIds.length == 0 ) ) {
            return Department.NO_DEPARTMENTS;
        }

        final List< Department > departments = new ArrayList<>();

        for ( final Department dept : IotConstants.TestData.DEPARTMENTS ) {
            for ( final long id : deptIds ) {
                if ( id == dept.getId() ) {
                    departments.add( dept );
                }
            }
        }

        return departments.toArray( new Department[ departments.size() ] );
    }

}
