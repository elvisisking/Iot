package com.redhat.iot.domain;

import java.util.Calendar;

/**
 * Represents an order.
 */
public class Order {

    private final String comments;
    private final int customerId;
    private final int id;
    private final int[] productIds;
    private final Calendar orderDate;
    private final double price;
    private final Calendar requiredDate;
    private final Calendar shippedDate;
    private final String status;

    public Order( final int id,
                  final int customerId,
                  final int[] productIds,
                  final Calendar orderDate,
                  final double price,
                  final String comments,
                  final String status,
                  final Calendar shippedDate,
                  final Calendar requiredDate ) {
        this.id = id;
        this.customerId = customerId;
        this.productIds = productIds;
        this.orderDate = orderDate;
        this.price = price;
        this.comments = comments;
        this.status = status;
        this.shippedDate = shippedDate;
        this.requiredDate = requiredDate;
    }

    /**
     * @return the order comments (can be empty)
     */
    public String getComments() {
        return this.comments;
    }

    /**
     * @return the customer ID
     */
    public int getCustomerId() {
        return this.customerId;
    }

    /**
     * @return the order ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return the IDs of the products bought with this order (never <code>null</code>)
     */
    public int[] getProducts() {
        return this.productIds;
    }

    /**
     * @return the date of the order (never <code>null</code>)
     */
    public Calendar getOrderDate() {
        return this.orderDate;
    }

    /**
     * @return the order price
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * @return the required date of the order (never <code>null</code>)
     */
    public Calendar getRequiredDate() {
        return this.requiredDate;
    }

    /**
     * @return the shipped date of the order (never <code>null</code>)
     */
    public Calendar getShippedDate() {
        return this.shippedDate;
    }

    /**
     * @return the order status (never empty)
     */
    public String getStatus() {
        return this.status;
    }

    @Override
    public String toString() {
        return ( "Order: " + this.id );
    }

}
