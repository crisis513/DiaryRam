package com.ocssd.diaryram.dto;

import android.graphics.Bitmap;

import java.util.ArrayList;


public class UploadPost {
    int mId;                     // id
    String mTitle;               // title
    ArrayList<String> mHash;     // hash
    String mText;                // content
    int mEmoticon;              // emoticon
    Bitmap mPhoto;               // image

    public UploadPost() {
        this.mId = 0;
        this.mTitle = "";
        this.mHash = null;
        this.mText = "";
        this.mEmoticon = 0;
        this.mPhoto = null;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public ArrayList<String> getmHash() {
        return mHash;
    }

    public void setmHash(ArrayList<String> mHash) {
        this.mHash = mHash;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public int getmEmoticon() {
        return mEmoticon;
    }

    public void setmEmoticon(int mEmoticon) {
        this.mEmoticon = mEmoticon;
    }

    public Bitmap getmPhoto() {
        return mPhoto;
    }

    public void setmPhoto(Bitmap mPhoto) {
        this.mPhoto = mPhoto;
    }

}
