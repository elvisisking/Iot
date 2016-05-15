package com.redhat.iot;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A class that builds and sends notifications to the customer running the app.
 */
public class Notifier {

    private AtomicInteger id = new AtomicInteger();
    private final Context context;

    /**
     * @param context the notifier context (cannot be <code>null</code>)
     */
    public Notifier( final Context context ) {
        this.context = context;
    }

    /**
     * @param title the title of the notification (cannot be empty)
     * @param content the notification message (cannot be empty)
     */
    public void sendNotification( final String title,
                                  final String content ) {
        // configure
        final NotificationCompat.Builder builder = new NotificationCompat.Builder( this.context );
        builder.setSmallIcon( R.drawable.ic_info );
        builder.setContentTitle( title );
        builder.setContentText( content );

        // create
        final Notification notification = builder.build();

        // send
        final NotificationManager mgr = ( NotificationManager )this.context.getSystemService( Context.NOTIFICATION_SERVICE );
        mgr.notify( this.id.getAndIncrement(), notification );
    }

}
