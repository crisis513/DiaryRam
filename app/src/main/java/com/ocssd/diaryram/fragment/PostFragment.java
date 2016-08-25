package com.ocssd.diaryram.fragment;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ocssd.diaryram.R;
import com.ocssd.diaryram.dto.Post;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PostFragment extends Fragment {

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    private List<Post> postList;
    private Post post = new Post();
    private ImageView photoImg;
    private TextView titleTxt, contentTxt;

    private final static String TAG = "PostFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        new AsyncFetch().execute();
        return rootView;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        photoImg = (ImageView) view.findViewById(R.id.photo_iv);
        titleTxt = (TextView) view.findViewById(R.id.title_txt);
        contentTxt = (TextView) view.findViewById(R.id.content_txt);
    }

    private class AsyncFetch extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(getActivity());
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL("http://diaryram.herokuapp.com/posts/" + "8" + ".json");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return e.toString();
            }
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoInput(true);

                conn.connect();
            } catch (IOException e1) {
                e1.printStackTrace();
                return e1.toString();
            }

            try {
                int response_code = conn.getResponseCode();
                Log.d(TAG, "" + response_code);
                if (response_code == HttpURLConnection.HTTP_OK) {
                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return (result.toString());
                } else {
                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }

        }

        @Override
        protected void onPostExecute(String result) {
            //this method will be running on UI thread
            Log.d(TAG, "result = " + result);
            pdLoading.dismiss();
            postList = new ArrayList<>();

            pdLoading.dismiss();
            try {
                JSONObject json_data = new JSONObject(result);
                String photoString = json_data.getString("photo");
                post.setmId(json_data.getInt("id"));
                post.setmTitle(json_data.getString("title"));
                post.setmText(json_data.getString("text"));
                post.setmPhoto("http://diaryram.herokuapp.com" + photoString.substring(17, photoString.length()-3).replace("\\", ""));
                post.setmEmoticon(json_data.getInt("emotion_id"));
            } catch (JSONException e) {
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
            }
            titleTxt.setText(post.getmTitle());
            contentTxt.setText(post.getmText());
            photoImg.setImageURI(Uri.parse(post.getmPhoto()));
        }
    }

}
