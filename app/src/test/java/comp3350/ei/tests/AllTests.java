package comp3350.ei.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.ei.persistence.ProductPersistenceInterface.ProductDeletePersistence;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductUpdatePersistence;
import comp3350.ei.tests.business.AccessProductsTest;
import comp3350.ei.tests.business.AccessStoreProductsTest;
import comp3350.ei.tests.business.AccessOrdersTest;
import comp3350.ei.tests.business.AccessStoresTest;

import comp3350.ei.tests.business.ProductImagesTest;
import comp3350.ei.tests.objects.OrderInTest;
import comp3350.ei.tests.objects.OrderOutTest;
import comp3350.ei.tests.objects.ProductTest;
import comp3350.ei.tests.objects.StoreTest;
import comp3350.ei.tests.objects.StoreProductTest;
import comp3350.ei.tests.persistence.StoreProductPersistenceTest;
import comp3350.ei.tests.persistence.OrderPersistenceTest;
import comp3350.ei.tests.persistence.ProductDeletePersistenceTest;
import comp3350.ei.tests.persistence.ProductUpdatePersistenceTest;
import comp3350.ei.tests.persistence.ProductRetrievePersistenceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccessProductsTest.class,
        AccessStoreProductsTest.class,
        AccessOrdersTest.class,
        AccessStoresTest.class,
        OrderInTest.class,
        OrderOutTest.class,
        ProductTest.class,
        StoreTest.class,
        StoreProductTest.class,
        OrderPersistenceTest.class,
        ProductImagesTest.class,
        ProductDeletePersistenceTest.class,
        ProductUpdatePersistenceTest.class,
        ProductRetrievePersistenceTest.class,
        StoreProductPersistenceTest.class,
})

public class AllTests {

}
