package com.redhat.iot.json;

import com.redhat.iot.domain.Department;
import com.redhat.iot.TestUtils;
import com.redhat.iot.json.DepartmentMarshaller.Name;

import org.json.JSONObject;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * A test class for the {@link DepartmentMarshaller} class.
 */
public final class DepartmentMarshallerTest {

    private static final String DESCRIPTION = "Womans clothing and assessories.";
    private static final long ID = 1000;
    private static final String NAME = "Womans";

    private static final Department IOT = new Department( ID, NAME, DESCRIPTION );
    private static final String JSON = "{ "
        + TestUtils.quote( Name.ID ) + " : " + ID + ", "
        + TestUtils.quote( Name.NAME ) + " : " + TestUtils.quote( NAME ) + ", "
        + TestUtils.quote( Name.DESCRIPTION ) + " : " + TestUtils.quote( DESCRIPTION )
        + " }";

    private static final DepartmentMarshaller MARSHALLER = DepartmentMarshaller.get();

    @Test
    public void shouldConvertToIot() throws Exception {
        final Department dept = MARSHALLER.toIot( JSON );
        assertThat( dept, is( notNullValue() ) );
        assertThat( dept.getId(), is( ID ) );
        assertThat( dept.getName(), is( NAME ) );
        assertThat( dept.getDescription(), is( DESCRIPTION ) );
    }

    @Test
    public void shouldConvertToJson() throws Exception {
        final String json = MARSHALLER.toJson( IOT );
        final JSONObject jDept = new JSONObject( json );

        assertThat( jDept.has( Name.ID ), is( true ) );
        assertThat( jDept.getLong( Name.ID ), is( ID ) );

        assertThat( jDept.has( Name.NAME ), is( true ) );
        assertThat( jDept.getString( Name.NAME ), is( NAME ) );

        assertThat( jDept.has( Name.DESCRIPTION ), is( true ) );
        assertThat( jDept.getString( Name.DESCRIPTION ), is( DESCRIPTION ) );
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
