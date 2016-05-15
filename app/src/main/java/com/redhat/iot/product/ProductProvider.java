package com.redhat.iot.product;

import com.redhat.iot.IotConstants;
import com.redhat.iot.domain.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * A provider for store {@link Product}s.
 */
public class ProductProvider {

    private static ProductProvider _shared = null;

    /**
     * @return the shared provider (never <code>null</code>)
     */
    public static ProductProvider get() {
        if ( _shared == null ) {
            _shared = new ProductProvider();
        }

        return _shared;
    }

    /**
     * Don't allow construction outside of this class.
     */
    private ProductProvider() {
        // nothing to do
    }

    /**
     * @param productId the ID of the product being requested
     * @return the product or <code>null</code> if not found
     */
    public Product findProduct( final int productId ) {
        for ( final Product product : IotConstants.TestData.PRODUCTs ) {
            if ( productId == product.getId() ) {
                return product;
            }
        }

        return null;
    }

    /**
     * @param productIds the IDs of the products being requested (can be <code>null</code> or empty)
     * @return the products (never <code>null</code> but can be empty)
     */
    public Product[] getProducts( final int... productIds ) {
        if ( ( productIds == null ) || ( productIds.length == 0 ) ) {
            return Product.NO_PRODUCTs;
        }

        final List< Product > products = new ArrayList<>();

        for ( final Product product : IotConstants.TestData.PRODUCTs ) {
            for ( final int id : productIds ) {
                if ( id == product.getId() ) {
                    products.add( product );
                }
            }
        }

        return products.toArray( new Product[ 0 ] );
    }

}
