package comp3350.ei.persistence;

import java.util.List;

import comp3350.ei.objects.Order;

public interface OrderPersistence {
    List<Order> getOrderSequential();

    List<Order> getOrderIns();

    List<Order> getOrderOuts();

    Order insertOrder(Order currentOrder);

    Order updateOrder(Order currentOrder);

    void deleteOrder(Order currentOrder);
}

