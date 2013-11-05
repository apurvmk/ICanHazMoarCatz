package com.mobiquityinc.moblobsters.icanhazmoarcatz;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashajgotra on 11/4/13.
 */
public class FragmentGetImages extends Fragment {

    private final static String IMAGE_COUNT  = "imageCount";
    private final static String DEBUG_TAG = "debug_tag";
    public static OnListCreatedListener myListCreatedListener;

    public static FragmentGetImages newInstance(int imageCount){

        FragmentGetImages f = new FragmentGetImages();
        Bundle bdl = new Bundle();
        bdl.putInt(IMAGE_COUNT,imageCount);
        f.setArguments(bdl);

        return f;
    }

    public interface OnListCreatedListener {
        public void onListCreated(List<Image> list);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myListCreatedListener = (OnListCreatedListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConnectivityManager connMgr = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.i("PRA","executing GetMoreCats");
            GetMoreCats getMoreCats = new GetMoreCats();
            getMoreCats.execute();
        } else {
            // display error
        }
        setRetainInstance(true);
    }

    private static class GetMoreCats extends AsyncTask<Void,Void,List<Image>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Image> doInBackground(Void... voids)
        {

            List<Image> imageList = new ArrayList<Image>();

            // do the network call
            int len = 500;
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.

            String myurl = "http://thecatapi.com/api/images/get?format=xml&results_per_page=20";

            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                Log.d(DEBUG_TAG, "The response is: " + response);
                is = conn.getInputStream();

                BufferedReader bufferedReader = null;
                StringBuilder sb = new StringBuilder();
                String line;

                bufferedReader = new BufferedReader(new InputStreamReader(is));
                while ((line = bufferedReader.readLine()) !=null){
                    sb.append(line);
                }

                imageList = (List<Image>) new SimpleXmlPullApp().main(sb.toString());

                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return imageList;
        }

        @Override
        protected void onPostExecute(List<Image> list)
        {
            super.onPostExecute(list);
            myListCreatedListener.onListCreated(list);
        }
    }
}
