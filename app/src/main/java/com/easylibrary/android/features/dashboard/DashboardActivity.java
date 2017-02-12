package com.easylibrary.android.features.dashboard;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.easylibrary.android.R;
import com.easylibrary.android.features.base.ELBaseActivity;

/**
 * Created by rohan on 12/2/17.
 */

public class DashboardActivity extends ELBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("EasyLibrary");
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_dashboard;
    }

    @Override
    public boolean isToolbarPresent() {
        return true;
    }
}
