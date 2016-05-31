package com.redhat.iot;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.redhat.iot.domain.Customer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The IoT Mobile App.
 */
public class IotApp extends Application {

    private static final int NUM_IMAGES = 50;
    private static final AtomicInteger IMAGE_COUNT = new AtomicInteger( 1 );
    private static final Map< Long, Integer > IMAGE_MAP = new HashMap<>();

    private static Context _context;

    /**
     * @return the app context (never <code>null</code>)
     */
    public static Context getContext() {
        return _context;
    }

    /**
     * @param o the object whose image resource identifier is being requested (cannot be <code>null</code>)
     * @return the image resource ID
     */
    public static int getImageId( final Object o ) {
        final long id = o.hashCode();
        Integer imageId = IMAGE_MAP.get( id );

        if ( imageId == null ) {
            final Resources resources = getContext().getResources();
            imageId = resources.getIdentifier( "com.redhat.iot:drawable/item_" + IMAGE_COUNT.get(), null, null );
            IMAGE_MAP.put( id, imageId );

            if ( IMAGE_COUNT.intValue() > NUM_IMAGES ) {
                IMAGE_COUNT.set( 1 );
            } else {
                IMAGE_COUNT.incrementAndGet();
            }
        }

        return imageId;
    }

    /**
     * @return the app preferences (never <code>null</code>)
     */
    public static SharedPreferences getPrefs() {
        return _context.getSharedPreferences( IotConstants.Prefs.PREFS_NAME, 0 );
    }

    /**
     * @return the ID of the logged in user or {@link Customer#UNKNOWN_USER} if no one is logged in
     */
    public static int getUserId() {
        final SharedPreferences prefs = IotApp.getPrefs();
        return prefs.getInt( IotConstants.Prefs.CUSTOMER_ID, Customer.UNKNOWN_USER );
    }

    /**
     * @param userId the ID of the logged in user or {@link Customer#UNKNOWN_USER} if no one is logged in
     */
    public static void setUserId(final int userId ) {
        final SharedPreferences.Editor editor = getPrefs().edit();
        editor.putInt( IotConstants.Prefs.CUSTOMER_ID, userId );
        editor.apply();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _context = this;
    }

}
