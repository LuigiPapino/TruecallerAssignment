package net.dragora.truecallerassignment.network;

import retrofit.RestAdapter;
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
                .build();
        apiService = restAdapter.create(ApiService.class);


    }
}
