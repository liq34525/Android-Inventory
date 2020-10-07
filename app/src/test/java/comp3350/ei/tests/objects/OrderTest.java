//package comp3350.ei.tests.objects;
//
//import org.junit.Test;
//
//import comp3350.ei.objects.Order;
//import comp3350.ei.objects.StoreProduct;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//public class OrderTest {
//    @Test
//    public void testOrder() {
//        System.out.println("\nStarting OrderTest 1");
//
//        Order order;
//        int storeId = 12345;
//
//        String orderId = "0011";
//        String year = "2019";
//        String month = "06";
//        String day = "14";
//
//        order = new Order(orderId, storeId, year, month, day);
//        assertNotNull(order);
//
//        // test getters
//        assertEquals(orderId, order.getOrderId());
//        assertEquals(storeId, order.getStoreId());
//        assertEquals(year, order.getYear());
//        assertEquals(month, order.getMonth());
//        assertEquals(day, order.getDay());
//        System.out.println("Finished OrderTest 1");
//    }
//
//    @Test
//    public void testOrderConstructor2() {
//        System.out.println("\nStarting OrderTest 3");
//
//        String orderId = "0011";
//
//        Order o = new Order(orderId);
//        assertEquals(orderId, o.getOrderId());
//        assertNull(o.getYear());
//        assertNull(o.getMonth());
//        assertNull(o.getDay());
//
//        System.out.println("Finished OrderTest 3");
//    }
//
//    @Test
//    public void testSetters() {
//        System.out.println("\nStarting OrderTest 4");
//
//        String orderId = "0011";
//        int storeId = 12345;
//        String year = "2019";
//        String month = "06";
//        String day = "14";
//
//        Order order = new Order(orderId, storeId, year, month, day);
//
//        String newOrderID = "93";
//        int newStoreId = 66;
//        String newYear = "2014";
//        String newMonth = "11";
//        String newDay = "20";
//
//        order.setOrderId(newOrderID);
//        order.setStoreId(newStoreId);
//        order.setYear(newYear);
//        order.setMonth(newMonth);
//        order.setDay(newDay);
//
//        assertEquals(newOrderID, order.getOrderId());
//        assertEquals(newStoreId, order.getStoreId());
//        assertEquals(newYear, order.getYear());
//        assertEquals(newMonth, order.getMonth());
//        assertEquals(newDay, order.getDay());
//
//        System.out.println("Finished OrderTest 4");
//    }
//
//    @Test
//    public void testAddStoreProducts() {
//        System.out.println("\nStarting OrderTest 5");
//
//        String orderId = "0011";
//        int storeId = 12345;
//        String year = "2019";
//        String month = "06";
//        String day = "14";
//
//        Order order = new Order(orderId, storeId, year, month, day);
//
//        String productId = "1001111011";
//        double price = 0.5;
//        StoreProduct ivp = new StoreProduct(storeId, productId, price, 30);
//        assertNotNull(ivp);
//
//        order.addStoreProduct(ivp);
//
//        List<StoreProduct> tempList = order.getOrderedProducts();
//        assertNull(tempList);
//
//        String expected =  "Order ID: " + orderId + ", Store ID: " + storeId + ", Date: "
//                + year + "/" + month + "/" + day;
//        assertEquals(expected, order.toString());
//
//        List<StoreProduct> newList = new ArrayList<>();
//        StoreProduct newP = new StoreProduct(123123, "321321", 321);
//
//
//        newList.put(newP, 321);
//        order.setOrderedProduct(newList);
//
//        tempList = order.getOrderedProducts();
//        assertNull(tempList);
//
//        System.out.println("Finished OrderTest 5");
//    }
//
//}
