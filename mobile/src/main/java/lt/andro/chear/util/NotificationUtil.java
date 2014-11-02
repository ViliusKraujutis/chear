package lt.andro.chear.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;

import lt.andro.chear.NewOrderActivity;
import lt.andro.chear.R;
import lt.andro.chear.oms.Dish;
import lt.andro.chear.oms.DishMenu;
import lt.andro.chear.oms.Order;

import static lt.andro.chear.MainApplication.context;
import static lt.andro.chear.MainApplication.resources;

/**
 * @author Vilius Kraujutis
 * @since 2014-11-01
 */
public class NotificationUtil {

    public static final String REJECT_RESULT_KEY = "REJECT_RESULT_KEY";
    public static final String EXTRA_WEAR_ORDER_ID = "EXTRA_WEAR_ORDER_ID";
    public static final int WEAR_REJECTION_REQUEST_CODE = 1;
    private static final String GROUP_KEY_ORDERS = "GROUP_KEY_ORDERS";
    public static final int LONG_NOTIFICATION_LENGTH = 15;

    public static void showOrderNotification(Order order) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        NotificationCompat.WearableExtender extender = new NotificationCompat.WearableExtender();

        String dishName = order.getDishName();
        Dish dish = DishMenu.getInstance().getDish(dishName);
        if (dish == null) {
            return;
        }
        addDoneAction(builder, order);
        addRejectAction(builder, order);

        builder.setContentTitle(dishName);
        String specialNote = order.getSpecialNote();
        if (isLongNote(specialNote)) {
            builder.setContentText(context.getString(R.string.new_order_has_special_notes));
            addPage(extender, context.getString(R.string.new_order_page_title), specialNote);
        } else {
            builder.setContentText(specialNote);
        }
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setGroup(GROUP_KEY_ORDERS);

        builder.setLargeIcon(dish.getDishImage());

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

    public static void addRejectAction(NotificationCompat.Builder builder, Order order) {
        NotificationCompat.WearableExtender extender = new NotificationCompat.WearableExtender();
        Bitmap replyIcon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_action_reply); // TODO change reply icon with nice rejection icon
        extender.setHintHideIcon(true)
                .setBackground(replyIcon);
        String rejectLabel = resources.getString(R.string.reject_voice_action_label);
        String[] rejectChoices = resources.getStringArray(R.array.rejection_choices);

        RemoteInput remoteInput = new RemoteInput.Builder(REJECT_RESULT_KEY)
                .setLabel(rejectLabel)
                .setChoices(rejectChoices)
                .build();

        // Create an intent for the rejection action
        Intent rejectionIntent = new Intent(context, NewOrderActivity.class);
        rejectionIntent.putExtra(EXTRA_WEAR_ORDER_ID, order.getId());
        PendingIntent replyPendingIntent =
                PendingIntent.getActivity(context, WEAR_REJECTION_REQUEST_CODE, rejectionIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        // Create the reply action and add the remote input
        NotificationCompat.Action.Builder actionBuilder = new NotificationCompat.Action.Builder(R.drawable.ic_action_reply, rejectLabel, replyPendingIntent);
        actionBuilder.addRemoteInput(remoteInput);

        builder.extend(extender.addAction(actionBuilder.build()));
    }

    public static CharSequence getRejectionMessage(Intent intent) {
        if (intent == null) {
            return null;
        }
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(REJECT_RESULT_KEY);
        } else {
            return null;
        }
    }

    public static void addDoneAction(NotificationCompat.Builder builder, Order order) {
        Intent intent = new Intent(context, NewOrderActivity.class);
        intent.putExtra(EXTRA_WEAR_ORDER_ID, order.getId());

        PendingIntent actionIntent = PendingIntent.getActivity(context, 0, intent, 0);
        builder.addAction(R.drawable.ic_launcher, "Done", actionIntent);
    }
}
