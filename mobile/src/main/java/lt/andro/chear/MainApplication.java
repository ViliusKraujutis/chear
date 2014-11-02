package lt.andro.chear;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import io.realm.Realm;

/**
 * @author Vilius Kraujutis
 * @since 2014-11-01 16:59
 */
public class MainApplication extends Application {
    public static Context context;
    public static Resources resources;
    public static Realm realm;

    @Override public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        resources = context.getResources();
        realm = Realm.getInstance(context);
    }
}
