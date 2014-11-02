package lt.andro.chear.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
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
    public static final int LONG_NOTIFICATION_LENGTH = 15;

    public static void showOrderNotification(Order order) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        NotificationCompat.WearableExtender extender = new NotificationCompat.WearableExtender();

        WearUtils.addDoneAction(builder, order);
        WearUtils.addRejectAction(builder, order);

        builder.setContentTitle(order.getDishName());
        String specialNote = order.getSpecialNote();
        if (isLongNote(specialNote)) {
            builder.setContentText(context.getString(R.string.new_order_has_special_notes));
            addPage(extender, context.getString(R.string.new_order_page_title), specialNote);
        } else {
            builder.setContentText(specialNote);
        }
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setGroup(GROUP_KEY_ORDERS);

        final Notification notification = builder.build();
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        notification.ledARGB = Color.YELLOW;

        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;

        getNotificationService().notify(order.getId(), builder.extend(extender).build());
    }

    private static void addPage(NotificationCompat.WearableExtender extender, String contentTitle, String contentText) {
        NotificationCompat.BigTextStyle pageStyle = new NotificationCompat.BigTextStyle();
        pageStyle.setBigContentTitle(contentTitle).bigText(contentText);

        Notification page =
                new NotificationCompat.Builder(context)
                        .setStyle(pageStyle)
                        .build();

        extender.addPage(page);
    }

    private static boolean isLongNote(String text) {
        return text != null && text.length() > LONG_NOTIFICATION_LENGTH;
    }


    public static NotificationManager getNotificationService() {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
