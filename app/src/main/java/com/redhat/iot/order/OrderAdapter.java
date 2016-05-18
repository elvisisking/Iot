package com.redhat.iot.order;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.redhat.iot.DataProvider;
import com.redhat.iot.IotConstants;
import com.redhat.iot.R;
import com.redhat.iot.domain.Order;
import com.redhat.iot.domain.Product;

import java.util.Calendar;

/**
 * An adapter for displaying collections of {@link Order}s.
 */
public class OrderAdapter extends BaseAdapter {

    private final Context context;
    private final Order[] orders;

    public OrderAdapter( final Context c,
                         final Order[] orders ) {
        this.context = c;
        this.orders = orders;
    }

    @Override
    public int getCount() {
        return this.orders.length;
    }

    @Override
    public Order getItem( final int position ) {
        return this.orders[ position ];
    }

    @Override
    public long getItemId( final int position ) {
        return position;
    }

    @Override
    public View getView( final int position,
                         final View convertView,
                         final ViewGroup parent ) {
        final Order order = this.orders[ position ];
        ViewHolder holder = null;
        View orderView;

        if ( convertView == null ) {
            final LayoutInflater inflater = ( LayoutInflater )this.context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            orderView = inflater.inflate( R.layout.order, null );

            holder = new ViewHolder();
            holder.tvId = ( TextView )orderView.findViewById( R.id.orderId );
            holder.tvDate = ( TextView )orderView.findViewById( R.id.orderDate );
            holder.ivOrder = ( ImageView )orderView.findViewById( R.id.orderImage );
            holder.tvDescription = ( TextView )orderView.findViewById( R.id.orderFirstItem );
            holder.tvNumItems = ( TextView )orderView.findViewById( R.id.orderNumItems );
            holder.tvPrice = ( TextView )orderView.findViewById( R.id.orderPrice );

            orderView.setTag( holder );
        } else {
            orderView = convertView;
            holder = ( ViewHolder )orderView.getTag();
        }

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

        return orderView;
    }

    static class ViewHolder {

        TextView tvId;
        TextView tvDate;
        ImageView ivOrder;
        TextView tvDescription;
        TextView tvNumItems;
        TextView tvPrice;

    }

}
