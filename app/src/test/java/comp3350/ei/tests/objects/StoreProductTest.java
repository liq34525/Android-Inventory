package comp3350.ei.tests.objects;

import org.junit.Test;

import comp3350.ei.objects.Product;
import comp3350.ei.objects.Store;
import comp3350.ei.objects.StoreProduct;

import static org.junit.Assert.*;

public class StoreProductTest
{
    @Test
    public void testStoreProduct1()
    {/*
        StoreProduct ivp, sp;
        int storeId = 10232;
        int productId = 102947343;

        System.out.println("\nStarting StoreProductTest 1");
        double price = 0.8;
        ivp = new StoreProduct(storeId, productId, price, 30);

        // test getters
        assertEquals(30, ivp.getQuantity());
        assertEquals(storeId, ivp.getStoreId());
        assertEquals(productId, ivp.getProductName());
        assertEquals(price, ivp.getPrice() , 0);

        assertNotNull(ivp.toString());

        sp = new StoreProduct(storeId, productId);
        // test getters
        assertEquals(0, sp.getQuantity());
        assertEquals(storeId, sp.getStoreId());
        assertEquals(productId, sp.getProductName());
*/
        System.out.println("Finished StoreProductTest 1");
    }

    @Test
    public void testStoreProductConstructor1() {
        System.out.println("\nStarting StoreProductTest 2");
        StoreProduct storeProduct;
        String name = "Chocolate";
        int storeID = 55;
        Store store = new Store(storeID);
        Product product = new Product(name);

        storeProduct = new StoreProduct(store, product);

        assertNotNull(storeProduct);
        assertEquals(storeID, storeProduct.getStore().getStoreId());
        assertEquals(name, storeProduct.getProductName());
        assertEquals(0, storeProduct.getQuantity());
        assertEquals(product.getDescription(), storeProduct.getDescription());
        assertEquals(0, storeProduct.getPrice(), 0);
        assertEquals(product.getUnit(), storeProduct.getUnit());
        System.out.println("\nFinished StoreProductTest 2");
    }

    @Test
    public void testStoreProductConstructor2() {
        System.out.println("\nStarting StoreProductTest 3");
        StoreProduct storeProduct;
        String name = "Chocolate";
        String category = "Category 1";
        String description = "This is new product description";
        double price = 20.2;
        int quantity = 10;
        String unit = "box";
        Product product = new Product(name, category, description, unit);

        int storeID = 55;
        Store store = new Store(storeID);
        storeProduct = new StoreProduct(store, product, price, quantity);

        assertNotNull(storeProduct);
        assertEquals(storeID, storeProduct.getStore().getStoreId());
        assertEquals(name, storeProduct.getProductName());
        assertEquals(description, storeProduct.getDescription());
        assertEquals(quantity, storeProduct.getQuantity());
        assertEquals(price, storeProduct.getPrice(), 0.0);
        assertEquals(unit, storeProduct.getUnit());
        assertEquals(category, storeProduct.getCategory());
        System.out.println("\nFinished StoreProductTest 3");
    }



    @Test
    public void testStoreProductNullDefaults() {
        System.out.println("\nStarting StoreProductTest 5");
        StoreProduct storeProduct;
        int storeID = 55;
        Store store = new Store(storeID);
        Product product = null;

        storeProduct = new StoreProduct(store, product);

        assertNotNull(storeProduct);
        assertNull(storeProduct.getProductName());
        assertEquals(0, storeProduct.getQuantity());
        assertEquals("", storeProduct.getDescription());
        assertEquals(0, storeProduct.getPrice(), 0.0);
        assertEquals("", storeProduct.getUnit());
        System.out.println("\nFinished StoreProductTest 5");
    }

    @Test
    public void testStoreProductSetters() {
        System.out.println("\nStarting StoreProductTest 6");
        StoreProduct storeProduct;
        String name = "Chocolate";
        int storeID = 55;
        Store store = new Store(storeID);
        Product product = new Product(name);

        storeProduct = new StoreProduct(store, product);

        assertNotNull(storeProduct);

        String newName = "Manitoba";
        String newCategory = "Category 1";
        String newDescription = "This is new product description";
        double newPrice = 20.2;
        int newQuantity = 9;
        String newUnit = "kg";

        storeProduct.setName(newName);
        storeProduct.setCategory(newCategory);
        storeProduct.setDescription(newDescription);
        storeProduct.setPrice(newPrice);
        storeProduct.setQuantity(newQuantity);
        storeProduct.setUnit(newUnit);

        assertEquals(newName, storeProduct.getProductName());
        assertEquals(newCategory, storeProduct.getCategory());
        assertEquals(product.getDescription(), storeProduct.getDescription());
        assertEquals(newPrice, storeProduct.getPrice(), 0.0);
        assertEquals(newQuantity, storeProduct.getQuantity());
        assertEquals(product.getUnit(), storeProduct.getUnit());
        System.out.println("\nFinished StoreProductTest 6");
    }

    @Test
    public void testStoreProductNotValidData() {
        System.out.println("\nStarting test (set invalid data on Product");
        int storeID = 55;
        Store store = new Store(storeID);
        Product product;
        String name = "New Product 3";
        String category = "Category 1";
        String description = "This is new product description";
        int invalidPrice = -20;
        String unit = "box";

        product = new Product(name, category, description, unit);

        StoreProduct storeProduct = new StoreProduct(store, product);

        storeProduct.setPrice(invalidPrice);
        assertEquals(name, product.getName());
        assertEquals(category, product.getCategory());
        assertEquals(description, product.getDescription());

        System.out.println("Finished test (set invalid data on Product");
    }

}
