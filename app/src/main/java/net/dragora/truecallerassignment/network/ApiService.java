package net.dragora.truecallerassignment.network;

import retrofit.client.Response;
import retrofit.http.GET;

/**
 * Created by Luigi Papino on 13/09/15.
 */
public interface ApiService {

    @GET("/support")
    Response getSupport();
}
