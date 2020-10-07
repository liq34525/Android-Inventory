package comp3350.ei.tests.objects;

import org.junit.Test;

import comp3350.ei.objects.Product;
import comp3350.ei.objects.StoreProduct;
import comp3350.ei.objects.OrderOut;
import comp3350.ei.objects.Store;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class OrderOutTest {
    @Test
    public void testOrderOut() {
        System.out.println("\nStarting OrderInTest 1");

        OrderOut order;
        int storeId = 55;
        Store store = new Store(storeId);
        int orderId = 4011;
        String year = "2019";
        String month = "06";
        String day = "14";

        order = new OrderOut(orderId, store, year, month, day);
        assertNotNull(order);

        // test getters
        assertEquals(orderId, order.getOrderId());
        assertEquals(store, order.getStore());
        assertEquals(year, order.getYear());
        assertEquals(month, order.getMonth());
        assertEquals(day, order.getDay());
        System.out.println("Finished OrderOutTest 1");
    }

    @Test
    public void testOrderOutConstructor1() {
        System.out.println("\nStarting OrderOutTest 2");

        Store store = new Store();

        OrderOut o = new OrderOut(store);

        assertNotNull(o.getProducts());
        assertEquals(0, o.getProducts().size());
        assertNull(o.getYear());
        assertNull(o.getMonth());
        assertNull(o.getDay());

        System.out.println("Finished OrderOutTest 2");
    }

    @Test
    public void testOrderOutConstructor2() {
        System.out.println("\nStarting OrderInTest 3");

        OrderOut o = new OrderOut();

        assertNull(o.getYear());
        assertNull(o.getMonth());
        assertNull(o.getDay());

        System.out.println("Finished OrderOutTest 3");
    }

    @Test
    public void testSetters() {
        System.out.println("\nStarting OrderOutTest 4");

        OrderOut order;
        int storeId = 55;
        Store store = new Store(storeId);
        int orderId = 4011;
        String year = "2019";
        String month = "06";
        String day = "14";

        order = new OrderOut(orderId, store, year, month, day);

        int newOrderID = 93;
        String newYear = "2014";
        String newMonth = "11";
        String newDay = "20";

        order.setOrderId(newOrderID);
        order.setYear(newYear);
        order.setMonth(newMonth);
        order.setDay(newDay);

        assertEquals(newOrderID, order.getOrderId());
        assertEquals(newYear, order.getYear());
        assertEquals(newMonth, order.getMonth());
        assertEquals(newDay, order.getDay());

        System.out.println("Finished OrderOutTest 4");
    }

    @Test
    public void testAddStoreProducts() {
        System.out.println("\nStarting OrderOutTest 5");

        int orderId = 4011;
        String year = "2019";
        String month = "06";
        String day = "14";

        int storeId = 12345;
        Store store = new Store(storeId);
        String prodName = "prod";
        Product product = new Product(prodName);
        double price = 0.8;

        OrderOut order = new OrderOut(orderId, store, year, month, day);

        StoreProduct ivp = new StoreProduct(store, product, price, 30);
        assertNotNull(ivp);

        order.addStoreProduct(ivp);

        HashMap<StoreProduct, Integer> tempList = order.getProducts();
        for (Map.Entry<StoreProduct, Integer> entry : tempList.entrySet()) {
            StoreProduct tempP = entry.getKey();
            int quantity = entry.getValue();
            assertEquals(1, quantity);
            assertEquals(prodName, tempP.getProductName());
            assertEquals(store,tempP.getStore());
            assertEquals(30, tempP.getQuantity());
        }


        HashMap<StoreProduct, Integer> newList = new HashMap<>();
        StoreProduct newP = new StoreProduct(store, product, price, 321);

        newList.put(newP, 321);
        order.setOrderedProduct(newList);

        tempList = order.getProducts();

        for (Map.Entry<StoreProduct, Integer> entry : tempList.entrySet()) {
            StoreProduct tempP = entry.getKey();
            int quantity = entry.getValue();
            assertEquals(321, quantity);
            assertEquals(store, tempP.getStore());
            assertEquals(prodName, tempP.getProductName());
            assertEquals(321, tempP.getQuantity());
        }


        System.out.println("Finished OrderOutTest 5");
    }

    @Test
    public void testDuplicateAdd() {
        System.out.println("\nStarting OrderOutTest 6");

        int orderId = 4011;
        String year = "2019";
        String month = "06";
        String day = "14";

        int storeId = 12345;
        Store store = new Store(storeId);
        String prodName = "prod";
        Product product = new Product(prodName);
        double price = 0.8;

        OrderOut order = new OrderOut(orderId, store, year, month, day);

        StoreProduct ivp = new StoreProduct(store, product, price, 30);
        assertNotNull(ivp);

        order.addStoreProduct(ivp);

        HashMap<StoreProduct, Integer> tempList = order.getProducts();
        for (Map.Entry<StoreProduct, Integer> entry : tempList.entrySet()) {
            StoreProduct tempP = entry.getKey();
            int quantity = entry.getValue();
            assertEquals(1, quantity);
            assertEquals(prodName, tempP.getProductName());
            assertEquals(store,tempP.getStore());
            assertEquals(30, tempP.getQuantity());
        }

        order.addStoreProduct(ivp);

        tempList = order.getProducts();
        for (Map.Entry<StoreProduct, Integer> entry : tempList.entrySet()) {
            StoreProduct tempP = entry.getKey();
            int quantity = entry.getValue();
            assertEquals(2, quantity);
            assertEquals(prodName, tempP.getProductName());
            assertEquals(store,tempP.getStore());
            assertEquals(30, tempP.getQuantity());
        }

        System.out.println("Finished OrderOutTest 6");
    }
}
