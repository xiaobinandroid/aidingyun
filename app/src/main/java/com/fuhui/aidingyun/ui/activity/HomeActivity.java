package com.fuhui.aidingyun.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fuhui.aidingyun.R;

public class HomeActivity extends AppCompatActivity {
    /**
     * 退出逻辑
     */
    public static final String TAG_EXIT = "TAG_EXIT";

    public static final String TAG_LOGIN_EXCEPTION = "TAG_LOGIN_EXCEPTION";
    public static final String TAG_LOGIN_EXCEPTION_MSG = "TAG_LOGIN_EXCEPTION_MSG";
    public static final String TAG_LOGIN_EXCEPTION_ERROR_CODE = "TAG_LOGIN_EXCEPTION_ERROR_CODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
