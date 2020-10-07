package comp3350.ei.tests.persistence;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

import comp3350.ei.objects.Product;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductRetrievePersistence;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductUpdatePersistence;
import comp3350.ei.persistence.stubs.ProductPersistenceStub;

public class ProductRetrievePersistenceTest {
    @Test
    public void productRetrieveTest1(){
        int index;
        System.out.println("\nStarting ProductRetrievePersistenceTest 1");

        ProductRetrievePersistence pr = new ProductPersistenceStub();
        String prodName1 = "name1";
        Product p1 = new Product(prodName1);

        //test getProductSequential
        List<Product> splist = pr.getProductSequential();
        index = splist.indexOf(p1);
        assertEquals(index, -1);

        for(int i=0;i<splist.size();i++)
            if(splist.get(i).getName().equals("Milk"))
                 index = i;
        assertTrue(index > -1);

        //test getProductRandom
        List<Product> returnlist = pr.getProductRandom(p1);
        assertTrue(returnlist.size()==0);

        returnlist = pr.getProductRandom(splist.get(1));
        index = returnlist.indexOf(splist.get(1));
        assertTrue(index > -1);

        System.out.println("Finished ProductRetrievePersistenceTest 1");
    }

    @Test
    public void productRetrieveTest2(){
        int index;
        System.out.println("\nStarting ProductRetrievePersistenceTest 1");

        ProductUpdatePersistence pu = new ProductPersistenceStub();
        String prodName1 = "name1";
        Product p1 = new Product(prodName1);
        pu.insertProduct(p1);

        ProductRetrievePersistence pr = ((ProductPersistenceStub) pu).getProductPersistenceStub();
        List<Product> afterAdd = pr.getProducts();
        index = afterAdd.indexOf(p1);
        assertTrue(index > -1);

        System.out.println("Finished ProductRetrievePersistenceTest 1");
    }
}
