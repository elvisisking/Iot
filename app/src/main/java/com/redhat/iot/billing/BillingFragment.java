package com.redhat.iot.billing;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.redhat.iot.R;

/**
 * A billing screen.
 */
public class BillingFragment extends Fragment {

    public BillingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView( final LayoutInflater inflater,
                              final ViewGroup container,
                              final Bundle savedInstanceState ) {
        return inflater.inflate( R.layout.billing, container, false );
    }

}
