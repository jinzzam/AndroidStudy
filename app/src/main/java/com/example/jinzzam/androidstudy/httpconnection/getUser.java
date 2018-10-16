package com.example.jinzzam.androidstudy.httpconnection;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.jinzzam.androidstudy.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class getUser extends AppCompatActivity {
    private TextView textView;
//    private String url = "http://192.168.0.3:3000/api/user/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_http_test_main);
        initView();
        new Thread(){
            public void run(){
                getUserInfo();
            }
        }.start();
    }

    private void initView() {
        textView = findViewById(R.id.tv1);
    }



    public boolean getUserInfo() {
        try {
            OkHttpClient client = new OkHttpClient();

            String url = "http://192.168.0.3:3000/api/user/";

            Request request = new Request.Builder()
                    .addHeader("Authorization", "TEST AUTH")
                    .url(url)
                    .build();
            Response response = client.newCall(request)
                    .execute();

            String result = response.body().string();
            JsonParser jsonParser = new JsonParser();
//            JsonObject jsonObject = (JsonObject) jsonParser.parse(result);

//            Gson gson = new Gson();
            Log.e("TAG", "getUserInfo: " + result.toString());
//            Log.e("TAG", "getUserInfo: " + jsonObject.toString());

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
