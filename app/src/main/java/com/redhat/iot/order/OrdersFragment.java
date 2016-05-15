package com.redhat.iot.order;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.redhat.iot.IotConstants;
import com.redhat.iot.R;
import com.redhat.iot.domain.Order;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends ListFragment
    implements AdapterView.OnItemClickListener {

    private Activity activity;
    private OrderAdapter adapter;

    public OrdersFragment() {
        // nothing to do
    }

    @Override
    public void onActivityCreated( final Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );

        // look up customer ID
        final SharedPreferences settings = this.activity.getSharedPreferences( IotConstants.PREFS_NAME, 0 );
        final int customerId = settings.getInt( IotConstants.CUSTOMER_ID, -1 );

        // obtain customer orders and create adapter
        final Order[] orders = OrderProvider.get().getOrders( customerId );
        this.adapter = new OrderAdapter( this.activity, orders );
        setListAdapter( this.adapter );

        // register click listener
        getListView().setOnItemClickListener( this );
    }

    @Override
    public View onCreateView( final LayoutInflater inflater,
                              final ViewGroup parent,
                              final Bundle savedInstanceState ) {
        this.activity = getActivity();
        return inflater.inflate( R.layout.order_history, parent, false );
    }

    @Override
    public void onItemClick( final AdapterView< ? > parent,
                             final View view,
                             final int position,
                             final long id ) {
        Toast.makeText( this.activity, "Order: " + position, Toast.LENGTH_SHORT ).show();
    }

}
