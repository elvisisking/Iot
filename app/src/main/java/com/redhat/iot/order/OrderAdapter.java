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
        View orderView;

        if ( convertView == null ) {
            final LayoutInflater inflater = ( LayoutInflater )this.context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            orderView = inflater.inflate( R.layout.order, null );
        } else {
            orderView = convertView;
        }

        {// order ID
            final TextView textView = ( TextView )orderView.findViewById( R.id.orderId );
            textView.setText( this.context.getString( R.string.order_id, order.getId() ) );
        }

        {// order date
            final TextView textView = ( TextView )orderView.findViewById( R.id.orderDate );

            // format date
            final Calendar calendar = order.getOrderDate();
            IotConstants.DATE_FORMATTER.setCalendar( calendar );
            final String formatted = IotConstants.DATE_FORMATTER.format( calendar.getTime() );
            textView.setText( formatted );
        }

        // find first product
        final int productId = order.getProducts()[ 0 ];
        final Product firstProduct = DataProvider.get().findProduct( productId );

        if ( firstProduct == null ) {
            Log.e( IotConstants.LOG_TAG,
                   "Product " + productId + " was not found for order " + order.getId() );
        } else {
            {// item 1 image
                final ImageView imageView = ( ImageView )orderView.findViewById( R.id.orderImage );
                imageView.setImageResource( firstProduct.getImageId() );
            }

            {// item 1 description
                final TextView textView = ( TextView )orderView.findViewById( R.id.orderFirstItem );
                textView.setText( firstProduct.getDescription() );
            }
        }

        {// additional items
            final TextView textView = ( TextView )orderView.findViewById( R.id.orderNumItems );

            if ( order.getProducts().length > 1 ) {
                textView.setText( this.context.getString( R.string.order_num_additional, ( order.getProducts().length - 1 ) ) );
            } else {
                textView.setText( "" );
            }
        }

        {// order price
            final TextView textView = ( TextView )orderView.findViewById( R.id.orderPrice );
            textView.setText( this.context.getString( R.string.order_price, order.getPrice() ) );
        }

        return orderView;
    }

}
