package comp3350.ei.persistence.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.ei.objects.Product;
import comp3350.ei.objects.Store;
import comp3350.ei.objects.StoreProduct;
import comp3350.ei.persistence.StoreProductPersistence;

public class StoreProductPersistenceStub implements StoreProductPersistence {
    private final List<StoreProduct> storeProducts;

    public StoreProductPersistenceStub() {
        this.storeProducts = new ArrayList<>();

        Store store = new Store(0);
        storeProducts.add(new StoreProduct(store, new Product("Milk powder"), 4.50, 300));
        storeProducts.add(new StoreProduct(store, new Product("Banana"), 2, 100));
        storeProducts.add(new StoreProduct(store, new Product("Chicken wings"), 5, 200));
        storeProducts.add(new StoreProduct(store, new Product("Milk"), 8, 400));
        storeProducts.add(new StoreProduct(store, new Product("Apple"), 6, 300));
    }

    @Override
    public List<StoreProduct> getStoreProductSequential() {
        return Collections.unmodifiableList(storeProducts);
    }

    @Override
    public StoreProduct getStoreProductById(int productID, int storeID) {
        for (StoreProduct prod : storeProducts) {
            if ((prod.getProductId()) == productID && prod.getStore().getStoreId() == storeID) {
                return prod;
            }
        }
        return null;
    }

    @Override
    public StoreProduct insertStoreProduct(StoreProduct storeProduct) {
        if (storeProduct == null) {
            return null;
        }

        StoreProduct duplicate = null;

        for (StoreProduct prod : storeProducts) {
            if ((prod.getProductName()).equalsIgnoreCase(storeProduct.getProductName())
                    && prod.getStore() == storeProduct.getStore()) {
                duplicate = prod;
            }
        }

        if (duplicate == null) {
            storeProducts.add(storeProduct);
            return storeProduct;
        } else {
            return null;
        }
    }

    @Override
    public void updateStoreProduct(StoreProduct storeProduct) {
        //idk what this is supposed to do
    }

    @Override
    public void deleteStoreProduct(StoreProduct currentStoreProduct) {
        int index;

        index = storeProducts.indexOf(currentStoreProduct);
        if (index >= 0) {
            storeProducts.remove(index);
        }
    }

    @Override
    public List<String> getStoreProductCategory() {
        List<String> categories = new ArrayList<>();
        for (StoreProduct prod : getStoreProductSequential()) {
            if (prod.getCategory() != null) {
                String cat = prod.getCategory();

                for (String c : categories) {
                    if (cat.equalsIgnoreCase(c)) {
                        break;
                    }
                }

                categories.add(cat);
            }
        }
        return categories;
    }

    @Override
    public List<StoreProduct> getProductByCategory(String category) {
        List<StoreProduct> productsInCat = new ArrayList<>();

        for (StoreProduct prod : getStoreProductSequential()) {
            if (prod.getCategory().equalsIgnoreCase(category)) {
                productsInCat.add(prod);
            }
        }
        return productsInCat;
    }
}
