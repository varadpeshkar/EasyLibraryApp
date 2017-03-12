package com.easylibrary.android.features.authentication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.easylibrary.android.R;
import com.easylibrary.android.features.base.ELBaseActivity;

import butterknife.OnClick;

/**
 * Created by varad on 12/2/17.
 */

public class EnterURLActivity extends ELBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @OnClick(R.id.btn_submit)
    void onClickLogin() {
        start(LoginActivity.class);
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_enter_url;
    }

    @Override
    public boolean isToolbarPresent() {
        return false;
    }


}
