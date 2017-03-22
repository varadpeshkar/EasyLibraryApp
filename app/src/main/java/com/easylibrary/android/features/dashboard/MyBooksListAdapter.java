package com.easylibrary.android.features.dashboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easylibrary.android.R;
import com.easylibrary.android.api.models.BookIssueRequest;
import com.easylibrary.android.utils.ELUtility;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rohan on 23/3/17.
 */

public class MyBooksListAdapter extends RecyclerView.Adapter<MyBooksListAdapter.MyBookViewHolder> {

    private ArrayList<BookIssueRequest> mBookIssueRequests;

    private Context mContext;

    public MyBooksListAdapter(ArrayList<BookIssueRequest> bookIssueRequests, Context context) {
        mBookIssueRequests = bookIssueRequests;
        mContext = context;
    }

    @Override
    public MyBookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyBookViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_layout_my_book, parent, false));
    }

    @Override
    public void onBindViewHolder(MyBookViewHolder holder, int position) {
        BookIssueRequest bookIssueRequest = getItem(position);
        holder.txtBookName.setText(bookIssueRequest.getBook().getName());
        holder.txtExpiryDate.setText(ELUtility.formatDate(bookIssueRequest.getExpiryDate()));
    }

    @Override
    public int getItemCount() {
        return mBookIssueRequests.size();
    }

    public BookIssueRequest getItem(int position) {
        return mBookIssueRequests.get(position);
    }

    public static class MyBookViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.txt_book_name)
        TextView txtBookName;

        @Bind(R.id.txt_expiry_date)
        TextView txtExpiryDate;

        public MyBookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
