package com.redhat.iot;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.redhat.iot.domain.Product;
import com.redhat.iot.domain.Promotion;

import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A class that builds and sends notifications to the customer running the app.
 */
class Notifier {

    private static final String GROUP = IotApp.getContext().getString( R.string.app_name );

    private final AtomicInteger id = new AtomicInteger();

    /**
     * @param notification the notification being sent (cannot be empty)
     */
    public void sendNotification( final DataProvider.Notification notification ) {
        // configure
        final NotificationCompat.Builder builder = new NotificationCompat.Builder( IotApp.getContext() );
        builder.setSmallIcon( notification.getProduct().getImageId() );
        builder.setContentTitle( IotApp.getContext().getString( R.string.notification_title, notification.getDepartment() ) );
        builder.setContentText( IotApp.getContext().getString( R.string.notification_message,
                                                               notification.getProduct().getName(),
                                                               notification.getPromotion().getDiscount() ) );
        builder.setGroup( GROUP );

        // create
        final Notification alert = builder.build();

        // send
        final NotificationManager mgr = ( NotificationManager )IotApp.getContext().getSystemService( Context.NOTIFICATION_SERVICE );
        final int alertId = this.id.getAndIncrement();
        Log.d( IotConstants.LOG_TAG,
               MessageFormat.format( "Sending notification {0} to customer {1} for promotion {2}",
                                     alertId,
                                     notification.getCustomerId(),
                                     notification.getPromotion().getId() ) );
        mgr.notify( alertId, alert );
    }

}
