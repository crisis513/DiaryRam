package com.ocssd.diaryram.dto;

import android.graphics.Bitmap;


public class Posts {
    String mTitle;      // title
    String mText;       // content
    String mEmoticon;   // emoticon
    Bitmap mImage;      // image

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public String getmEmoticon() {
        return mEmoticon;
    }

    public void setmEmoticon(String mEmoticon) {
        this.mEmoticon = mEmoticon;
    }

    public Bitmap getmImage() {
        return mImage;
    }

    public void setmImage(Bitmap mImage) {
        this.mImage = mImage;
    }
}
