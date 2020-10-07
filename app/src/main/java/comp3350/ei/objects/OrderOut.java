package comp3350.ei.objects;

import java.util.HashMap;
import java.util.Map;

import comp3350.ei.business.AccessStoreProducts;

public class OrderOut extends Order {
    private HashMap<StoreProduct, Integer> orderOutProducts;

    public OrderOut() {
        super();
        this.orderOutProducts = new HashMap<>();
    }

    public OrderOut(Store store) {
        super(store);
        this.orderOutProducts = new HashMap<>();
    }

    public OrderOut(final int orderId, final Store storeId, final String year,
                    final String month, final String day) {
        super(orderId, storeId, year, month, day);
        this.orderOutProducts = new HashMap<>();
    }

    public HashMap<StoreProduct, Integer> getProducts() {
        return orderOutProducts;
    }

    public void addStoreProduct(StoreProduct sp) {
        addStoreProduct(sp, 1);
    }

    public void setOrderedProduct(final HashMap<StoreProduct, Integer> newOrderOutProducts) {
        this.orderOutProducts = newOrderOutProducts;
    }

    public void addStoreProduct(StoreProduct sp, int quantity) {
        for (Map.Entry<StoreProduct, Integer> entry : orderOutProducts.entrySet()) {

            StoreProduct prod = entry.getKey();

            if (sp.getProductId() == prod.getProductId()) {
                quantity += entry.getValue();
                orderOutProducts.remove(prod);
                break;
            }
        }

        if (quantity <= 0) {
            quantity = 0;
        }
        orderOutProducts.put(sp, quantity);
    }

    public void finalizeOrder(AccessStoreProducts asp) {
        for (Map.Entry<StoreProduct, Integer> entry : orderOutProducts.entrySet()) {

            StoreProduct prod = entry.getKey();
            int quantity = entry.getValue();
            prod.setQuantity(prod.getQuantity() - quantity);

            asp.updateDatabase(prod);
        }
    }
}
