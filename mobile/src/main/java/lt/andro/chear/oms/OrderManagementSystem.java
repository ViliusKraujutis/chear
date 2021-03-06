package lt.andro.chear.oms;

import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

import static lt.andro.chear.MainApplication.realm;

/**
 * @author Vilius Kraujutis
 * @since 2014-11-01 16:50
 */
public class OrderManagementSystem {
    private static OrderManagementSystem instance;

    private List<Order> orders = new ArrayList<Order>();

    public OrderManagementSystem() {
        // TODO populate data from realm
    }

    public static OrderManagementSystem getInstance() {
        if (instance == null) {
            instance = new OrderManagementSystem();
        }
        return instance;
    }

    public Order addNewOrder(String dishName, String specialNote) {
        realm.beginTransaction();
        Order order = realm.createObject(Order.class);
        order.setId(orders.size());
        order.setDishName(dishName);
        order.setSpecialNote(specialNote);
        realm.commitTransaction();

        addNewOrder(order);
        return order;
    }

    private void addNewOrder(Order order) {
        orders.add(order);
    }

    public Order getOrder(int orderId) {
        if (0 > orderId || orderId >= orders.size()) {
            return null;
        }

        return orders.get(orderId);
    }

    public RealmResults<Order> getPendingOrders() {
        RealmResults<Order> pendingOrders = realm.where(Order.class).equalTo("status", Order.Status.PENDING).findAll();
        return pendingOrders;
    }
}
