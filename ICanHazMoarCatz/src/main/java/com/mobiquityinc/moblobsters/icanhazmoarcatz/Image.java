package com.mobiquityinc.moblobsters.icanhazmoarcatz;

import android.graphics.Bitmap;

/**
 * Created by ashajgotra on 11/4/13.
 */
public class Image
{

    String imageUrl;
    String imageId;
    String imageTeasureUrl;

    public Image(String url, String imageId, String srcUrl) {
        this.imageUrl = url;
        this.imageId = imageId;
        this.imageTeasureUrl = srcUrl;
    }

    public String getUrl() {
        return imageUrl;
    }

    public void setUrl(String url) {
        this.imageUrl = url;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getSrcUrl() {
        return imageTeasureUrl;
    }

    public void setSrcUrl(String srcUrl) {
        this.imageTeasureUrl = srcUrl;
    }
}
