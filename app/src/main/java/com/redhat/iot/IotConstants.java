package com.redhat.iot;

import com.redhat.iot.domain.Customer;
import com.redhat.iot.domain.OrderDetail;
import com.redhat.iot.domain.Product;

import java.text.SimpleDateFormat;

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
         * The customer ID when no one is logged in. Value is {@value}.
         *
         * @see Customer#UNKNOWN_USER
         */
        int DEFAULT_CUSTOMER_ID = Customer.UNKNOWN_USER;

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

        /**
         * The name of the preference holding the department IDs of the last viewed promotions.
         */
        String PROMO_DEPT_IDS = "IoTPrefs";

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

        int DEPT_1_ID = 1000;
        String DEPT_1_JSON =
            "{ \"departmentCode\" : \"" + DEPT_1_ID + "\", \"departmentName\" : \"Womans\", \"departmentDescription\" : \"Womans " +
                "clothing and assessories.\" }";

        int DEPT_2_ID = 1001;
        String DEPT_2_JSON =
            "{ \"departmentCode\" : \"" + DEPT_2_ID + "\", \"departmentName\" : \"Boys\", \"departmentDescription\" : \"Boys " +
                "clothing and assessories.\" }";

        int DEPT_3_ID = 1002;
        String DEPT_3_JSON =
            "{ \"departmentCode\" : \"" + DEPT_3_ID + "\", \"departmentName\" : \"Girls\", \"departmentDescription\" : \"Girls " +
                "clothing and assessories.\" }";

        int DEPT_4_ID = 1003;
        String DEPT_4_JSON =
            "{ \"departmentCode\" : \"" + DEPT_4_ID + "\", \"departmentName\" : \"Mens\", \"departmentDescription\" : \"Mens " +
                "clothing and assessories.\" }";

        int DEPT_5_ID = 1004;
        String DEPT_5_JSON =
            "{ \"departmentCode\" : \"" + DEPT_5_ID + "\", \"departmentName\" : \"Formal\", \"departmentDescription\" : \"Formal " +
                "wear for men and women.\" }";

        int DEPT_6_ID = 1005;
        String DEPT_6_JSON =
            "{ \"departmentCode\" : \"" + DEPT_6_ID + "\", \"departmentName\" : \"Sport\", \"departmentDescription\" : \"Sports " +
                "wear for men and women.\" }";

        String DEPARTMENTS_JSON = "{ d: { \"results\": [ "
            + DEPT_1_JSON + ','
            + DEPT_2_JSON + ','
            + DEPT_3_JSON + ','
            + DEPT_4_JSON + ','
            + DEPT_5_JSON + ','
            + DEPT_6_JSON
            + " ] } }";

        int ELVIS_ID = 10000;
        int RINGO_ID = 10001;
        int SLEDGE_ID = 10002;

        Product PRODUCT_100 = new Product( 100,
                                           DEPT_1_ID,
                                           "Boy's socks, Ages 1-3",
                                           1.99,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_100_JSON = "{\"productCode\" : 100, "
            + "\"departmentCode\" : " + DEPT_1_ID + ", "
            + "\"productDescription\" : \"Boy's socks, Ages 1-3\", "
            + "\"MSRP\" : 1.99, "
            + "\"buyPrice\" : 1.00 }";

        Product PRODUCT_101 = new Product( 101,
                                           DEPT_1_ID,
                                           "Boy's shirt, Size 12",
                                           10.50,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_101_JSON = "{\"productCode\" : 101, "
            + "\"departmentCode\" : " + DEPT_1_ID + ", "
            + "\"productDescription\" : \"Boy's shirt, Size 12\", "
            + "\"MSRP\" : 10.50, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_102 = new Product( 102,
                                           DEPT_1_ID,
                                           "Boy's shoes, Size 11",
                                           11.00,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_102_JSON = "{\"productCode\" : 102, "
            + "\"departmentCode\" : " + DEPT_1_ID + ", "
            + "\"productDescription\" : \"Boy's shoes, Size 11\", "
            + "\"MSRP\" : 11.00, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_200 = new Product( 200,
                                           DEPT_2_ID,
                                           "Cuff links, Gold",
                                           5.99,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_200_JSON = "{\"productCode\" : 200, "
            + "\"departmentCode\" : " + DEPT_2_ID + ", "
            + "\"productDescription\" : \"Cuff links, Gold\", "
            + "\"MSRP\" : 5.99, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_201 = new Product( 201,
                                           DEPT_2_ID,
                                           "Tuxedo pants, Size 32W 32L",
                                           50.50,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_201_JSON = "{\"productCode\" : 201, "
            + "\"departmentCode\" : " + DEPT_2_ID + ", "
            + "\"productDescription\" : \"Tuxedo pants, Size 32W 32L\", "
            + "\"MSRP\" : 50.50, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_202 = new Product( 202,
                                           DEPT_2_ID,
                                           "Tuxedo shirt, Size 15",
                                           55.00,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_202_JSON = "{\"productCode\" : 202, "
            + "\"departmentCode\" : " + DEPT_2_ID + ", "
            + "\"productDescription\" : \"Tuxedo shirt, Size 15\", "
            + "\"MSRP\" : 55.00, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_300 = new Product( 300,
                                           DEPT_3_ID,
                                           "Girl's socks, Ages 5-10",
                                           3.99,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_300_JSON = "{\"productCode\" : 300, "
            + "\"departmentCode\" : " + DEPT_3_ID + ", "
            + "\"productDescription\" : \"Girl's socks, Ages 5-10\", "
            + "\"MSRP\" : 3.99, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_301 = new Product( 301,
                                           DEPT_3_ID,
                                           "Girl's dress, White, Full length",
                                           30.50,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_301_JSON = "{\"productCode\" : " + 301 + ", "
            + "\"departmentCode\" : " + DEPT_3_ID + ", "
            + "\"productDescription\" : \"Girl's dress, White, Full length\", "
            + "\"MSRP\" : 30.50, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_302 = new Product( 302,
                                           DEPT_3_ID,
                                           "Girl's outfit, Shirt and Slacks",
                                           33.00,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_302_JSON = "{\"productCode\" : 302, "
            + "\"departmentCode\" : " + DEPT_3_ID + ", "
            + "\"productDescription\" : \"Girl's outfit, Shirt and Slacks\", "
            + "\"MSRP\" : 33.00, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_400 = new Product( 400,
                                           DEPT_4_ID,
                                           "Men's tie, Black, Thin",
                                           4.99,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_400_JSON = "{\"productCode\" : 400, "
            + "\"departmentCode\" : " + DEPT_4_ID + ", "
            + "\"productDescription\" : \"Men's tie, Black, Thin\", "
            + "\"MSRP\" : 4.99, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_401 = new Product( 401,
                                           DEPT_4_ID,
                                           "Men's dress pants, Size 34W 33L",
                                           40.50,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_401_JSON = "{\"productCode\" : 401, "
            + "\"departmentCode\" : " + DEPT_4_ID + ", "
            + "\"productDescription\" : \"Men's dress pants, Size 34W 33L\", "
            + "\"MSRP\" : 40.50, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_402 = new Product( 402,
                                           DEPT_4_ID,
                                           "Men's sport coat, Winter, Wool",
                                           44.00,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_402_JSON = "{\"productCode\" : 402, "
            + "\"departmentCode\" : " + DEPT_4_ID + ", "
            + "\"productDescription\" : \"Men's sport coat, Winter, Wool3\", "
            + "\"MSRP\" : 44.00, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_500 = new Product( 500,
                                           DEPT_5_ID,
                                           "Swim suit, Women's, 2 piece",
                                           5.99,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_500_JSON = "{\"productCode\" : 500, "
            + "\"departmentCode\" : " + DEPT_5_ID + ", "
            + "\"productDescription\" : \"Swim suit, Women's, 2 piece\", "
            + "\"MSRP\" : 5.99, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_501 = new Product( 501,
                                           DEPT_5_ID,
                                           "Jogging pants, Cleveland Cavaliers",
                                           50.50,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_501_JSON = "{\"productCode\" : 501, "
            + "\"departmentCode\" : " + DEPT_5_ID + ", "
            + "\"productDescription\" : \"Jogging pants, Cleveland Cavaliers\", "
            + "\"MSRP\" : 50.50, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_502 = new Product( 502,
                                           DEPT_5_ID,
                                           "St Louis Blues Jersey, Wayne Gretsky",
                                           55.00,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_502_JSON = "{\"productCode\" : 502, "
            + "\"departmentCode\" : " + DEPT_5_ID + ", "
            + "\"productDescription\" : \"St Louis Blues Jersey, Wayne Gretsky\", "
            + "\"MSRP\" : 55.00, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_600 = new Product( 600,
                                           DEPT_6_ID,
                                           "Woman's scarf, Multi-colored",
                                           6.99,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_600_JSON = "{\"productCode\" : 600, "
            + "\"departmentCode\" : " + DEPT_6_ID + ", "
            + "\"productDescription\" : \"Woman's scarf, Multi-colored\", "
            + "\"MSRP\" : 6.99, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_601 = new Product( 601,
                                           DEPT_6_ID,
                                           "Woman's summer dress, Peach",
                                           60.50,
                                           1.00,
                                           null,
                                           null,
                                           null );
        String PRODUCT_601_JSON = "{\"productCode\" : 601, "
            + "\"departmentCode\" : " + DEPT_6_ID + ", "
            + "\"productDescription\" : \"Woman's summer dress, Peach\", "
            + "\"MSRP\" : 60.50, "
            + "\"buyPrice\" : 1.00}";

        Product PRODUCT_602 = new Product( 602,
                                           DEPT_6_ID,
                                           "Woman's cowboy boots",
                                           66.00,
                                           1.00,
                                           null,
                                           null,
                                           null );

        String PRODUCT_602_JSON = "{\"productCode\" : 602, "
            + "\"departmentCode\" : " + DEPT_6_ID + ", "
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

        int ORDER_1010_ID = 1010;
        OrderDetail DETAIL_1010_1 = new OrderDetail( ORDER_1010_ID, PRODUCT_300.getId(), 1, 5.5, 1 );
        String DETAIL_1010_1_JSON =
            "{\"orderNumber\" : 1010, \"productCode\" : 300, \"quantityOrdered\" : 1, \"msrp\" : 5.5, \"discount\" : " +
                "10 }";

        OrderDetail DETAIL_1010_2 = new OrderDetail( ORDER_1010_ID, PRODUCT_301.getId(), 2, 10.0, 2 );
        String DETAIL_1010_2_JSON =
            "{\"orderNumber\" : 1010, \"productCode\" : 301, \"quantityOrdered\" : 2, \"msrp\" : 10.0, \"discount\" :" +
                " 20 }";

        OrderDetail DETAIL_1010_3 = new OrderDetail( ORDER_1010_ID, PRODUCT_302.getId(), 2, 20.5, 3 );
        String DETAIL_1010_3_JSON =
            "{\"orderNumber\" : 1010, \"productCode\" : 302, \"quantityOrdered\" : 2, \"msrp\" : 20.5, \"discount\" :" +
                " 30 }";

        String ORDER_1010_JSON = "{\"orderNumber\" : 1010, "
            + "\"customerNumber\" : " + ELVIS_ID + ", "
            + "\"orderDate\" : \"/Date(1463360426000)/\"}";

        int ORDER_2020_ID = 2020;
        OrderDetail DETAIL_2020_1 = new OrderDetail( ORDER_2020_ID, PRODUCT_100.getId(), 1, 5.5, 1 );
        String DETAIL_2020_1_JSON =
            "{\"orderNumber\" : 2020, \"productCode\" : 100, \"quantityOrdered\" : 1, \"msrp\" : 5.5, \"discount\" : " +
                "10 }";

        OrderDetail DETAIL_2020_2 = new OrderDetail( ORDER_2020_ID, PRODUCT_200.getId(), 2, 10.0, 2 );
        String DETAIL_2020_2_JSON =
            "{\"orderNumber\" : 2020, \"productCode\" : 200, \"quantityOrdered\" : 2, \"msrp\" : 10.0, \"discount\" :" +
                " 20 }";

        OrderDetail DETAIL_2020_3 = new OrderDetail( ORDER_2020_ID, PRODUCT_602.getId(), 2, 20.5, 3 );
        String DETAIL_2020_3_JSON =
            "{\"orderNumber\" : 2020, \"productCode\" : 602, \"quantityOrdered\" : 2, \"msrp\" : 20.5, \"discount\" :" +
                " 30 }";

        OrderDetail DETAIL_2020_4 = new OrderDetail( ORDER_2020_ID, PRODUCT_400.getId(), 1, 5.5, 4 );
        String DETAIL_2020_4_JSON =
            "{\"orderNumber\" : 2020, \"productCode\" : 400, \"quantityOrdered\" : 1, \"msrp\" : 5.5, \"discount\" : " +
                "40 }";

        OrderDetail DETAIL_2020_5 = new OrderDetail( ORDER_2020_ID, PRODUCT_500.getId(), 2, 10.0, 5 );
        String DETAIL_2020_5_JSON =
            "{\"orderNumber\" : 2020, \"productCode\" : 500, \"quantityOrdered\" : 2, \"msrp\" : 10.0, \"discount\" :" +
                " 15 }";

        OrderDetail DETAIL_2020_6 = new OrderDetail( ORDER_2020_ID, PRODUCT_600.getId(), 2, 20.5, 6 );
        String DETAIL_2020_6_JSON =
            "{\"orderNumber\" : 2020, \"productCode\" : 600, \"quantityOrdered\" : 2, \"msrp\" : 20.5, \"discount\" :" +
                " 25 }";

        String ORDER_2020_JSON = "{\"orderNumber\" : 2020, "
            + "\"customerNumber\" : " + RINGO_ID + ", "
            + "\"orderDate\" : \"/Date(1463360451000)/\"}";

        int ORDER_3030_ID = 3030;
        OrderDetail DETAIL_3030_1 = new OrderDetail( ORDER_3030_ID, PRODUCT_202.getId(), 1, 5.5, 1 );
        String DETAIL_3030_1_JSON =
            "{\"orderNumber\" : 3030, \"productCode\" : 202, \"quantityOrdered\" : 1, \"msrp\" : 5.5, \"discount\" : " +
                "10 }";

        OrderDetail DETAIL_3030_2 = new OrderDetail( ORDER_3030_ID, PRODUCT_102.getId(), 2, 10.0, 2 );
        String DETAIL_3030_2_JSON =
            "{\"orderNumber\" : 3030, \"productCode\" : 102, \"quantityOrdered\" : 2, \"msrp\" : 10.0, \"discount\" :" +
                " 20 }";

        String ORDER_3030_JSON = "{\"orderNumber\" : 3030, "
            + "\"customerNumber\" : " + RINGO_ID + ", "
            + "\"orderDate\" : \"/Date(1419033600000)/\"}";

        int ORDER_4040_ID = 4040;
        OrderDetail DETAIL_4040_1 = new OrderDetail( ORDER_4040_ID, PRODUCT_101.getId(), 1, 5.5, 1 );
        String DETAIL_4040_1_JSON =
            "{\"orderNumber\" : 4040, \"productCode\" : 101, \"quantityOrdered\" : 1, \"msrp\" : 5.5, \"discount\" : " +
                "10 }";

        OrderDetail DETAIL_4040_2 = new OrderDetail( ORDER_4040_ID, PRODUCT_201.getId(), 2, 10.0, 2 );
        String DETAIL_4040_2_JSON =
            "{\"orderNumber\" : 4040, \"productCode\" : 201, \"quantityOrdered\" : 2, \"msrp\" : 10.0, \"discount\" :" +
                " 20 }";

        OrderDetail DETAIL_4040_3 = new OrderDetail( ORDER_4040_ID, PRODUCT_402.getId(), 2, 20.5, 3 );
        String DETAIL_4040_3_JSON =
            "{\"orderNumber\" : 4040, \"productCode\" : 402, \"quantityOrdered\" : 2, \"msrp\" : 20.5, \"discount\" :" +
                " 30 }";

        String ORDER_4040_JSON = "{\"orderNumber\" : 4040, "
            + "\"customerNumber\" : " + SLEDGE_ID + ", "
            + "\"orderDate\" : \"/Date(1419033600000)/\"}";

        int ORDER_5050_ID = 5050;
        OrderDetail DETAIL_5050_1 = new OrderDetail( ORDER_5050_ID, PRODUCT_301.getId(), 1, 5.5, 1 );
        String DETAIL_5050_1_JSON =
            "{\"orderNumber\" : 5050, \"productCode\" : 301, \"quantityOrdered\" : 1, \"msrp\" : 5.5, \"discount\" : " +
                "10 }";

        OrderDetail DETAIL_5050_2 = new OrderDetail( ORDER_5050_ID, PRODUCT_401.getId(), 2, 10.0, 2 );
        String DETAIL_5050_2_JSON =
            "{\"orderNumber\" : 5050, \"productCode\" : 401, \"quantityOrdered\" : 2, \"msrp\" : 10.0, \"discount\" :" +
                " 20 }";

        OrderDetail DETAIL_5050_3 = new OrderDetail( ORDER_5050_ID, PRODUCT_501.getId(), 2, 20.5, 3 );
        String DETAIL_5050_3_JSON =
            "{\"orderNumber\" : 5050, \"productCode\" : 501, \"quantityOrdered\" : 2, \"msrp\" : 20.5, \"discount\" :" +
                " 30 }";

        String ORDER_5050_JSON = "{\"orderNumber\" : 5050, "
            + "\"customerNumber\" : " + SLEDGE_ID + ", "
            + "\"orderDate\" : \"/Date(1419033600000)/\"}";

        int ORDER_6060_ID = 6060;
        OrderDetail DETAIL_6060_1 = new OrderDetail( ORDER_6060_ID, PRODUCT_100.getId(), 1, 5.5, 1 );
        String DETAIL_6060_1_JSON =
            "{\"orderNumber\" : 6060, \"productCode\" : 100, \"quantityOrdered\" : 1, \"msrp\" : 5.5, \"discount\" : " +
                "10 }";

        OrderDetail DETAIL_6060_2 = new OrderDetail( ORDER_6060_ID, PRODUCT_502.getId(), 2, 10.0, 2 );
        String DETAIL_6060_2_JSON =
            "{\"orderNumber\" : 6060, \"productCode\" : 502, \"quantityOrdered\" : 2, \"msrp\" : 10.0, \"discount\" :" +
                " 20 }";

        String ORDER_6060_JSON = "{\"orderNumber\" : 6060, "
            + "\"customerNumber\" : " + SLEDGE_ID + ", "
            + "\"orderDate\" : \"/Date(1419033600000)/\"}";

        String ELVIS_ORDERS_JSON = "{ d: { \"results\": [ "
            + ORDER_1010_JSON
            + " ] } }";

        String RINGO_ORDERS_JSON = "{ d: { \"results\": [ "
            + ORDER_2020_JSON + ','
            + ORDER_3030_JSON
            + " ] } }";

        String SLEDGE_ORDERS_JSON = "{ d: { \"results\": [ "
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

        String ORDER_1010_DETAILS_JSON = "{ d: { \"results\": [ "
            + DETAIL_1010_1_JSON + ','
            + DETAIL_1010_2_JSON + ','
            + DETAIL_1010_3_JSON
            + " ] } }";

        String ORDER_2020_DETAILS_JSON = "{ d: { \"results\": [ "
            + DETAIL_2020_1_JSON + ','
            + DETAIL_2020_2_JSON + ','
            + DETAIL_2020_3_JSON + ','
            + DETAIL_2020_4_JSON + ','
            + DETAIL_2020_5_JSON + ','
            + DETAIL_2020_6_JSON
            + " ] } }";

        String ORDER_3030_DETAILS_JSON = "{ d: { \"results\": [ "
            + DETAIL_3030_1_JSON + ','
            + DETAIL_3030_2_JSON
            + " ] } }";

        String ORDER_4040_DETAILS_JSON = "{ d: { \"results\": [ "
            + DETAIL_4040_1_JSON + ','
            + DETAIL_4040_2_JSON + ','
            + DETAIL_4040_3_JSON
            + " ] } }";

        String ORDER_5050_DETAILS_JSON = "{ d: { \"results\": [ "
            + DETAIL_5050_1_JSON + ','
            + DETAIL_5050_2_JSON + ','
            + DETAIL_5050_3_JSON
            + " ] } }";

        String ORDER_6060__DETAILS_JSON = "{ d: { \"results\": [ "
            + DETAIL_6060_1_JSON + ','
            + DETAIL_6060_2_JSON
            + " ] } }";

        String PROMO_1_JSON = "{\"id\" : 1, \"productCode\" : \"100\", \"discount\" : \"10.0\"}";
        String PROMO_2_JSON = "{\"id\" : 2, \"productCode\" : \"101\", \"discount\" : \"20.0\"}";
        String PROMO_3_JSON = "{\"id\" : 3, \"productCode\" : \"200\", \"discount\" : \"30.0\"}";
        String PROMO_4_JSON = "{\"id\" : 4, \"productCode\" : \"201\", \"discount\" : \"40.0\"}";
        String PROMO_5_JSON = "{\"id\" : 5, \"productCode\" : \"300\", \"discount\" : \"50.0\"}";
        String PROMO_6_JSON = "{\"id\" : 6, \"productCode\" : \"301\", \"discount\" : \"10.0\"}";
        String PROMO_7_JSON = "{\"id\" : 7, \"productCode\" : \"400\", \"discount\" : \"20.0\"}";
        String PROMO_8_JSON = "{\"id\" : 8, \"productCode\" : \"401\", \"discount\" : \"30.0\"}";
        String PROMO_9_JSON = "{\"id\" : 9, \"productCode\" : \"500\", \"discount\" : \"40.0\"}";
        String PROMO_10_JSON = "{\"id\" : 10, \"productCode\" : \"501\", \"discount\" : \"50.0\"}";
        String PROMO_11_JSON = "{\"id\" : 11, \"productCode\" : \"600\", \"discount\" : \"10.0\"}";
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
