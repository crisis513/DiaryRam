package com.ocssd.diaryram.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.Profile;
import com.ocssd.diaryram.R;
import com.ocssd.diaryram.fragment.ProfileFragment;


public class UserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        initToolbar();
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
        mToolBarTextView.setText("Settings");
    }

    public void introduceButton(View view) {
        /*EditText userIntroduceTxt = (EditText) findViewById(R.id.user_introduce_txt);

        //Starting the previous Intent
        Intent previousScreen = new Intent(this, MainActivity.class);

        previousScreen.putExtra("introduce", userIntroduceTxt.getText());
        setResult(1000, previousScreen);*/
        finish();
    }

}
