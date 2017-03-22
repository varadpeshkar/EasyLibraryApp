package com.easylibrary.android.features.authentication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.easylibrary.android.R;
import com.easylibrary.android.api.models.Auth;
import com.easylibrary.android.api.models.BookIssueRequest;
import com.easylibrary.android.api.models.Student;
import com.easylibrary.android.api.network.managers.AuthManager;
import com.easylibrary.android.api.network.managers.StudentManager;
import com.easylibrary.android.db.BookIssueRequestStorage;
import com.easylibrary.android.db.StudentStorage;
import com.easylibrary.android.features.base.ELBaseActivity;
import com.easylibrary.android.features.dashboard.DashboardActivity;
import com.easylibrary.android.utils.ELPreferences;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;

/**
 * Created by rohan on 12/2/17.
 */

public class LoginActivity extends ELBaseActivity {


    @Bind(R.id.edt_roll_number)
    EditText edtEmail;

    @Bind(R.id.edt_password)
    EditText edtPassword;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.btn_login)
    void onClickLogin() {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        if (email.length() == 0) {
            edtEmail.setError("Enter valid email");
            return;
        }

        if (password.length() == 0) {
            edtPassword.setError("Password required");
            return;
        }
        AuthManager.getInstance().addEmail(email);
        AuthManager.getInstance().loginUser(email, password)
                .doOnSubscribe(() -> showProgress("Authenticating..."))
                .doOnCompleted(this::dismissProgress)
                .doOnTerminate(this::dismissProgress)
                .flatMap(this::handleAuthSuccess)
                .flatMap(this::handleProfileSuccess)
                .onErrorResumeNext(Observable::error)
                .subscribe(this::handleIssueRequestSuccess, this::handleAuthFailure);

    }

    private void handleIssueRequestSuccess(ArrayList<BookIssueRequest> bookIssueRequests) {
        BookIssueRequestStorage.save(bookIssueRequests);
        ELPreferences.get(this).saveBoolean(ELPreferences.Keys.IS_LOGGED_IN, true);
        start(DashboardActivity.class);
        finish();
    }

    private Observable<ArrayList<BookIssueRequest>> handleProfileSuccess(Student student) {
        StudentStorage.save(student);
        return StudentManager.getInstance().getAllIssueRequests();
    }

    private Observable<Student> handleAuthSuccess(Auth auth) {
        AuthManager.getInstance().addAuthentication(auth);
        return StudentManager.getInstance().getProfile();
    }

    private void handleAuthFailure(Throwable throwable) {
        showToast("Invalid email or password");
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public boolean isToolbarPresent() {
        return false;
    }
}
