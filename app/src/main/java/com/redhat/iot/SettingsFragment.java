package com.redhat.iot;

import android.app.Fragment;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.redhat.iot.IotConstants.Prefs;
import com.redhat.iot.R.array;
import com.redhat.iot.R.id;
import com.redhat.iot.R.layout;
import com.redhat.iot.R.string;
import com.redhat.iot.concurrent.CustomerCallback;
import com.redhat.iot.domain.Customer;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment
    implements OnCheckedChangeListener, OnItemSelectedListener {

    private TextView txt;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCheckedChanged( final CompoundButton buttonView,
                                  final boolean isChecked ) {

        // save preference
        final Editor editor = IotApp.getPrefs().edit();
        editor.putBoolean( Prefs.ENABLE_NOTIFICATIONS, isChecked );
        editor.apply();
    }

    private void setDataOnCreateView( final Customer customer ) {
        final String name =
            ( ( customer == null ) ? getActivity().getString( string.settings_user_not_logged_in ) : customer.getName() );

        this.txt.setText( name );
    }

    @Override
    public View onCreateView( final LayoutInflater inflater,
                              final ViewGroup container,
                              final Bundle savedInstanceState ) {
        final View view = inflater.inflate( layout.settings, container, false );

        // user name
        this.txt = ( TextView )view.findViewById( id.settingsCurrentUser );
        final int userId = IotApp.getCustomerId();

        DataProvider.get().findCustomer( userId, new CustomerCallback() {

            @Override
            public void onSuccess( final Customer[] results ) {
                final Customer customer = ( ( ( results == null ) || ( results.length != 1 ) ) ? null : results[ 0 ] );
                setDataOnCreateView( customer );
            }
        } );

        // enable notifications
        final CheckBox chk = ( CheckBox )view.findViewById( id.settingsEnableNotifications );
        final boolean checked = ( IotApp.getPrefs()
            .getBoolean( Prefs.ENABLE_NOTIFICATIONS, Prefs.DEFAULT_ENABLE_NOTIFICATIONS ) );
        chk.setChecked( checked );
        chk.setOnCheckedChangeListener( this );

        // notification interval
        final Spinner spinner = ( Spinner )view.findViewById( id.settingsNotificationInterval );
        spinner.setOnItemSelectedListener( this );

        final ArrayAdapter< CharSequence > adapter = new ArrayAdapter<>( getActivity(),
                                                                         layout.notification_interval,
                                                                         getResources().getTextArray( array
                                                                                                          .notification_intervals
                                                                                                    ) );
        spinner.setAdapter( adapter );

        // set selection to value of preference
        final int interval = ( ( IotApp.getPrefs()
            .getInt( Prefs.NOTIFICATION_INTERVAL, Prefs.DEFAULT_NOTIFICATION_INTERVAL ) ) / 60000 );
        int index;

        switch ( interval ) {
            case 2:
                index = 1;
                break;
            case 3:
                index = 2;
                break;
            case 4:
                index = 3;
                break;
            case 5:
                index = 4;
                break;
            case 10:
                index = 5;
                break;
            default:
                index = 0;
                break;
        }

        spinner.setSelection( index );

        return view;
    }

    @Override
    public void onItemSelected( final AdapterView< ? > parent,
                                final View view,
                                final int position,
                                final long id ) {
        int minutes;

        switch ( position ) {
            case 0:
                minutes = 1;
                break;
            case 1:
                minutes = 2;
                break;
            case 2:
                minutes = 3;
                break;
            case 3:
                minutes = 4;
                break;
            case 4:
                minutes = 5;
                break;
            case 5:
                minutes = 10;
                break;
            default:
                minutes = 1;
                break;
        }

        // save preference
        final Editor editor = IotApp.getPrefs().edit();
        editor.putInt( Prefs.NOTIFICATION_INTERVAL, ( minutes * 60000 ) );
        editor.apply();
    }

    @Override
    public void onNothingSelected( final AdapterView< ? > parent ) {
        // nothing to do
    }

}
