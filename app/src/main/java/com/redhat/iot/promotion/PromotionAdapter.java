package com.redhat.iot.promotion;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.redhat.iot.IotConstants;
import com.redhat.iot.R;
import com.redhat.iot.domain.Product;
import com.redhat.iot.domain.Promotion;
import com.redhat.iot.product.ProductProvider;

/**
 * An adapter for displaying collections of {@link Promotion}s.
 */
public class PromotionAdapter extends BaseAdapter {

    private final Context context;
    private final Promotion[] promotions;

    public PromotionAdapter( final Context c,
                             final Promotion[] promotions ) {
        this.context = c;
        this.promotions = promotions;
    }

    @Override
    public int getCount() {
        return this.promotions.length;
    }

    @Override
    public Promotion getItem( final int position ) {
        return this.promotions[ position ];
    }

    @Override
    public long getItemId( final int position ) {
        return position;
    }

    @Override
    public View getView( final int position,
                         final View convertView,
                         final ViewGroup parent ) {
        View dealView;

        if ( convertView == null ) {
            final LayoutInflater inflater = ( LayoutInflater )this.context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            dealView = inflater.inflate( R.layout.promotion, null );
        } else {
            dealView = convertView;
        }

        final Promotion promotion = this.promotions[ position ];
        final Product product = ProductProvider.get().findProduct( promotion.getProductId() );

        if ( product == null ) {
            Log.e( IotConstants.LOG__TAG,
                   "Product " + promotion.getProductId() + " was not found for promotion " + promotion.getId() );
            return dealView;
        }

        {// promotion image
            final ImageView imageView = ( ImageView )dealView.findViewById( R.id.dealImage );
            imageView.setImageResource( product.getImageId() );
        }

        {// promotion sale price
            final TextView textView = ( TextView )dealView.findViewById( R.id.dealSalePrice );
            final double discount = ( product.getMsrp() * ( promotion.getDiscount() / 100 ) );
            final double salePrice = ( product.getMsrp() - discount );
            textView.setText( this.context.getString( R.string.deal_sale_price, salePrice ) );
        }

        {// promotion original price
            final TextView textView = ( TextView )dealView.findViewById( R.id.dealOriginalPrice );
            textView.setText( this.context.getString( R.string.deal_original_price, product.getMsrp() ) );
        }

        {// promotion description
            final TextView textView = ( TextView )dealView.findViewById( R.id.dealDescription );
            textView.setText( product.getDescription() );
        }

        return dealView;
    }

}
