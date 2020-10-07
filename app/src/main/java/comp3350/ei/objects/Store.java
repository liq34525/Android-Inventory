package comp3350.ei.objects;

public class Store {
    private String storeName;
    private int storeId;

    public Store() {
        storeId = 0;
    }

    public Store(final int id) {
        this.storeId = id;
        this.storeName = null;
    }

    public Store(final int id, final String name) {
        this.storeName = name;
        this.storeId = id;
    }

    public String getStoreName() {
        return (this.storeName);
    }

    public int getStoreId() { return (this.storeId);}

    public void setStoreName(final String name) {
        this.storeName = name;
    }

    public void setStoreId(final int id) { this.storeId = id; }

    public String toString() {
        return String.format("Store: %s id: %s", this.storeName, this.storeId);
    }
}
