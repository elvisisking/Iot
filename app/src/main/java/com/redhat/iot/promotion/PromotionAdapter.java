package com.redhat.iot.promotion;

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
import com.redhat.iot.domain.Product;
import com.redhat.iot.domain.Promotion;

/**
 * An adapter for displaying collections of {@link Promotion}s.
 */
class PromotionAdapter extends BaseAdapter {

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
        ViewHolder holder;

        if ( convertView == null ) {
            final LayoutInflater inflater = ( LayoutInflater )this.context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            dealView = inflater.inflate( R.layout.promotion, null );

            holder = new ViewHolder();
            holder.ivItem = ( ImageView )dealView.findViewById( R.id.dealImage );
            holder.tvSalePrice = ( TextView )dealView.findViewById( R.id.dealSalePrice );
            holder.tvOriginalPrice = ( TextView )dealView.findViewById( R.id.dealOriginalPrice );
            holder.tvDescription = ( TextView )dealView.findViewById( R.id.dealDescription );

            dealView.setTag( holder );
        } else {
            dealView = convertView;
            holder = ( ViewHolder )dealView.getTag();
        }

        final Promotion promotion = this.promotions[ position ];
        final Product product = DataProvider.get().findProduct( promotion.getProductId() );

        if ( product == null ) {
            Log.e( IotConstants.LOG_TAG,
                   "Product " + promotion.getProductId() + " was not found for promotion " + promotion.getId() );
            return dealView;
        }

        // set promotion image
        holder.ivItem.setImageResource( product.getImageId() );

        // set promotion sale price
        final double discount = ( product.getMsrp() * ( promotion.getDiscount() / 100 ) );
        final double salePrice = ( product.getMsrp() - discount );
        holder.tvSalePrice.setText( this.context.getString( R.string.deal_sale_price, salePrice ) );

        // set promotion original price
        holder.tvOriginalPrice.setText( this.context.getString( R.string.deal_original_price, product.getMsrp() ) );

        // set promotion description
        holder.tvDescription.setText( product.getDescription() );

        return dealView;
    }

    static class ViewHolder {

        ImageView ivItem;
        TextView tvSalePrice;
        TextView tvOriginalPrice;
        TextView tvDescription;

    }

}
