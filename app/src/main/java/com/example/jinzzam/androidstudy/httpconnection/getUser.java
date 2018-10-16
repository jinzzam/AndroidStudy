package com.example.jinzzam.androidstudy.httpconnection;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.jinzzam.androidstudy.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class getUser extends AppCompatActivity {
    private TextView textView;
    private String userEmail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_http_test_main);
        initView();
        new Thread() {
            public void run() {
                getUserInfo();
            }
        }.start();
        textView.setText(userEmail);

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
            JsonArray jsonArray = (JsonArray) jsonParser.parse(result);

            userEmail = jsonArray.get(0).getAsJsonObject().get("email").toString();
            Log.e("TAG", "getUserInfo: " + jsonArray.get(0).getAsJsonObject().get("username"));
            Log.e("TAG", "getUserInfo: " + result.toString());

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
