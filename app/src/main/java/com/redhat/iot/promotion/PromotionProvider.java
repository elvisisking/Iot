package com.redhat.iot.promotion;

import android.util.Log;

import com.redhat.iot.IotConstants;
import com.redhat.iot.domain.Product;
import com.redhat.iot.domain.Promotion;
import com.redhat.iot.product.ProductProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Provider for {@link Promotion}s.
 */
public class PromotionProvider {

    private static PromotionProvider _shared = null;

    /**
     * @return the shared provider (never <code>null</code>)
     */
    public static PromotionProvider get() {
        if ( _shared == null ) {
            _shared = new PromotionProvider();
        }

        return _shared;
    }

    /**
     * Don't allow construction outside of this class.
     */
    private PromotionProvider() {
        // nothing to do
    }

    /**
     * @return all promotions (never <code>null</code> but can be empty)
     */
    public Promotion[] getPromotions() {
        return IotConstants.TestData.PROMOTIONS;
    }

    /**
     * @param departmentIds the IDs of the departments whose promotions are being requested (can be <code>null</code>)
     * @return the promotions (never <code>null</code> but can be empty)
     */
    public Promotion[] getPromotions( final Long... departmentIds ) {
        if ( ( departmentIds == null ) || ( departmentIds.length == 0 ) ) {
            return Promotion.NO_PROMOTIONs;
        }

        final List< Promotion > promotions = new ArrayList<>();
        final List< Long > deptIds = Arrays.asList( departmentIds );

        for ( final Promotion promotion : IotConstants.TestData.PROMOTIONS ) {
            final int productId = promotion.getProductId();
            final Product product = ProductProvider.get().findProduct( productId );

            if ( product == null ) {
                Log.e( IotConstants.LOG__TAG,
                       "Product " + productId + " was not found for promotion " + promotion.getId() );
                continue;
            }

            if ( deptIds.contains( product.getDepartmentId() ) ) {
                promotions.add( promotion );
            }
        }

        return promotions.toArray( new Promotion[ promotions.size() ] );
    }

}
