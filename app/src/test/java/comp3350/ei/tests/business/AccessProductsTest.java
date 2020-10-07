package comp3350.ei.tests.business;

import org.junit.Test;

import java.util.List;

import comp3350.ei.objects.Product;

import comp3350.ei.business.AccessProducts;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductDeletePersistence;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductRetrievePersistence;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductUpdatePersistence;
import comp3350.ei.persistence.stubs.ProductPersistenceStub;

import static org.junit.Assert.*;

public class AccessProductsTest {
    //testing product constructor and getMethods
    @Test
    public void testCon() {
        ProductDeletePersistence pd = new ProductPersistenceStub();
        ProductRetrievePersistence pr = ((ProductPersistenceStub) pd).getProductPersistenceStub();
        ProductUpdatePersistence pu = ((ProductPersistenceStub) pd).getProductPersistenceStub();
        AccessProducts accessProducts = new AccessProducts(pr,pu,pd);

        System.out.println("\nStarting test AccessProduct constructor");

        assertNotNull(accessProducts);
        assertNotNull(accessProducts.getProducts());
        assertNotNull(accessProducts.getSequential());

        System.out.println("Finished test on AccessProduct");
    }

    //testing product insert and delete
    @Test
    public void testInsertDelete() {
        Product product;
        String productName = "Product for testInsertDelete";
        String productCategory = "Category 1";
        String productDescription = "This is new product description";
        int productPrice = 20;
        String unit = "grams";

        int size;
        List<Product> products;

        product = new Product(productName, productCategory, productDescription, unit);
        assertNotNull(product);

        ProductDeletePersistence pd = new ProductPersistenceStub();
        ProductRetrievePersistence pr = ((ProductPersistenceStub) pd).getProductPersistenceStub();
        ProductUpdatePersistence pu = ((ProductPersistenceStub) pd).getProductPersistenceStub();
        AccessProducts accessProducts = new AccessProducts(pr,pu,pd);

        System.out.println("\nStarting test AccessProduct constructor");

        assertNotNull(accessProducts);

        products = accessProducts.getProducts();
        size = products.size();

        assertNotNull(accessProducts.getProducts());

        accessProducts.insertProduct(product);
        assertNotNull(accessProducts.getProducts());
        products = accessProducts.getProducts();

        assertEquals(size+1, products.size());

        size = products.size();
        accessProducts.deleteProduct(product);
        assertNotNull(accessProducts.getProducts());
        products = accessProducts.getProducts();

        assertEquals(size-1, products.size());

        System.out.println("Finished test on AccessProduct");
    }

    @Test
    public void testGetProduct() {
        ProductDeletePersistence pd = new ProductPersistenceStub();
        ProductRetrievePersistence pr = ((ProductPersistenceStub) pd).getProductPersistenceStub();
        ProductUpdatePersistence pu = ((ProductPersistenceStub) pd).getProductPersistenceStub();
        AccessProducts accessProducts = new AccessProducts(pr,pu,pd);

        System.out.println("\nStarting test AccessProduct get product by ID");

        assertNotNull(accessProducts);

        Product product;
        String productName = "New Product 3";
        String productCategory = "Category 1";
        String productDescription = "This is new product description";
        int productPrice = 20;
        String unit = "grams";

        product = new Product(productName, productCategory, productDescription, unit);

        String nonexistentProduct = "Product 2";

        accessProducts.insertProduct(product);

        assertEquals(product, accessProducts.getProduct(productName));
        assertNull(accessProducts.getProduct(nonexistentProduct));

        System.out.println("Finished test on AccessProduct");
    }
}
