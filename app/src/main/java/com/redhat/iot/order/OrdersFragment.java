package com.redhat.iot.order;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.redhat.iot.DataProvider;
import com.redhat.iot.IotConstants;
import com.redhat.iot.R;
import com.redhat.iot.domain.Order;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment {

    private Activity activity;

    public OrdersFragment() {
        // nothing to do
    }

    @Override
    public void onActivityCreated( final Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );

        // look up customer ID
        final SharedPreferences settings = this.activity.getSharedPreferences( IotConstants.Prefs.PREFS_NAME, 0 );
        final int customerId = settings.getInt( IotConstants.Prefs.CUSTOMER_ID, -1 );

        // obtain customer orders and create adapter
        final Order[] orders = DataProvider.get().getOrders( customerId );
        final RecyclerView ordersView = ( RecyclerView )getActivity().findViewById( R.id.orderHistory );
        final TextView emptyView = ( TextView )getActivity().findViewById( R.id.tv_no_orders );

        if ( orders.length == 0 ) {
            ordersView.setVisibility( View.GONE );
            emptyView.setVisibility( View.VISIBLE );
        } else {
            ordersView.setVisibility( View.VISIBLE );
            emptyView.setVisibility( View.GONE );

            final OrderAdapter adapter = new OrderAdapter( this.activity, orders );
            ordersView.setAdapter( adapter );
            ordersView.setLayoutManager( new GridLayoutManager( this.activity, 1 ) );
        }
    }

    @Override
    public View onCreateView( final LayoutInflater inflater,
                              final ViewGroup parent,
                              final Bundle savedInstanceState ) {
        this.activity = getActivity();
        return inflater.inflate( R.layout.orders, parent, false );
    }

}
