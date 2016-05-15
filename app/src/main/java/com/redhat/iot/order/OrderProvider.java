package com.redhat.iot.order;

import com.redhat.iot.IotConstants;
import com.redhat.iot.domain.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Provider for {@link Order}s.
 */
public class OrderProvider {

    private static OrderProvider _shared = null;

    /**
     * Don't allow construction outside of this class.
     */
    private OrderProvider() {
        // nothing to do
    }

    /**
     * @return the shared provider (never <code>null</code>)
     */
    public static OrderProvider get() {
        if ( _shared == null ) {
            _shared = new OrderProvider();
        }

        return _shared;
    }

    /**
     * @param customerId the ID of the customer whose orders are being requested
     * @return the orders (never <code>null</code>)
     */
    public Order[] getOrders( final int customerId ) {
        final List< Order > orders = new ArrayList<>();

        for ( final Order order : IotConstants.TestData.ORDERS ) {
            if ( customerId == order.getCustomerId() ) {
                orders.add( order );
            }
        }

        return orders.toArray( new Order[ orders.size() ] );
    }

}
