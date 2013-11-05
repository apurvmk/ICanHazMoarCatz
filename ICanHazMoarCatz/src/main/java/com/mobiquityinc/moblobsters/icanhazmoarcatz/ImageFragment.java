package com.mobiquityinc.moblobsters.icanhazmoarcatz;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * Created by tgatling on 11/4/13.
 */
public class ImageFragment extends Fragment {

    private final static String IMAGE_URL  = "image_url";
    ImageView catImage;

    public static ImageFragment newInstance(String url){

        ImageFragment f = new ImageFragment();
        Bundle bdl = new Bundle();
        bdl.putString(IMAGE_URL,url);
        f.setArguments(bdl);

        return f;
    }

    @Override
    public void onAttach(Activity activity) {
            super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cat_fullscreen_image, container, false);
        catImage = (ImageView) view.findViewById(R.id.cat_image_view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        com.mobiquityinc.moblobsters.icanhazmoarcatz.BitmapDownLoader download = new BitmapDownLoader(catImage);
        download.execute(getArguments().getString(IMAGE_URL));

    }
}
