package com.redhat.iot;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.redhat.iot.domain.Customer;

/**
 * A login screen.
 */
public class LoginFragment extends Fragment implements OnClickListener, OnSharedPreferenceChangeListener {

    private Button btnSignIn;
    private TextView txtCurrUser;
    private EditText txtUserId;
    private TextView txtUserName;

    public LoginFragment() {
        // Required empty public constructor
    }

    private int getCustomerId() {
        return IotApp.getPrefs().getInt( IotConstants.Prefs.CUSTOMER_ID, Customer.UNKNOWN_USER );
    }

    @Override
    public void onClick( final View btn ) {
        final String idTxt = this.txtUserId.getText().toString();
        final int userId = Integer.parseInt( idTxt );

        // find customer
        final Customer customer = DataProvider.get().getCustomer( userId );

        if ( customer == null ) {
            Toast.makeText( getActivity(),
                            getActivity().getString( R.string.login_unknown_user ),
                            Toast.LENGTH_SHORT ).show();
        } else {
            // save customer ID to prefs
            final SharedPreferences prefs = IotApp.getPrefs();
            final SharedPreferences.Editor editor = prefs.edit();
            editor.putInt( IotConstants.Prefs.CUSTOMER_ID, userId );
            editor.apply();

            Toast.makeText( getActivity(),
                            getActivity().getString( R.string.login_success ),
                            Toast.LENGTH_SHORT ).show();

        }
    }

    @Override
    public View onCreateView( final LayoutInflater inflater,
                              final ViewGroup container,
                              final Bundle savedInstanceState ) {
        final View view = inflater.inflate( R.layout.login, container, false );

        {// click listener for the sign in button
            this.btnSignIn = ( Button )view.findViewById( R.id.loginSignIn );
            this.btnSignIn.setOnClickListener( this );
        }

        // get reference to user name text view
        this.txtCurrUser = ( TextView )view.findViewById( R.id.loginCurrentUser );

        // get reference to user name text view
        this.txtUserName = ( TextView )view.findViewById( R.id.loginUserName );

        {//setup key listener for the user ID textfield and set to current user's ID if necessary
            this.txtUserId = ( EditText )view.findViewById( R.id.loginUserId );
            this.txtUserId.addTextChangedListener( new TextWatcher() {

                @Override
                public void afterTextChanged( final Editable s ) {
                    userIdChanged();
                }

                @Override
                public void beforeTextChanged( final CharSequence s,
                                               final int start,
                                               final int count,
                                               final int after ) {
                    // nothing to do
                }

                @Override
                public void onTextChanged( final CharSequence s,
                                           final int start,
                                           final int before,
                                           final int count ) {
                    // nothing to do
                }

            } );

            final int loggedInUser = getCustomerId();

            if ( loggedInUser == Customer.UNKNOWN_USER ) {
                this.txtCurrUser.setText( R.string.login_not_logged_in );
            } else {
                this.txtCurrUser.setText( DataProvider.get().getCustomerName( loggedInUser ) );
                this.txtUserId.setText( String.format( "%d", loggedInUser ) );
                this.txtUserId.setSelection( this.txtUserId.getText().length() );
            }
        }

        // register to receive preference changes
        IotApp.getPrefs().registerOnSharedPreferenceChangeListener( this );

        return view;
    }

    @Override
    public void onSharedPreferenceChanged( final SharedPreferences sharedPreferences,
                                           final String key ) {
        if ( IotConstants.Prefs.CUSTOMER_ID.equals( key ) ) {
            final int custId = getCustomerId();

            if ( custId == Customer.UNKNOWN_USER ) {
                this.txtCurrUser.setText( R.string.login_not_logged_in );
            } else {
                this.txtCurrUser.setText( DataProvider.get().getCustomerName( custId ) );
            }
        }
    }

    private void userIdChanged() {
        final String idString = this.txtUserId.getText().toString();
        boolean enable = !idString.isEmpty();

        if ( enable ) {
            final int userId = Integer.parseInt( idString );
            final String name = DataProvider.get().getCustomerName( userId );
            this.txtUserName.setText( ( name == null ) ? "" : name );
            enable = ( name != null );
        }

        if ( this.btnSignIn.isEnabled() != enable ) {
            this.btnSignIn.setEnabled( enable );
        }
    }

}
