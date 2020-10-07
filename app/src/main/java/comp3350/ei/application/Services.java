package comp3350.ei.application;

import comp3350.ei.persistence.ProductPersistenceInterface.ProductDeletePersistence;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductRetrievePersistence;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductUpdatePersistence;
import comp3350.ei.persistence.StorePersistence;
import comp3350.ei.persistence.StoreProductPersistence;
import comp3350.ei.persistence.OrderPersistence;
import comp3350.ei.persistence.hsqldb.OrderPersistenceHSQLDB;
import comp3350.ei.persistence.hsqldb.StorePersistenceHSQLDB;
import comp3350.ei.persistence.hsqldb.ProductPersistenceHSQLDB;
import comp3350.ei.persistence.hsqldb.StoreProductPersistenceHSQLDB;

public class Services {
    private static ProductDeletePersistence productdeletePersistence = null;
    private static ProductRetrievePersistence productRetrievePersistence = null;
    private static ProductUpdatePersistence productupdatePersistence = null;
    private static StoreProductPersistence storeProductPersistence = null;
    private static StorePersistence storePersistence = null;
    private static OrderPersistence orderPersistence = null;

    public static synchronized ProductDeletePersistence getProductDeletePersistence() {
        if (productdeletePersistence == null) {
            productdeletePersistence = new ProductPersistenceHSQLDB(Main.getDBPathName());
        }

        return productdeletePersistence;
    }

    public static synchronized ProductUpdatePersistence getProductUpdatePersistence() {
        if (productupdatePersistence == null) {
            productupdatePersistence = new ProductPersistenceHSQLDB(Main.getDBPathName());
        }

        return productupdatePersistence;
    }

    public static synchronized ProductRetrievePersistence getProductRetrievePersistence() {
        if (productRetrievePersistence == null) {
            productRetrievePersistence = new ProductPersistenceHSQLDB(Main.getDBPathName());
        }

        return productRetrievePersistence;
    }

    public static synchronized StoreProductPersistence getStoreProductPersistence() {
        if (storeProductPersistence == null) {
            storeProductPersistence = new StoreProductPersistenceHSQLDB(Main.getDBPathName());
        }

        return storeProductPersistence;
    }

    public static synchronized StorePersistence getStorePersistence() {
        if (storePersistence == null) {
            storePersistence = new StorePersistenceHSQLDB(Main.getDBPathName());
        }

        return storePersistence;
    }

    public static synchronized OrderPersistence getOrderPersistence() {
        if (orderPersistence == null) {
            orderPersistence = new OrderPersistenceHSQLDB(Main.getDBPathName());
        }

        return orderPersistence;
    }
}
