package com.mobiquityinc.moblobsters.icanhazmoarcatz;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by ashajgotra on 11/4/13.
 */
public class ListViewAdapter extends BaseAdapter
{
    private Activity mContext;
    private List<Image> catImages;

    public ListViewAdapter(Activity activity, List<Image> images)
    {
        this.mContext = activity;
        this.catImages = images;
    }

    @Override
    public int getCount() {
        return catImages.size();
    }

    @Override
    public Object getItem(int i)
    {
        return catImages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
