package comp3350.ei.persistence;

import java.util.List;

import comp3350.ei.objects.Store;

public interface StorePersistence {
    List<Store> getStores();
    List<Store> getStoreSequential();
    Store getStore(int storeId);
    Store insertStore(Store currStore);
    Store updateStore(Store currStore);
    void deleteStore(Store currStore);
}
