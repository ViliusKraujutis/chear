package lt.andro.chear.util;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;

import lt.andro.chear.NewOrderActivity;
import lt.andro.chear.R;
import lt.andro.chear.oms.Order;

import static android.support.v4.app.NotificationCompat.Action.Builder;
import static android.support.v4.app.NotificationCompat.WearableExtender;
import static lt.andro.chear.MainApplication.context;
import static lt.andro.chear.MainApplication.resources;

/**
 * @author Vilius Kraujutis
 * @since 2014-11-01
 */
public class WearUtils {
    public static final String REJECT_RESULT_KEY = "REJECT_RESULT_KEY";
    public static final String EXTRA_WEAR_ORDER_ID = "EXTRA_WEAR_ORDER_ID";

    public static final int WEAR_REJECTION_REQUEST_CODE = 1;

    public static void addRejectAction(NotificationCompat.Builder builder, Order order) {
        WearableExtender extender = new WearableExtender();
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
        Builder actionBuilder = new Builder(R.drawable.ic_action_reply, rejectLabel, replyPendingIntent);
        actionBuilder.addRemoteInput(remoteInput);

        builder.extend(extender.addAction(actionBuilder.build()));
    }


    /**
     * Obtain the intent that started this activity by calling
     * Activity.getIntent() and pass it into this method to
     * get the associated voice input string.
     */
    public static CharSequence getRejectionMessage(Intent intent) {
        if (intent == null) {
            return null;
        }
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(WearUtils.REJECT_RESULT_KEY);
        } else {
            return null;
        }
    }

    public static void addDoneAction(NotificationCompat.Builder builder, Order order) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com")); // TODO dismiss notification and mark order as done

        intent.putExtra(EXTRA_WEAR_ORDER_ID, order.getId());

        PendingIntent actionIntent = PendingIntent.getActivity(context, 0, intent, 0);
        builder.addAction(R.drawable.ic_launcher, "Done", actionIntent);//TODO replace launcher icon
    }
}
