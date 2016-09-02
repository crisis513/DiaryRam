package com.ocssd.diaryram.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ocssd.diaryram.R;
import com.ocssd.diaryram.dto.UploadPost;

import java.util.ArrayList;


public class CreatePostNextActivity extends AppCompatActivity{

    private ArrayList<String> hashList = new ArrayList<String>();
    private UploadPost post = new UploadPost();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createpostnext);
        initToolbar();

        Intent intent = getIntent();
        post.setmPhoto((Bitmap) intent.getExtras().get("post_photo"));
        post.setmEmoticon(intent.getExtras().getInt("post_emotion"));
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
            for(int i = 0; i < hashList.size(); i++ ) {
                textView.setText("#" + hashList.get(i) + " ");
            }
            linearLayout.addView(textView);
            inputHash.setText("");
        }
    }

    public void prevClick(View view) {
        finish();
    }

    public void nextClick(View view) {
        EditText titleEdit = (EditText) findViewById(R.id.title_txt);
        EditText contentEdit = (EditText) findViewById(R.id.content_txt);

        post.setmTitle(titleEdit.getText().toString());
        post.setmText(contentEdit.getText().toString());
        post.setmHash(hashList);

        Intent intent = new Intent(this, PostActivity.class);
        intent.putExtra("post_title", post.getmTitle());
        intent.putExtra("post_text", post.getmText());
        intent.putExtra("post_emotion", post.getmEmoticon());
        intent.putExtra("post_photo", post.getmPhoto());
        intent.putStringArrayListExtra("post_hash", post.getmHash());
        intent.putExtra("activity", "CreatePostNextActivity");
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}