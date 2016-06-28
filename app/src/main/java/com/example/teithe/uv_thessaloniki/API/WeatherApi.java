package com.example.teithe.uv_thessaloniki.API;

import com.example.teithe.uv_thessaloniki.Model.UvModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

/**
 * Created by christos on 26/6/2016.
 */
public interface WeatherApi {

    @GET("v3/uvi/22.7,40.2/current.json?appid=119cffe1b057340d2b98f4cee862f3d4")
    Call<UvModel> getFeed();

}
