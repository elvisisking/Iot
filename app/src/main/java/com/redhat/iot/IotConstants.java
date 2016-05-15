package com.redhat.iot;

import com.redhat.iot.domain.Customer;
import com.redhat.iot.domain.Department;
import com.redhat.iot.domain.Order;
import com.redhat.iot.domain.Product;
import com.redhat.iot.domain.Promotion;
import com.redhat.iot.product.DepartmentProvider;

import java.util.GregorianCalendar;

/**
 * Constants used in the IoT mobile app.
 */
public interface IotConstants {

    /**
     * Tag to use when logging messages.
     */
    String LOG__TAG = "IoT_App";

    int NOTIFICATION_INTERVAL = 30000;

    /**
     * The name of the preference store.
     */
    String PREFS_NAME = "IoTPrefs";

    /**
     * The name of the <code>int</code> preference that contains the customer ID.
     */
    String CUSTOMER_ID = "customer_id";

    interface TestData {

        Department DEPT_1 = new Department( 1, "Boy's", "Boy clothing and apparel" );
        Department DEPT_2 = new Department( 2, "Formal", "Formal wear and apparel" );
        Department DEPT_3 = new Department( 3, "Girl's", "Girl's clothing and apparel" );
        Department DEPT_4 = new Department( 4, "Men's", "Men's clothing and apparel" );
        Department DEPT_5 = new Department( 5, "Sports", "Sports clothing and apparel" );
        Department DEPT_6 = new Department( 6, "Women's", "Women clothing and apparel" );

        Department[] DEPARTMENTS = new Department[]{
            DEPT_1,
            DEPT_2,
            DEPT_3,
            DEPT_4,
            DEPT_5,
            DEPT_6
        };

        Customer ELVIS = new Customer( 1, "elvis@iot.com", "elvis", null, null, null, null, null, null, null, null, 1000 );
        Customer RINGO = new Customer( 2, "ringo@iot.com", "ringo", null, null, null, null, null, null, null, null, 1000 );
        Customer SLEDGE = new Customer( 3, "sledge@iot.com", "sledge", null, null, null, null, null, null, null, null, 1000 );

        Customer[] CUSTOMERs = new Customer[]{
            ELVIS,
            RINGO,
            SLEDGE
        };

        Product PRODUCT_100 = new Product( 100,
                                           IotConstants.TestData.DEPT_1.getId(),
                                           "Boy's socks, Ages 1-3",
                                           1.99,
                                           1.00,
                                           null,
                                           null,
                                           null );
        Product PRODUCT_101 = new Product( 101,
                                           IotConstants.TestData.DEPT_1.getId(),
                                           "Boy's shirt, Size 12",
                                           10.50,
                                           1.00,
                                           null,
                                           null,
                                           null );
        Product PRODUCT_102 = new Product( 102,
                                           IotConstants.TestData.DEPT_1.getId(),
                                           "Boy's shoes, Size 11",
                                           11.00,
                                           1.00,
                                           null,
                                           null,
                                           null );
        Product PRODUCT_200 = new Product( 200,
                                           IotConstants.TestData.DEPT_2.getId(),
                                           "Cuff links, Gold",
                                           5.99,
                                           1.00,
                                           null,
                                           null,
                                           null );
        Product PRODUCT_201 = new Product( 201,
                                           IotConstants.TestData.DEPT_2.getId(),
                                           "Tuxedo pants, Size 32W 32L",
                                           50.50,
                                           1.00,
                                           null,
                                           null,
                                           null );
        Product PRODUCT_202 = new Product( 202,
                                           IotConstants.TestData.DEPT_2.getId(),
                                           "Wedding dress, White, with veil",
                                           55.00,
                                           1.00,
                                           null,
                                           null,
                                           null );
        Product PRODUCT_300 = new Product( 300,
                                           IotConstants.TestData.DEPT_3.getId(),
                                           "Girl's socks, Ages 5-10",
                                           3.99,
                                           1.00,
                                           null,
                                           null,
                                           null );
        Product PRODUCT_301 = new Product( 301,
                                           IotConstants.TestData.DEPT_3.getId(),
                                           "Girl's dress, White, Full length",
                                           30.50,
                                           1.00,
                                           null,
                                           null,
                                           null );
        Product PRODUCT_302 = new Product( 302,
                                           IotConstants.TestData.DEPT_3.getId(),
                                           "Girl's outfit, Shirt and Slacks",
                                           33.00,
                                           1.00,
                                           null,
                                           null,
                                           null );
        Product PRODUCT_400 = new Product( 400,
                                           IotConstants.TestData.DEPT_4.getId(),
                                           "Men's tie, Black, Thin",
                                           4.99,
                                           1.00,
                                           null,
                                           null,
                                           null );
        Product PRODUCT_401 = new Product( 401,
                                           IotConstants.TestData.DEPT_4.getId(),
                                           "Men's dress pants, Size 34W 33L",
                                           40.50,
                                           1.00,
                                           null,
                                           null,
                                           null );
        Product PRODUCT_402 = new Product( 402,
                                           IotConstants.TestData.DEPT_4.getId(),
                                           "Men's sport coat, Winter, Wool",
                                           44.00,
                                           1.00,
                                           null,
                                           null,
                                           null );
        Product PRODUCT_500 = new Product( 500,
                                           IotConstants.TestData.DEPT_5.getId(),
                                           "Swim suit, Women's, 2 piece",
                                           5.99,
                                           1.00,
                                           null,
                                           null,
                                           null );
        Product PRODUCT_501 = new Product( 501,
                                           IotConstants.TestData.DEPT_5.getId(),
                                           "Jogging pants, Cleveland Cavaliers",
                                           50.50,
                                           1.00,
                                           null,
                                           null,
                                           null );
        Product PRODUCT_502 = new Product( 502,
                                           IotConstants.TestData.DEPT_5.getId(),
                                           "St Louis Blues Jersey, Wayne Gretsky",
                                           55.00,
                                           1.00,
                                           null,
                                           null,
                                           null );
        Product PRODUCT_600 = new Product( 600,
                                           IotConstants.TestData.DEPT_6.getId(),
                                           "Woman's scarf, Multi-colored",
                                           6.99,
                                           1.00,
                                           null,
                                           null,
                                           null );
        Product PRODUCT_601 = new Product( 601,
                                           IotConstants.TestData.DEPT_6.getId(),
                                           "Woman's summer dress, Peach",
                                           60.50,
                                           1.00,
                                           null,
                                           null,
                                           null );
        Product PRODUCT_602 = new Product( 602,
                                           IotConstants.TestData.DEPT_6.getId(),
                                           "Woman's cowboy boots",
                                           66.00,
                                           1.00,
                                           null,
                                           null,
                                           null );

        Product[] PRODUCTs = new Product[]{
            PRODUCT_100,
            PRODUCT_101,
            PRODUCT_102,
            PRODUCT_200,
            PRODUCT_201,
            PRODUCT_202,
            PRODUCT_300,
            PRODUCT_301,
            PRODUCT_302,
            PRODUCT_400,
            PRODUCT_401,
            PRODUCT_402,
            PRODUCT_500,
            PRODUCT_501,
            PRODUCT_502,
            PRODUCT_600,
            PRODUCT_601,
            PRODUCT_602
        };

        Order ORDER_1010 = new Order( 1010,
                                      ELVIS.getId(),
                                      new int[]{ PRODUCT_300.getId(), PRODUCT_301.getId(), PRODUCT_302.getId() },
                                      new GregorianCalendar( 2013, 0, 31 ),
                                      12.34,
                                      null,
                                      null,
                                      null,
                                      null );
        Order ORDER_2020 = new Order( 2020,
                                      RINGO.getId(),
                                      new int[]{ PRODUCT_100.getId(), PRODUCT_200.getId(), PRODUCT_300.getId(), PRODUCT_400.getId
                                          (), PRODUCT_500.getId(), PRODUCT_600.getId() },
                                      new GregorianCalendar( 2014, 1, 20 ),
                                      23.45,
                                      null,
                                      null,
                                      null,
                                      null );
        Order ORDER_3030 = new Order( 3030,
                                      RINGO.getId(),
                                      new int[]{ PRODUCT_101.getId(), PRODUCT_102.getId() },
                                      new GregorianCalendar( 2013, 2, 10 ),
                                      26.67,
                                      null,
                                      null,
                                      null,
                                      null );
        Order ORDER_4040 = new Order( 4040,
                                      SLEDGE.getId(),
                                      new int[]{ PRODUCT_101.getId(), PRODUCT_201.getId(), PRODUCT_500.getId() },
                                      new GregorianCalendar( 2013, 3, 22 ),
                                      34.56,
                                      null,
                                      null,
                                      null,
                                      null );
        Order ORDER_5050 = new Order( 5050,
                                      SLEDGE.getId(),
                                      new int[]{ PRODUCT_301.getId(), PRODUCT_401.getId(), PRODUCT_501.getId() },
                                      new GregorianCalendar( 2013, 4, 15 ),
                                      35.67,
                                      null,
                                      null,
                                      null,
                                      null );
        Order ORDER_6060 = new Order( 6060,
                                      SLEDGE.getId(),
                                      new int[]{ PRODUCT_100.getId(), PRODUCT_502.getId() },
                                      new GregorianCalendar( 2013, 12, 25 ),
                                      36.78,
                                      null,
                                      null,
                                      null,
                                      null );

        Order[] ORDERS = new Order[]{
            ORDER_1010,
            ORDER_2020,
            ORDER_3030,
            ORDER_4040,
            ORDER_5050,
            ORDER_6060
        };

        Promotion PROMO_1 = new Promotion( 1, IotConstants.TestData.PRODUCT_100.getId(), 10.0 );
        Promotion PROMO_2 = new Promotion( 2, IotConstants.TestData.PRODUCT_101.getId(), 20.0 );
        Promotion PROMO_3 = new Promotion( 3, IotConstants.TestData.PRODUCT_200.getId(), 30.0 );
        Promotion PROMO_4 = new Promotion( 4, IotConstants.TestData.PRODUCT_201.getId(), 40.0 );
        Promotion PROMO_5 = new Promotion( 5, IotConstants.TestData.PRODUCT_300.getId(), 50.0 );
        Promotion PROMO_6 = new Promotion( 6, IotConstants.TestData.PRODUCT_301.getId(), 10.0 );
        Promotion PROMO_7 = new Promotion( 7, IotConstants.TestData.PRODUCT_400.getId(), 20.0 );
        Promotion PROMO_8 = new Promotion( 8, IotConstants.TestData.PRODUCT_401.getId(), 30.0 );
        Promotion PROMO_9 = new Promotion( 9, IotConstants.TestData.PRODUCT_500.getId(), 40.0 );
        Promotion PROMO_10 = new Promotion( 10, IotConstants.TestData.PRODUCT_501.getId(), 50.0 );
        Promotion PROMO_11 = new Promotion( 11, IotConstants.TestData.PRODUCT_600.getId(), 10.0 );
        Promotion PROMO_12 = new Promotion( 12, IotConstants.TestData.PRODUCT_601.getId(), 20.0 );

        Promotion[] PROMOTIONS = new Promotion[]{
            PROMO_1,
            PROMO_2,
            PROMO_3,
            PROMO_4,
            PROMO_5,
            PROMO_6,
            PROMO_7,
            PROMO_8,
            PROMO_9,
            PROMO_10,
            PROMO_11,
            PROMO_12
        };

    }

}
