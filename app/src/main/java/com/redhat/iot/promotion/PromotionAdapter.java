package com.redhat.iot.promotion;

import android.content.Context;
import android.support.v7.widget.CardView;
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
import com.redhat.iot.domain.Department;
import com.redhat.iot.domain.Product;
import com.redhat.iot.domain.Promotion;

/**
 * An adapter for displaying collections of {@link Promotion}s.
 */
class PromotionAdapter extends RecyclerView.Adapter {

    private final Context context;
    private final LayoutInflater inflater;
    private final Promotion[] promotions;
    private RecyclerView recyclerView;

    public PromotionAdapter( final Context c,
                             final Promotion[] promotions ) {
        this.context = c;
        this.inflater = LayoutInflater.from( this.context );
        this.promotions = promotions;
    }

    @Override
    public int getItemCount() {
        return this.promotions.length;
    }

    @Override
    public long getItemId( final int position ) {
        return position;
    }

    private void handlePromotionClicked( final View promotionView ) {
        final int index = this.recyclerView.getChildLayoutPosition( promotionView );
        final Promotion promotion = this.promotions[ index ];
        Toast.makeText( this.context, "Promotion: " + promotion.getId(), Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void onAttachedToRecyclerView( final RecyclerView recyclerView ) {
        super.onAttachedToRecyclerView( recyclerView );
        this.recyclerView = recyclerView;
    }

    @Override
    public void onBindViewHolder( final RecyclerView.ViewHolder promotionHolder,
                                  final int position ) {
        final PromotionViewHolder holder = ( PromotionViewHolder )promotionHolder;
        final Promotion promotion = this.promotions[ position ];
        final Product product = DataProvider.get().findProduct( promotion.getProductId() );

        if ( product == null ) {
            Log.e( IotConstants.LOG_TAG,
                   "Product " + promotion.getProductId() + " was not found for promotion " + promotion.getId() );
            return;
        }

        // set card background color for the product department
        holder.view.setCardBackgroundColor( DataProvider.get().getDepartmentColor( product.getDepartmentId() ) );

        // set product image
        holder.ivItem.setImageResource( product.getImageId() );

        // set product department
        final Department dept = DataProvider.get().findDepartment( product.getDepartmentId() );
        holder.tvDept.setText( dept.getName() );

        // set product sale price
        final double discount = ( product.getMsrp() * ( promotion.getDiscount() / 100 ) );
        final double salePrice = ( product.getMsrp() - discount );
        holder.tvSalePrice.setText( this.context.getString( R.string.deal_sale_price, salePrice ) );

        // set product original price
        holder.tvOriginalPrice.setText( this.context.getString( R.string.deal_original_price, product.getMsrp() ) );

        // set product description
        holder.tvDescription.setText( product.getDescription() );
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( final ViewGroup parent,
                                                       final int viewType ) {
        final View promotionView = this.inflater.inflate( R.layout.promotion, parent, false );
        return new PromotionViewHolder( promotionView );
    }

    private class PromotionViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivItem;
        private final TextView tvDept;
        private final TextView tvDescription;
        private final TextView tvSalePrice;
        private final TextView tvOriginalPrice;
        private final CardView view;

        public PromotionViewHolder( final View promotionlView ) {
            super( promotionlView );

            this.view = ( CardView )promotionlView;
            this.ivItem = ( ImageView )promotionlView.findViewById( R.id.dealImage );
            this.tvDept = ( TextView )promotionlView.findViewById( R.id.dealDept );
            this.tvDescription = ( TextView )promotionlView.findViewById( R.id.dealDescription );
            this.tvSalePrice = ( TextView )promotionlView.findViewById( R.id.dealSalePrice );
            this.tvOriginalPrice = ( TextView )promotionlView.findViewById( R.id.dealOriginalPrice );

            promotionlView.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick( final View promotionView ) {
                    handlePromotionClicked( promotionView );
                }

            } );
        }

    }

}
