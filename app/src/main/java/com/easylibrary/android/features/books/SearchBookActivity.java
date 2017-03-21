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
import com.easylibrary.android.api.network.BooksManager;
import com.easylibrary.android.db.BookStorage;
import com.easylibrary.android.features.base.ELBaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;

/**
 * Created by rohan on 14/2/17.
 */

public class SearchBookActivity extends ELBaseActivity {

    @Bind(R.id.rev_books)
    RecyclerView rvBooks;

    @Bind(R.id.edt_search_query)
    EditText edtSearchQuery;

    @Bind(R.id.txt_error_books)
    TextView txtErrorBooks;

    BookListAdapter mBookListAdapter;

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
                .flatMap(this::saveBooksToDb)
                .subscribe(this::handleBooksSuccess, this::handleBooksFailure);

    }

    private Observable<Boolean> saveBooksToDb(ArrayList<Book> books) {
        if (books != null && books.size() > 0) {
            for (Book book : books) {
                BookStorage.save(book);
            }
            return Observable.just(true);
        } else {
            return Observable.just(false);
        }
    }

    private void handleBooksSuccess(boolean isSaved) {
        mBookListAdapter.notifyDataSetChanged();
        if (mBookListAdapter.getItemCount() == 0) {
            showError("No books present");
        } else {
            hideError();
        }
    }

    private void handleBooksFailure(Throwable throwable) {
        showError(throwable.getMessage());
    }

    private void initRecyclerView() {
        rvBooks.setLayoutManager(new LinearLayoutManager(this));
        rvBooks.setHasFixedSize(true);
        mBookListAdapter = new BookListAdapter(this, BookStorage.getAll(), true);
        rvBooks.setAdapter(mBookListAdapter);
        if (mBookListAdapter.getItemCount() == 0) {
            showError("No books present");
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

    /*private void addDummyData() {
        for (int i = 0; i < 10; i++) {
            Book book = new Book();
            book.setId(i + 1);
            book.setName("Book name " + i + 1);
            book.setAuthor("Author " + i + 1);
            book.setIsbn("ISBN " + i + 1);

            BookLocation bookLocation = new BookLocation();
            bookLocation.setId(i + 1);
            bookLocation.setCurrentCount(i);
            bookLocation.setSection("SECT" + i);
            bookLocation.setShelf("A" + i);
            bookLocation.setRow(i + 1);
            bookLocation.setColumn(i + 2);

            book.setBookLocation(bookLocation);

            BookStorage.save(book);
        }
    }*/

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
