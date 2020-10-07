package comp3350.ei.tests.business;

import org.junit.Test;

import java.util.List;

import comp3350.ei.objects.Store;

import comp3350.ei.business.AccessStores;
import comp3350.ei.persistence.StorePersistence;
import comp3350.ei.persistence.stubs.StorePersistenceStub;

import static org.junit.Assert.*;

public class AccessStoresTest {
    @Test
    public void testConstructor() {

        StorePersistence sp = new StorePersistenceStub();
        AccessStores AccessStores = new AccessStores(sp);

        System.out.println("\nStarting test AccessStore constructor");

        assertNotNull(AccessStores);
        assertNotNull(AccessStores.getStores());
        assertNotNull(AccessStores.getSequential());

        System.out.println("Finished test on AccessStore constructor");
    }


    @Test
    public void testInsertDelete() {
        System.out.println("\nStarting test AccessStore Insert Delete");

        StorePersistence sp = new StorePersistenceStub();
        AccessStores asp = new AccessStores(sp);
        Store o1 = new Store(10);
        Store o2 = new Store(11);
        Store o3 = new Store(11);
        Store o4 = new Store(13);

        List<Store> list = asp.getStores();
        int initSize = list.size();

        asp.insertStore(o1);
        asp.insertStore(o2);
        asp.insertStore(o3);
        asp.insertStore(o4);
        asp.insertStore(null);

        assertEquals(initSize + 3, asp.getStores().size());

        asp.deleteStore(o1);
        asp.deleteStore(o2);
        asp.deleteStore(o3);
        asp.deleteStore(o4);
        asp.deleteStore(null);

        assertEquals(initSize, asp.getStores().size());
        System.out.println("\nFinished test AccessStores Insert Delete");
    }

    @Test
    public void testgetStores() {

        System.out.println("\nStarting test AccessStore getStores");
        StorePersistence op = new StorePersistenceStub();
        AccessStores asp = new AccessStores(op);
        Store o1 = new Store(10);
        Store o2 = new Store(11);
        Store o3 = new Store(11);
        Store o4 = new Store(13);

        assertNotNull(asp);

        int prevStoreSize = asp.getStores().size();

        asp.insertStore(o1);
        asp.insertStore(o2);
        asp.insertStore(o3);
        asp.insertStore(o4);


        int StoreSize = asp.getStores().size();

        assertEquals(prevStoreSize + 3, StoreSize);

        System.out.println("Finished test getStores");
    }

    @Test
    public void testGetStore() {
        StorePersistence op = new StorePersistenceStub();
        AccessStores AccessStores = new AccessStores(op);

        System.out.println("\nStarting test AccessStore get Store by StoreID");

        assertNotNull(AccessStores);

        int storeID = 16;
        Store o1 = new Store(storeID);

        int nonexistantStore = 999;

        AccessStores.insertStore(o1);

        assertEquals(o1, AccessStores.getStore(storeID));
        assertNull(AccessStores.getStore(nonexistantStore));

        System.out.println("Finished test on AccessStore getStore");
    }
}
