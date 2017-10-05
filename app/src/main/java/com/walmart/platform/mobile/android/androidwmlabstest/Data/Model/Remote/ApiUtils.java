package com.walmart.platform.mobile.android.androidwmlabstest.Data.Model.Remote;

/**
 * Created by kchris6 on 9/27/17.
 */

public class ApiUtils {

    public static final String BASE_URL = "https://walmartlabs-test.appspot.com/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
