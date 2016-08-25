package com.ocssd.diaryram.dto;

public class Post {
    int mId;          // id
    String mTitle;    // title
    String mHash;     // hash
    String mText;     // content
    int mEmoticon;   // emoticon
    String mPhoto;    // image

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

    public String getmHash() {
        return mHash;
    }

    public void setmHash(String mHash) {
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

    public String getmPhoto() {
        return mPhoto;
    }

    public void setmPhoto(String mPhoto) {
        this.mPhoto = mPhoto;
    }

}
