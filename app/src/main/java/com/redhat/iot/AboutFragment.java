package com.redhat.iot;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.redhat.iot.R.id;
import com.redhat.iot.R.layout;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView( final LayoutInflater inflater,
                              final ViewGroup container,
                              final Bundle savedInstanceState ) {
        final View view = inflater.inflate( layout.about, container, false );

        // version and build number
        final TextView tv = ( TextView )view.findViewById( id.tv_about_build_info );
        tv.setText( IotApp.getAppVersion() );

        return view;
    }

}
