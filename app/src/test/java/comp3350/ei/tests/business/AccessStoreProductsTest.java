package comp3350.ei.tests.business;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

import comp3350.ei.business.AccessProducts;
import comp3350.ei.business.AccessStoreProducts;
import comp3350.ei.objects.StoreProduct;
import comp3350.ei.objects.Store;
import comp3350.ei.objects.Product;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductDeletePersistence;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductRetrievePersistence;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductUpdatePersistence;
import comp3350.ei.persistence.StoreProductPersistence;
import comp3350.ei.persistence.stubs.ProductPersistenceStub;
import comp3350.ei.persistence.stubs.StoreProductPersistenceStub;

public class AccessStoreProductsTest {
    @Test
    public void testConstructor() {
        System.out.println("\nStarting test AccessStoreProducts constructor");
        StoreProductPersistence spp = new StoreProductPersistenceStub();
        ProductDeletePersistence pd = new ProductPersistenceStub();
        ProductRetrievePersistence pr = ((ProductPersistenceStub) pd).getProductPersistenceStub();
        ProductUpdatePersistence pu = ((ProductPersistenceStub) pd).getProductPersistenceStub();
        AccessStoreProducts asp = new AccessStoreProducts(spp, pr,pd,pu);
        assertNotNull(asp);
        System.out.println("Finished test AccessStoreProducts constructor");
    }

    @Test
    public void testGet() {
        System.out.println("\nStarting test AccessStoreProducts get");
        StoreProductPersistence spp = new StoreProductPersistenceStub();
        ProductDeletePersistence pd = new ProductPersistenceStub();
        ProductRetrievePersistence pr = ((ProductPersistenceStub) pd).getProductPersistenceStub();
        ProductUpdatePersistence pu = ((ProductPersistenceStub) pd).getProductPersistenceStub();

        AccessStoreProducts asp = new AccessStoreProducts(spp, pr,pd,pu);
        assertNotNull(asp.getStoreProducts());
        System.out.println("\nFinished test AccessStoreProducts get");
    }

    @Test
    public void testInsertDelete() {
        System.out.println("\nStarting test AccessStoreProducts insert delete");
        int storeId = 2;
        String prodName = "product";
        Product product = new Product(prodName);
        Store store = new Store(storeId);
        StoreProductPersistence spp = new StoreProductPersistenceStub();
        ProductDeletePersistence pd = new ProductPersistenceStub();
        ProductRetrievePersistence pr = ((ProductPersistenceStub) pd).getProductPersistenceStub();
        ProductUpdatePersistence pu = ((ProductPersistenceStub) pd).getProductPersistenceStub();

        AccessStoreProducts asp = new AccessStoreProducts(spp, pr,pd,pu);

        StoreProduct sp = new StoreProduct(store,product);
        List<StoreProduct> list = asp.getStoreProducts();
        int initSize = list.size();
        asp.insertStoreProduct(sp);

        assertTrue(initSize < asp.getStoreProducts().size());

        asp.deleteStoreProduct(sp);

        assertEquals(initSize, asp.getStoreProducts().size());
        System.out.println("\nFinished test AccessStoreProducts insert delete update");
    }

    @Test
    public void testInsertDuplicate() {
        System.out.println("\nStarting test AccessStoreProducts insert duplicate");

        int storeId = 2;
        String prodName = "product";
        Product product = new Product(prodName);
        Store store = new Store(storeId);
        StoreProductPersistence spp = new StoreProductPersistenceStub();
        ProductDeletePersistence pd = new ProductPersistenceStub();
        ProductRetrievePersistence pr = ((ProductPersistenceStub) pd).getProductPersistenceStub();
        ProductUpdatePersistence pu = ((ProductPersistenceStub) pd).getProductPersistenceStub();

        AccessStoreProducts asp = new AccessStoreProducts(spp, pr,pd,pu);

        StoreProduct sp = new StoreProduct(store,product);
        List<StoreProduct> storeProducts = asp.getStoreProducts();
        int initSize = storeProducts.size();
        asp.insertStoreProduct(sp);

        int sizeAfterInsert = storeProducts.size();

        assertEquals(initSize+1, sizeAfterInsert);

        asp.insertStoreProduct(sp);

        assertEquals(sizeAfterInsert,storeProducts.size());

        asp.deleteStoreProduct(sp);

        assertEquals(initSize, storeProducts.size());
        System.out.println("\nFinished test AccessStoreProducts insert duplicate");
    }

    @Test
    public void testInsertNull() {
        System.out.println("\nStarting test AccessStoreProducts insert null");
        StoreProductPersistence spp = new StoreProductPersistenceStub();
        ProductDeletePersistence pd = new ProductPersistenceStub();
        ProductRetrievePersistence pr = ((ProductPersistenceStub) pd).getProductPersistenceStub();
        ProductUpdatePersistence pu = ((ProductPersistenceStub) pd).getProductPersistenceStub();

        AccessStoreProducts asp = new AccessStoreProducts(spp, pr,pd,pu);
        List<StoreProduct> storeProducts = asp.getStoreProducts();
        int initSize = storeProducts.size();
        assertNull(asp.insertStoreProduct(null));

        int sizeAfterInsert = storeProducts.size();

        assertEquals(initSize, sizeAfterInsert);

        asp.deleteStoreProduct(null);

        assertEquals(initSize, storeProducts.size());
        System.out.println("\nFinished test AccessStoreProducts insert null");
    }

    @Test
    public void testGetStoreProduct() {
        StoreProductPersistence spp = new StoreProductPersistenceStub();
        ProductDeletePersistence pd = new ProductPersistenceStub();
        ProductRetrievePersistence pr = ((ProductPersistenceStub) pd).getProductPersistenceStub();
        ProductUpdatePersistence pu = ((ProductPersistenceStub) pd).getProductPersistenceStub();

        AccessStoreProducts asp = new AccessStoreProducts(spp, pr,pd,pu);

        System.out.println("\nStarting test on AccessStoreProduct");

        assertNotNull(asp);

        StoreProduct sp;
        int storeId = 2;
        Store store = new Store(storeId);
        String productName = "New Product 3";
        String productCategory = "Category 1";
        String productDescription = "This is new product description";
        String productPicture = "This is new product Picture";
        double productPrice = 20;
        int quantity = 50;
        String unit = "grams";

        Product product = new Product(productName, productCategory,
                productDescription, unit, productPicture);

        sp = new StoreProduct(store, product, productPrice, quantity);

        String nonexistentProduct = "Product 2";

        asp.insertStoreProduct(sp);

        assertEquals(sp, asp.getStoreProduct(productName));
        assertNull(asp.getStoreProduct(nonexistentProduct));

        System.out.println("Finished test on AccessStoreProduct");
    }


    @Test
    public void testGetCategories() {
        StoreProductPersistence spp = new StoreProductPersistenceStub();
        ProductDeletePersistence pd = new ProductPersistenceStub();
        ProductRetrievePersistence pr = ((ProductPersistenceStub) pd).getProductPersistenceStub();
        ProductUpdatePersistence pu = ((ProductPersistenceStub) pd).getProductPersistenceStub();

        AccessStoreProducts accessProducts = new AccessStoreProducts(spp, pr,pd,pu);

        System.out.println("\nStarting test AccessStoreProduct categories");

        assertNotNull(accessProducts);

        StoreProduct sp;
        int storeId = 2;
        Store store = new Store(storeId);
        String productName1 = "New Product 1";
        String productDescription = "This is new product description";
        String productPicture = "This is new product Picture";
        String unit = "grams";

        String productCategory1 = "Category 1";

        Product product = new Product(productName1, productCategory1,
                productDescription, unit, productPicture);

        sp = new StoreProduct(store, product);

        assertNotNull(accessProducts.getStoreProductCategories());

        int numCategories = accessProducts.getStoreProductCategories().size();

        accessProducts.insertStoreProduct(sp);
        assertEquals(numCategories + 1, accessProducts.getStoreProductCategories().size());

        accessProducts.insertStoreProduct(sp);
        assertEquals(numCategories + 1, accessProducts.getStoreProductCategories().size());

        System.out.println("Finished test on AccessProduct");
    }
}
