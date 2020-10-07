package comp3350.ei.tests.persistence;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;

import comp3350.ei.objects.Order;
import comp3350.ei.objects.OrderIn;
import comp3350.ei.objects.OrderOut;
import comp3350.ei.objects.Store;
import comp3350.ei.persistence.OrderPersistence;
import comp3350.ei.persistence.stubs.OrderPersistenceStub;

public class OrderPersistenceTest {
    @Test
    public void testOrderPersistence1() {
        int index;
        System.out.println("\nStarting OrderPersistenceTest 1");

        OrderPersistence spp = new OrderPersistenceStub();

        Store store = new Store();

        int orderId1 = 11;
        int orderId2 = 21;
        int orderId3 = 31;
        int orderId4 = 41;

        Order sp1 = new OrderIn(orderId1, store, "2019", "06", "10");
        Order sp2 = new OrderIn(orderId2, store, "2018", "07", "11");
        Order sp3 = new OrderOut(orderId3, store, "2019", "08", "12");
        Order sp4 = new OrderOut(orderId4, store, "2019", "06", "13");

        // test insert
        Order temp = spp.insertOrder(sp1);

        assertEquals(orderId1, temp.getOrderId());
        assertEquals(store.getStoreId(), temp.getStore().getStoreId());
        assertEquals("2019", temp.getYear());
        assertEquals("06", temp.getMonth());
        assertEquals("10", temp.getDay());

        temp = spp.insertOrder(sp2);
        assertEquals(orderId2, temp.getOrderId());
        assertEquals("2018", temp.getYear());
        assertEquals("07", temp.getMonth());
        assertEquals("11", temp.getDay());

        temp = spp.insertOrder(sp3);
        assertEquals(orderId3, temp.getOrderId());
        assertEquals("2019", temp.getYear());
        assertEquals("08", temp.getMonth());
        assertEquals("12", temp.getDay());
/*
        //test getOrderSequential
        List<Order> splist = spp.getOrderSequential();
        index = splist.indexOf(sp1);
        assertTrue(index > -1);
        index = splist.indexOf(sp2);
        assertTrue(index > -1);
        index = splist.indexOf(sp3);
        assertTrue(index > -1);
        index = splist.indexOf(sp4);
        assertEquals(index, -1);
*/
        //test update
        sp1.setYear("1997");
        temp = spp.updateOrder(sp1);
        assertEquals(orderId1, temp.getOrderId());
        assertEquals("1997", temp.getYear());
        assertEquals("06", temp.getMonth());
        assertEquals("10", temp.getDay());

        System.out.println("Finished OrderPersistenceTest 1");
    }

    @Test
    public void testOrderPersistence2() {
        int index;
        System.out.println("\nStarting OrderPersistenceTest 2");

        OrderPersistence spp = new OrderPersistenceStub();

        int prevOrdersSize = spp.getOrderSequential().size();
        int prevOrderInSize = spp.getOrderIns().size();
        int prevOrderOutSize = spp.getOrderOuts().size();


        Store store = new Store();

        int orderId1 = 10;
        int orderId2 = 20;
        int orderId3 = 30;
        int orderId4 = 40;

        int storeid1 = 321;
        int storeid2 = 654;
        int storeid3 = 987;
        int storeid4 = 121110;


        Order sp1 = new OrderIn(orderId1, store, "2019", "06", "10");
        Order sp2 = new OrderIn(orderId2, store, "2018", "07", "11");
        Order sp3 = new OrderOut(orderId3, store, "2019", "08", "12");
        Order sp4 = new OrderOut(orderId4, store, "2019", "06", "13");

        // test insert
        Order temp = spp.insertOrder(sp1);
        temp = spp.insertOrder(sp2);
        temp = spp.insertOrder(sp3);
        temp = spp.insertOrder(sp4);


        List<Order> orders = spp.getOrderSequential();
        List<Order> orderIn = spp.getOrderIns();
        List<Order> orderOut = spp.getOrderOuts();

        int orderCount = orders.size();
        int orderInCount = orderIn.size();
        int orderOutCount = orderOut.size();

//        assertEquals(prevOrderInSize + 1, orderInCount);
        OrderIn oi = (OrderIn) orderIn.get(prevOrderInSize);
        assertEquals("2019", oi.getYear());
        assertEquals("06", oi.getMonth());
        assertEquals("10", oi.getDay());

        assertEquals(prevOrdersSize + 4, orderCount);

        System.out.println("\nFinished OrderPersistenceTest 2");
    }
}