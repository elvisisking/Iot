package com.redhat.iot;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.redhat.iot.domain.Customer;

/**
 * A login screen.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private Button btnSignIn;
    private TextView txtEmail;
    private TextView txtPswd;

    public LoginFragment() {
        // Required empty public constructor
    }

    void enableSignIn( final boolean enable ) {
        if ( this.btnSignIn.isEnabled() != enable ) {
            this.btnSignIn.setEnabled( enable );
        }
    }

    @Override
    public void onClick( final View btn ) {
        final String email = this.txtEmail.getText().toString();

        // find customer
        final Customer customer = DataProvider.get().getCustomer( email );

        if ( customer == null ) {
            Toast.makeText( getActivity(),
                            getActivity().getString( R.string.login_unknown_user ),
                            Toast.LENGTH_SHORT ).show();
        } else {
            // check password
            final String pswd = customer.getPswd();
            final String text = this.txtPswd.getText().toString();
            boolean success = false;

            if ( ( pswd == null ) || pswd.isEmpty() ) {
                if ( ( text == null ) || text.isEmpty() ) {
                    success = true;
                }
            } else if ( pswd.equals( text ) ) {
                success = true;
            }

            if ( success ) {
                // save customer ID to prefs
                final int userId = customer.getId();
                final SharedPreferences prefs = IotApp.getPrefs();
                final SharedPreferences.Editor editor = prefs.edit();
                editor.putInt( IotConstants.CUSTOMER_ID, userId );
                editor.apply();

                Toast.makeText( getActivity(),
                                getActivity().getString( R.string.login_success ),
                                Toast.LENGTH_SHORT ).show();

            } else {
                // incorrect password
                Toast.makeText( getActivity(),
                                getActivity().getString( R.string.login_failed ),
                                Toast.LENGTH_SHORT ).show();
            }
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

        {//key listener for the email textfield
            this.txtEmail = ( TextView )view.findViewById( R.id.loginEmail );
            this.txtEmail.addTextChangedListener( new TextWatcher() {

                public void afterTextChanged( final Editable s ) {
                    final boolean enable = ( LoginFragment.this.txtEmail.getText().length() != 0 );
                    enableSignIn( enable );
                }

                public void beforeTextChanged( final CharSequence s,
                                               final int start,
                                               final int count,
                                               final int after ) {
                    // nothing to do
                }

                public void onTextChanged( final CharSequence s,
                                           final int start,
                                           final int before,
                                           final int count ) {
                    // nothing to do
                }
            } );
        }

        {// password textfield
            this.txtPswd = ( TextView )view.findViewById( R.id.loginPswd );
        }

        return view;
    }

}
