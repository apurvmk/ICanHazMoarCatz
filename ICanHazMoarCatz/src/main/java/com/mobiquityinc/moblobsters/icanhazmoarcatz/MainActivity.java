package com.mobiquityinc.moblobsters.icanhazmoarcatz;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity{

    private final String TAG = "MobiDribbble || " + getClass().getSimpleName();
    private static String FULL_SCREEN = "full_screen";
    RelativeLayout fragment_container;
    GridView gridView;
    ImageAdapter imgAdapter;
    boolean isShowingFullScreen=false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, DribbleDownloadService.class);
        startService(intent);

        gridView = (GridView) findViewById(R.id.cat_gv_cats);
        fragment_container = (RelativeLayout)findViewById(R.id.fragment_container);

        if(savedInstanceState!=null){
            isShowingFullScreen = savedInstanceState.getBoolean(FULL_SCREEN);
        }

        Cursor cursor = getContentResolver().query(DribbleContract.Dribble.CONTENT_URI, null, null, null, null);


        /*
        // Tests to see all the column names
        String colNames = "";
        for(String name : cursor.getColumnNames())
            colNames = colNames + " | " + name;
        Log.d(TAG, colNames);
        */
        if(cursor.moveToFirst()){
        imgAdapter = new ImageAdapter(
                this,
                cursor,
                false);
        }
        else
            Toast.makeText(this, "Sorry, cursor was null", Toast.LENGTH_SHORT).show();

        gridView.setAdapter(imgAdapter);
        /*
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showFullScreenCat("http://cdn.meme.li/i/pg2lr.jpg");
            }
        });
        */
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FULL_SCREEN,isShowingFullScreen);
    }

    public void showFullScreenCat(String imageUrl){

        isShowingFullScreen=true;
        fragment_container.setVisibility(View.VISIBLE);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, new ImageFragment().newInstance(imageUrl));
        ft.addToBackStack(null);
        ft.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
