package com.easylibrary.android.features.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.easylibrary.android.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rohan on 12/2/17.
 */

public abstract class ELBaseActivity extends AppCompatActivity implements ELBaseView {

    @Nullable
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(getActivity());
        configureToolbar();
    }

    protected abstract Activity getActivity();

    protected abstract
    @LayoutRes
    int getLayout();

    public abstract boolean isToolbarPresent();

    private void configureToolbar() {
        if (isToolbarPresent() && mToolbar != null)
            setSupportActionBar(mToolbar);
    }

    protected void setTitle(String title) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);
    }

    protected void start(Class clazz) {
        getActivity().startActivity(new Intent(getActivity(), clazz));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected Intent getNewIntent(Class clazz) {
        return new Intent(getActivity(), clazz);
    }

    @Override
    public void showProgress(String msg) {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(msg);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if ((mProgressDialog != null) &&
                mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = null;
    }

    @Override
    public void dismissProgress() {
        if (mProgressDialog != null &&
                mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    protected void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
