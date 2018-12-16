package com.gemalto.assignment.api;

import com.gemalto.assignment.data.Urls;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by jacksondeng on 15/12/18.
 */

public interface GemaltoApiInterface {
    @GET(Urls.baseUrl)
    Observable<ApiResponse> getUsers(@QueryMap(encoded = true) Map<String,String> queryFilter);
}