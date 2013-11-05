package com.mobiquityinc.moblobsters.icanhazmoarcatz;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends Activity implements AbsListView.OnScrollListener, com.mobiquityinc.moblobsters.icanhazmoarcatz.FragmentGetImages.OnListCreatedListener {

    private static String FULL_SCREEN = "full_screen";
    List<com.mobiquityinc.moblobsters.icanhazmoarcatz.Image> allImages;
    RelativeLayout fragment_container;
    TextView loadingText;
    GridView gridView;
    int scrollState;
    ImageAdapter imgAdapter;
    com.mobiquityinc.moblobsters.icanhazmoarcatz.ImageFragment imageFragment;
    boolean isShowingFullScreen=false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.cat_gv_cats);
        fragment_container = (RelativeLayout)findViewById(R.id.fragment_container);
        loadingText = (TextView)findViewById(R.id.loadingText);

        if(savedInstanceState!=null){
            isShowingFullScreen = savedInstanceState.getBoolean(FULL_SCREEN);
        }

        if(!isShowingFullScreen){
            loadMoreCats();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FULL_SCREEN,isShowingFullScreen);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(isShowingFullScreen){
            fragment_container.setVisibility(View.GONE);
            if(allImages!=null){
                if(allImages.isEmpty()){
                    loadMoreCats();
                }else{
                    isShowingFullScreen=false;
                    gridView.setVisibility(View.VISIBLE);
                }
            }else {
                loadMoreCats();
            }
        }
    }

    public void loadMoreCats(){

        gridView.setVisibility(View.GONE);
        loadingText.setText("loading more cats...");
        fragment_container.setVisibility(View.VISIBLE);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        FragmentGetImages fragmentGetImages = FragmentGetImages.newInstance(10);
        ft.add(fragmentGetImages,"");
        ft.commit();
    }

    public void onListCreated(List<Image> list){

        allImages = list;
        imgAdapter = new ImageAdapter(allImages);
        gridView.setAdapter(imgAdapter);
        gridView.setOnScrollListener(this);

        fragment_container.setVisibility(View.GONE);
        gridView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        this.scrollState = scrollState;
        if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
            imgAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i2, int i3) {
    }

    public void showFullScreenCat(Image catImage){

        isShowingFullScreen=true;
        loadingText.setText("loading full screen");
        fragment_container.setVisibility(View.VISIBLE);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, new ImageFragment().newInstance(catImage.getUrl()));
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

    public class ImageAdapter extends BaseAdapter {

        List<Image> imageList;
        ImageFragment adImageFragment;

        public class ImageHolder{
            ImageView catIV;
        }

        public ImageAdapter(List<Image> imageList){
            this.imageList = imageList;
            this.adImageFragment = imageFragment;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ImageHolder imageHolder;

            if(convertView == null){

                imageHolder = new ImageHolder();
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.thumbnail_img_layout, parent, false);
                imageHolder.catIV = (ImageView) convertView.findViewById(R.id.thumbnail_image);
                convertView.setTag(imageHolder);

            }
            else {
                imageHolder = (ImageHolder) convertView.getTag();
            }

            if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE)
            {
                BitmapDownLoader download = new BitmapDownLoader(imageHolder.catIV);
                download.execute(allImages.get(position).getUrl());

                imageHolder.catIV.setTag(position);
                imageHolder.catIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int position = Integer.valueOf((Integer) v.getTag());
                        Image image = allImages.get(position);
                        MainActivity.this.showFullScreenCat(image);

                    }
                });
            }
            return convertView;
        }

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public Object getItem(int position) {
            return imageList.get(position);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }
    }
}
