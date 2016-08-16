package com.ocssd.diaryram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Profile;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        createUI();
    }

    private void createUI() {
        Profile profile = Profile.getCurrentProfile();

        ImageView iv_logo = (ImageView) findViewById(R.id.cv_logo);
        Picasso.with(this)
                .load(profile.getProfilePictureUri(150, 150).toString())
                .transform(new CircleTransform())
                .into(iv_logo);
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);  // disable the button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false); // remove the left caret
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mToolBarTextView.setText("DiaryRam");
    }

}
