package com.easylibrary.android.features.books;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.easylibrary.android.R;
import com.easylibrary.android.api.models.Book;
import com.easylibrary.android.api.models.BookLocation;
import com.easylibrary.android.db.BookStorage;
import com.easylibrary.android.features.base.ELBaseActivity;

import butterknife.Bind;

/**
 * Created by varad on 14/2/17.
 */

public class SearchBookActivity extends ELBaseActivity {

    @Bind(R.id.rev_books)
    RecyclerView rvBooks;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Search Book");
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addDummyData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        rvBooks.setLayoutManager(new LinearLayoutManager(this));
        rvBooks.setHasFixedSize(true);
        BookListAdapter bookListAdapter = new BookListAdapter(this, BookStorage.getAll(), true);
        rvBooks.setAdapter(bookListAdapter);
    }

    private void addDummyData() {
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
