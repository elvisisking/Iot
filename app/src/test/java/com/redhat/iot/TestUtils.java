package com.redhat.iot;

import com.redhat.iot.json.JsonUtils;

import java.util.Calendar;

/**
 * Utilities used by IoT test classes.
 */
public final class TestUtils {

    /**
     * @param calendar the date being surrounded by double quotes (cannot be empty)
     * @return the quoted string (never empty)
     */
    public static String quote( final Calendar calendar ) {
        return quote( JsonUtils.toJson( calendar ) );
    }

    /**
     * @param text the text begin surrounded by double quotes (cannot be empty)
     * @return the quoted string (never empty)
     */
    public static String quote( final String text ) {
        return ( '"' + text + '"' );
    }

    /**
     * Don't allow construction from outside this class.
     */
    private TestUtils() {
        // nothing to do
    }

}
