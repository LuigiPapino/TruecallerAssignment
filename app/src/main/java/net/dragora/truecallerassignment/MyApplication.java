package net.dragora.truecallerassignment;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Luigi Papino on 17/09/15.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
