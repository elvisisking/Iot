package com.redhat.iot.concurrent;

import android.widget.ImageView;
import android.widget.TextView;

import com.redhat.iot.DataProvider;
import com.redhat.iot.IotApp;
import com.redhat.iot.R.string;
import com.redhat.iot.domain.Inventory;
import com.redhat.iot.domain.Product;

/**
 * Obtains inventory lookup data.
 */
public class GetInventoryDetail {

    private final ImageView ivProduct;
    private final int productId;
    private final TextView tvProductDescription;
    private final TextView tvProductName;

    /**
     * @param inventory            the {@link Inventory} IoT object being used (cannot be <code>null</code>)
     * @param ivProduct            the widget used to display the product image (cannot be <code>null</code>)
     * @param tvProductName        the widget used to display the product name (cannot be <code>null</code>)
     * @param tvProductDescription the widget used to display the product description (cannot be <code>null</code>)
     */
    public GetInventoryDetail( final Inventory inventory,
                               final ImageView ivProduct,
                               final TextView tvProductName,
                               final TextView tvProductDescription ) {
        this.ivProduct = ivProduct;
        this.productId = inventory.getProductId();
        this.tvProductName = tvProductName;
        this.tvProductDescription = tvProductDescription;

        // find product
        DataProvider.get().findProduct( this.productId, new ProductCallback() {

            @Override
            public void onSuccess( final Product[] results ) {
                setProduct( results );
            }
        } );
    }

    private void setProduct( final Product[] results ) {
        if ( ( results != null ) && ( results.length != 0 ) ) {
            this.ivProduct.setImageResource( results[ 0 ].getImageId() );
            this.tvProductName.setText( results[ 0 ].getName() );
            this.tvProductDescription.setText( results[ 0 ].getDescription() );
        } else {
            this.tvProductName.setText( IotApp.getContext().getString( string.product_not_found, this.productId ) );
        }
    }

}
