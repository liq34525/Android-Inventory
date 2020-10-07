package comp3350.ei.tests.persistence;

import org.junit.Test;
import static org.junit.Assert.*;


import comp3350.ei.objects.Product;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductRetrievePersistence;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductUpdatePersistence;
import comp3350.ei.persistence.stubs.ProductPersistenceStub;

public class ProductUpdatePersistenceTest {
        @Test
        public void testProductPersistence1() {
                System.out.println("\nStarting ProductUpdatePersistenceTest 1");

                String prodName1 = "name1";
                String prodName2 = "name2";
                String prodName3 = "name3";
                String prodName4 = "name3";

                ProductUpdatePersistence pu = new ProductPersistenceStub();
                Product p1 = new Product(prodName1);
                Product p2 = new Product(prodName2);
                Product p3 = new Product(prodName3);
                Product p4 = new Product(prodName4);

                // test insert
                Product temp = pu.insertProduct(p1);

                assertEquals(prodName1, temp.getName());
                assertNull( temp.getDescription());
                assertNull(temp.getPicture());

                //assertEquals(0, temp.getPrice(), 0.0);

                assertNull(temp.getCategory());
                assertEquals( "",temp.getUnit());

                temp = pu.insertProduct(p2);
                assertEquals(prodName2, temp.getName());
                assertNull( temp.getDescription());
                assertNull(temp.getPicture());

                //assertEquals(0, temp.getPrice(), 0.0);

                assertNull(temp.getCategory());
                assertEquals( "", temp.getUnit());

                temp = pu.insertProduct(p3);
                assertEquals(prodName3, temp.getName());
                assertNull( temp.getDescription());
                assertNull(temp.getPicture());

                //assertEquals(0, temp.getPrice(), 0.0);

                assertNull(temp.getCategory());
                assertEquals( "", temp.getUnit());

                System.out.println("Finished ProductUpdatePersistenceTest 1");
        }
}
