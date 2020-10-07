package comp3350.ei.objects;

import java.util.HashMap;

public abstract class Order {
    private int orderId;
    private Store store;
    private String year;
    private String month;
    private String day;

    Order() {
        this.orderId = 0;
        this.year = null;
        this.month = null;
        this.day = null;
        this.store = null;
    }

    public Order(Store store) {
        this.orderId = -1;
        this.year = null;
        this.month = null;
        this.day = null;
        this.store = store;
    }

    public Order(final int orderId, final Store storeId,
                 final String year, final String month, final String day) {
        this.orderId = orderId;
        this.store = storeId;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getOrderId() {
        return (this.orderId);
    }

    public Store getStore() {
        return (this.store);
    }

    public String getYear() {
        return (this.year);
    }

    public String getMonth() {
        return (this.month);
    }

    public String getDay() {
        return (this.day);
    }

    public void setOrderId(int id) { this.orderId = id; }

    public void setYear(String newYear) {
        this.year = newYear;
    }

    public void setMonth(String newMonth) {
        this.month = newMonth;
    }

    public void setDay(String newDay) {
        this.day = newDay;
    }

    public abstract HashMap<StoreProduct, Integer> getProducts();

    public abstract void setOrderedProduct(final HashMap<StoreProduct, Integer> newOrderProducts);
}