package com.mobiquityinc.moblobsters.icanhazmoarcatz;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

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

    public DribbleDownloadService(){
        super("DribbleDownloadService");
    }

    private final String TAG = "TAVON";
    private JSONObject jsonObject;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        InputStream is;

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

                //Log.e("TAVON", jsonObject.toString());

                try {
                    JSONArray ja = jsonObject.getJSONArray("shots");
                    for(int i = 0; i < Integer.parseInt(jsonObject.getString("per_page")); i++){

                        JSONObject obj = ja.getJSONObject(i);
                        ContentValues imageCv = new ContentValues();

                        imageCv.put(DribbleContract.Dribble.DRIBBLE_TITLE, obj.getString("title"));
                        imageCv.put(DribbleContract.Dribble.DRIBBLE_URL, obj.getString("image_url"));
                        imageCv.put(DribbleContract.Dribble.DRIBBLE_TEASER, obj.getString("image_teaser_url"));
                        Uri uri = getContentResolver().insert(DribbleContract.Dribble.CONTENT_URI, imageCv);
                        Cursor cursor= getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToFirst();
                        while(!cursor.isAfterLast()){
                            Log.i("PRA  Query Returned",cursor.getString(cursor.getColumnIndex(DribbleContract.Dribble._ID)));
                            cursor.moveToNext();
                        }
                        cursor.close();
                        /*
                        Log.e(TAG, "==========");
                        Log.e(TAG, "Id: " + obj.getString("id"));
                        Log.e(TAG, "Title: " + obj.getString("title"));
                        Log.e(TAG, "Url: " + obj.getString("image_url"));
                        Log.e(TAG, "Tease Url: " + obj.getString("image_teaser_url") + "\n\n");
                        */

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }


        }

    }
}
