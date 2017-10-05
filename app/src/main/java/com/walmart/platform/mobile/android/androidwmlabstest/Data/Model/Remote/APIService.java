package com.walmart.platform.mobile.android.androidwmlabstest.Data.Model.Remote;

import com.walmart.platform.mobile.android.androidwmlabstest.Data.Model.GetProducts;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by kchris6 on 9/27/17.
 */

public interface APIService {
    @GET("/_ah/api/walmart/v1/walmartproducts/{apiKey}/{pageNumber}/{pageSize}")
    Call<GetProducts> postRequest(
            @Path("apiKey") String apiKey,
            @Path("pageNumber") int pageNumber,
            @Path("pageSize") int pageSize);
}
