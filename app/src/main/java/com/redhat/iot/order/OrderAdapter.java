package com.redhat.iot.order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.redhat.iot.DataProvider;
import com.redhat.iot.IotConstants;
import com.redhat.iot.R;
import com.redhat.iot.domain.Order;
import com.redhat.iot.domain.Product;

import java.util.Calendar;

/**
 * An adapter for displaying collections of {@link Order}s.
 */
class OrderAdapter extends RecyclerView.Adapter {

    private final Context context;
    private final LayoutInflater inflater;
    private final Order[] orders;
    private RecyclerView recyclerView;

    public OrderAdapter( final Context c,
                         final Order[] orders ) {
        this.context = c;
        this.inflater = LayoutInflater.from( this.context );
        this.orders = orders;
    }

    @Override
    public int getItemCount() {
        return this.orders.length;
    }

    @Override
    public long getItemId( final int position ) {
        return position;
    }

    void handleOrderClicked( final View orderView ) {
        final int index = this.recyclerView.getChildLayoutPosition( orderView );
        final Order order = this.orders[ index ];
        Toast.makeText( this.context, "Order: " + order.getId(), Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void onAttachedToRecyclerView( final RecyclerView recyclerView ) {
        super.onAttachedToRecyclerView( recyclerView );
        this.recyclerView = recyclerView;
    }

    @Override
    public void onBindViewHolder( final RecyclerView.ViewHolder viewHolder,
                                  final int position ) {
        final OrderViewHolder holder = ( OrderViewHolder )viewHolder;
        final Order order = this.orders[ position ];

        // set order ID
        holder.tvId.setText( this.context.getString( R.string.order_id, order.getId() ) );

        // set order date
        final Calendar calendar = order.getOrderDate();
        IotConstants.DATE_FORMATTER.setCalendar( calendar );
        final String formatted = IotConstants.DATE_FORMATTER.format( calendar.getTime() );
        holder.tvDate.setText( formatted );

        // find first product
        final int productId = order.getProducts()[ 0 ];
        final Product firstProduct = DataProvider.get().findProduct( productId );

        if ( firstProduct == null ) {
            Log.e( IotConstants.LOG_TAG,
                   "Product " + productId + " was not found for order " + order.getId() );
        } else {
            // set order image based on first item
            holder.ivOrder.setImageResource( firstProduct.getImageId() );

            // set order description based on first item
            holder.tvDescription.setText( firstProduct.getDescription() );
        }

        // set number of items in order
        if ( order.getProducts().length > 1 ) {
            holder.tvNumItems.setText( this.context.getString( R.string.order_num_additional,
                                                               ( order.getProducts().length - 1 ) ) );
        } else {
            holder.tvNumItems.setText( "" );
        }

        // set order price
        holder.tvPrice.setText( this.context.getString( R.string.order_price, order.getPrice() ) );
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( final ViewGroup parent,
                                                       final int viewType ) {
        final View view = this.inflater.inflate( R.layout.order, null );
        return new OrderViewHolder( view );
    }

    private class OrderViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivOrder;
        private final TextView tvDate;
        private final TextView tvDescription;
        private final TextView tvId;
        private final TextView tvNumItems;
        private final TextView tvPrice;

        public OrderViewHolder( final View orderView ) {
            super( orderView );

            this.ivOrder = ( ImageView )orderView.findViewById( R.id.orderImage );
            this.tvDate = ( TextView )orderView.findViewById( R.id.orderDate );
            this.tvDescription = ( TextView )orderView.findViewById( R.id.orderFirstItem );
            this.tvId = ( TextView )orderView.findViewById( R.id.orderId );
            this.tvNumItems = ( TextView )orderView.findViewById( R.id.orderNumItems );
            this.tvPrice = ( TextView )orderView.findViewById( R.id.orderPrice );

            orderView.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick( final View orderView ) {
                    handleOrderClicked( orderView );
                }

            } );
        }

    }

}
