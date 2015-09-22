package net.dragora.truecallerassignment.network;

import android.os.AsyncTask;

import retrofit.RestAdapter;
import retrofit.android.MainThreadExecutor;
import retrofit.client.OkClient;

/**
 * Created by Luigi Papino on 13/09/15.
 */
public class NetworkApi {

    private static final String BASE_HOST = "http://www.truecaller.com";


    private static RestAdapter restAdapter;

    public static ApiService getApiService() {
        if (apiService == null)
            init();

        return apiService;
    }

    private static  ApiService apiService;


    private static  void init() {
        restAdapter = new RestAdapter.Builder()
                .setClient(new OkClient())
                .setEndpoint(BASE_HOST)
                .setExecutors(AsyncTask.THREAD_POOL_EXECUTOR, new MainThreadExecutor()) // Just to allow espresso to wait for network events
                .build();
        apiService = restAdapter.create(ApiService.class);


    }
}
