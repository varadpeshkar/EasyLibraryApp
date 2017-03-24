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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.easylibrary.android.R;
import com.easylibrary.android.api.models.Book;
import com.easylibrary.android.api.models.BookIssueRequest;
import com.easylibrary.android.api.network.managers.BooksManager;
import com.easylibrary.android.api.network.managers.StudentManager;
import com.easylibrary.android.db.BookIssueRequestStorage;
import com.easylibrary.android.db.StudentStorage;
import com.easylibrary.android.features.base.ELBaseActivity;
import com.easylibrary.android.features.books.AllDepartmentsActivity;
import com.easylibrary.android.features.books.OnlineBooksListAdapter;
import com.easylibrary.android.features.books.SearchBookActivity;
import com.easylibrary.android.features.pendingrequests.PendingRequestsActivity;
import com.easylibrary.android.features.student.StudentProfileActivity;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import io.realm.RealmResults;

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

    @Bind(R.id.rev_my_books)
    RecyclerView rvMyBooks;

    @Bind(R.id.txt_error_books)
    TextView txtErrorBooks;

    @Bind(R.id.txt_error_recommendations)
    TextView txtRecommendations;

    @Bind(R.id.pb_books)
    ProgressBar pbBooks;

    @Bind(R.id.imv_action_refresh)
    ImageView imvRefresh;

    @Bind(R.id.rev_recommendations)
    RecyclerView rvRecommendations;

    private HashMap<Integer, Intent> mNavItemMap;

    private OnlineBooksListAdapter mOnlineBooksListAdapter;

    private MyBooksListAdapter mMyBooksListAdapter;

    private ArrayList<Book> mBooks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("EasyLibrary");
        setUpNavDrawer();
        initNavMap();
        initRecommendations();
        //initRecyclerView();
    }

    private void initRecyclerView() {
        rvMyBooks.setLayoutManager(new LinearLayoutManager(this));
        RealmResults<BookIssueRequest> bookIssueRequests = BookIssueRequestStorage.getAllApproved();
        if (bookIssueRequests.size() > 0) {
            mMyBooksListAdapter = new MyBooksListAdapter(this, bookIssueRequests, true);
            rvMyBooks.setAdapter(mMyBooksListAdapter);
            txtErrorBooks.setVisibility(View.INVISIBLE);
        } else {
            txtErrorBooks.setVisibility(View.VISIBLE);
        }

    }

    @OnClick(R.id.imv_action_refresh)
    void onClickRefresh() {
        refreshAPICall();
    }

    private void refreshAPICall() {
        showProgress("Refreshing...");
        StudentManager.getInstance().getAllIssueRequests()
                .subscribe(this::handleRequestSuccess, this::handleError);
    }

    private void handleRequestSuccess(ArrayList<BookIssueRequest> bookIssueRequests) {
        dismissProgress();
        BookIssueRequestStorage.deleteAll();
        BookIssueRequestStorage.save(bookIssueRequests);
        mMyBooksListAdapter.notifyDataSetChanged();
    }

    private void handleError(Throwable throwable) {

    }


    private void initRecommendations() {
        rvRecommendations.setLayoutManager(new LinearLayoutManager(this));
        mBooks = new ArrayList<>();
        mOnlineBooksListAdapter = new OnlineBooksListAdapter(mBooks, this);
        rvRecommendations.setAdapter(mOnlineBooksListAdapter);
        makeAPICall();
    }

    private void showProgress() {
        pbBooks.setIndeterminate(true);
        pbBooks.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        pbBooks.setVisibility(View.INVISIBLE);
    }

    private void showError(String errorMsg) {
        txtRecommendations.setText(errorMsg);
        txtRecommendations.setVisibility(View.VISIBLE);
    }

    private void hideError() {
        txtRecommendations.setVisibility(View.INVISIBLE);
    }

    private void makeAPICall() {
        BooksManager.getInstance().getBookRecommendations()
                .doOnSubscribe(this::showProgress)
                .doOnCompleted(this::hideProgress)
                .doOnTerminate(this::hideProgress)
                .subscribe(this::handleSuccess, this::handleFailure);
    }

    private void handleSuccess(ArrayList<Book> books) {
        mBooks.clear();
        mBooks.addAll(books);
        mOnlineBooksListAdapter.notifyDataSetChanged();
        if (books.size() > 0) {
            hideError();
        } else {
            showError("No Recommendations Present");
        }
    }

    private void handleFailure(Throwable throwable) {
        showError(throwable.getMessage());
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
