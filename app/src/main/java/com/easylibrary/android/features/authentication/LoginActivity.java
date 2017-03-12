package com.easylibrary.android.features.authentication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.easylibrary.android.R;
import com.easylibrary.android.features.base.ELBaseActivity;
import com.easylibrary.android.features.dashboard.DashboardActivity;

import butterknife.OnClick;

/**
 * Created by varad on 12/2/17.
 */

public class LoginActivity extends ELBaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.btn_login)
    void onClickLogin() {
        start(DashboardActivity.class);
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public boolean isToolbarPresent() {
        return false;
    }
}
