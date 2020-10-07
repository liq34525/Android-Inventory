package comp3350.ei.tests.business;

import org.junit.Test;

import java.util.List;

import comp3350.ei.objects.Order;
import comp3350.ei.objects.OrderIn;
import comp3350.ei.objects.OrderOut;

import comp3350.ei.business.AccessOrders;
import comp3350.ei.objects.Store;
import comp3350.ei.persistence.OrderPersistence;
import comp3350.ei.persistence.stubs.OrderPersistenceStub;

import static org.junit.Assert.*;

public class AccessOrdersTest {
    @Test
    public void testConstructor() {
        OrderPersistence op = new OrderPersistenceStub();
        AccessOrders accessOrders = new AccessOrders(op);

        System.out.println("\nStarting test AccessOrder constructor");

        assertNotNull(accessOrders);
        assertNotNull(accessOrders.getOrders());
        assertNotNull(accessOrders.getSequential());

        System.out.println("Finished test on AccessOrder constructor");
    }


    @Test
    public void testInsertDelete() {
        System.out.println("\nStarting test AccessOrder Insert Delete");

        OrderPersistence op = new OrderPersistenceStub();
        AccessOrders asp = new AccessOrders(op);
        Store store = new Store();
        Order o1 = new OrderIn(20, store, "2019", "05", "11");
        Order o2 = new OrderIn(21, store, "2019", "05", "13");
        Order o3 = new OrderOut(22, store, "2019", "05", "13");
        Order o4 = new OrderOut(23, store, "2019", "06", "13");

        List<Order> list = asp.getOrders();
        int initSize = list.size();

        asp.insertOrder(o1);
        asp.insertOrder(o2);
        asp.insertOrder(o3);
        asp.insertOrder(o4);
        asp.insertOrder(null);

        assertEquals(initSize + 4, asp.getOrders().size());

        asp.deleteOrder(o1);
        asp.deleteOrder(o2);
        asp.deleteOrder(o3);
        asp.deleteOrder(o4);
        asp.deleteOrder(null);

        assertEquals(initSize, asp.getOrders().size());
        System.out.println("\nFinished test AccessOrders Insert Delete");
    }

    @Test
    public void testGetOrders() {

        System.out.println("\nStarting test AccessOrder getOrders");
        OrderPersistence op = new OrderPersistenceStub();
        AccessOrders asp = new AccessOrders(op);

        Store store = new Store();
        Order o1 = new OrderIn(30, store, "2019", "05", "11");
        Order o2 = new OrderIn(31, store, "2019", "05", "13");
        Order o3 = new OrderOut(32, store, "2019", "05", "13");
        Order o4 = new OrderOut(33, store, "2019", "05", "13");

        assertNotNull(asp);

        int prevOrderSize = asp.getOrders().size();
        int prevOrderInSize = asp.getOrderIns().size();
        int prevOrderOutSize = asp.getOrderOuts().size();

        asp.insertOrder(o1);
        asp.insertOrder(o2);
        asp.insertOrder(o3);
        asp.insertOrder(o4);

        int orderSize = asp.getOrders().size();
        int orderInSize = asp.getOrderIns().size();
        int orderOutSize = asp.getOrderOuts().size();

        assertEquals(prevOrderSize + 4, orderSize);
        assertEquals(prevOrderInSize + 2, orderInSize);
        assertEquals(prevOrderOutSize+2, orderOutSize);

        System.out.println("Finished test getOrders");
    }

    @Test
    public void testGetOrder() {
        OrderPersistence op = new OrderPersistenceStub();
        AccessOrders accessOrders = new AccessOrders(op);

        System.out.println("\nStarting test AccessOrder get Order by orderID");

        assertNotNull(accessOrders);

        int orderID = 16;
        int orderID2 = 17;
        Store store = new Store();
        Order o1 = new OrderIn(orderID, store, "2019", "05", "11");
        Order o2 = new OrderOut(orderID2, store, "2019", "06", "11");

        int nonexistantOrder = 999;

        accessOrders.insertOrder(o1);
        accessOrders.insertOrder(o2);

        assertEquals(o1, accessOrders.getOrder(orderID));
        assertNull(accessOrders.getOrder(nonexistantOrder));

        assertEquals(o2, accessOrders.getOrder(orderID2));
        assertNull(accessOrders.getOrder(nonexistantOrder));

        System.out.println("Finished test on AccessOrder getOrder");
    }
}
