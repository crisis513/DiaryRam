package com.ocssd.diaryram.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ocssd.diaryram.R;


public class HashFragment extends Fragment {

    private static final String TAG = "HashFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_hash, container, false);
    }

}
