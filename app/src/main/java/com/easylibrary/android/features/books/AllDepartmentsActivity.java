package com.easylibrary.android.features.books;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.easylibrary.android.R;
import com.easylibrary.android.api.models.Department;
import com.easylibrary.android.api.network.managers.BooksManager;
import com.easylibrary.android.features.base.ELBaseActivity;
import com.easylibrary.android.utils.ELConstants;
import com.easylibrary.android.utils.RecyclerItemClickListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by rohan on 14/2/17.
 */

public class AllDepartmentsActivity extends ELBaseActivity implements RecyclerItemClickListener.OnItemClickListener {

    @Bind(R.id.rev_departments)
    RecyclerView rvDepartments;

    @Bind(R.id.pb_departments)
    ProgressBar pbDepartments;

    @Bind(R.id.btn_retry)
    Button btnRetry;

    @Bind(R.id.txt_error_departments)
    TextView txtErrorDepartments;

    private DepartmentsListAdapter mDepartmentsListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Departments");
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initRecyclerView();
        makeAPICall();
    }

    private void initRecyclerView() {
        rvDepartments.setLayoutManager(new LinearLayoutManager(this));
        rvDepartments.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rvDepartments.addOnItemTouchListener(new RecyclerItemClickListener(this, rvDepartments, this));
    }

    @OnClick(R.id.btn_retry)
    void onClickRetry() {
        hideError();
        makeAPICall();
    }

    private void makeAPICall() {
        BooksManager.getInstance().getAllDepartments()
                .doOnSubscribe(this::showProgress)
                .doOnCompleted(this::hideProgress)
                .doOnTerminate(this::hideProgress)
                .subscribe(this::handleSuccess, this::handleError);
    }

    private void handleError(Throwable throwable) {
        showError(throwable.getMessage());
    }

    private void handleSuccess(ArrayList<Department> departments) {
        if (departments.size() > 0) {
            mDepartmentsListAdapter = new DepartmentsListAdapter(departments, this);
            rvDepartments.setAdapter(mDepartmentsListAdapter);
            mDepartmentsListAdapter.notifyDataSetChanged();
        } else {
            showError("No departments available");
        }
    }

    private void showProgress() {
        pbDepartments.setIndeterminate(true);
        pbDepartments.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        pbDepartments.setVisibility(View.INVISIBLE);
    }

    private void showError(String msg) {
        txtErrorDepartments.setText(msg);
        txtErrorDepartments.setVisibility(View.VISIBLE);
        btnRetry.setVisibility(View.VISIBLE);
    }

    private void hideError() {
        txtErrorDepartments.setVisibility(View.INVISIBLE);
        btnRetry.setVisibility(View.INVISIBLE);
    }


    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_all_departments;
    }

    @Override
    public boolean isToolbarPresent() {
        return true;
    }

    @Override
    public void OnItemClick(View view, int position) {
        if (position < 0)
            return;

        Intent intent = getNewIntent(BooksForDepartmentActivity.class);
        intent.putExtra(ELConstants.KEY_BOOK_DEPARTMENT,
                mDepartmentsListAdapter.getItem(position).getDepartment());
        startActivity(intent);
    }

    @Override
    public void OnItemLongClick(View view, int position) {

    }
}
