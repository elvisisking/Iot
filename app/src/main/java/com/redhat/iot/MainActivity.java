package com.redhat.iot;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.redhat.iot.order.OrdersFragment;
import com.redhat.iot.promotion.PromotionsFragment;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private Timer notifierTimer;

    @Override
    public void onBackPressed() {
        final DrawerLayout drawer = ( DrawerLayout )findViewById( R.id.drawer_layout );

        if ( drawer.isDrawerOpen( GravityCompat.START ) ) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate( final Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        final Toolbar toolbar = ( Toolbar )findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        final DrawerLayout drawer = ( DrawerLayout )findViewById( R.id.drawer_layout );
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.setDrawerListener( toggle );
        toggle.syncState();

        final NavigationView navigationView = ( NavigationView )findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );

        // set home as first fragment
        final FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
            .replace( R.id.content_frame, new HomeFragment() )
            .commit();
        navigationView.setCheckedItem( R.id.nav_home );

        // register to receive preference changes
        IotApp.getPrefs().registerOnSharedPreferenceChangeListener( this );
    }

    @Override
    public boolean onCreateOptionsMenu( final Menu menu ) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
    }

    @Override
    public boolean onNavigationItemSelected( final MenuItem item ) {
        // handle navigation drawer clicks
        final int id = item.getItemId();
        Fragment fragment = null;
        int titleId = -1;

        if ( id == R.id.nav_login ) {
            fragment = new LoginFragment();
            titleId = R.string.title_login_fragment;
        } else if ( id == R.id.nav_home ) {
            fragment = new HomeFragment();
        } else if ( id == R.id.nav_deals ) {
            fragment = new PromotionsFragment();
            titleId = R.string.title_deals_fragment;
        } else if ( id == R.id.nav_purchase_history ) {
            fragment = new OrdersFragment();
            titleId = R.string.title_orders_fragment;
        } else if ( id == R.id.nav_billing ) {
            fragment = new BillingFragment();
            titleId = R.string.title_billing_fragment;
        } else if ( id == R.id.nav_settings ) {
            fragment = new SettingsFragment();
            titleId = R.string.title_settings_fragment;
        } else if ( id == R.id.nav_contact ) {
            fragment = new ContactFragment();
            titleId = R.string.title_contact_fragment;
        } else if ( id == R.id.nav_about ) {
            fragment = new AboutFragment();
            titleId = R.string.title_about_fragment;
        }

        if ( fragment != null ) {
            final FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace( R.id.content_frame, fragment ).commit();

            final DrawerLayout drawer = ( DrawerLayout )findViewById( R.id.drawer_layout );
            drawer.closeDrawer( GravityCompat.START );

            String title = getString( R.string.app_name );

            if ( titleId != -1 ) {
                title += " - " + getString( titleId );
            }

            getSupportActionBar().setTitle( title );
        } else {
            Log.e( "MainActivity", "ID " + id + " does not create a fragment" );
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected( final MenuItem item ) {
        int id = item.getItemId();

        // log user out
        if ( id == R.id.action_sign_out ) {
            IotApp.setUserId( DataProvider.UNKNOWN_USER );
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    protected void onPause() {
        Log.d( IotConstants.LOG_TAG, "onPause" );
        stopNotificationThread();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Log.d( IotConstants.LOG_TAG, "onRestart" );
        super.onRestart();
        startNotificationThread();
    }

    @Override
    public void onSharedPreferenceChanged( final SharedPreferences sharedPreferences,
                                           final String key ) {
        if ( IotConstants.Prefs.ENABLE_NOTIFICATIONS.equals( key ) ) {
            Log.d( IotConstants.LOG_TAG, ( "Enable notifications preference changed" ) );
            final boolean enable = IotApp.getPrefs().getBoolean( IotConstants.Prefs.ENABLE_NOTIFICATIONS,
                                                                 IotConstants.Prefs.DEFAULT_ENABLE_NOTIFICATIONS );

            if ( enable ) {
                startNotificationThread();
            } else {
                stopNotificationThread();
            }
        } else if ( IotConstants.Prefs.NOTIFICATION_INTERVAL.equals( key ) ) {
            Log.d( IotConstants.LOG_TAG, ( "Notifications interval preference changed" ) );
            stopNotificationThread();
            startNotificationThread();
        }
    }

    @Override
    protected void onStart() {
        Log.d( IotConstants.LOG_TAG, "onStart" );
        super.onStart();

        // make sure we have a user logged in at startup
        final int userId = IotApp.getUserId();

        if ( userId == DataProvider.UNKNOWN_USER ) {
            IotApp.setUserId( IotConstants.FIRST_CUST_ID );
        }

        startNotificationThread();
    }

    @Override
    protected void onStop() {
        Log.d( IotConstants.LOG_TAG, "onStop" );
        stopNotificationThread();
        super.onStop();
    }

    private void startNotificationThread() {
        stopNotificationThread();
        Log.d( IotConstants.LOG_TAG, "starting notification timer" );
        final int interval = IotApp.getPrefs().getInt( IotConstants.Prefs.NOTIFICATION_INTERVAL,
                                                       IotConstants.Prefs.DEFAULT_NOTIFICATION_INTERVAL );
        this.notifierTimer = new Timer( true );
        this.notifierTimer.schedule( new NotificationTask( IotApp.getContext() ), 0, interval );
    }

    private void stopNotificationThread() {
        if ( this.notifierTimer != null ) {
            Log.d( IotConstants.LOG_TAG, "stopping notification timer" );
            this.notifierTimer.cancel();
        }
    }

    /**
     * A handler that sends notifications to the user.
     */
    static class NotificationHandler extends Handler {

        private final Context context;
        private final Notifier notifier;

        public NotificationHandler( final Context context ) {
            this.context = context;
            this.notifier = new Notifier( context );
        }

        @Override
        public void handleMessage( final Message msg ) {
            final String notification = DataProvider.get().getNotification();

            if ( ( notification != null ) && !notification.isEmpty() ) {
                Log.d( IotConstants.LOG_TAG, "Sending notification: " + notification );
                sendNotification( notification );
            }
        }

        private void sendNotification( final String msg ) {
            this.notifier.sendNotification( this.context.getString( R.string.app_name ), msg );
        }

    }

    /**
     * A task to check for notifications.
     */
    class NotificationTask extends TimerTask {

        private final NotificationHandler handler;

        public NotificationTask( final Context context ) {
            this.handler = new NotificationHandler( context );
        }

        @Override
        public void run() {
            this.handler.sendEmptyMessage( 0 );
        }

    }

}
