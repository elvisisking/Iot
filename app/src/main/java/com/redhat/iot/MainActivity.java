package com.redhat.iot;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.redhat.iot.domain.Customer;
import com.redhat.iot.order.OrdersFragment;
import com.redhat.iot.promotion.PromotionsFragment;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final int ICON_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final Integer[][] DRAWER_CONFIG = new Integer[][]{
        new Integer[]{ R.drawable.ic_user, R.string.title_login_fragment },
        new Integer[]{ R.drawable.ic_home, R.string.title_home_fragment },
        new Integer[]{ R.drawable.ic_price_tags, R.string.title_deals_fragment },
        new Integer[]{ R.drawable.ic_coin_dollar, R.string.title_orders_fragment },
        new Integer[]{ R.drawable.ic_credit_card, R.string.title_billing_fragment },
        new Integer[]{ R.drawable.ic_cog, R.string.title_settings_fragment },
        new Integer[]{ R.drawable.ic_mail, R.string.title_contact_fragment },
        new Integer[]{ R.drawable.ic_info, R.string.title_about_fragment },
    };

    static final int LOGIN_SCREEN_INDEX = 1;
    static final int HOME_SCREEN_INDEX = 2;
    static final int PROMOTIOHS_SCREEN_INDEX = 3;
    static final int ORDERS_SCREEN_INDEX = 4;
    static final int BILLING_SCREEN_INDEX = 5;
    static final int SETTINGS_SCREEN_INDEX = 6;
    static final int CONTACT_SCREEN_INDEX = 7;
    static final int ABOUT_SCREEN_INDEX = 8;

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
        getSupportActionBar().setDisplayShowHomeEnabled( true );

        final RecyclerView drawerRecyclerView = ( RecyclerView )findViewById( R.id.drawer_view );
        drawerRecyclerView.setHasFixedSize( true );

        final RecyclerView.Adapter drawerAdapter = new DrawerAdapter( this );
        drawerRecyclerView.setAdapter( drawerAdapter );

        final RecyclerView.LayoutManager drawerLayoutMgr = new LinearLayoutManager( this );
        drawerRecyclerView.setLayoutManager( drawerLayoutMgr );

        final DrawerLayout drawerLayout = ( DrawerLayout )findViewById( R.id.drawer_layout );
        final ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle( this,
                                                                              drawerLayout,
                                                                              toolbar,
                                                                              R.string.navigation_drawer_open,
                                                                              R.string.navigation_drawer_close );
        drawerLayout.addDrawerListener( drawerToggle );
        drawerToggle.syncState();
//
//        final NavigationView navigationView = ( NavigationView )findViewById( R.id.nav_view );
//        navigationView.setNavigationItemSelectedListener( this );

        // set home as first fragment
        final FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
            .replace( R.id.content_frame, new HomeFragment() )
            .commit();

//        this.drawerLayoutMgr.scrollToPosition( 2 );
//        this.drawerRecyclerView.setCheckedItem( R.id.nav_home );

        // register to receive preference changes
        IotApp.getPrefs().registerOnSharedPreferenceChangeListener( this );
    }

    @Override
    public boolean onCreateOptionsMenu( final Menu menu ) {
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
    }

    void showScreen( final int index ) {
        Fragment fragment;
        int titleId = -1;

        switch ( index ) {
            case LOGIN_SCREEN_INDEX:
                fragment = new LoginFragment();
                titleId = R.string.title_login_fragment;
                break;
            case HOME_SCREEN_INDEX:
                fragment = new HomeFragment();
                break;
            case PROMOTIOHS_SCREEN_INDEX:
                fragment = new PromotionsFragment();
                titleId = R.string.title_deals_fragment;
                break;
            case ORDERS_SCREEN_INDEX:
                fragment = new OrdersFragment();
                titleId = R.string.title_orders_fragment;
                break;
            case BILLING_SCREEN_INDEX:
                fragment = new BillingFragment();
                titleId = R.string.title_billing_fragment;
                break;
            case SETTINGS_SCREEN_INDEX:
                fragment = new SettingsFragment();
                titleId = R.string.title_settings_fragment;
                break;
            case CONTACT_SCREEN_INDEX:
                fragment = new ContactFragment();
                titleId = R.string.title_contact_fragment;
                break;
            case ABOUT_SCREEN_INDEX:
                fragment = new AboutFragment();
                titleId = R.string.title_about_fragment;
                break;
            default:
                Log.e( IotConstants.LOG_TAG, "index " + index + " does not create a fragment" );
                fragment = new HomeFragment();
                break;
        }

        final FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace( R.id.content_frame, fragment ).commit();

        final DrawerLayout drawer = ( DrawerLayout )findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );

        if ( getSupportActionBar() != null ) {
            if ( titleId == -1 ) {
                getSupportActionBar().setSubtitle( "" );
            } else {
                getSupportActionBar().setSubtitle( getString( titleId ) );
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected( final MenuItem item ) {
        int id = item.getItemId();

        // log user out
        if ( id == R.id.action_sign_out ) {
            IotApp.setUserId( Customer.UNKNOWN_USER );
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

        // start notifications if pref is set
        onSharedPreferenceChanged( IotApp.getPrefs(), IotConstants.Prefs.ENABLE_NOTIFICATIONS );
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

        if ( userId == Customer.UNKNOWN_USER ) {
            IotApp.setUserId( IotConstants.FIRST_CUST_ID );
        }

        // start notifications if pref is set
        onSharedPreferenceChanged( IotApp.getPrefs(), IotConstants.Prefs.ENABLE_NOTIFICATIONS );
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

    class DrawerAdapter extends RecyclerView.Adapter< DrawerAdapter.Holder > {

        private final Context context;
        private final LayoutInflater inflater;

        DrawerAdapter( final Context c ) {
            this.context = c;
            this.inflater = LayoutInflater.from( this.context );
        }

        @Override
        public int getItemCount() {
            return ( DRAWER_CONFIG.length + 1 );
        }

        @Override
        public long getItemId( final int position ) {
            return position;
        }

        @Override
        public int getItemViewType( final int position ) {
            return ( ( position == 0 ) ? 0 : 1 );
        }

        @Override
        public void onBindViewHolder( final Holder holder,
                                      final int position ) {
            if ( position != 0 ) {
                holder.ivIcon.setImageResource( DRAWER_CONFIG[ position - 1 ][ ICON_INDEX ] );
                holder.tvName.setText( DRAWER_CONFIG[ position - 1 ][ NAME_INDEX ] );
            }
        }

        @Override
        public Holder onCreateViewHolder( final ViewGroup parent,
                                          final int viewType ) {
            // item
            if ( viewType == 1 ) {
                final View view = this.inflater.inflate( R.layout.drawer_item, parent, false );
                return new Holder( view, viewType );
            }

            // header
            final View view = this.inflater.inflate( R.layout.nav_header_main, parent, false );
            return new Holder( view, viewType );
        }

        class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private final ImageView ivIcon;
            private final TextView tvName;

            public Holder( final View itemView,
                           final int itemType ) {
                super( itemView );

                if ( itemType == 1 ) {
                    itemView.setClickable( true );
                    itemView.setOnClickListener( this );

                    this.ivIcon = ( ImageView )itemView.findViewById( R.id.drawerItemIcon );
                    this.tvName = ( TextView )itemView.findViewById( R.id.drawerItemName );
                } else {
                    this.ivIcon = null;
                    this.tvName = null;
                }
            }

            @Override
            public void onClick( final View view ) {
                showScreen( getAdapterPosition() );
            }

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
