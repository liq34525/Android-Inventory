package comp3350.ei.business;

import java.util.Collections;
import java.util.List;

import comp3350.ei.application.Services;
import comp3350.ei.objects.Product;
import comp3350.ei.objects.StoreProduct;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductDeletePersistence;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductRetrievePersistence;
import comp3350.ei.persistence.ProductPersistenceInterface.ProductUpdatePersistence;
import comp3350.ei.persistence.StoreProductPersistence;

public class AccessStoreProducts {
    private final AccessProducts accessProducts;
    private final StoreProductPersistence StoreProductPersistence;

    public AccessStoreProducts() {
        StoreProductPersistence = Services.getStoreProductPersistence();
        accessProducts = new AccessProducts();
    }

    public AccessStoreProducts(StoreProductPersistence database, ProductRetrievePersistence prodDB, ProductDeletePersistence pd, ProductUpdatePersistence pu) {
        StoreProductPersistence = database;
        accessProducts = new AccessProducts(prodDB,pu,pd);
    }

    public List<StoreProduct> getStoreProducts() {
        List<StoreProduct> products = StoreProductPersistence.getStoreProductSequential();
        return Collections.unmodifiableList(products);
    }

    public StoreProduct getStoreProduct(String productName) {
        List<StoreProduct> products = StoreProductPersistence.getStoreProductSequential();
        for (StoreProduct prod : products) {
            if (prod.getProductName().equalsIgnoreCase(productName)) {
                return prod;
            }
        }
        return null;
    }

    public StoreProduct insertStoreProduct(StoreProduct storeProduct) {
        if (storeProduct == null) {
            return null;
        }

        Product nonDuplicate =
                accessProducts.insertProduct(storeProduct.getProduct());
        storeProduct.setProduct(nonDuplicate);

        return StoreProductPersistence.insertStoreProduct(storeProduct);
    }

    public void deleteStoreProduct(StoreProduct currentStoreProduct) {
        StoreProductPersistence.deleteStoreProduct(currentStoreProduct);
    }

    public void updateDatabase(StoreProduct storeProduct) {
        StoreProductPersistence.updateStoreProduct(storeProduct);
        accessProducts.updateProduct(storeProduct.getProduct());
    }

    public List<String> getStoreProductCategories() {
        return StoreProductPersistence.getStoreProductCategory();
    }
}
