package lt.andro.chear.util;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import lt.andro.chear.R;
import lt.andro.chear.oms.Order;

import static lt.andro.chear.MainApplication.context;

/**
 * @author Vilius Kraujutis
 * @since 2014-11-01
 */
public class NotificationUtil {

    private static final String GROUP_KEY_ORDERS = "GROUP_KEY_ORDERS";

    public static void showOrderNotification(Order order) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(order.dishName);
        builder.setContentText(order.specialNote);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setGroup(GROUP_KEY_ORDERS);

        getNotificationService().notify(order.id, builder.build());
    }


    public static NotificationManager getNotificationService() {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
