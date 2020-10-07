package comp3350.ei.tests.persistence;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

import comp3350.ei.objects.StoreProduct;
import comp3350.ei.objects.Store;
import comp3350.ei.objects.Product;

import comp3350.ei.persistence.StoreProductPersistence;
import comp3350.ei.persistence.stubs.StoreProductPersistenceStub;

public class StoreProductPersistenceTest {
    @Test
    public void testStoreProductPersistence1() {
        int index;
        System.out.println("\nStarting StoreProductPersistenceTest 1");

        StoreProductPersistence spp = new StoreProductPersistenceStub();

        int storeId1 = 1;
        int storeId2 = 2;
        int storeId3 = 3;
        int storeId4 = 4;

        String prodName1 = "name1";
        String prodName2 = "name2";
        String prodName3 = "name3";
        String prodName4 = "name3";

        int quantity = 88;

        int price = 300;

        Store store1 = new Store(storeId1);
        Store store2 = new Store(storeId2);
        Store store3 = new Store(storeId3);
        Store store4 = new Store(storeId4);

        int prod1ID = 103;
        String prod1Cat = "999";
        String prod1Desc = "callme";
        String prod1Unit = "thing";
        String prod1Pic = "./9999";
        Product prod1 = new Product(prod1ID, prodName1, prod1Cat, prod1Desc,
        prod1Unit, prod1Pic);

        Product prod2 = new Product(prodName2);
        Product prod3 = new Product(prodName3);
        Product prod4 = new Product(prodName4);

        StoreProduct sp1 = new StoreProduct(store1, prod1, price, quantity);
        StoreProduct sp2 = new StoreProduct(store2, prod2, price, quantity);
        StoreProduct sp3 = new StoreProduct(store3, prod3, price, quantity);
        StoreProduct sp4 = new StoreProduct(store4, prod4, price, quantity);

        // test insert
        StoreProduct inserted = spp.insertStoreProduct(sp1);

        assertNotNull(inserted);
        assertEquals(store1, inserted.getStore());
        assertEquals(prodName1, inserted.getProductName());

        assertEquals(quantity, inserted.getQuantity());
        assertEquals(price, inserted.getPrice(), 0.0);

        inserted = spp.insertStoreProduct(sp2);
        assertEquals(store2, inserted.getStore());
        assertEquals(prodName2, inserted.getProductName());
        assertEquals(quantity, inserted.getQuantity());
        assertEquals(price, inserted.getPrice(), 0.0);

        inserted = spp.insertStoreProduct(sp3);
        assertEquals(store3, inserted.getStore());
        assertEquals(prodName3, inserted.getProductName());
        assertEquals(quantity, inserted.getQuantity());
        assertEquals(price, inserted.getPrice(), 0.0);

        //test getStoreProductSequential
        List<StoreProduct> splist = spp.getStoreProductSequential();
        index = splist.indexOf(sp1);
        assertTrue(index > -1);
        index = splist.indexOf(sp2);
        assertTrue(index > -1);
        index = splist.indexOf(sp3);
        assertTrue(index > -1);
        index = splist.indexOf(sp4);
        assertEquals(index, -1);

        //test getStoreProductById

        StoreProduct temp = spp.getStoreProductById(prod1ID, storeId1);
        assertEquals(temp, sp1);

        //test delete
        spp.deleteStoreProduct(sp1);
        temp = spp.getStoreProductById(prod1ID, storeId1);
        assertNull(temp);

        System.out.println("Finished StoreProductPersistenceTest 1");
    }
}
