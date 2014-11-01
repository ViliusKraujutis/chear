package lt.andro.chear.dialog;

import android.support.v4.app.FragmentActivity;
import android.util.Log;

/**
 * @author Vilius Kraujutis
 * @since 2014-11-01 16:21
 */
public class Dialogs {
    private static final String TAG = Dialogs.class.getCanonicalName();

    public static void show(BaseDialog dialog, FragmentActivity activity) {
        String tag = dialog.getTagName();
        try {
            dialog.show(activity.getSupportFragmentManager(), tag);
        } catch (IllegalStateException e) {
            Log.e(TAG, "Could not show dialog with tag " + tag, e);
        }

    }
}
