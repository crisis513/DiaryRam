package com.ocssd.diaryram.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.ocssd.diaryram.fragment.LoginFragment;
import com.ocssd.diaryram.R;

import java.util.Arrays;


public class LoginActivity extends FragmentActivity {

    private CallbackManager callbackManager;
    private Profile mProfile;

    private final static String TAG = "LoginActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        isLoginFacebook();

        if (savedInstanceState == null) {
            LoginFragment lf = new LoginFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.mainContainer, lf).commit();
        }
    }

    private void isLoginFacebook() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "페이스북 토큰 -> " + loginResult.getAccessToken().getToken());
                Log.d(TAG, "페이스북 UserID -> " + loginResult.getAccessToken().getUserId());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "로그인 취소됨");
            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

}
