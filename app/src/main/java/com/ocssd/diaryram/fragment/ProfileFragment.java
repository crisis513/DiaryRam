package com.ocssd.diaryram.fragment;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Profile;
import com.ocssd.diaryram.CircleTransform;
import com.ocssd.diaryram.ImageAdapter;
import com.ocssd.diaryram.R;
import com.ocssd.diaryram.activity.CreatePostActivity;
import com.ocssd.diaryram.activity.PostActivity;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Profile profile = Profile.getCurrentProfile();
        ImageView iv_logo = (ImageView) view.findViewById(R.id.cv_logo);
        Picasso.with(getActivity())
                .load(profile.getProfilePictureUri(150, 150).toString())
                .transform(new CircleTransform())
                .into(iv_logo);
        TextView userName = (TextView) view.findViewById(R.id.txt_user_name);
        userName.setText(profile.getName());

        GridView gridView = (GridView) view.findViewById(R.id.grid_view);

        // Instance of ImageAdapter Class
        gridView.setAdapter(new ImageAdapter(getActivity()));

        /**
         * On Click event for Single Gridview Item
         **/
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent i = new Intent(getActivity(), PostActivity.class);

                i.putExtra("id", position);
                startActivity(i);
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
}
