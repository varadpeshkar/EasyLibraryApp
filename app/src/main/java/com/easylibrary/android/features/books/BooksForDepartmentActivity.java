package com.easylibrary.android.features.books;

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
import com.easylibrary.android.api.models.Book;
import com.easylibrary.android.api.network.managers.BooksManager;
import com.easylibrary.android.features.base.ELBaseActivity;
import com.easylibrary.android.utils.ELConstants;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by rohan on 21/3/17.
 */

public class BooksForDepartmentActivity extends ELBaseActivity {

    @Bind(R.id.rev_books)
    RecyclerView rvBooks;

    @Bind(R.id.pb_books)
    ProgressBar pbBooks;

    @Bind(R.id.btn_retry)
    Button btnRetry;

    @Bind(R.id.txt_error_books)
    TextView txtErrorBooks;

    private String departmentName;

    private OnlineBooksListAdapter mOnlineBooksListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        initRecyclerView();
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (bundle != null) {
            departmentName = bundle.getString(ELConstants.KEY_BOOK_DEPARTMENT);
            setTitle("Books for " + departmentName);
            makeAPICall();
        }
    }

    @OnClick(R.id.btn_retry)
    void onClickRetry() {
        hideError();
        showProgress();
    }

    private void makeAPICall() {
        departmentName = departmentName.trim();
        departmentName = departmentName.replace(" ", ",");
        BooksManager.getInstance().getBooksByDepartments(departmentName)
                .doOnSubscribe(this::showProgress)
                .doOnCompleted(this::hideProgress)
                .doOnTerminate(this::hideProgress)
                .subscribe(this::handleSuccess, this::handleError);
    }

    private void handleSuccess(ArrayList<Book> books) {
        if (books != null && books.size() > 0) {
            mOnlineBooksListAdapter = new OnlineBooksListAdapter(books, this);
            rvBooks.setAdapter(mOnlineBooksListAdapter);
        } else {
            showError("No Books for this department");
        }
    }

    private void handleError(Throwable throwable) {
        showError(throwable.getMessage());
    }

    private void initRecyclerView() {
        rvBooks.setLayoutManager(new LinearLayoutManager(this));
    }

    private void showProgress() {
        pbBooks.setIndeterminate(true);
        pbBooks.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        pbBooks.setVisibility(View.INVISIBLE);
    }

    private void showError(String msg) {
        txtErrorBooks.setText(msg);
        txtErrorBooks.setVisibility(View.VISIBLE);
        btnRetry.setVisibility(View.VISIBLE);
    }

    private void hideError() {
        txtErrorBooks.setVisibility(View.INVISIBLE);
        btnRetry.setVisibility(View.INVISIBLE);
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_books_for_department;
    }

    @Override
    public boolean isToolbarPresent() {
        return true;
    }
}
