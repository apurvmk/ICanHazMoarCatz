package com.mobiquityinc.moblobsters.icanhazmoarcatz;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by tgatling on 11/5/13.
 */
public class ImageAdapter extends CursorAdapter {

    private final String TAG = "MobiDribbble || " + getClass().getCanonicalName();
    private Context context;
    private Cursor cursor;

    public ImageAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        this.context = context;
        this.cursor = c;
    }

    public class ImageHolder{
        ImageView catIV;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ImageHolder imageHolder;

        if(convertView == null){

            imageHolder = new ImageHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.thumbnail_img_layout, parent, false);
            imageHolder.catIV = (ImageView) convertView.findViewById(R.id.thumbnail_image);
            convertView.setTag(imageHolder);

        }
        else {
            imageHolder = (ImageHolder) convertView.getTag();
        }

        //Picasso.with(context).load("http://cdn.meme.li/i/pg2lr.jpg").into(imageHolder.catIV);

        //Log.d(TAG, DribbleContract.Dribble.DRIBBLE_URL + " | " + cursor.getColumnIndex(DribbleContract.Dribble.DRIBBLE_URL));


        if(cursor.moveToPosition(position)){
            Picasso.with(context).load(cursor.getString(cursor.getColumnIndex(DribbleContract.Dribble.DRIBBLE_URL))).into(imageHolder.catIV);
            //Picasso.with(context).load("http://cdn.meme.li/i/pg2lr.jpg").into(imageHolder.catIV);
        }


        return convertView;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }
}
