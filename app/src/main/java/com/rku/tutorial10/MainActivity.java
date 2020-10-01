package com.rku.tutorial10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    ListView lstData;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstData = findViewById(R.id.lstData);

        new MyAsyncTask().execute();
    }

    class MyAsyncTask extends AsyncTask {
        ProgressDialog dialog;
        JSONObject jsonObject;
        JSONArray jsonArray = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                InputStream is = getAssets().open("data.json");
                int size = 0;
                size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                String json = new String(buffer,"UTF-8");
                Log.i("json",json);

                jsonObject = new JSONObject(json);
                jsonArray = jsonObject.getJSONArray("sites");

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            adapter = new CustomAdapter(MainActivity.this,jsonArray);
            lstData.setAdapter(adapter);

            if(dialog.isShowing())dialog.dismiss();
        }
    }
}