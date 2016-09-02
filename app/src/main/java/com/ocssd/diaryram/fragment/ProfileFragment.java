package com.ocssd.diaryram.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.ocssd.diaryram.CircleTransform;
import com.ocssd.diaryram.ImageAdapter;
import com.ocssd.diaryram.R;
import com.ocssd.diaryram.activity.CreatePostActivity;
import com.ocssd.diaryram.activity.PostActivity;
import com.ocssd.diaryram.activity.UserInfoActivity;
import com.ocssd.diaryram.dto.Post;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    private GridView gridView;
    private ArrayList<String> urlString = new ArrayList<>();
    private List<Post> postList;
    private Post postView;
    private TextView postCount;

    public TextView introduceTxt;

    private static final String TAG = "ProfileFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new AsyncFetch().execute();

        postCount = (TextView) view.findViewById(R.id.post_count_txt);

        Profile profile = Profile.getCurrentProfile();
        ImageView ivLogo = (ImageView) view.findViewById(R.id.cv_logo);
        Picasso.with(getActivity())
                .load(profile.getProfilePictureUri(150, 150).toString())
                .transform(new CircleTransform())
                .into(ivLogo);
        TextView userName = (TextView) view.findViewById(R.id.txt_user_name);
        userName.setText(profile.getName());

        Button infoBtn = (Button) view.findViewById(R.id.info_btn);
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                startActivityForResult(intent, 1000);
            }
        });

        gridView = (GridView) view.findViewById(R.id.grid_view);

        /**
         * On Click event for Single Gridview Item
         **/
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(getActivity(), PostActivity.class);
                intent.putExtra("id", position);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF007F")));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreatePostActivity.class);
                startActivity(intent);
            }
        });
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
                url = new URL("http://diaryram.herokuapp.com/newsfeed.json");
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
            JSONArray jArray = null;
            try {
                jArray = new JSONArray(result);

                // Extract data from json and store into ArrayList as class objects
                for(int i=0; i<jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    String photoString = json_data.getString("photo");
                    if (json_data.isNull("emotion_id")) {
                        json_data.put("emotion_id", 0);
                    }
                    if (json_data.isNull("photo")) {
                        json_data.put("photo", 0);
                    }

                    Post post = new Post();
                    post.setmId(json_data.getInt("id"));
                    post.setmTitle(json_data.getString("title"));
                    post.setmText(json_data.getString("text"));
                    post.setmEmoticon(json_data.getInt("emotion_id"));
                    post.setmPhoto("http://diaryram.herokuapp.com" + photoString.substring(8, photoString.length()-2));

                    postList.add(post);
                }
            } catch (JSONException e) {
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
            }

            for(int i=0; i<postList.size(); i++) {
                urlString.add(postList.get(i).getmPhoto().replace("\\", ""));
            }
            // Instance of ImageAdapter Class
            gridView.setAdapter(new ImageAdapter(getActivity(), urlString));
            postCount.setText("" + jArray.length());
        }
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String introduceText = data.getStringExtra("introduce");
        introduceTxt.setText(introduceText);
    }*/
}
