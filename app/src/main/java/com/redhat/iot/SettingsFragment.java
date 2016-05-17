package com.redhat.iot;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.redhat.iot.domain.Customer;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView( final LayoutInflater inflater,
                              final ViewGroup container,
                              final Bundle savedInstanceState ) {
        final View view = inflater.inflate( R.layout.settings, container, false );
        final TextView txt = ( TextView )view.findViewById( R.id.settingsTextView );

        String name = null;
        final int userId = IotApp.getUserId();

        if ( userId != DataProvider.UNKNOWN_USER ) {
            for ( final Customer user : DataProvider.get().getCustomersFromJsonFile() ) {
                if ( user.getId() == userId ) {
                    name = user.getName();
                    break;
                }
            }
        }

        if ( name == null ) {
            name = getActivity().getString( R.string.settings_user_not_logged_in );
        }

        txt.setText( getActivity().getString( R.string.settings_user, name ) );
        return view;
    }

}
