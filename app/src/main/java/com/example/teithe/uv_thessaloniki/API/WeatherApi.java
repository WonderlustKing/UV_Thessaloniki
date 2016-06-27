package com.example.teithe.uv_thessaloniki.API;

import com.example.teithe.uv_thessaloniki.Model.UvModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

/**
 * Created by christos on 26/6/2016.
 */
public interface WeatherApi {

    @GET("air/1.0/uvi/current?lat=40&lon=22&appid=119cffe1b057340d2b98f4cee862f3d4")
    Call<UvModel> getFeed();

}
