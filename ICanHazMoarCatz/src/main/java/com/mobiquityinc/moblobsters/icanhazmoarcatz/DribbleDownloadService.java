package com.mobiquityinc.moblobsters.icanhazmoarcatz;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tgatling on 11/5/13.
 */
public class DribbleDownloadService extends IntentService{

    private DribbleDownloadService(){
        super("DribbleDownloadService");
    }

    private JSONObject jsonObject;

    @Override
    protected void onHandleIntent(Intent intent) {

        int len = 500;
        InputStream is = null;

        try {
            URL url = new URL("http://api.dribbble.com/shots/everyone");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            //Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            BufferedReader bufferedReader = null;
            StringBuilder sb = new StringBuilder();
            String line;

            bufferedReader = new BufferedReader(new InputStreamReader(is));
            while ((line = bufferedReader.readLine()) !=null){
                sb.append(line);
            }

            //jsonObject = (JSONObject) new SimpleXmlPullApp().main(sb.toString());
            jsonObject = new JSONObject(sb.toString());


            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {

            if(jsonObject != null){

                ContentValues cv = new ContentValues();
                try {
                    cv.put("page", jsonObject.getString("page"));
                    cv.put("pages", jsonObject.getString("pages"));
                    cv.put("per_page", jsonObject.getString("per_page"));
                    cv.put("total", jsonObject.getString("total"));

                    JSONArray ja = jsonObject.getJSONArray("shots");
                    cv.put("image_url", ja.getString(5));
                    cv.put("image_url_teaser", ja.getString(6));


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        }

    }
}
