package net.dragora.truecallerassignment.tasks;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import net.dragora.truecallerassignment.MyPrefs_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.lang.ref.WeakReference;

/**
 * Base TaskLoader with common operation for all tasks.
 * Inject Delay: add a delay to loadInBackground
 * INject Error: return null result to display an error
 * Created by Luigi Papino on 14/09/15.
 */
@EBean
public abstract class BaseTask<T> extends AsyncTaskLoader<T> {

    private static final String TAG = BaseTask.class.getSimpleName();
    private boolean isRunning = false;


    public BaseTask(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        Log.d(TAG, "onStartLoading() called with: " + "");
        super.onStartLoading();
    }

    @Pref
    MyPrefs_ myPrefs;

    @Override
    public T loadInBackground() {
        isRunning = true;
        if (onStartLoadingCallback.get() != null) {
            onStartLoadingCallback.get().onStartLoading();
        }
        Log.d(TAG, "loadInBackground() called with: " + "");
        // Some debug utility common to all tasks
        if (myPrefs.injectDelay().get() != 0) {
            try {
                Thread.sleep(myPrefs.injectDelay().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (myPrefs.injectError().get()) {
            isRunning = false;
            return null;
        }

        T result = background();
        Log.d(TAG, "loadInBackground() returned:" + result + "|");
        isRunning = false;

        return result;
    }


    public abstract T background();

    public boolean isRunning() {
        return isRunning;
    }

    public BaseTask setOnStartLoadingCallback(OnStartLoadingCallback onStartLoadingCallback) {
        this.onStartLoadingCallback = new WeakReference<>(onStartLoadingCallback);
        return this;
    }

    private WeakReference<OnStartLoadingCallback> onStartLoadingCallback = null;

    public static interface OnStartLoadingCallback {
        public void onStartLoading();
    }


}
