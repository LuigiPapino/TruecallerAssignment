package net.dragora.truecallerassignment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.dragora.truecallerassignment.tasks.BaseTask;
import net.dragora.truecallerassignment.tasks.T10thCharacterTask;
import net.dragora.truecallerassignment.tasks.T10thCharacterTask_;
import net.dragora.truecallerassignment.tasks.TEvery10thCharacterTask;
import net.dragora.truecallerassignment.tasks.TEvery10thCharacterTask_;
import net.dragora.truecallerassignment.tasks.TWordCounterTask;
import net.dragora.truecallerassignment.tasks.TWordCounterTask_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.Map;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @ViewById
    Toolbar toolbar;
    @ViewById
    ProgressBar progressBar10thChar;
    @ViewById
    TextView textTask10thChar;
    @ViewById
    ProgressBar progressBarEvery10thChar;
    @ViewById
    TextView textTaskEvery10thChar;
    @ViewById
    ProgressBar progressBarWordCounter;
    @ViewById
    TextView textTaskWordCounter;
    @ViewById
    FloatingActionButton fab;
    private LoaderManager loaderManager;

    @AfterViews
    protected void setup() {
        Log.d(TAG, "setup() called with: " + "");
        setSupportActionBar(toolbar);

        loaderManager = getSupportLoaderManager();


        //Init loader to restore e previously started loader
        //It is useful to manage configuration changes like screen rotation without lose the current AsyncTaskLoader execution
        ((BaseTask)loaderManager.initLoader(T10thCharacterTask.TASK_ID, null, callbacks10thCharacterTask))
                .setOnStartLoadingCallback(onStartLoadingCallback);
        ((BaseTask)loaderManager.initLoader(TEvery10thCharacterTask.TASK_ID, null, callbacksEvery10thCharacterTask))
                .setOnStartLoadingCallback(onStartLoadingCallback);
        ((BaseTask)loaderManager.initLoader(TWordCounterTask.TASK_ID, null, callbacksWordCounterTask))
                .setOnStartLoadingCallback(onStartLoadingCallback);

        refreshProgressBars();
    }




    @Click
    protected void fab() {
        clearTextViews();
        //Force the start of the loader to have a new execution of the tasks
        ((BaseTask)loaderManager.restartLoader(T10thCharacterTask.TASK_ID, null, callbacks10thCharacterTask))
                .setOnStartLoadingCallback(onStartLoadingCallback)
                .forceLoad();
        ((BaseTask)loaderManager.restartLoader(TEvery10thCharacterTask.TASK_ID, null, callbacksEvery10thCharacterTask))
                .setOnStartLoadingCallback(onStartLoadingCallback)
                .forceLoad();
        ((BaseTask)loaderManager.restartLoader(TWordCounterTask.TASK_ID, null, callbacksWordCounterTask))
                .setOnStartLoadingCallback(onStartLoadingCallback)
                .forceLoad();

        refreshProgressBars();

    }

    private void refreshProgressBars() {
        Log.d(TAG, "refreshProgressBars() called with: " + "");
        refreshProgressBar(progressBar10thChar, T10thCharacterTask.TASK_ID);
        refreshProgressBar(progressBarEvery10thChar, TEvery10thCharacterTask.TASK_ID);
        refreshProgressBar(progressBarWordCounter, TWordCounterTask.TASK_ID);
    }

    @UiThread
    protected void refreshProgressBar(final ProgressBar progressBar, int taskID) {
        Log.d(TAG, "refreshProgressBar() called with: " + "progressBar = [" + progressBar + "], taskID = [" + taskID + "]");
        boolean isRunning = ((BaseTask)loaderManager.getLoader(taskID)).isRunning();
        progressBar.setVisibility(isRunning ? View.VISIBLE : View.INVISIBLE);
    }


    //======================= LOADER CALLBACKS
    private BaseTask.OnStartLoadingCallback onStartLoadingCallback = new BaseTask.OnStartLoadingCallback() {
        @Override
        public void onStartLoading() {
            Log.d(TAG, "onStartLoadingCallback() called with: " + "");
            refreshProgressBars();
        }
    };

    LoaderManager.LoaderCallbacks<String> callbacks10thCharacterTask = new LoaderManager.LoaderCallbacks<String>() {
        @Override
        public Loader<String> onCreateLoader(int id, Bundle args) {
            Log.d(TAG, "onCreateLoader() called with: " + "id = [" + id + "], args = [" + args + "]");
            return T10thCharacterTask_.getInstance_(MainActivity.this);
        }

        @Override
        public void onLoadFinished(Loader<String> loader, String data) {
            Log.d(TAG, "onLoadFinished() called with: " + "data = [" + data + "]");
            if (data == null) {
                textTask10thChar.setError("Unkown error");
                textTask10thChar.setText("");
            } else {
                textTask10thChar.setText(String.format("'%s'", data));
            }
            refreshProgressBars();

        }

        @Override
        public void onLoaderReset(Loader<String> loader) {

        }

    };

    LoaderManager.LoaderCallbacks<CharSequence> callbacksEvery10thCharacterTask = new LoaderManager.LoaderCallbacks<CharSequence>() {
        @Override
        public Loader<CharSequence> onCreateLoader(int id, Bundle args) {
            Log.d(TAG, "onCreateLoader() called with: " + "id = [" + id + "], args = [" + args + "]");
            return TEvery10thCharacterTask_.getInstance_(MainActivity.this);
        }

        @Override
        public void onLoadFinished(Loader<CharSequence> loader, CharSequence data) {
            Log.d(TAG, "onLoadFinished() called with: " + "data = [" + data + "]");
            if (data == null) {
                textTaskEvery10thChar.setError("Unkown error");
                textTaskEvery10thChar.setText("");
            } else {
                textTaskEvery10thChar.setText(String.format("'%s'", data));
            }
            refreshProgressBars();

        }

        @Override
        public void onLoaderReset(Loader<CharSequence> loader) {

        }

    };

    LoaderManager.LoaderCallbacks<Map<String, Integer>> callbacksWordCounterTask = new LoaderManager.LoaderCallbacks<Map<String, Integer>>() {
        @Override
        public Loader<Map<String, Integer>> onCreateLoader(int id, Bundle args) {
            Log.d(TAG, "onCreateLoader() called with: " + "id = [" + id + "], args = [" + args + "]");
            return TWordCounterTask_.getInstance_(MainActivity.this);
        }

        @Override
        public void onLoadFinished(Loader<Map<String, Integer>> loader, Map<String, Integer> data) {
            Log.d(TAG, "onLoadFinished() called with: " + "data = [" + data + "]");
            if (data == null) {
                textTaskWordCounter.setError("Unkown error");
                textTaskWordCounter.setText("");
            } else {
                textTaskWordCounter.setText(String.format("'truecaller: %d'", data.get("truecaller")));
            }
            refreshProgressBars();
        }

        @Override
        public void onLoaderReset(Loader<Map<String, Integer>> loader) {

        }

    };

    private void clearTextViews() {
        textTask10thChar.setText("");
        textTask10thChar.setError(null);
        textTaskEvery10thChar.setText("");
        textTaskEvery10thChar.setError(null);
        textTaskWordCounter.setText("");
        textTaskWordCounter.setError(null);
    }



    @Pref
    MyPrefs_ myPrefs;

    //================================
    // Some testing tools to inject error and delay

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        actionInjectDelay.setChecked(myPrefs.injectDelay().get() != 0);
        actionInjectError.setChecked(myPrefs.injectError().get());
        return super.onCreateOptionsMenu(menu);
    }

    @OptionsMenuItem
    MenuItem actionInjectDelay;

    @OptionsMenuItem
    MenuItem actionInjectError;

    @OptionsItem(R.id.action_inject_delay)
    protected void injectDelay() {
        actionInjectDelay.setChecked(!actionInjectDelay.isChecked());
        myPrefs.injectDelay().put(actionInjectDelay.isChecked() ? 10 * 1000 : 0);
    }

    @OptionsItem(R.id.action_inject_error)
    protected void injectError() {
        actionInjectError.setChecked(!actionInjectError.isChecked());
        myPrefs.injectError().put(actionInjectError.isChecked());
    }


    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy() called with: " + "");
        onStartLoadingCallback = null;
        callbacks10thCharacterTask = null;
        callbacksEvery10thCharacterTask = null;
        callbacksWordCounterTask = null;


        loaderManager = null;

        super.onDestroy();

    }
}
