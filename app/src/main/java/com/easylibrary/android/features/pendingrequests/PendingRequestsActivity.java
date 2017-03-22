package com.easylibrary.android.features.pendingrequests;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.easylibrary.android.R;
import com.easylibrary.android.api.models.BookIssueRequest;
import com.easylibrary.android.api.network.managers.StudentManager;
import com.easylibrary.android.db.BookIssueRequestStorage;
import com.easylibrary.android.features.base.ELBaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by rohan on 22/3/17.
 */

public class PendingRequestsActivity extends ELBaseActivity {

    @Bind(R.id.rev_requests)
    RecyclerView rvRequests;

    @Bind(R.id.pb_requests)
    ProgressBar pbRequests;

    @Bind(R.id.btn_retry)
    Button btnRetry;

    @Bind(R.id.txt_error_requests)
    TextView txtErrorRequests;

    @Bind(R.id.pb_toolbar)
    ProgressBar pbToolbar;

    private PendingRequestAdapter mPendingRequestAdapter;

    ArrayList<BookIssueRequest> bookIssueRequests;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRecyclerView();
        setTitle("Pending Requests");
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadData();

    }


    private void loadData() {
        bookIssueRequests.clear();
        bookIssueRequests.addAll(Realm.getDefaultInstance().copyFromRealm(BookIssueRequestStorage.getAllPending()));
        if (bookIssueRequests.size() > 0) {
            mPendingRequestAdapter.notifyDataSetChanged();
        } else {
            showError("No Pending request yet");
        }
    }

    @OnClick(R.id.imv_action_refresh)
    void onClickRefresh() {
        showProgressTop();
        makeAPICall();
    }


    @OnClick(R.id.btn_retry)
    void onClickRetry() {

    }

    private void makeAPICall() {

        StudentManager.getInstance().getAllIssueRequests()
                .subscribe(this::handleSuccess, this::handleError);
    }

    private void handleSuccess(ArrayList<BookIssueRequest> bookIssueRequests) {
        hideProgressTop();
        BookIssueRequestStorage.save(bookIssueRequests);
        loadData();
    }

    private void handleError(Throwable throwable) {
        hideProgressTop();
        loadData();
    }

    private void initRecyclerView() {
        bookIssueRequests = new ArrayList<>();
        rvRequests.setLayoutManager(new LinearLayoutManager(this));
        mPendingRequestAdapter = new PendingRequestAdapter(bookIssueRequests, this);
        rvRequests.setAdapter(mPendingRequestAdapter);
    }

    private void showProgress() {
        pbRequests.setIndeterminate(true);
        pbRequests.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        pbRequests.setVisibility(View.INVISIBLE);
    }

    private void showProgressTop() {
        pbToolbar.setIndeterminate(true);
        pbToolbar.setVisibility(View.VISIBLE);
    }

    private void hideProgressTop() {
        pbToolbar.setVisibility(View.INVISIBLE);
    }

    private void showError(String msg) {
        txtErrorRequests.setText(msg);
        txtErrorRequests.setVisibility(View.VISIBLE);
        //btnRetry.setVisibility(View.VISIBLE);
    }

    private void hideError() {
        txtErrorRequests.setVisibility(View.INVISIBLE);
        btnRetry.setVisibility(View.INVISIBLE);
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_pending_requests;
    }

    @Override
    public boolean isToolbarPresent() {
        return true;
    }
}
