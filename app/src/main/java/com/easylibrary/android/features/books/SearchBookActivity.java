package com.easylibrary.android.features.books;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.easylibrary.android.R;
import com.easylibrary.android.api.models.Book;
import com.easylibrary.android.api.network.managers.BooksManager;
import com.easylibrary.android.features.base.ELBaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by varad on 14/2/17.
 */

public class SearchBookActivity extends ELBaseActivity {

    @Bind(R.id.rev_books)
    RecyclerView rvBooks;

    @Bind(R.id.edt_search_query)
    EditText edtSearchQuery;

    @Bind(R.id.txt_error_books)
    TextView txtErrorBooks;

    private OnlineBooksListAdapter mOnlineBooksListAdapter;

    private ArrayList<Book> mBooks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Search Book");
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initRecyclerView();
    }

    @OnClick(R.id.imv_search_icon)
    void onClickSearch() {
        hideError();
        String query = edtSearchQuery.getText().toString();
        query = query.trim();
        if (TextUtils.isEmpty(query)) {
            showToast("Enter Query");
            return;
        }
        edtSearchQuery.setText("");

        BooksManager.getInstance().searchBooks(query).doOnSubscribe(() -> showProgress("Searching..."))
                .doOnCompleted(this::dismissProgress)
                .doOnTerminate(this::dismissProgress)
                .subscribe(this::handleBooksSuccess, this::handleBooksFailure);

    }

    private void handleBooksSuccess(ArrayList<Book> books) {
        mBooks.clear();
        mBooks.addAll(books);
        mOnlineBooksListAdapter.notifyDataSetChanged();
        if (mOnlineBooksListAdapter.getItemCount() == 0) {
            showError("No books present");
        } else {
            hideError();
        }
    }

    private void handleBooksFailure(Throwable throwable) {
        showError(throwable.getMessage());
    }

    private void initRecyclerView() {
        mBooks = new ArrayList<>();
        rvBooks.setLayoutManager(new LinearLayoutManager(this));
        rvBooks.setHasFixedSize(true);
        mOnlineBooksListAdapter = new OnlineBooksListAdapter(mBooks, this);
        rvBooks.setAdapter(mOnlineBooksListAdapter);
        if (mOnlineBooksListAdapter.getItemCount() == 0) {
            showError("Enter Search Query");
        } else {
            hideError();
        }
    }

    private void showError(String msg) {
        txtErrorBooks.setText(msg);
        txtErrorBooks.setVisibility(View.VISIBLE);
    }

    private void hideError() {
        txtErrorBooks.setVisibility(View.GONE);
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_search_book;
    }

    @Override
    public boolean isToolbarPresent() {
        return true;
    }
}
