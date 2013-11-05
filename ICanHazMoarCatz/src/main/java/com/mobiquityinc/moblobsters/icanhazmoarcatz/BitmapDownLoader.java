package com.mobiquityinc.moblobsters.icanhazmoarcatz;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by ashajgotra on 11/4/13.
 */

public class BitmapDownLoader extends AsyncTask<String, Void, Bitmap>
{
    private final WeakReference<ImageView> imageViewReference;
    private int data = 0;

    public BitmapDownLoader(ImageView imageView)
    {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageViewReference = new WeakReference<ImageView>(imageView);
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(String... params)
    {

        Bitmap bMap=null;
        try {
            URL url = new URL(params[0]);

            // STILL GETTING AN:   Caused by: java.lang.OutOfMemoryError
            bMap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return bMap;
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap)
    {
        if (imageViewReference != null && bitmap != null)
        {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null)
            {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
