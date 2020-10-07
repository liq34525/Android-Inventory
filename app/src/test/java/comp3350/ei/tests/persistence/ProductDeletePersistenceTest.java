package comp3350.ei.tests.persistence;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

import comp3350.ei.objects.Product;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductDeletePersistence;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductRetrievePersistence;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductUpdatePersistence;
import comp3350.ei.persistence.stubs.ProductPersistenceStub;

public class ProductDeletePersistenceTest {
    @Test
    public void testProductDelete1() {
        int index;
        System.out.println("\nStarting ProductDeletePersistenceTest 1");
        ProductUpdatePersistence pu = new ProductPersistenceStub();

        String prodName1 = "name3";
        Product p1 = new Product(prodName1);
        pu.insertProduct(p1);

        ProductRetrievePersistence pr = ((ProductPersistenceStub) pu).getProductPersistenceStub();
        ProductDeletePersistence pd = ((ProductPersistenceStub) pu).getProductPersistenceStub();
        pd.deleteProduct(p1);
        List<Product> afterDelete = pr.getProducts();
        index = afterDelete.indexOf(p1);
        assertEquals(index, -1);

        System.out.println("Finished ProductDeletePersistenceTest 1");
    }
}
