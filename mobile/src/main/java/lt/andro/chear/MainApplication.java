package lt.andro.chear;

import android.app.Application;
import android.content.Context;

/**
 * @author Vilius Kraujutis
 * @since 2014-11-01 16:59
 */
public class MainApplication extends Application {
    private static Context context;

    @Override public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
