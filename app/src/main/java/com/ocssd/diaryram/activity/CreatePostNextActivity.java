package com.ocssd.diaryram.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ocssd.diaryram.R;

import java.util.ArrayList;


public class CreatePostNextActivity extends AppCompatActivity{

    ArrayList<String> hashList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createpostnext);
        initToolbar();

    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mToolBarTextView.setText("CreatePost");
    }

    public void addButton(View view) {
        EditText inputHash = (EditText) findViewById(R.id.input_hash);
        String hash = inputHash.getText().toString();

        if(!hash.equals("")) {
            hashList.add(hash);

            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.hashTextLayout);
            TextView textView = new TextView(this);
            for( int i = 0; i < hashList.size(); i++ ) {
                textView.setText("#" + hashList.get(i) + " ");
            }
            linearLayout.addView(textView);
            inputHash.setText("");
        }
    }

    public void prevButtonClick(View view) {
        finish();
    }

    public void nextButtonClick(View view) {
        Toast.makeText(this, "포스트 만들어야됨!", Toast.LENGTH_SHORT).show();
    }
}