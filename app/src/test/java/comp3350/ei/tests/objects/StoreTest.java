package comp3350.ei.tests.objects;

import org.junit.Test;

import comp3350.ei.objects.Store;

import static org.junit.Assert.*;

public class StoreTest
{
    @Test
    public void testStore1()
    {
        Store iv, s;
        String storeName = "BabyProduct";
        int storeId = 10232;
        System.out.println("\nStarting StoreTest 1");

        iv = new Store(storeId,storeName);
        assertNotNull(iv);

        assertEquals(storeName, iv.getStoreName());
        assertEquals(storeId, iv.getStoreId());

        String expected = "Store: "+ storeName + " id: " + storeId;
        assertEquals(expected, iv.toString());

        iv.setStoreId(10233);
        assertEquals(10233, iv.getStoreId());
        iv.setStoreName("Alcohol");
        assertEquals("Alcohol", iv.getStoreName());

        s = new Store(storeId);
        assertEquals(storeId, s.getStoreId());
        assertNull(s.getStoreName());

        System.out.println("Finished StoreTest 1");
    }
}