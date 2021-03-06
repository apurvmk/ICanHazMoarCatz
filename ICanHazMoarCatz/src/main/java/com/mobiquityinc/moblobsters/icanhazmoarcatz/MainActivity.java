package com.mobiquityinc.moblobsters.icanhazmoarcatz;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.gsm.GsmCellLocation;
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

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;
import java.util.logging.Handler;

public class MainActivity extends Activity{

    private final String TAG = "MobiDribbble || " + getClass().getSimpleName();
    private static String PAGE_INDEX = "page_index";
    private static String FULL_SCREEN = "full_screen";
    SharedPreferences myPreferences;
    SharedPreferences.Editor  myPreferencesEditor;
    RelativeLayout fragment_container;
    GridView gridView;
    ImageAdapter imgAdapter;
    boolean isShowingFullScreen=false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        myPreferencesEditor = myPreferences.edit();

        if(myPreferences.getInt(PAGE_INDEX,0)!=0){
            GlobalData.setDribblePageIndex(myPreferences.getInt(PAGE_INDEX,0));
        }

        Intent intent = new Intent(this, DribbleDownloadService.class);
        startService(intent);

        gridView = (GridView) findViewById(R.id.cat_gv_cats);
        fragment_container = (RelativeLayout)findViewById(R.id.fragment_container);

        if(savedInstanceState!=null){
            isShowingFullScreen = savedInstanceState.getBoolean(FULL_SCREEN);
        }

        final Cursor cursor = getContentResolver().query(DribbleContract.Dribble.CONTENT_URI, null, null, null, null);
        if(cursor.getCount()>1){
            showTheList(cursor);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myPreferencesEditor.putInt(PAGE_INDEX,GlobalData.getDribblePageIndex()).commit();
    }

    @Subscribe
    public void onListReady(EventListReady eventListReady){
        if(eventListReady.isListReady){
            final Cursor cursor = getContentResolver().query(DribbleContract.Dribble.CONTENT_URI, null, null, null, null);
            showTheList(cursor);
        }
    }

    public void showTheList(final Cursor cursor){

        imgAdapter = new ImageAdapter(this,cursor,false);

        gridView.setAdapter(imgAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                showFullScreenCat(cursor.getString(cursor.getColumnIndex(DribbleContract.Dribble.DRIBBLE_URL)));
            }
        });
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
