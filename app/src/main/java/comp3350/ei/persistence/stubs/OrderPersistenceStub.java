package comp3350.ei.persistence.stubs;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.ei.objects.Order;
import comp3350.ei.objects.OrderIn;
import comp3350.ei.objects.OrderOut;
import comp3350.ei.objects.Store;
import comp3350.ei.persistence.OrderPersistence;

public class OrderPersistenceStub implements OrderPersistence {
    private final List<Order> orders;

    public OrderPersistenceStub() {
        this.orders = new ArrayList<>();

        Store store = new Store(0);

        orders.add(new OrderIn(1, store, "2019", "01", "01"));
        orders.add(new OrderIn(2, store, "2019", "02", "02"));
        orders.add(new OrderIn(3, store, "2019", "03", "03"));
        orders.add(new OrderIn(4, store, "2019", "04", "04"));
        orders.add(new OrderOut(5, store, "2019", "05", "05"));
    }

    @Override
    public List<Order> getOrderSequential() {
        return Collections.unmodifiableList(orders);
    }

    @Override
    public List<Order> getOrderIns() {
        List<Order> newOrder = new ArrayList<>();
        for (Order o : orders) {
            if (o instanceof OrderIn) {
                newOrder.add(o);
            }
        }
        return Collections.unmodifiableList(newOrder);
    }

    @Override
    public List<Order> getOrderOuts() {
        List<Order> newOrder = new ArrayList<>();
        for (Order o : orders) {
            if (o instanceof OrderOut) {
                newOrder.add(o);
            }
        }
        return Collections.unmodifiableList(newOrder);
    }

    @Override
    public Order insertOrder(Order currentOrder) {
        Order duplicate = null;

        for (Order order : orders) {
            if (currentOrder != null && order.getOrderId() == (currentOrder.getOrderId())) {
                duplicate = order;
            }
        }

        if (duplicate == null) {
            orders.add(currentOrder);
            return currentOrder;
        } else {
            return duplicate;
        }
    }

    @Override
    public Order updateOrder(Order currentOrder) {
        int index;

        index = orders.indexOf(currentOrder);
        if (index >= 0) {
            orders.set(index, currentOrder);
        }
        return currentOrder;
    }

    @Override
    public void deleteOrder(Order currentOrder) {
        int index;

        index = orders.indexOf(currentOrder);
        if (index >= 0) {
            orders.remove(index);
        }
    }
}