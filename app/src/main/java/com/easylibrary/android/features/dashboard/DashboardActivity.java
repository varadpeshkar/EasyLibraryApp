package com.easylibrary.android.features.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.easylibrary.android.R;
import com.easylibrary.android.api.models.BookIssueRequest;
import com.easylibrary.android.db.BookIssueRequestStorage;
import com.easylibrary.android.db.StudentStorage;
import com.easylibrary.android.features.base.ELBaseActivity;
import com.easylibrary.android.features.books.AllDepartmentsActivity;
import com.easylibrary.android.features.books.SearchBookActivity;
import com.easylibrary.android.features.pendingrequests.PendingRequestsActivity;
import com.easylibrary.android.features.student.StudentProfileActivity;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by varad on 12/2/17.
 */

public class DashboardActivity extends ELBaseActivity {

    @Bind(R.id.nav_drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.nav_drawer)
    NavigationView mNavigationView;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.rev_my_books)
    RecyclerView rvMyBooks;

    @Bind(R.id.txt_error_books)
    TextView txtErrorBooks;

    private HashMap<Integer, Intent> mNavItemMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("EasyLibrary");
        setUpNavDrawer();
        initNavMap();
        //initRecyclerView();
    }

    private void initRecyclerView() {
        rvMyBooks.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<BookIssueRequest> bookIssueRequests = new ArrayList<>();
        bookIssueRequests.addAll(Realm.getDefaultInstance()
                .copyFromRealm(BookIssueRequestStorage.getAllApproved()));
        if (bookIssueRequests.size() > 0) {
            MyBooksListAdapter myBooksListAdapter = new MyBooksListAdapter(bookIssueRequests, this);
            rvMyBooks.setAdapter(myBooksListAdapter);
            txtErrorBooks.setVisibility(View.INVISIBLE);
        } else {
            txtErrorBooks.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        initRecyclerView();
    }

    @OnClick(R.id.btn_books)
    void onClickBooks() {
        start(AllDepartmentsActivity.class);
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
        TextView txtStudentName = (TextView) mNavigationView.getHeaderView(0)
                .findViewById(R.id.txt_student_name);
        txtStudentName.setText(StudentStorage.getName());
        mNavigationView.setNavigationItemSelectedListener(item -> {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
            if (getIntentForNav(item.getItemId()) != null) {
                startActivity(getIntentForNav(item.getItemId()));
            }
            return true;
        });
    }

    private void initNavMap() {
        mNavItemMap = new HashMap<>();
        mNavItemMap.put(R.id.nav_item_profile, getNewIntent(StudentProfileActivity.class));
        mNavItemMap.put(R.id.nav_item_pending_requests, getNewIntent(PendingRequestsActivity.class));
    }

    private Intent getIntentForNav(Integer index) {
        return mNavItemMap.get(index);
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
