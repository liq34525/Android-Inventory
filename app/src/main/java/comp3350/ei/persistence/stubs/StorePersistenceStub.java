package comp3350.ei.persistence.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.ei.objects.Store;
import comp3350.ei.persistence.StorePersistence;

public class StorePersistenceStub implements StorePersistence {
    private final List<Store> stores;

    public StorePersistenceStub() {
        this.stores = new ArrayList<>();

        stores.add(new Store(0));
    }

    @Override
    public List<Store> getStoreSequential() {
        return Collections.unmodifiableList(stores);
    }

    @Override
    public List<Store> getStores() {
        List<Store> newStore = new ArrayList<>();
        for (Store o : stores) {
            newStore.add(o);
        }
        return Collections.unmodifiableList(newStore);
    }

    @Override
    public Store getStore(int storeId) {
        Store newStore = null;
        for (Store o : stores) {
            if (o.getStoreId() == storeId) {
                newStore = o;
                return newStore;
            }
        }
        return newStore;
    }


    @Override
    public Store insertStore(Store currentStore) {
        Store duplicate = null;

        for (Store Store : stores) {
            if (currentStore != null && Store.getStoreId() == currentStore.getStoreId()) {
                duplicate = Store;
            }
        }

        if (duplicate == null) {
            stores.add(currentStore);
            return currentStore;
        } else {
            return duplicate;
        }
    }

    @Override
    public Store updateStore(Store currentStore) {
        int index;

        index = stores.indexOf(currentStore);
        if (index >= 0) {
            stores.set(index, currentStore);
        }
        return currentStore;
    }

    @Override
    public void deleteStore(Store currentStore) {
        int index;

        index = stores.indexOf(currentStore);
        if (index >= 0) {
            stores.remove(index);
        }
    }
}