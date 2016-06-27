package com.example.teithe.uv_thessaloniki;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teithe.uv_thessaloniki.API.WeatherApi;
import com.example.teithe.uv_thessaloniki.Model.UvModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    String URL = "http://api.owm.io/";
    private CardView cardView;
    private TextView date_textview;
    private TextView hour_textview;
    private TextView tips_textview;
    private TextView risk_textview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUV();
                Snackbar.make(view, "Refreshed", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        cardView = (CardView) findViewById(R.id.card_view);
        date_textview = (TextView) findViewById(R.id.date_value);
        hour_textview = (TextView) findViewById(R.id.time_value);
        tips_textview = (TextView) findViewById(R.id.tips);
        risk_textview = (TextView) findViewById(R.id.risk_value);

        getUV();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getUV(){

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        WeatherApi api = retrofit.create(WeatherApi.class);
         Call<UvModel> call = api.getFeed();
        call.enqueue(new Callback<UvModel>() {
            @Override
            public void onResponse(Call<UvModel> call, Response<UvModel> response) {
                SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy");
                sdfDate.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));
                String date = sdfDate.format(new Date(response.body().getDate() * 1000L));
                date_textview.setText(date);

                SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm");
                sdfHour.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));
                String hour = sdfHour.format(new Date(response.body().getDate() * 1000L));
                hour_textview.setText(hour);

                uvObjInit(response.body().getValue());
            }

            @Override
            public void onFailure(Call<UvModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something go wrong :( ", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void uvObjInit(double value){
        UV uv = null;
        if(value >= 11){
            uv = new UVExtreme();
        }else if(value >= 8){
            uv = new UVVeryHigh();
        }else if(value >= 6){
            uv = new UVHigh();
        }else if(value >= 3){
            uv= new UVModerate();
        }else if(value > 0){
            uv = new UVLow();
        }

        cardView.setCardBackgroundColor(uv.getColor());
        risk_textview.setText(uv.getRisk());
        tips_textview.setText(uv.getTips());
    }

}
