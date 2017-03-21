package com.easylibrary.android.features.books;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easylibrary.android.R;
import com.easylibrary.android.api.models.Department;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rohan on 21/3/17.
 */

public class DepartmentsListAdapter extends RecyclerView.Adapter<DepartmentsListAdapter.DepartmentViewHolder> {

    private ArrayList<Department> mDepartments;

    private Context mContext;

    public DepartmentsListAdapter(ArrayList<Department> departments, Context context) {
        mDepartments = departments;
        mContext = context;
    }

    @Override
    public DepartmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DepartmentViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_layout_category, parent, false));
    }

    @Override
    public void onBindViewHolder(DepartmentViewHolder holder, int position) {
        holder.txtDepartmentName.setText(getItem(position).getDepartment());
    }

    public Department getItem(int position) {
        return mDepartments.get(position);
    }

    @Override
    public int getItemCount() {
        return mDepartments.size();
    }

    public static class DepartmentViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.txt_department_name)
        TextView txtDepartmentName;

        public DepartmentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
