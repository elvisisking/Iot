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
     * Constants related to preferences.
     */
    interface Prefs {

        /**
         * The name of the <code>int</code> preference that contains the customer ID.
         */
        String CUSTOMER_ID = "customer_id";

        /**
         * The default enable notifications preference value. Value is {@value}.
         */
        boolean DEFAULT_ENABLE_NOTIFICATIONS = true;

        /**
         * The default interval length in milliseconds preference value. Value is {@value}.
         */
        int DEFAULT_NOTIFICATION_INTERVAL = 60000;

        /**
         * The name of the <code>boolean</code> preference that indicates if notifications are enabled.
         */
        String ENABLE_NOTIFICATIONS = "enabled_notifications";

        /**
         * The name of the time interval in milliseconds the app checks to see if a notification is available to send to the user.
         */
        String NOTIFICATION_INTERVAL = "notification_interval";

        /**
         * The name of the preference store.
         */
        String PREFS_NAME = "IoTPrefs";

    }

    /**
     * The date format used in the app.
     */
    String DATE_FORMAT = "MMM dd, yyyy";

    /**
     * The first customer ID in the actual demo data.
     */
    int FIRST_CUST_ID = 10000;

    /**
     * The shared date formatter.
     */
    SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat( DATE_FORMAT );

    /**
     * Tag to use when logging messages.
     */
    String LOG_TAG = "IoT_App";

    interface TestData {

        Department DEPT_1 = new Department( 1, "Boy's", "Boy's clothing and apparel" );
        String DEPT_1_JSON =
            "{ \"departmentCode\" : \"1\", \"departmentName\" : \"Boy's\", \"departmentDescription\" : \"Boy's clothing and " +
                "apparel\" }";

        Department DEPT_2 = new Department( 2, "Formal", "Formal wear and apparel" );
        String DEPT_2_JSON =
            "{ \"departmentCode\" : \"2\", \"departmentName\" : \"Formal\", \"departmentDescription\" : \"Formal wear and " +
                "apparel\" }";

        Department DEPT_3 = new Department( 3, "Girl's", "Girl's clothing and apparel" );
        String DEPT_3_JSON =
            "{ \"departmentCode\" : \"3\", \"departmentName\" : \"Girl's\", \"departmentDescription\" : \"Girl's clothing and " +
                "apparel\" }";

        Department DEPT_4 = new Department( 4, "Men's", "Men's clothing and apparel" );
        String DEPT_4_JSON =
            "{ \"departmentCode\" : \"4\", \"departmentName\" : \"Men's\", \"departmentDescription\" : \"Men's clothing and " +
                "apparel\" }";

        Department DEPT_5 = new Department( 5, "Sports", "Sports clothing and apparel" );
        String DEPT_5_JSON =
            "{ \"departmentCode\" : \"5\", \"departmentName\" : \"Sports\", \"departmentDescription\" : \"Sports clothing and " +
                "apparel\" }";

        Department DEPT_6 = new Department( 6, "Women's", "Women clothing and apparel" );
        String DEPT_6_JSON =
            "{ \"departmentCode\" : \"6\", \"departmentName\" : \"Women's\", \"departmentDescription\" : \"Women's clothing and " +
                "apparel\" }";

        String DEPARTMENTS_JSON = "{ d: { \"results\": [ "
            + DEPT_1_JSON + ','
            + DEPT_2_JSON + ','
            + DEPT_3_JSON + ','
            + DEPT_4_JSON + ','
            + DEPT_5_JSON + ','
            + DEPT_6_JSON
            + " ] } }";

        Customer ELVIS = new Customer( 10000, "elvis@iot.com", "elvis", null, null, null, null, null, null, null, null, 1000 );
        String ELVIS_JSON = "{\"id\" : 10000, \"email\" : \"elvis@iot.com\", \"name\" : \"elvis\", \"pswd\" : \"elvis\"}";

        Customer RINGO = new Customer( 10001, "ringo@iot.com", "ringo", null, null, null, null, null, null, null, null, 1000 );
        String RINGO_JSON = "{\"id\" : 10001, \"email\" : \"ringo@iot.com\", \"name\" : \"ringo\", \"pswd\" : \"ringo\"}";

        Customer SLEDGE = new Customer( 10002, "sledge@iot.com", "sledge", null, null, null, null, null, null, null, null, 1000 );
        String SLEDGE_JSON = "{\"id\" : 10002, \"email\" : \"sledge@iot.com\", \"name\" : \"sledge\", \"pswd\" : \"sledge\"}";

        String CUSTOMERS_JSON = "{ d: { \"results\": [ "
            + ELVIS_JSON + ','
            + RINGO_JSON + ','
            + SLEDGE_JSON
            + " ] } }";

        Product PRODUCT_100 = new Product( 100,
                                           IotConstants.TestData.DEPT_1.getId(),
                                           "Boy's socks, Ages 1-3",
                                           1.99,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_100_JSON = "{\"productCode\" : 100, "
            + "\"departmentCode\" : " + IotConstants.TestData.DEPT_1.getId() + ", "
            + "\"productDescription\" : \"Boy's socks, Ages 1-3\", "
            + "\"MSRP\" : 1.99, "
            + "\"buyPrice\" : 1.00 }";

        Product PRODUCT_101 = new Product( 101,
                                           IotConstants.TestData.DEPT_1.getId(),
                                           "Boy's shirt, Size 12",
                                           10.50,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_101_JSON = "{\"productCode\" : 101, "
            + "\"departmentCode\" : " + IotConstants.TestData.DEPT_1.getId() + ", "
            + "\"productDescription\" : \"Boy's shirt, Size 12\", "
            + "\"MSRP\" : 10.50, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_102 = new Product( 102,
                                           IotConstants.TestData.DEPT_1.getId(),
                                           "Boy's shoes, Size 11",
                                           11.00,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_102_JSON = "{\"productCode\" : 102, "
            + "\"departmentCode\" : " + IotConstants.TestData.DEPT_1.getId() + ", "
            + "\"productDescription\" : \"Boy's shoes, Size 11\", "
            + "\"MSRP\" : 11.00, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_200 = new Product( 200,
                                           IotConstants.TestData.DEPT_2.getId(),
                                           "Cuff links, Gold",
                                           5.99,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_200_JSON = "{\"productCode\" : 200, "
            + "\"departmentCode\" : " + IotConstants.TestData.DEPT_2.getId() + ", "
            + "\"productDescription\" : \"Cuff links, Gold\", "
            + "\"MSRP\" : 5.99, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_201 = new Product( 201,
                                           IotConstants.TestData.DEPT_2.getId(),
                                           "Tuxedo pants, Size 32W 32L",
                                           50.50,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_201_JSON = "{\"productCode\" : 201, "
            + "\"departmentCode\" : " + IotConstants.TestData.DEPT_2.getId() + ", "
            + "\"productDescription\" : \"Tuxedo pants, Size 32W 32L\", "
            + "\"MSRP\" : 50.50, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_202 = new Product( 202,
                                           IotConstants.TestData.DEPT_2.getId(),
                                           "Tuxedo shirt, Size 15",
                                           55.00,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_202_JSON = "{\"productCode\" : 202, "
            + "\"departmentCode\" : " + IotConstants.TestData.DEPT_2.getId() + ", "
            + "\"productDescription\" : \"Tuxedo shirt, Size 15\", "
            + "\"MSRP\" : 55.00, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_300 = new Product( 300,
                                           IotConstants.TestData.DEPT_3.getId(),
                                           "Girl's socks, Ages 5-10",
                                           3.99,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_300_JSON = "{\"productCode\" : 300, "
            + "\"departmentCode\" : " + IotConstants.TestData.DEPT_3.getId() + ", "
            + "\"productDescription\" : \"Girl's socks, Ages 5-10\", "
            + "\"MSRP\" : 3.99, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_301 = new Product( 301,
                                           IotConstants.TestData.DEPT_3.getId(),
                                           "Girl's dress, White, Full length",
                                           30.50,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_301_JSON = "{\"productCode\" : " + 301 + ", "
            + "\"departmentCode\" : " + IotConstants.TestData.DEPT_3.getId() + ", "
            + "\"productDescription\" : \"Girl's dress, White, Full length\", "
            + "\"MSRP\" : 30.50, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_302 = new Product( 302,
                                           IotConstants.TestData.DEPT_3.getId(),
                                           "Girl's outfit, Shirt and Slacks",
                                           33.00,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_302_JSON = "{\"productCode\" : 302, "
            + "\"departmentCode\" : " + IotConstants.TestData.DEPT_3.getId() + ", "
            + "\"productDescription\" : \"Girl's outfit, Shirt and Slacks\", "
            + "\"MSRP\" : 33.00, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_400 = new Product( 400,
                                           IotConstants.TestData.DEPT_4.getId(),
                                           "Men's tie, Black, Thin",
                                           4.99,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_400_JSON = "{\"productCode\" : 400, "
            + "\"departmentCode\" : " + IotConstants.TestData.DEPT_4.getId() + ", "
            + "\"productDescription\" : \"Men's tie, Black, Thin\", "
            + "\"MSRP\" : 4.99, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_401 = new Product( 401,
                                           IotConstants.TestData.DEPT_4.getId(),
                                           "Men's dress pants, Size 34W 33L",
                                           40.50,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_401_JSON = "{\"productCode\" : 401, "
            + "\"departmentCode\" : " + IotConstants.TestData.DEPT_4.getId() + ", "
            + "\"productDescription\" : \"Men's dress pants, Size 34W 33L\", "
            + "\"MSRP\" : 40.50, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_402 = new Product( 402,
                                           IotConstants.TestData.DEPT_4.getId(),
                                           "Men's sport coat, Winter, Wool",
                                           44.00,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_402_JSON = "{\"productCode\" : 402, "
            + "\"departmentCode\" : " + IotConstants.TestData.DEPT_4.getId() + ", "
            + "\"productDescription\" : \"Men's sport coat, Winter, Wool3\", "
            + "\"MSRP\" : 44.00, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_500 = new Product( 500,
                                           IotConstants.TestData.DEPT_5.getId(),
                                           "Swim suit, Women's, 2 piece",
                                           5.99,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_500_JSON = "{\"productCode\" : 500, "
            + "\"departmentCode\" : " + IotConstants.TestData.DEPT_5.getId() + ", "
            + "\"productDescription\" : \"Swim suit, Women's, 2 piece\", "
            + "\"MSRP\" : 5.99, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_501 = new Product( 501,
                                           IotConstants.TestData.DEPT_5.getId(),
                                           "Jogging pants, Cleveland Cavaliers",
                                           50.50,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_501_JSON = "{\"productCode\" : 501, "
            + "\"departmentCode\" : " + IotConstants.TestData.DEPT_5.getId() + ", "
            + "\"productDescription\" : \"Jogging pants, Cleveland Cavaliers\", "
            + "\"MSRP\" : 50.50, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_502 = new Product( 502,
                                           IotConstants.TestData.DEPT_5.getId(),
                                           "St Louis Blues Jersey, Wayne Gretsky",
                                           55.00,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_502_JSON = "{\"productCode\" : 502, "
            + "\"departmentCode\" : " + IotConstants.TestData.DEPT_5.getId() + ", "
            + "\"productDescription\" : \"St Louis Blues Jersey, Wayne Gretsky\", "
            + "\"MSRP\" : 55.00, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_600 = new Product( 600,
                                           IotConstants.TestData.DEPT_6.getId(),
                                           "Woman's scarf, Multi-colored",
                                           6.99,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_600_JSON = "{\"productCode\" : 600, "
            + "\"departmentCode\" : " + IotConstants.TestData.DEPT_6.getId() + ", "
            + "\"productDescription\" : \"Woman's scarf, Multi-colored\", "
            + "\"MSRP\" : 6.99, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_601 = new Product( 601,
                                           IotConstants.TestData.DEPT_6.getId(),
                                           "Woman's summer dress, Peach",
                                           60.50,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_601_JSON = "{\"productCode\" : 601, "
            + "\"departmentCode\" : " + IotConstants.TestData.DEPT_6.getId() + ", "
            + "\"productDescription\" : \"Woman's summer dress, Peach\", "
            + "\"MSRP\" : 60.50, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_602 = new Product( 602,
                                           IotConstants.TestData.DEPT_6.getId(),
                                           "Woman's cowboy boots",
                                           66.00,
                                           1.00,
                                           null,
                                           null,
                                           null );

        String PRODUCT_602_JSON = "{\"productCode\" : 602, "
            + "\"departmentCode\" : " + IotConstants.TestData.DEPT_6.getId() + ", "
            + "\"productDescription\" : \"Woman's cowboy boots\", "
            + "\"MSRP\" : 66.00, "
            + "\"buyPrice\" : 1.00}";

        String PRODUCTS_JSON = "{ d: { \"results\": [ "
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
            + " ] } }";

        Order ORDER_1010 = new Order( 1010,
                                      ELVIS.getId(),
                                      new GregorianCalendar( 2013, 3, 31 ),
                                      12.34,
                                      null,
                                      null,
                                      null,
                                      null );
        OrderDetail DETAIL_1010_1 = new OrderDetail( ORDER_1010.getId(), PRODUCT_300.getId(), 1, 5.5, 1 );
        String DETAIL_1010_1_JSON =
            "{\"orderNumber\" : 1010, \"productCode\" : 300, \"quantityOrdered\" : 1, \"priceEach\" : 5.5, \"orderLineNumber\" : " +
                "1 }";

        OrderDetail DETAIL_1010_2 = new OrderDetail( ORDER_1010.getId(), PRODUCT_301.getId(), 2, 10.0, 2 );
        String DETAIL_1010_2_JSON =
            "{\"orderNumber\" : 1010, \"productCode\" : 301, \"quantityOrdered\" : 2, \"priceEach\" : 10.0, \"orderLineNumber\" :" +
                " 2 }";

        OrderDetail DETAIL_1010_3 = new OrderDetail( ORDER_1010.getId(), PRODUCT_302.getId(), 2, 20.5, 3 );
        String DETAIL_1010_3_JSON =
            "{\"orderNumber\" : 1010, \"productCode\" : 302, \"quantityOrdered\" : 2, \"priceEach\" : 20.5, \"orderLineNumber\" :" +
                " 3 }";

        String ORDER_1010_JSON = "{\"orderNumber\" : 1010, "
            + "\"customerNumber\" : " + ELVIS.getId() + ", "
            + "\"orderDate\" : \"Date(1463360426000)\", "
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
        String DETAIL_2020_1_JSON =
            "{\"orderNumber\" : 2020, \"productCode\" : 100, \"quantityOrdered\" : 1, \"priceEach\" : 5.5, \"orderLineNumber\" : " +
                "1 }";

        OrderDetail DETAIL_2020_2 = new OrderDetail( ORDER_2020.getId(), PRODUCT_200.getId(), 2, 10.0, 2 );
        String DETAIL_2020_2_JSON =
            "{\"orderNumber\" : 2020, \"productCode\" : 200, \"quantityOrdered\" : 2, \"priceEach\" : 10.0, \"orderLineNumber\" :" +
                " 2 }";

        OrderDetail DETAIL_2020_3 = new OrderDetail( ORDER_2020.getId(), PRODUCT_602.getId(), 2, 20.5, 3 );
        String DETAIL_2020_3_JSON =
            "{\"orderNumber\" : 2020, \"productCode\" : 602, \"quantityOrdered\" : 2, \"priceEach\" : 20.5, \"orderLineNumber\" :" +
                " 3 }";

        OrderDetail DETAIL_2020_4 = new OrderDetail( ORDER_2020.getId(), PRODUCT_400.getId(), 1, 5.5, 4 );
        String DETAIL_2020_4_JSON =
            "{\"orderNumber\" : 2020, \"productCode\" : 400, \"quantityOrdered\" : 1, \"priceEach\" : 5.5, \"orderLineNumber\" : " +
                "4 }";

        OrderDetail DETAIL_2020_5 = new OrderDetail( ORDER_2020.getId(), PRODUCT_500.getId(), 2, 10.0, 5 );
        String DETAIL_2020_5_JSON =
            "{\"orderNumber\" : 2020, \"productCode\" : 500, \"quantityOrdered\" : 2, \"priceEach\" : 10.0, \"orderLineNumber\" :" +
                " 5 }";

        OrderDetail DETAIL_2020_6 = new OrderDetail( ORDER_2020.getId(), PRODUCT_600.getId(), 2, 20.5, 6 );
        String DETAIL_2020_6_JSON =
            "{\"orderNumber\" : 2020, \"productCode\" : 600, \"quantityOrdered\" : 2, \"priceEach\" : 20.5, \"orderLineNumber\" :" +
                " 6 }";

        String ORDER_2020_JSON = "{\"orderNumber\" : 2020, "
            + "\"customerNumber\" : " + RINGO.getId() + ", "
            + "\"orderDate\" : \"Date(1463360451000)\", "
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
        String DETAIL_3030_1_JSON =
            "{\"orderNumber\" : 3030, \"productCode\" : 202, \"quantityOrdered\" : 1, \"priceEach\" : 5.5, \"orderLineNumber\" : " +
                "1 }";

        OrderDetail DETAIL_3030_2 = new OrderDetail( ORDER_3030.getId(), PRODUCT_102.getId(), 2, 10.0, 2 );
        String DETAIL_3030_2_JSON =
            "{\"orderNumber\" : 3030, \"productCode\" : 102, \"quantityOrdered\" : 2, \"priceEach\" : 10.0, \"orderLineNumber\" :" +
                " 2 }";

        String ORDER_3030_JSON = "{\"orderNumber\" : 3030, "
            + "\"customerNumber\" : " + RINGO.getId() + ", "
            + "\"orderDate\" : \"Date(1419033600000)\", "
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
        String DETAIL_4040_1_JSON =
            "{\"orderNumber\" : 4040, \"productCode\" : 101, \"quantityOrdered\" : 1, \"priceEach\" : 5.5, \"orderLineNumber\" : " +
                "1 }";

        OrderDetail DETAIL_4040_2 = new OrderDetail( ORDER_4040.getId(), PRODUCT_201.getId(), 2, 10.0, 2 );
        String DETAIL_4040_2_JSON =
            "{\"orderNumber\" : 4040, \"productCode\" : 201, \"quantityOrdered\" : 2, \"priceEach\" : 10.0, \"orderLineNumber\" :" +
                " 2 }";

        OrderDetail DETAIL_4040_3 = new OrderDetail( ORDER_4040.getId(), PRODUCT_402.getId(), 2, 20.5, 3 );
        String DETAIL_4040_3_JSON =
            "{\"orderNumber\" : 4040, \"productCode\" : 402, \"quantityOrdered\" : 2, \"priceEach\" : 20.5, \"orderLineNumber\" :" +
                " 3 }";

        String ORDER_4040_JSON = "{\"orderNumber\" : 4040, "
            + "\"customerNumber\" : " + SLEDGE.getId() + ", "
            + "\"orderDate\" : \"Date(1419033600000)\", "
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
        String DETAIL_5050_1_JSON =
            "{\"orderNumber\" : 5050, \"productCode\" : 301, \"quantityOrdered\" : 1, \"priceEach\" : 5.5, \"orderLineNumber\" : " +
                "1 }";

        OrderDetail DETAIL_5050_2 = new OrderDetail( ORDER_5050.getId(), PRODUCT_401.getId(), 2, 10.0, 2 );
        String DETAIL_5050_2_JSON =
            "{\"orderNumber\" : 5050, \"productCode\" : 401, \"quantityOrdered\" : 2, \"priceEach\" : 10.0, \"orderLineNumber\" :" +
                " 2 }";

        OrderDetail DETAIL_5050_3 = new OrderDetail( ORDER_5050.getId(), PRODUCT_501.getId(), 2, 20.5, 3 );
        String DETAIL_5050_3_JSON =
            "{\"orderNumber\" : 5050, \"productCode\" : 501, \"quantityOrdered\" : 2, \"priceEach\" : 20.5, \"orderLineNumber\" :" +
                " 3 }";

        String ORDER_5050_JSON = "{\"orderNumber\" : 5050, "
            + "\"customerNumber\" : " + SLEDGE.getId() + ", "
            + "\"orderDate\" : \"Date(1419033600000)\", "
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
        String DETAIL_6060_1_JSON =
            "{\"orderNumber\" : 6060, \"productCode\" : 100, \"quantityOrdered\" : 1, \"priceEach\" : 5.5, \"orderLineNumber\" : " +
                "1 }";

        OrderDetail DETAIL_6060_2 = new OrderDetail( ORDER_6060.getId(), PRODUCT_502.getId(), 2, 10.0, 2 );
        String DETAIL_6060_2_JSON =
            "{\"orderNumber\" : 6060, \"productCode\" : 502, \"quantityOrdered\" : 2, \"priceEach\" : 10.0, \"orderLineNumber\" :" +
                " 2 }";

        String ORDER_6060_JSON = "{\"orderNumber\" : 6060, "
            + "\"customerNumber\" : " + SLEDGE.getId() + ", "
            + "\"orderDate\" : \"Date(1419033600000)\", "
            + "\"price\" : 36.78}";

        String ORDERS_JSON = "{ d: { \"results\": [ "
            + ORDER_1010_JSON + ','
            + ORDER_2020_JSON + ','
            + ORDER_3030_JSON + ','
            + ORDER_4040_JSON + ','
            + ORDER_5050_JSON + ','
            + ORDER_6060_JSON
            + " ] } }";

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

        String ORDER_DETAILS_JSON = "{ d: { \"results\": [ "
            + DETAIL_1010_1_JSON + ','
            + DETAIL_1010_2_JSON + ','
            + DETAIL_1010_3_JSON + ','
            + DETAIL_2020_1_JSON + ','
            + DETAIL_2020_2_JSON + ','
            + DETAIL_2020_3_JSON + ','
            + DETAIL_2020_4_JSON + ','
            + DETAIL_2020_5_JSON + ','
            + DETAIL_2020_6_JSON + ','
            + DETAIL_3030_1_JSON + ','
            + DETAIL_3030_2_JSON + ','
            + DETAIL_4040_1_JSON + ','
            + DETAIL_4040_2_JSON + ','
            + DETAIL_4040_3_JSON + ','
            + DETAIL_5050_1_JSON + ','
            + DETAIL_5050_2_JSON + ','
            + DETAIL_5050_3_JSON + ','
            + DETAIL_6060_1_JSON + ','
            + DETAIL_6060_2_JSON
            + " ] } }";

        Promotion PROMO_1 = new Promotion( 1, IotConstants.TestData.PRODUCT_100.getId(), 10.0 );
        String PROMO_1_JSON = "{\"id\" : 1, \"productCode\" : \"100\", \"discount\" : \"10.0\"}";

        Promotion PROMO_2 = new Promotion( 2, IotConstants.TestData.PRODUCT_101.getId(), 20.0 );
        String PROMO_2_JSON = "{\"id\" : 2, \"productCode\" : \"101\", \"discount\" : \"20.0\"}";

        Promotion PROMO_3 = new Promotion( 3, IotConstants.TestData.PRODUCT_200.getId(), 30.0 );
        String PROMO_3_JSON = "{\"id\" : 3, \"productCode\" : \"200\", \"discount\" : \"30.0\"}";

        Promotion PROMO_4 = new Promotion( 4, IotConstants.TestData.PRODUCT_201.getId(), 40.0 );
        String PROMO_4_JSON = "{\"id\" : 4, \"productCode\" : \"201\", \"discount\" : \"40.0\"}";

        Promotion PROMO_5 = new Promotion( 5, IotConstants.TestData.PRODUCT_300.getId(), 50.0 );
        String PROMO_5_JSON = "{\"id\" : 5, \"productCode\" : \"300\", \"discount\" : \"50.0\"}";

        Promotion PROMO_6 = new Promotion( 6, IotConstants.TestData.PRODUCT_301.getId(), 10.0 );
        String PROMO_6_JSON = "{\"id\" : 6, \"productCode\" : \"301\", \"discount\" : \"10.0\"}";

        Promotion PROMO_7 = new Promotion( 7, IotConstants.TestData.PRODUCT_400.getId(), 20.0 );
        String PROMO_7_JSON = "{\"id\" : 7, \"productCode\" : \"400\", \"discount\" : \"20.0\"}";

        Promotion PROMO_8 = new Promotion( 8, IotConstants.TestData.PRODUCT_401.getId(), 30.0 );
        String PROMO_8_JSON = "{\"id\" : 8, \"productCode\" : \"401\", \"discount\" : \"30.0\"}";

        Promotion PROMO_9 = new Promotion( 9, IotConstants.TestData.PRODUCT_500.getId(), 40.0 );
        String PROMO_9_JSON = "{\"id\" : 9, \"productCode\" : \"500\", \"discount\" : \"40.0\"}";

        Promotion PROMO_10 = new Promotion( 10, IotConstants.TestData.PRODUCT_501.getId(), 50.0 );
        String PROMO_10_JSON = "{\"id\" : 10, \"productCode\" : \"501\", \"discount\" : \"50.0\"}";

        Promotion PROMO_11 = new Promotion( 11, IotConstants.TestData.PRODUCT_600.getId(), 10.0 );
        String PROMO_11_JSON = "{\"id\" : 11, \"productCode\" : \"600\", \"discount\" : \"10.0\"}";

        Promotion PROMO_12 = new Promotion( 12, IotConstants.TestData.PRODUCT_601.getId(), 20.0 );
        String PROMO_12_JSON = "{\"id\" : 12, \"productCode\" : \"601\", \"discount\" : \"20.0\"}";

        String PROMOTIONS_JSON = "{ d: { \"results\": [ "
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
            + " ] } }";

    }

}
