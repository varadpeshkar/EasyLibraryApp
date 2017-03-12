package com.easylibrary.android.features.authentication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;

import com.easylibrary.android.R;
import com.easylibrary.android.api.network.ELRetrofit;
import com.easylibrary.android.features.base.ELBaseActivity;
import com.easylibrary.android.features.dashboard.DashboardActivity;
import com.easylibrary.android.utils.ELPreferences;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by varad on 12/2/17.
 */

public class EnterURLActivity extends ELBaseActivity {

    @Bind(R.id.edt_enter_url)
    EditText edtEnterUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String baseUrl = ELPreferences.get(this).getString(ELPreferences.Keys.SERVER_BASE_URL);
        boolean isLoggedIn = ELPreferences.get(this).getBoolean(ELPreferences.Keys.IS_LOGGED_IN);
        if (!TextUtils.isEmpty(baseUrl)) {
            ELRetrofit.setUp(getApplicationContext(), baseUrl);
            if (isLoggedIn) {
                start(DashboardActivity.class);
                finish();
            } else {
                start(LoginActivity.class);
                finish();
            }
        }
    }


    @OnClick(R.id.btn_submit)
    void onClickSubmit() {
        String baseUrl = edtEnterUrl.getText().toString();
        if (baseUrl.length() <= 7) {
            showToast("Enter valid URL");
        } else {
            baseUrl = "http://" + baseUrl + "/";
            ELPreferences.get(this).saveString(ELPreferences.Keys.SERVER_BASE_URL, baseUrl);
            boolean isLoggedIn = ELPreferences.get(this)
                    .getBoolean(ELPreferences.Keys.IS_LOGGED_IN);
            ELRetrofit.setUp(getApplicationContext(), baseUrl);
            if (isLoggedIn) {
                start(DashboardActivity.class);
                finish();
            } else {
                start(LoginActivity.class);
                finish();
            }
        }
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
