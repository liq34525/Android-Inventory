package comp3350.ei.business;

import java.util.Collections;
import java.util.List;

import comp3350.ei.application.Services;
import comp3350.ei.objects.Order;
import comp3350.ei.persistence.OrderPersistence;

public class AccessOrders {
    private final OrderPersistence OrderPersistence;
    private List<Order> orders;
    private Order order;
    private int currentOrder;

    public AccessOrders() {
        OrderPersistence = Services.getOrderPersistence();
        order = null;
        orders = null;
        currentOrder = 0;
    }

    public AccessOrders(OrderPersistence op) {
        OrderPersistence = op;
        order = null;
        orders = null;
        currentOrder = 0;
    }

    public List<Order> getOrders() {
        List<Order> orders = OrderPersistence.getOrderSequential();
        return Collections.unmodifiableList(orders);
    }

    public List<Order> getOrderIns() {
        List<Order> orderIns = OrderPersistence.getOrderIns();
        return Collections.unmodifiableList(orderIns);
    }

    public List<Order> getOrderOuts() {
        List<Order> orderOuts = OrderPersistence.getOrderOuts();
        return Collections.unmodifiableList(orderOuts);
    }

    public Order getOrder(int orderID) {
        List<Order> new_orders = OrderPersistence.getOrderSequential();
        for (Order order : new_orders) {
            if (order.getOrderId() == (orderID)) {
                return order;
            }
        }
        return null;
    }

    public Order getSequential() {
        if (order == null) {
            orders = OrderPersistence.getOrderSequential();
            currentOrder = 0;
        }
        if (currentOrder < orders.size()) {
            order = orders.get(currentOrder);
            currentOrder++;
        } else {
            order = null;
            currentOrder = 0;
        }
        return order;
    }

    public Order insertOrder(Order newOrder) {
        if (newOrder == null) {
            return null;
        }
        return OrderPersistence.insertOrder(newOrder);
    }

    public void deleteOrder(Order currentOrder) {
        OrderPersistence.deleteOrder(currentOrder);
    }
}
