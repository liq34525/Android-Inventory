package comp3350.ei.persistence;

import java.util.List;

import comp3350.ei.objects.StoreProduct;

public interface StoreProductPersistence {
    List<StoreProduct> getStoreProductSequential();

    StoreProduct insertStoreProduct(StoreProduct currentStoreProduct);

    void deleteStoreProduct(StoreProduct currentStoreProduct);

    StoreProduct getStoreProductById(int storeID, int productID);

    void updateStoreProduct(StoreProduct storeProduct);

    List<String> getStoreProductCategory();

    List<StoreProduct> getProductByCategory(String category);
}
