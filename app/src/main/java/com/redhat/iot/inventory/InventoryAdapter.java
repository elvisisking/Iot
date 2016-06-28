package com.redhat.iot.inventory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.redhat.iot.R.id;
import com.redhat.iot.R.layout;
import com.redhat.iot.R.string;
import com.redhat.iot.concurrent.GetInventoryDetail;
import com.redhat.iot.domain.Inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An adapter for displaying collections of {@link Inventory inventories}.
 */
class InventoryAdapter extends Adapter {

    private final Context context;
    private final LayoutInflater inflater;
    private final List< Inventory > inventories;
    private RecyclerView recyclerView;

    public InventoryAdapter( final Context c,
                             final Inventory[] inventories ) {
        this.context = c;
        this.inflater = LayoutInflater.from( this.context );
        this.inventories = new ArrayList<>();

        if ( ( inventories != null ) && ( inventories.length != 0 ) ) {
            this.inventories.addAll( Arrays.asList( inventories ) );
        }
    }

    void add( final Inventory inventoryBeingAdded ) {
        int index = 0;

        if ( this.inventories.isEmpty() ) {
            this.inventories.add( inventoryBeingAdded );
        } else {
            // add in sorted by product ID
            for ( final Inventory current : this.inventories ) {
                final int result = Integer.compare( current.getProductId(), inventoryBeingAdded.getProductId() );

                if (result < 0) {
                    ++index;
                } else {
                    break;
                }
            }

            this.inventories.add( index, inventoryBeingAdded );
        }

        notifyItemInserted( index );
    }

    @Override
    public int getItemCount() {
        return this.inventories.size();
    }

    @Override
    public long getItemId( final int position ) {
        return position;
    }

    private String getString( final int resId,
                              final Object... args ) {
        return this.recyclerView.getContext().getString( resId, args );
    }

    private void handleInventoryClicked( final View inventoryView ) {
        final int index = this.recyclerView.getChildLayoutPosition( inventoryView );
        final Inventory inventory = this.inventories.get( index );
        Toast.makeText( this.context, "Inventory store ID: " + inventory.getStoreId()
                            + ", product ID: " + inventory.getProductId(),
                        Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void onAttachedToRecyclerView( final RecyclerView recyclerView ) {
        super.onAttachedToRecyclerView( recyclerView );
        this.recyclerView = recyclerView;
    }

    @Override
    public void onBindViewHolder( final ViewHolder viewHolder,
                                  final int position ) {
        final InventoryViewHolder holder = ( InventoryViewHolder )viewHolder;
        final Inventory inventory = this.inventories.get( position );

        holder.tvProductId.setText( getString( string.inventory_product_id, inventory.getProductId() ) );
        holder.tvQuantity.setText( getString( string.inventory_quantity, inventory.getQuantity() ) );
        holder.tvStoreId.setText( getString( string.inventory_store_id, inventory.getStoreId() ) );

        if ( holder.tvProductName.getText().length() == 0 ) {
            new GetInventoryDetail( inventory, holder.ivProduct, holder.tvProductName, holder.tvProductDescription );
        }
    }

    @Override
    public ViewHolder onCreateViewHolder( final ViewGroup parent,
                                          final int viewType ) {
        final View view = this.inflater.inflate( layout.inventory_item, parent, false );
        return new InventoryViewHolder( view );
    }

    private class InventoryViewHolder extends ViewHolder {

        private final ImageView ivProduct;
        private final TextView tvProductDescription;
        private final TextView tvProductId;
        private final TextView tvProductName;
        private final TextView tvQuantity;
        private final TextView tvStoreId;

        public InventoryViewHolder( final View inventoryView ) {
            super( inventoryView );

            this.ivProduct = ( ImageView )inventoryView.findViewById( id.iv_inventory_product_image );
            this.tvProductDescription = ( TextView )inventoryView.findViewById( id.tv_inventory_product_description );
            this.tvProductId = ( TextView )inventoryView.findViewById( id.tv_inventory_product_id );
            this.tvProductName = ( TextView )inventoryView.findViewById( id.tv_inventory_product_name );
            this.tvQuantity = ( TextView )inventoryView.findViewById( id.tv_inventory_quantity );
            this.tvStoreId = ( TextView )inventoryView.findViewById( id.tv_inventory_store_id );

            inventoryView.setOnClickListener( new OnClickListener() {

                @Override
                public void onClick( final View inventoryView ) {
                    handleInventoryClicked( inventoryView );
                }

            } );
        }

    }

}
