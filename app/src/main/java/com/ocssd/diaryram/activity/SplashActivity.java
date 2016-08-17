package com.ocssd.diaryram.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.ocssd.diaryram.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 페이스북 SDK 초기화
        FacebookSdk.sdkInitialize(getApplicationContext());
        FacebookSdk.setApplicationId(getResources().getString(R.string.facebook_app_id));

        setContentView(R.layout.activity_splash);

        // 스플래시 처리 핸들러
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 엑세스 토큰 확인 부분 (추가해야 함)
                //Boolean isValidAccessTocken = false;

                if (isLoggedIn()) {
                    // 엑세스 토큰이 유효 할 경우 메인 액티비티로 이동
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    // 엑세스 토큰이 유효하지 않을 경우 로그인 액티비티로 이동
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                // 스플래시 액티비티 종료
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    public void getHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.ocssd.diaryram", //앱의 패키지 명
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
        }
    }
}
