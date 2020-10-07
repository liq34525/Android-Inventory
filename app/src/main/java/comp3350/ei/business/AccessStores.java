package comp3350.ei.business;

import java.util.Collections;
import java.util.List;

import comp3350.ei.application.Services;
import comp3350.ei.objects.Store;
import comp3350.ei.persistence.StorePersistence;

public class AccessStores {
    private final StorePersistence StorePersistence;
    private List<Store> stores;
    private Store store;
    private int currentStore;

    public AccessStores() {
        StorePersistence = Services.getStorePersistence();
        store = null;
        stores = null;
        currentStore = 0;
    }

    public AccessStores(StorePersistence sp) {
        StorePersistence = sp;
        store = null;
        stores = null;
        currentStore = 0;
    }

    public List<Store> getStores() {
        List<Store> stores = StorePersistence.getStoreSequential();
        return Collections.unmodifiableList(stores);
    }

    public Store getStore(int storeID) {
        List<Store> new_stores = StorePersistence.getStoreSequential();
        for (Store store : new_stores) {
            if (store.getStoreId() == storeID) {
                return store;
            }
        }
        return null;
    }

    public Store getSequential() {
        if (store == null) {
            stores = StorePersistence.getStoreSequential();
            currentStore = 0;
        }
        if (currentStore < stores.size()) {
            store = stores.get(currentStore);
            currentStore++;
        } else {
            store = null;
            currentStore = 0;
        }
        return store;
    }

    public Store insertStore(Store new_store) {
        if (new_store == null) {
            return null;
        }
        return StorePersistence.insertStore(new_store);
    }

    public void deleteStore(Store currentStore) {
        StorePersistence.deleteStore(currentStore);
    }
}
