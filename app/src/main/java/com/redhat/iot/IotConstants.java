package com.redhat.iot;

import com.redhat.iot.domain.Customer;
import com.redhat.iot.domain.Department;
import com.redhat.iot.domain.Order;
import com.redhat.iot.domain.OrderDetail;
import com.redhat.iot.domain.Product;
import com.redhat.iot.domain.Promotion;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Constants used in the IoT mobile app.
 */
public interface IotConstants {

    /**
     * The name of the <code>int</code> preference that contains the customer ID.
     */
    String CUSTOMER_ID = "customer_id";

    /**
     * The date format used in the app.
     */
    String DATE_FORMAT = "MMM dd, yyyy";

    /**
     * The shared date formatter.
     */
    SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat( DATE_FORMAT );

    /**
     * The name of the <code>boolean</code> preference that indicates if notifications are enabled.
     */
    String ENABLE_NOTIFICATIONS = "enabled_notifications";

    /**
     * Tag to use when logging messages.
     */
    String LOG__TAG = "IoT_App";

    /**
     * The time interval in which that app will check to see if a notification is available to send to the user.
     */
    int NOTIFICATION_INTERVAL = 60000;

    /**
     * The name of the preference store.
     */
    String PREFS_NAME = "IoTPrefs";

    interface TestData {

        Department DEPT_1 = new Department( 1, "Boy's", "Boy's clothing and apparel" );
        String DEPT_1_JSON = "{ \"id\" : \"1\", \"name\" : \"Boy's\", \"description\" : \"Boy's clothing and apparel\" }";

        Department DEPT_2 = new Department( 2, "Formal", "Formal wear and apparel" );
        String DEPT_2_JSON = "{ \"id\" : \"2\", \"name\" : \"Formal\", \"description\" : \"Formal wear and apparel\" }";

        Department DEPT_3 = new Department( 3, "Girl's", "Girl's clothing and apparel" );
        String DEPT_3_JSON = "{ \"id\" : \"3\", \"name\" : \"Girl's\", \"description\" : \"Girl's clothing and apparel\" }";

        Department DEPT_4 = new Department( 4, "Men's", "Men's clothing and apparel" );
        String DEPT_4_JSON = "{ \"id\" : \"4\", \"name\" : \"Men's\", \"description\" : \"Men's clothing and apparel\" }";

        Department DEPT_5 = new Department( 5, "Sports", "Sports clothing and apparel" );
        String DEPT_5_JSON = "{ \"id\" : \"5\", \"name\" : \"Sports\", \"description\" : \"Sports clothing and apparel\" }";

        Department DEPT_6 = new Department( 6, "Women's", "Women clothing and apparel" );
        String DEPT_6_JSON = "{ \"id\" : \"6\", \"name\" : \"Women's\", \"description\" : \"Women's clothing and apparel\" }";

        String DEPARTMENTS_JSON = "{\"departments\":[ "
            + DEPT_1_JSON + ','
            + DEPT_2_JSON + ','
            + DEPT_3_JSON + ','
            + DEPT_4_JSON + ','
            + DEPT_5_JSON + ','
            + DEPT_6_JSON
            + " ]}";

        Customer ELVIS = new Customer( 1, "elvis@iot.com", "elvis", null, null, null, null, null, null, null, null, 1000 );
        String ELVIS_JSON = "{\"id\" : 1, \"email\" : \"elvis@iot.com\", \"name\" : \"elvis\"}";

        Customer RINGO = new Customer( 2, "ringo@iot.com", "ringo", null, null, null, null, null, null, null, null, 1000 );
        String RINGO_JSON = "{\"id\" : 2, \"email\" : \"ringo@iot.com\", \"name\" : \"ringo\"}";

        Customer SLEDGE = new Customer( 3, "sledge@iot.com", "sledge", null, null, null, null, null, null, null, null, 1000 );
        String SLEDGE_JSON = "{\"id\" : 3, \"email\" : \"sledge@iot.com\", \"name\" : \"sledge\"}";

        Customer[] CUSTOMERS = new Customer[]{
            ELVIS,
            RINGO,
            SLEDGE
        };
        String CUSTOMERS_JSON = "{\"customers\": [ "
            + ELVIS_JSON + ','
            + RINGO_JSON + ','
            + SLEDGE_JSON
            + " ] }";

        Product PRODUCT_100 = new Product( 100,
                                           IotConstants.TestData.DEPT_1.getId(),
                                           "Boy's socks, Ages 1-3",
                                           1.99,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_100_JSON = "{\"id\" : 100, "
            + "\"departmentId\" : " + IotConstants.TestData.DEPT_1.getId() + ", "
            + "\"description\" : \"Boy's socks, Ages 1-3\", "
            + "\"msrp\" : 1.99, "
            + "\"buyPrice\" : 1.00 }";

        Product PRODUCT_101 = new Product( 101,
                                           IotConstants.TestData.DEPT_1.getId(),
                                           "Boy's shirt, Size 12",
                                           10.50,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_101_JSON = "{\"id\" : 101, "
            + "\"departmentId\" : " + IotConstants.TestData.DEPT_1.getId() + ", "
            + "\"description\" : \"Boy's shirt, Size 12\", "
            + "\"msrp\" : 10.50, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_102 = new Product( 102,
                                           IotConstants.TestData.DEPT_1.getId(),
                                           "Boy's shoes, Size 11",
                                           11.00,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_102_JSON = "{\"id\" : 102, "
            + "\"departmentId\" : " + IotConstants.TestData.DEPT_1.getId() + ", "
            + "\"description\" : \"Boy's shoes, Size 11\", "
            + "\"msrp\" : 11.00, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_200 = new Product( 200,
                                           IotConstants.TestData.DEPT_2.getId(),
                                           "Cuff links, Gold",
                                           5.99,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_200_JSON = "{\"id\" : 200, "
            + "\"departmentId\" : " + IotConstants.TestData.DEPT_2.getId() + ", "
            + "\"description\" : \"Cuff links, Gold\", "
            + "\"msrp\" : 5.99, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_201 = new Product( 201,
                                           IotConstants.TestData.DEPT_2.getId(),
                                           "Tuxedo pants, Size 32W 32L",
                                           50.50,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_201_JSON = "{\"id\" : 201, "
            + "\"departmentId\" : " + IotConstants.TestData.DEPT_2.getId() + ", "
            + "\"description\" : \"Tuxedo pants, Size 32W 32L\", "
            + "\"msrp\" : 50.50, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_202 = new Product( 202,
                                           IotConstants.TestData.DEPT_2.getId(),
                                           "Tuxedo shirt, Size 15",
                                           55.00,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_202_JSON = "{\"id\" : 202, "
            + "\"departmentId\" : " + IotConstants.TestData.DEPT_2.getId() + ", "
            + "\"description\" : \"Tuxedo shirt, Size 15\", "
            + "\"msrp\" : 55.00, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_300 = new Product( 300,
                                           IotConstants.TestData.DEPT_3.getId(),
                                           "Girl's socks, Ages 5-10",
                                           3.99,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_300_JSON = "{\"id\" : 300, "
            + "\"departmentId\" : " + IotConstants.TestData.DEPT_3.getId() + ", "
            + "\"description\" : \"Girl's socks, Ages 5-10\", "
            + "\"msrp\" : 3.99, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_301 = new Product( 301,
                                           IotConstants.TestData.DEPT_3.getId(),
                                           "Girl's dress, White, Full length",
                                           30.50,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_301_JSON = "{\"id\" : " + 301 + ", "
            + "\"departmentId\" : " + IotConstants.TestData.DEPT_3.getId() + ", "
            + "\"description\" : \"Girl's dress, White, Full length\", "
            + "\"msrp\" : 30.50, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_302 = new Product( 302,
                                           IotConstants.TestData.DEPT_3.getId(),
                                           "Girl's outfit, Shirt and Slacks",
                                           33.00,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_302_JSON = "{\"id\" : 302, "
            + "\"departmentId\" : " + IotConstants.TestData.DEPT_3.getId() + ", "
            + "\"description\" : \"Girl's outfit, Shirt and Slacks\", "
            + "\"msrp\" : 33.00, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_400 = new Product( 400,
                                           IotConstants.TestData.DEPT_4.getId(),
                                           "Men's tie, Black, Thin",
                                           4.99,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_400_JSON = "{\"id\" : 400, "
            + "\"departmentId\" : " + IotConstants.TestData.DEPT_4.getId() + ", "
            + "\"description\" : \"Men's tie, Black, Thin\", "
            + "\"msrp\" : 4.99, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_401 = new Product( 401,
                                           IotConstants.TestData.DEPT_4.getId(),
                                           "Men's dress pants, Size 34W 33L",
                                           40.50,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_401_JSON = "{\"id\" : 401, "
            + "\"departmentId\" : " + IotConstants.TestData.DEPT_4.getId() + ", "
            + "\"description\" : \"Men's dress pants, Size 34W 33L\", "
            + "\"msrp\" : 40.50, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_402 = new Product( 402,
                                           IotConstants.TestData.DEPT_4.getId(),
                                           "Men's sport coat, Winter, Wool",
                                           44.00,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_402_JSON = "{\"id\" : 402, "
            + "\"departmentId\" : " + IotConstants.TestData.DEPT_4.getId() + ", "
            + "\"description\" : \"Men's sport coat, Winter, Wool3\", "
            + "\"msrp\" : 44.00, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_500 = new Product( 500,
                                           IotConstants.TestData.DEPT_5.getId(),
                                           "Swim suit, Women's, 2 piece",
                                           5.99,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_500_JSON = "{\"id\" : 500, "
            + "\"departmentId\" : " + IotConstants.TestData.DEPT_5.getId() + ", "
            + "\"description\" : \"Swim suit, Women's, 2 piece\", "
            + "\"msrp\" : 5.99, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_501 = new Product( 501,
                                           IotConstants.TestData.DEPT_5.getId(),
                                           "Jogging pants, Cleveland Cavaliers",
                                           50.50,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_501_JSON = "{\"id\" : 501, "
            + "\"departmentId\" : " + IotConstants.TestData.DEPT_5.getId() + ", "
            + "\"description\" : \"Jogging pants, Cleveland Cavaliers\", "
            + "\"msrp\" : 50.50, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_502 = new Product( 502,
                                           IotConstants.TestData.DEPT_5.getId(),
                                           "St Louis Blues Jersey, Wayne Gretsky",
                                           55.00,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_502_JSON = "{\"id\" : 502, "
            + "\"departmentId\" : " + IotConstants.TestData.DEPT_5.getId() + ", "
            + "\"description\" : \"St Louis Blues Jersey, Wayne Gretsky\", "
            + "\"msrp\" : 55.00, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_600 = new Product( 600,
                                           IotConstants.TestData.DEPT_6.getId(),
                                           "Woman's scarf, Multi-colored",
                                           6.99,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_600_JSON = "{\"id\" : 600, "
            + "\"departmentId\" : " + IotConstants.TestData.DEPT_6.getId() + ", "
            + "\"description\" : \"Woman's scarf, Multi-colored\", "
            + "\"msrp\" : 6.99, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_601 = new Product( 601,
                                           IotConstants.TestData.DEPT_6.getId(),
                                           "Woman's summer dress, Peach",
                                           60.50,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_601_JSON = "{\"id\" : 601, "
            + "\"departmentId\" : " + IotConstants.TestData.DEPT_6.getId() + ", "
            + "\"description\" : \"Woman's summer dress, Peach\", "
            + "\"msrp\" : 60.50, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_602 = new Product( 602,
                                           IotConstants.TestData.DEPT_6.getId(),
                                           "Woman's cowboy boots",
                                           66.00,
                                           1.00,
                                           null,
                                           null,
                                           null );

        String PRODUCT_602_JSON = "{\"id\" : 602, "
            + "\"departmentId\" : " + IotConstants.TestData.DEPT_6.getId() + ", "
            + "\"description\" : \"Woman's cowboy boots\", "
            + "\"msrp\" : 66.00, "
            + "\"buyPrice\" : 1.00}";

        String PRODUCTS_JSON = "{\"products\": [ "
            + PRODUCT_100_JSON + ','
            + PRODUCT_101_JSON + ','
            + PRODUCT_102_JSON + ','
            + PRODUCT_200_JSON + ','
            + PRODUCT_201_JSON + ','
            + PRODUCT_202_JSON + ','
            + PRODUCT_300_JSON + ','
            + PRODUCT_301_JSON + ','
            + PRODUCT_302_JSON + ','
            + PRODUCT_400_JSON + ','
            + PRODUCT_401_JSON + ','
            + PRODUCT_402_JSON + ','
            + PRODUCT_500_JSON + ','
            + PRODUCT_501_JSON + ','
            + PRODUCT_502_JSON + ','
            + PRODUCT_600_JSON + ','
            + PRODUCT_601_JSON + ','
            + PRODUCT_602_JSON
            + " ] }";

        Order ORDER_1010 = new Order( 1010,
                                      ELVIS.getId(),
                                      new GregorianCalendar( 2013, 3, 31 ),
                                      12.34,
                                      null,
                                      null,
                                      null,
                                      null );
        OrderDetail DETAIL_1010_1 = new OrderDetail( ORDER_1010.getId(), PRODUCT_300.getId(), 1, 5.5, 1 );
        OrderDetail DETAIL_1010_2 = new OrderDetail( ORDER_1010.getId(), PRODUCT_301.getId(), 2, 10.0, 2 );
        OrderDetail DETAIL_1010_3 = new OrderDetail( ORDER_1010.getId(), PRODUCT_302.getId(), 2, 20.5, 3 );

        String ORDER_1010_JSON = "{\"id\" : 1010, "
            + "\"customerId\" : " + ELVIS.getId() + ", "
            + "\"orderDate\" : \"Mar 31, 2013\", "
            + "\"price\" : 12.34}";

        Order ORDER_2020 = new Order( 2020,
                                      RINGO.getId(),
                                      new GregorianCalendar( 2014, 1, 20 ),
                                      23.45,
                                      null,
                                      null,
                                      null,
                                      null );
        OrderDetail DETAIL_2020_1 = new OrderDetail( ORDER_2020.getId(), PRODUCT_100.getId(), 1, 5.5, 1 );
        OrderDetail DETAIL_2020_2 = new OrderDetail( ORDER_2020.getId(), PRODUCT_200.getId(), 2, 10.0, 2 );
        OrderDetail DETAIL_2020_3 = new OrderDetail( ORDER_2020.getId(), PRODUCT_602.getId(), 2, 20.5, 3 );
        OrderDetail DETAIL_2020_4 = new OrderDetail( ORDER_2020.getId(), PRODUCT_400.getId(), 1, 5.5, 4 );
        OrderDetail DETAIL_2020_5 = new OrderDetail( ORDER_2020.getId(), PRODUCT_500.getId(), 2, 10.0, 5 );
        OrderDetail DETAIL_2020_6 = new OrderDetail( ORDER_2020.getId(), PRODUCT_600.getId(), 2, 20.5, 6 );

        String ORDER_2020_JSON = "{\"id\" : 2020, "
            + "\"customerId\" : " + RINGO.getId() + ", "
            + "\"orderDate\" : \"Jan 01, 2014\", "
            + "\"price\" : 23.45}";

        Order ORDER_3030 = new Order( 3030,
                                      RINGO.getId(),
                                      new GregorianCalendar( 2013, 2, 10 ),
                                      26.67,
                                      null,
                                      null,
                                      null,
                                      null );
        OrderDetail DETAIL_3030_1 = new OrderDetail( ORDER_3030.getId(), PRODUCT_202.getId(), 1, 5.5, 1 );
        OrderDetail DETAIL_3030_2 = new OrderDetail( ORDER_3030.getId(), PRODUCT_102.getId(), 2, 10.0, 2 );

        String ORDER_3030_JSON = "{\"id\" : 3030, "
            + "\"customerId\" : " + RINGO.getId() + ", "
            + "\"orderDate\" : \"Feb 10, 2013\", "
            + "\"price\" : 26.67}";

        Order ORDER_4040 = new Order( 4040,
                                      SLEDGE.getId(),
                                      new GregorianCalendar( 2013, 3, 22 ),
                                      34.56,
                                      null,
                                      null,
                                      null,
                                      null );
        OrderDetail DETAIL_4040_1 = new OrderDetail( ORDER_4040.getId(), PRODUCT_101.getId(), 1, 5.5, 1 );
        OrderDetail DETAIL_4040_2 = new OrderDetail( ORDER_4040.getId(), PRODUCT_201.getId(), 2, 10.0, 2 );
        OrderDetail DETAIL_4040_3 = new OrderDetail( ORDER_4040.getId(), PRODUCT_402.getId(), 2, 20.5, 3 );

        String ORDER_4040_JSON = "{\"id\" : 4040, "
            + "\"customerId\" : " + SLEDGE.getId() + ", "
            + "\"orderDate\" : \"Mar 22, 2013\", "
            + "\"price\" : 34.56}";

        Order ORDER_5050 = new Order( 5050,
                                      SLEDGE.getId(),
                                      new GregorianCalendar( 2013, 4, 15 ),
                                      35.67,
                                      null,
                                      null,
                                      null,
                                      null );
        OrderDetail DETAIL_5050_1 = new OrderDetail( ORDER_5050.getId(), PRODUCT_301.getId(), 1, 5.5, 1 );
        OrderDetail DETAIL_5050_2 = new OrderDetail( ORDER_5050.getId(), PRODUCT_401.getId(), 2, 10.0, 2 );
        OrderDetail DETAIL_5050_3 = new OrderDetail( ORDER_5050.getId(), PRODUCT_501.getId(), 2, 20.5, 3 );

        String ORDER_5050_JSON = "{\"id\" : 5050, "
            + "\"customerId\" : " + SLEDGE.getId() + ", "
            + "\"orderDate\" : \"Apr 15, 2013\", "
            + "\"price\" : 35.67}";

        Order ORDER_6060 = new Order( 6060,
                                      SLEDGE.getId(),
                                      new GregorianCalendar( 2013, 11, 25 ),
                                      36.78,
                                      null,
                                      null,
                                      null,
                                      null );
        OrderDetail DETAIL_6060_1 = new OrderDetail( ORDER_6060.getId(), PRODUCT_100.getId(), 1, 5.5, 1 );
        OrderDetail DETAIL_6060_2 = new OrderDetail( ORDER_6060.getId(), PRODUCT_502.getId(), 2, 10.0, 2 );

        String ORDER_6060_JSON = "{\"id\" : 6060, "
            + "\"customerId\" : " + SLEDGE.getId() + ", "
            + "\"orderDate\" : \"Nov 25, 2013\", "
            + "\"price\" : 36.78}";

        String ORDERS_JSON = "{\"orders\": [ "
            + ORDER_1010_JSON + ','
            + ORDER_2020_JSON + ','
            + ORDER_3030_JSON + ','
            + ORDER_4040_JSON + ','
            + ORDER_5050_JSON + ','
            + ORDER_6060_JSON
            + " ] }";

        OrderDetail[] ORDER_DETAILS = new OrderDetail[]{
            DETAIL_1010_1,
            DETAIL_1010_2,
            DETAIL_1010_3,
            DETAIL_2020_1,
            DETAIL_2020_2,
            DETAIL_2020_3,
            DETAIL_2020_4,
            DETAIL_2020_5,
            DETAIL_2020_6,
            DETAIL_3030_1,
            DETAIL_3030_2,
            DETAIL_4040_1,
            DETAIL_4040_2,
            DETAIL_4040_3,
            DETAIL_5050_1,
            DETAIL_5050_2,
            DETAIL_5050_3,
            DETAIL_6060_1,
            DETAIL_6060_2
        };

        Promotion PROMO_1 = new Promotion( 1, IotConstants.TestData.PRODUCT_100.getId(), 10.0 );
        String PROMO_1_JSON = "{\"id\" : 1, \"productId\" : \"100\", \"discount\" : \"10.0\"}";

        Promotion PROMO_2 = new Promotion( 2, IotConstants.TestData.PRODUCT_101.getId(), 20.0 );
        String PROMO_2_JSON = "{\"id\" : 2, \"productId\" : \"101\", \"discount\" : \"20.0\"}";

        Promotion PROMO_3 = new Promotion( 3, IotConstants.TestData.PRODUCT_200.getId(), 30.0 );
        String PROMO_3_JSON = "{\"id\" : 3, \"productId\" : \"200\", \"discount\" : \"30.0\"}";

        Promotion PROMO_4 = new Promotion( 4, IotConstants.TestData.PRODUCT_201.getId(), 40.0 );
        String PROMO_4_JSON = "{\"id\" : 4, \"productId\" : \"201\", \"discount\" : \"40.0\"}";

        Promotion PROMO_5 = new Promotion( 5, IotConstants.TestData.PRODUCT_300.getId(), 50.0 );
        String PROMO_5_JSON = "{\"id\" : 5, \"productId\" : \"300\", \"discount\" : \"50.0\"}";

        Promotion PROMO_6 = new Promotion( 6, IotConstants.TestData.PRODUCT_301.getId(), 10.0 );
        String PROMO_6_JSON = "{\"id\" : 6, \"productId\" : \"301\", \"discount\" : \"10.0\"}";

        Promotion PROMO_7 = new Promotion( 7, IotConstants.TestData.PRODUCT_400.getId(), 20.0 );
        String PROMO_7_JSON = "{\"id\" : 7, \"productId\" : \"400\", \"discount\" : \"20.0\"}";

        Promotion PROMO_8 = new Promotion( 8, IotConstants.TestData.PRODUCT_401.getId(), 30.0 );
        String PROMO_8_JSON = "{\"id\" : 8, \"productId\" : \"401\", \"discount\" : \"30.0\"}";

        Promotion PROMO_9 = new Promotion( 9, IotConstants.TestData.PRODUCT_500.getId(), 40.0 );
        String PROMO_9_JSON = "{\"id\" : 9, \"productId\" : \"500\", \"discount\" : \"40.0\"}";

        Promotion PROMO_10 = new Promotion( 10, IotConstants.TestData.PRODUCT_501.getId(), 50.0 );
        String PROMO_10_JSON = "{\"id\" : 10, \"productId\" : \"501\", \"discount\" : \"50.0\"}";

        Promotion PROMO_11 = new Promotion( 11, IotConstants.TestData.PRODUCT_600.getId(), 10.0 );
        String PROMO_11_JSON = "{\"id\" : 11, \"productId\" : \"600\", \"discount\" : \"10.0\"}";

        Promotion PROMO_12 = new Promotion( 12, IotConstants.TestData.PRODUCT_601.getId(), 20.0 );
        String PROMO_12_JSON = "{\"id\" : 12, \"productId\" : \"601\", \"discount\" : \"20.0\"}";

        String PROMOTIONS_JSON = "{\"promotions\": [ "
            + PROMO_1_JSON + ','
            + PROMO_2_JSON + ','
            + PROMO_3_JSON + ','
            + PROMO_4_JSON + ','
            + PROMO_5_JSON + ','
            + PROMO_6_JSON + ','
            + PROMO_7_JSON + ','
            + PROMO_8_JSON + ','
            + PROMO_9_JSON + ','
            + PROMO_10_JSON + ','
            + PROMO_11_JSON + ','
            + PROMO_12_JSON
            + " ] }";

    }

}
