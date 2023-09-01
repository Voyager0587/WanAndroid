package com.example.wanandroid.bean;

import android.net.Uri;

import org.litepal.crud.LitePalSupport;

/**
 * @version 1.0
 * @className: ImageUriBean
 * @author: Voyager
 * @description: 头像Uri
 * @date: 2023/9/1 20:44
 **/
public class ImageUriBean extends LitePalSupport {
    String imageUri;

    public String getImageUri() {
        return imageUri;
    }

    public ImageUriBean(String imageUri) {
        this.imageUri = imageUri;
    }

    public ImageUriBean() {
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
