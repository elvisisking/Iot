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
    private TextView txtUserId;

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

        {//key listener for the user ID textfield
            this.txtUserId = ( TextView )view.findViewById( R.id.loginUserId );
            this.txtUserId.addTextChangedListener( new TextWatcher() {

                public void afterTextChanged( final Editable s ) {
                    final boolean enable = ( LoginFragment.this.txtUserId.getText().length() != 0 );
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

        return view;
    }

}
