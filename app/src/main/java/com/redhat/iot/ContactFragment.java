package com.redhat.iot;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.redhat.iot.R.id;
import com.redhat.iot.R.layout;
import com.redhat.iot.R.string;

import java.util.List;

/**
 * The contact Johl's screen.
 */
public class ContactFragment extends Fragment {

    public ContactFragment() {
        // Required empty public constructor
    }

    private void makeCall() {
        final Intent intent = new Intent( Intent.ACTION_DIAL,
                                          Uri.parse( "tel:(800)12345678" ) );
        startActivity( intent );
    }

    @Override
    public View onCreateView( final LayoutInflater inflater,
                              final ViewGroup container,
                              final Bundle savedInstanceState ) {
        final View view = inflater.inflate( layout.contact, container, false );
        final Button btnEmail = ( Button )view.findViewById( id.btn_contact_by_email );
        btnEmail.setOnClickListener( new OnClickListener() {

            @Override
            public void onClick( final View v ) {
                sendMail();
            }
        } );

        final Button btnPhone = ( Button )view.findViewById( id.btn_contact_by_phone );
        btnPhone.setOnClickListener( new OnClickListener() {

            @Override
            public void onClick( final View v ) {
                makeCall();
            }
        } );

        return view;
    }

    private void sendMail() {
        final Intent intent = new Intent( Intent.ACTION_SEND );
        intent.setType( "message/rfc822" );
        intent.putExtra( Intent.EXTRA_EMAIL, new String[]{ getString( string.contact_email_to ) } );
        intent.putExtra( Intent.EXTRA_SUBJECT, getString( string.contact_email_subject ) );
        intent.putExtra( Intent.EXTRA_TEXT, getString( string.contact_email_body ) );

        try {
            final PackageManager pkManager = getActivity().getPackageManager();
            final List< ResolveInfo > activities = pkManager.queryIntentActivities( intent, 0 );

            if ( activities.size() == 0 ) {
                IotApp.logDebug( ContactFragment.class, "sendMail", getString( string.contact_no_email_client ) );
                final View view = getActivity().findViewById( id.contact_fragment );
                final Snackbar snackbar =
                    Snackbar.make( view, getString( string.contact_no_email_client ), Snackbar.LENGTH_LONG );
                snackbar.show();
            } else {
                final Intent chooser = Intent.createChooser( intent, getString( string.contact_intent_title ) );
                startActivity( chooser );
            }
        } catch ( final Exception e ) {
            IotApp.logError( ContactFragment.class, "sendMail", null, e );
            final View view = getActivity().findViewById( id.contact_fragment );
            final Snackbar snackbar = Snackbar.make( view, getString( string.contact_email_error ), Snackbar.LENGTH_LONG );
            snackbar.show();
        }
    }

}
