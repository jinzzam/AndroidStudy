package com.example.jinzzam.androidstudy.httpconnection;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.jinzzam.androidstudy.R;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private String strUrl = "http://192.168.43.36:3000/api/user/namolppam@pocket.mon";
    private String strMethod = "get";
    private BufferedReader in = null;

    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_http_test_main);
        initView();
        NetworkTask networkTask = new NetworkTask(strUrl, null);
        networkTask.execute();
//        httpConnect();
    }


    private void initView() {
        textView = findViewById(R.id.tv1);
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result;
            RequestHttpConnection requestHttpConnection = new RequestHttpConnection();
            result = requestHttpConnection.request(url, values);
            Log.e("TAG", "doInBackground: " + url);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            textView.setText(s);
        }


    }


    public void httpConnect() {
        try {
            URL url = new URL(strUrl);
            Log.e("TAG", "httpConnect: " + strUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            Log.e("TAG", "httpConnect: one");
            con.setRequestMethod(strMethod.toUpperCase());
//            con.setRequestProperty("Content-Type", "application/json");
            con.setConnectTimeout(10000);
            con.setReadTimeout(5000);
            in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            Log.e("TAG", "httpConnect: three");
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(in);
            System.out.println(jsonObject);
            Log.e("TAG", "httpConnect: four");
            textView.setText(jsonObject.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
