package comp3350.ei.tests.objects;

import org.junit.Test;

import comp3350.ei.objects.Product;
import comp3350.ei.objects.StoreProduct;
import comp3350.ei.objects.OrderIn;
import comp3350.ei.objects.Store;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class OrderInTest {
    @Test
    public void testOrderIn() {
        System.out.println("\nStarting OrderInTest 1");

        OrderIn order;
        int storeId = 55;
        Store store = new Store(storeId);
        int orderId = 4011;
        String year = "2019";
        String month = "06";
        String day = "14";

        order = new OrderIn(orderId, store, year, month, day);
        assertNotNull(order);

        // test getters
        assertEquals(orderId, order.getOrderId());
        assertEquals(store, order.getStore());
        assertEquals(year, order.getYear());
        assertEquals(month, order.getMonth());
        assertEquals(day, order.getDay());
        System.out.println("Finished OrderInTest 1");
    }

    @Test
    public void testOrderInConstructor1() {
        System.out.println("\nStarting OrderInTest 2");

        Store store = new Store();

        OrderIn o = new OrderIn(store);

        assertNotNull(o.getProducts());
        assertEquals(0, o.getProducts().size());
        assertNull(o.getYear());
        assertNull(o.getMonth());
        assertNull(o.getDay());

        System.out.println("Finished OrderInTest 2");
    }

    @Test
    public void testOrderInConstructor2() {
        System.out.println("\nStarting OrderInTest 3");

        OrderIn o = new OrderIn();

        assertNull(o.getYear());
        assertNull(o.getMonth());
        assertNull(o.getDay());

        System.out.println("Finished OrderInTest 3");
    }

    @Test
    public void testSetters() {
        System.out.println("\nStarting OrderInTest 4");

        OrderIn order;
        int storeId = 55;
        Store store = new Store(storeId);
        int orderId = 4011;
        String year = "2019";
        String month = "06";
        String day = "14";

        order = new OrderIn(orderId, store, year, month, day);

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

        System.out.println("Finished OrderInTest 4");
    }

    @Test
    public void testAddStoreProducts() {
        System.out.println("\nStarting OrderInTest 5");

        int orderId = 4011;
        String year = "2019";
        String month = "06";
        String day = "14";

        int storeId = 12345;
        Store store = new Store(storeId);
        String prodName = "prod";
        Product product = new Product(prodName);
        double price = 0.8;

        OrderIn order = new OrderIn(orderId, store, year, month, day);

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


        System.out.println("Finished OrderInTest 5");
    }

    @Test
    public void testDuplicateAdd() {
        System.out.println("\nStarting OrderInTest 6");

        int orderId = 4011;
        String year = "2019";
        String month = "06";
        String day = "14";

        int storeId = 12345;
        Store store = new Store(storeId);
        String prodName = "prod";
        Product product = new Product(prodName);
        double price = 0.8;

        OrderIn order = new OrderIn(orderId, store, year, month, day);

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

        System.out.println("Finished OrderInTest 6");
    }
}