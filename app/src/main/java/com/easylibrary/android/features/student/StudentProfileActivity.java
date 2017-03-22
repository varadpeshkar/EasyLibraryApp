package com.easylibrary.android.features.student;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.easylibrary.android.R;
import com.easylibrary.android.api.models.Student;
import com.easylibrary.android.db.StudentStorage;
import com.easylibrary.android.features.base.ELBaseActivity;

import butterknife.Bind;

/**
 * Created by rohan on 22/3/17.
 */

public class StudentProfileActivity extends ELBaseActivity {

    @Bind(R.id.txt_student_name)
    TextView txtName;

    @Bind(R.id.txt_current_year)
    TextView txtYear;

    @Bind(R.id.txt_branch)
    TextView txtBranch;

    @Bind(R.id.txt_mobile)
    TextView txtMobile;

    @Bind(R.id.txt_email)
    TextView txtEmail;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        setData();
    }

    private void setData() {
        Student student = StudentStorage.getStudent();
        txtName.setText(student.getName());
        txtBranch.setText(student.getBranch());
        txtYear.setText(student.getCurrentYear());
        txtMobile.setText(String.valueOf(student.getMobile()));
        txtEmail.setText(student.getEmail());
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_student_profile;
    }

    @Override
    public boolean isToolbarPresent() {
        return true;
    }
}
