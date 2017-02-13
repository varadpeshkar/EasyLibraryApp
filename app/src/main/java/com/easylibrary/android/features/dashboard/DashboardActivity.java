package com.easylibrary.android.features.dashboard;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.easylibrary.android.R;
import com.easylibrary.android.features.base.ELBaseActivity;
import com.easylibrary.android.features.books.AllBooksActivity;
import com.easylibrary.android.features.books.SearchBookActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by rohan on 12/2/17.
 */

public class DashboardActivity extends ELBaseActivity {

    @Bind(R.id.nav_drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.nav_drawer)
    NavigationView mNavigationView;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("EasyLibrary");
        setUpNavDrawer();
    }

    @OnClick(R.id.btn_books)
    void onClickBooks() {
        start(AllBooksActivity.class);
    }

    @OnClick(R.id.btn_search)
    void onClickBooksSearch() {
        start(SearchBookActivity.class);
    }


    private void setUpNavDrawer() {
        if (mToolbar != null) {
            mToolbar.setNavigationIcon(R.drawable.ic_menu);
            mToolbar.setNavigationOnClickListener(v ->
                    mDrawerLayout.openDrawer(GravityCompat.START));
        }
        mNavigationView.setItemIconTintList(null);
        mNavigationView.setNavigationItemSelectedListener(item -> {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }

            return true;
        });

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_dashboard;
    }

    @Override
    public boolean isToolbarPresent() {
        return true;
    }
}
