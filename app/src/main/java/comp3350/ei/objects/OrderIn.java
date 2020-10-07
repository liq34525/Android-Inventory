package comp3350.ei.objects;

import java.util.HashMap;
import java.util.Map;

import comp3350.ei.business.AccessStoreProducts;

public class OrderIn extends Order {
    private HashMap<StoreProduct, Integer> orderInProducts;

    public OrderIn() {
        super();
        this.orderInProducts = new HashMap<>();
    }

    public OrderIn(Store store) {
        super(store);
        this.orderInProducts = new HashMap<>();
    }

    public OrderIn(final int orderId, final Store storeId, final String year,
                   final String month, final String day) {
        super(orderId, storeId, year, month, day);
        this.orderInProducts = new HashMap<>();
    }

    public HashMap<StoreProduct, Integer> getProducts() {
        return orderInProducts;
    }

    public void addStoreProduct(StoreProduct sp) {
        addStoreProduct(sp, 1);
    }

    public void setOrderedProduct(final HashMap<StoreProduct, Integer> newOrderInProducts) {
        this.orderInProducts = newOrderInProducts;
    }

    public void addStoreProduct(StoreProduct sp, int quantity) {
        for (Map.Entry<StoreProduct, Integer> entry : orderInProducts.entrySet()) {

            StoreProduct prod = entry.getKey();

            if (sp.getProductId() == prod.getProductId()) {
                quantity += entry.getValue();
                orderInProducts.remove(prod);
                break;
            }
        }

        if (quantity <= 0) {
            quantity = 0;
        }
        orderInProducts.put(sp, quantity);
    }

    public void finalizeOrder(AccessStoreProducts asp) {
        for (Map.Entry<StoreProduct, Integer> entry : orderInProducts.entrySet()) {

            StoreProduct prod = entry.getKey();
            int quantity = entry.getValue();
            prod.setQuantity(prod.getQuantity() + quantity);

            asp.updateDatabase(prod);
        }
    }
}
