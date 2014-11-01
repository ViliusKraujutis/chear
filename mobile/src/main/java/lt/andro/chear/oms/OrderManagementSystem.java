package lt.andro.chear.oms;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;

import lt.andro.chear.util.NotificationUtil;

/**
 * @author Vilius Kraujutis
 * @since 2014-11-01 16:50
 */
public class OrderManagementSystem {
    private static OrderManagementSystem instance;

    private List<Order> orders = new ArrayList<Order>();

    public static OrderManagementSystem getInstance() {
        if (instance == null) {
            instance = new OrderManagementSystem();
        }
        return instance;
    }

    public void addNewOrder(String dishName, String specialNote) {
        Order order = new Order(dishName, specialNote, orders.size());
        addNewOrder(order);
        NotificationUtil.showOrderNotification(order);
    }

    private void addNewOrder(Order order) {
        orders.add(order);
    }

    public Order getOrder(int orderId) {
        if (0 >= orderId || orderId >= orders.size()) {
            return null;
        }

        return orders.get(orderId);
    }
}
