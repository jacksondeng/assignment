package com.gemalto.assignment.api;

import com.gemalto.assignment.data.Urls;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by jacksondeng on 15/12/18.
 */

public interface GemaltoApi {
    @GET(Urls.baseUrl)
    Observable<ApiResponse> getRandomUserByGender(@Query("gender") String gender);

    @GET(Urls.baseUrl)
    Observable<ApiResponse> getRandomUserBySeed(@Query("seed") int seed);

    @GET(Urls.baseUrl)
    Observable<ApiResponse> getMultipleRandomUsers(@Query("results") int number);
}