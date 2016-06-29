package com.redhat.iot.concurrent;

import android.util.Log;

import com.redhat.iot.IotApp;
import com.redhat.iot.IotConstants;
import com.redhat.iot.IotConstants.TestData;
import com.redhat.iot.R.string;
import com.redhat.iot.domain.Inventory;

/**
 * Task to retrieve product {@link Inventory}.
 */
public class GetInventory extends GetData< Inventory > {

    /**
     * Use to indicate all {@link com.redhat.iot.domain.Store}s or all {@link com.redhat.iot.domain.Product}s should be part of the
     * result.
     *
     * @see #GetInventory(String[], int, int, InventoryCallback)
     */
    public static final int ALL = -1;

    /**
     * The OData URL used to obtain {@link Inventory inventories}.
     */
    private static final String URL =
        ( String.format( GetData.URL_PATTERN, "FUSE.hanaallstores_Inventory" ) + GetData.JSONS_FORMAT );

    private final int productId;
    private final String[] queryKeywords; // not used now but maybe could pass in URL
    private final int storeId;

    /**
     * @param queryKeywords the keywords to search for in the inventory product name and description (can be <code>null</code> or
     *                      empty)
     * @param callback      the callback (cannot be <code>null</code>)
     */
    public GetInventory( final String[] queryKeywords,
                         final InventoryCallback callback ) {
        this( queryKeywords, ALL, ALL, callback );
    }

    /**
     * @param queryKeywords the keywords to search for in the inventory product name and description (can be <code>null</code> or
     *                      empty)
     * @param storeId       the ID of the {@link com.redhat.iot.domain.Store} whose {@link Inventory inventories} are being
     *                      requested or {@link GetInventory#ALL} if all stores should be looked at
     * @param productId     the ID of the {@link com.redhat.iot.domain.Product} whose {@link Inventory inventories} are being
     *                      requested or {@link GetInventory#ALL} if all products should be p
     * @param callback      the callback (cannot be <code>null</code>)
     */
    public GetInventory( final String[] queryKeywords,
                         final int storeId,
                         final int productId,
                         final InventoryCallback callback ) {
        super( String.format( URL, storeId ), callback, Inventory.class, string.load_inventory );
        this.queryKeywords = queryKeywords;
        this.storeId = storeId;
        this.productId = productId;
    }

    @Override
    protected String getTestData() {
        if ( this.storeId == ALL ) {
            if ( this.productId == ALL ) {
                return TestData.INVENTORY_JSON;
            }

            switch ( this.productId ) {
                case 100:
                    return TestData.PRODUCT_100_INVENTORY_JSON;
                case 101:
                    return TestData.PRODUCT_101_INVENTORY_JSON;
                case 102:
                    return TestData.PRODUCT_102_INVENTORY_JSON;
                case 200:
                    return TestData.PRODUCT_200_INVENTORY_JSON;
                case 201:
                    return TestData.PRODUCT_201_INVENTORY_JSON;
                case 202:
                    return TestData.PRODUCT_202_INVENTORY_JSON;
                case 300:
                    return TestData.PRODUCT_300_INVENTORY_JSON;
                case 301:
                    return TestData.PRODUCT_301_INVENTORY_JSON;
                case 302:
                    return TestData.PRODUCT_302_INVENTORY_JSON;
                case 400:
                    return TestData.PRODUCT_400_INVENTORY_JSON;
                case 401:
                    return TestData.PRODUCT_401_INVENTORY_JSON;
                case 402:
                    return TestData.PRODUCT_402_INVENTORY_JSON;
                case 500:
                    return TestData.PRODUCT_500_INVENTORY_JSON;
                case 501:
                    return TestData.PRODUCT_501_INVENTORY_JSON;
                case 502:
                    return TestData.PRODUCT_502_INVENTORY_JSON;
                case 600:
                    return TestData.PRODUCT_600_INVENTORY_JSON;
                case 601:
                    return TestData.PRODUCT_601_INVENTORY_JSON;
                case 602:
                    return TestData.PRODUCT_602_INVENTORY_JSON;
                default:
                    return null;
            }
        } else if ( this.productId == ALL ) {
            switch ( this.storeId ) {
                case 9001:
                    return TestData.STORE_9001_INVENTORY_JSON;
                case 9002:
                    return TestData.STORE_9002_INVENTORY_JSON;
                case 9003:
                    return TestData.STORE_9003_INVENTORY_JSON;
                case 9004:
                    return TestData.STORE_9004_INVENTORY_JSON;
                case 9005:
                    return TestData.STORE_9005_INVENTORY_JSON;
                default:
                    return null;
            }
        } else if ( this.storeId == 9001 ) {
            switch ( this.productId ) {
                case 100:
                    return TestData.INVENTORY_9001_100_JSON;
                case 101:
                    return TestData.INVENTORY_9001_101_JSON;
                case 102:
                    return TestData.INVENTORY_9001_102_JSON;
                case 200:
                    return TestData.INVENTORY_9001_200_JSON;
                case 201:
                    return TestData.INVENTORY_9001_201_JSON;
                case 202:
                    return TestData.INVENTORY_9001_202_JSON;
                case 300:
                    return TestData.INVENTORY_9001_300_JSON;
                case 301:
                    return TestData.INVENTORY_9001_301_JSON;
                case 302:
                    return TestData.INVENTORY_9001_302_JSON;
                case 400:
                    return TestData.INVENTORY_9001_400_JSON;
                case 401:
                    return TestData.INVENTORY_9001_401_JSON;
                case 402:
                    return TestData.INVENTORY_9001_402_JSON;
                case 500:
                    return TestData.INVENTORY_9001_500_JSON;
                case 501:
                    return TestData.INVENTORY_9001_501_JSON;
                case 502:
                    return TestData.INVENTORY_9001_502_JSON;
                case 600:
                    return TestData.INVENTORY_9001_600_JSON;
                case 601:
                    return TestData.INVENTORY_9001_601_JSON;
                case 602:
                    return TestData.INVENTORY_9001_602_JSON;
                default:
                    return null;
            }
        } else if ( this.storeId == 9002 ) {
            switch ( this.productId ) {
                case 100:
                    return TestData.INVENTORY_9003_100_JSON;
                case 102:
                    return TestData.INVENTORY_9002_102_JSON;
                case 201:
                    return TestData.INVENTORY_9002_201_JSON;
                case 300:
                    return TestData.INVENTORY_9002_300_JSON;
                case 302:
                    return TestData.INVENTORY_9002_302_JSON;
                case 401:
                    return TestData.INVENTORY_9002_401_JSON;
                case 500:
                    return TestData.INVENTORY_9002_500_JSON;
                case 502:
                    return TestData.INVENTORY_9002_502_JSON;
                case 601:
                    return TestData.INVENTORY_9002_601_JSON;
                default:
                    return null;
            }
        } else if ( this.storeId == 9003 ) {
            switch ( this.productId ) {
                case 100:
                    return TestData.INVENTORY_9003_100_JSON;
                case 101:
                    return TestData.INVENTORY_9003_101_JSON;
                case 102:
                    return TestData.INVENTORY_9003_102_JSON;
                case 200:
                    return TestData.INVENTORY_9003_200_JSON;
                case 201:
                    return TestData.INVENTORY_9003_201_JSON;
                case 202:
                    return TestData.INVENTORY_9003_202_JSON;
                case 300:
                    return TestData.INVENTORY_9003_300_JSON;
                case 301:
                    return TestData.INVENTORY_9003_301_JSON;
                case 302:
                    return TestData.INVENTORY_9003_302_JSON;
                case 400:
                    return TestData.INVENTORY_9003_400_JSON;
                case 401:
                    return TestData.INVENTORY_9003_401_JSON;
                case 402:
                    return TestData.INVENTORY_9003_402_JSON;
                default:
                    return null;
            }
        } else if ( this.storeId == 9004 ) {
            switch ( this.productId ) {
                case 100:
                    return TestData.INVENTORY_9004_100_JSON;
                case 101:
                    return TestData.INVENTORY_9004_101_JSON;
                case 102:
                    return TestData.INVENTORY_9004_102_JSON;
                case 200:
                    return TestData.INVENTORY_9004_200_JSON;
                case 201:
                    return TestData.INVENTORY_9004_201_JSON;
                case 202:
                    return TestData.INVENTORY_9004_202_JSON;
                case 300:
                    return TestData.INVENTORY_9004_300_JSON;
                case 301:
                    return TestData.INVENTORY_9004_301_JSON;
                case 302:
                    return TestData.INVENTORY_9004_302_JSON;
                default:
                    return null;
            }
        } else if ( this.storeId == 9005 ) {
            switch ( this.productId ) {
                case 100:
                    return TestData.INVENTORY_9001_100_JSON;
                case 101:
                    return TestData.INVENTORY_9001_101_JSON;
                case 102:
                    return TestData.INVENTORY_9001_102_JSON;
                default:
                    return null;
            }
        }

        return null;
    }

    private boolean isHanaRunning() {
        final boolean reachable = IotApp.ping( IotConstants.HANA_IP_ADDRESS );

        if ( reachable ) {
            Log.d( IotConstants.LOG_TAG, "Ping HANA (" + IotConstants.HANA_IP_ADDRESS + ") was successful" );
        } else {
            IotApp.logError( GetInventory.class,
                             "isHanaRunning",
                             "Ping of HANA (" + IotConstants.HANA_IP_ADDRESS + ") *** FAILED ***",
                             null );
        }

        return reachable;
    }

    @Override
    protected boolean isUsingRealData() {
        if ( super.isUsingRealData() ) {
            return isHanaRunning(); // only if HANA is running
        }

        // use test data
        return false;
    }

}
