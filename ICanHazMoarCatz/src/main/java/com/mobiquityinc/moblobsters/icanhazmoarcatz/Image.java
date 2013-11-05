package com.mobiquityinc.moblobsters.icanhazmoarcatz;

import android.graphics.Bitmap;

/**
 * Created by ashajgotra on 11/4/13.
 */
public class Image
{

    String url;
    String imageId;
    String srcUrl;

    public Image(String url, String imageId, String srcUrl) {
        this.url = url;
        this.imageId = imageId;
        this.srcUrl = srcUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getSrcUrl() {
        return srcUrl;
    }

    public void setSrcUrl(String srcUrl) {
        this.srcUrl = srcUrl;
    }
}
