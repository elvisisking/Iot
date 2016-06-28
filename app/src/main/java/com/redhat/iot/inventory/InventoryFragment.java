package com.redhat.iot.inventory;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.redhat.iot.DataProvider;
import com.redhat.iot.R.id;
import com.redhat.iot.R.layout;
import com.redhat.iot.R.string;
import com.redhat.iot.concurrent.InventoryCallback;
import com.redhat.iot.concurrent.ProductCallback;
import com.redhat.iot.domain.Inventory;
import com.redhat.iot.domain.Product;

/**
 * A billing screen.
 */
public class InventoryFragment extends Fragment {

    private static final String[] NO_KEYWORDS = new String[ 0 ];

    private Activity activity;
    private InventoryAdapter adapter;
    private TextView emptyView;
    private RecyclerView inventoriesView;
    private String[] keywords;

    public InventoryFragment() {
        // Required empty public constructor
    }

    private void handleInventoryDetail( final Inventory inventory,
                                        final Product[] results ) {
        if ( results.length == 1 ) {
            final String name = results[ 0 ].getName();
            final String description = results[ 0 ].getDescription();

            for ( final String keyword : this.keywords ) {
                if ( name.contains( keyword ) || description.contains( keyword ) ) {
                    this.adapter.add( inventory );

                    // make sure the inventories show
                    if (this.adapter.getItemCount() == 1) {
                        this.inventoriesView.setVisibility( View.VISIBLE );
                        this.emptyView.setVisibility( View.GONE );
                    }

                    break;
                }
            }
        }
    }

    @Override
    public void onActivityCreated( final Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );

        // obtain inventory based on query
        DataProvider.get().getInventories( this.keywords, new InventoryCallback() {

            @Override
            public void onSuccess( final Inventory[] results ) {
                onInventoriesDelivered( results );
            }
        } );
    }

    @Override
    public View onCreateView( final LayoutInflater inflater,
                              final ViewGroup parent,
                              final Bundle savedInstanceState ) {
        this.activity = getActivity();

        final View view = inflater.inflate( layout.inventory, parent, false );
        this.emptyView = ( TextView )view.findViewById( id.tv_no_inventory );
        this.inventoriesView = ( RecyclerView )view.findViewById( id.inventory_results );

        this.adapter = new InventoryAdapter( this.activity, Inventory.NO_INVENTORIES );
        this.inventoriesView.setAdapter( this.adapter );
        this.inventoriesView.setLayoutManager( new GridLayoutManager( this.activity, 1 ) );

        return view;
    }

    private void onInventoriesDelivered( final Inventory[] inventories ) {
        this.inventoriesView.setVisibility( View.GONE );
        this.emptyView.setVisibility( View.VISIBLE );

        // if no keywords show all
        if ( this.keywords == NO_KEYWORDS ) {
            this.adapter = new InventoryAdapter( this.activity, inventories );
            this.inventoriesView.setAdapter( this.adapter );
            this.adapter.notifyDataSetChanged();
        } else {
            this.emptyView.setText( string.inventory_running_query );

            // filter results by keywords
            for ( final Inventory inventory : inventories ) {
                // find product
                DataProvider.get().findProduct( inventory.getProductId(), new ProductCallback() {

                    @Override
                    public void onSuccess( final Product[] results ) {
                        handleInventoryDetail( inventory, results );
                    }
                } );
            }

            if (this.adapter.getItemCount()==0) {
                this.emptyView.setText( string.inventory_no_matches );
            }
        }
    }

    /**
     * Must be called before attaching this fragment to the activity.
     *
     * @param keywords the search keywords (can be <code>null</code> or empty)
     */
    public void setQuery( final String[] keywords ) {
        this.keywords = ( ( ( keywords == null ) || ( keywords.length == 0 ) ) ? NO_KEYWORDS : keywords );
    }

}
