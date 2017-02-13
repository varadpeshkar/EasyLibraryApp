package com.easylibrary.android.features.books;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.easylibrary.android.R;
import com.easylibrary.android.features.base.ELBaseActivity;

/**
 * Created by rohan on 14/2/17.
 */

public class BooksByCategoryActivity extends ELBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Category");
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_books_by_category;
    }

    @Override
    public boolean isToolbarPresent() {
        return true;
    }
}
