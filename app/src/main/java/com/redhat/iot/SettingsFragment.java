package com.redhat.iot;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.redhat.iot.domain.Customer;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment
    implements CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCheckedChanged( final CompoundButton buttonView,
                                  final boolean isChecked ) {

        // save preference
        final SharedPreferences.Editor editor = IotApp.getPrefs().edit();
        editor.putBoolean( IotConstants.Prefs.ENABLE_NOTIFICATIONS, isChecked );
        editor.apply();
    }

    @Override
    public View onCreateView( final LayoutInflater inflater,
                              final ViewGroup container,
                              final Bundle savedInstanceState ) {
        final View view = inflater.inflate( R.layout.settings, container, false );

        {// user name
            final TextView txt = ( TextView )view.findViewById( R.id.settingsTextView );
            String name = null;
            final int userId = IotApp.getUserId();

            if ( userId != Customer.UNKNOWN_USER ) {
                name = DataProvider.get().getCustomerName( userId );
            }

            if ( name == null ) {
                name = getActivity().getString( R.string.settings_user_not_logged_in );
            }

            txt.setText( getActivity().getString( R.string.settings_user, name ) );
        }

        {// enable notifications
            final CheckBox chk = ( CheckBox )view.findViewById( R.id.settingsEnableNotifications );
            final boolean checked = ( IotApp.getPrefs()
                .getBoolean( IotConstants.Prefs.ENABLE_NOTIFICATIONS, IotConstants.Prefs.DEFAULT_ENABLE_NOTIFICATIONS ) );
            chk.setChecked( checked );
            chk.setOnCheckedChangeListener( this );
        }

        {// notification interval
            final Spinner spinner = ( Spinner )view.findViewById( R.id.settingsNotificationInterval );
            spinner.setOnItemSelectedListener( this );

            final ArrayAdapter< CharSequence > adapter = new ArrayAdapter<>( getActivity(), R.layout.notification_interval, getResources().getTextArray( R.array.notification_intervals ) );
            spinner.setAdapter( adapter );

            // set selection to value of preference
            final int interval = ( ( IotApp.getPrefs()
                .getInt( IotConstants.Prefs.NOTIFICATION_INTERVAL, IotConstants.Prefs.DEFAULT_NOTIFICATION_INTERVAL ) ) / 60000 );
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
        }

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
        final SharedPreferences.Editor editor = IotApp.getPrefs().edit();
        editor.putInt( IotConstants.Prefs.NOTIFICATION_INTERVAL, ( minutes * 60000 ) );
        editor.apply();
    }

    @Override
    public void onNothingSelected( final AdapterView< ? > parent ) {
        // nothing to do
    }

}
