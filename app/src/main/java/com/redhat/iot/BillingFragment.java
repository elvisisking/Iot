package com.redhat.iot;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
