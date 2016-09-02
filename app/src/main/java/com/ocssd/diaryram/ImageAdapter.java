package com.ocssd.diaryram;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;

    // Keep all Images in array
    public ArrayList<String> mThumbIds;

    private static final String TAG = "ImageAdapter";

    // Constructor
    public ImageAdapter(Context context, ArrayList<String> thumblds){
        mContext = context;
        mThumbIds = thumblds;
    }

    @Override
    public int getCount() {
        return mThumbIds.size();
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);

        ImageDownloadTask task = new ImageDownloadTask(imageView, position);
        task.execute();

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(230,230));

        return imageView;
    }


    class ImageDownloadTask extends AsyncTask<Void, Integer, Bitmap> {
        private ImageView mView;
        private int mPosition;
        ImageDownloadTask(ImageView view, int position) {
            mView = view;
            mPosition = position;
        }
        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(mThumbIds.get(mPosition)).getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            mView.setImageBitmap(result);
        }

    }

}