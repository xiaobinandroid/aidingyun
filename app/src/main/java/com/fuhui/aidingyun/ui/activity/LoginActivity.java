package com.fuhui.aidingyun.ui.activity;

import android.os.Bundle;

import com.fuhui.aidingyun.R;
import com.fuhui.aidingyun.presenter.LoginPresenter;


public class LoginActivity extends BaseActivity<LoginPresenter> {



    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginPresenter newP() {
        return null;
    }
}
