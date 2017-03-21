package com.easylibrary.android.features.authentication;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.easylibrary.android.R;
import com.easylibrary.android.api.network.ELRetrofit;
import com.easylibrary.android.features.base.ELBaseActivity;
import com.easylibrary.android.features.dashboard.DashboardActivity;
import com.easylibrary.android.utils.ELConstants;
import com.easylibrary.android.utils.ELPreferences;

import java.io.IOException;
import java.net.InetAddress;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by rohan on 12/2/17.
 */

public class EnterURLActivity extends ELBaseActivity {

    @Bind(R.id.edt_enter_url)
    EditText edtEnterUrl;

    @Bind(R.id.pb_connect)
    ProgressBar pbConnect;

    @Bind(R.id.rel_enter_url)
    RelativeLayout relEnterUrl;

    private String baseWithHttp;

    private String baseWithoutHttp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseWithHttp = ELPreferences.get(this).getString(ELPreferences.Keys.SERVER_BASE_URL);
        if (!TextUtils.isEmpty(baseWithHttp)) {
            new TestConnectivity().execute(baseWithHttp.substring(7, baseWithHttp.length() - 1));
        }
    }


    @OnClick(R.id.btn_submit)
    void onClickSubmit() {
        baseWithoutHttp = edtEnterUrl.getText().toString();
        if (baseWithoutHttp.length() <= 7) {
            showToast("Enter valid URL");
        } else {
            new TestConnectivity().execute(baseWithoutHttp);
            baseWithHttp = "http://" + baseWithoutHttp + "/";
        }
    }

    private void showProgress() {
        relEnterUrl.setVisibility(View.INVISIBLE);
        pbConnect.setIndeterminate(true);
        pbConnect.setVisibility(View.VISIBLE);
    }

    private void handleErrorCase() {
        relEnterUrl.setVisibility(View.VISIBLE);
        pbConnect.setIndeterminate(true);
        pbConnect.setVisibility(View.GONE);
    }


    private class TestConnectivity extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                return InetAddress.getByName(params[0]).isReachable(0);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isAvailable) {
            handleConnection(isAvailable);
            Log.e(ELConstants.LOG_TAG, "isAvailable : " + isAvailable);
        }
    }

    private void handleConnection(boolean isAvailable) {
        if (isAvailable) {
            ELPreferences.get(this).saveString(ELPreferences.Keys.SERVER_BASE_URL, baseWithHttp);
            boolean isLoggedIn = ELPreferences.get(this)
                    .getBoolean(ELPreferences.Keys.IS_LOGGED_IN);
            ELRetrofit.setUp(getApplicationContext(), baseWithHttp);
            if (isLoggedIn) {
                start(DashboardActivity.class);
                finish();
            } else {
                start(LoginActivity.class);
                finish();
            }
        } else {
            showToast("Cannot connect to server, enter again");
            handleErrorCase();
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
