package com.easylibrary.android.features.dashboard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easylibrary.android.R;
import com.easylibrary.android.api.models.BookIssueRequest;
import com.easylibrary.android.api.models.BookStatus;
import com.easylibrary.android.utils.ELUtility;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by rohan on 23/3/17.
 */

public class MyBooksListAdapter extends RealmRecyclerViewAdapter<BookIssueRequest, MyBooksListAdapter.MyBookViewHolder> {

    private Context mContext;

    public MyBooksListAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<BookIssueRequest> data, boolean autoUpdate) {
        super(context, data, autoUpdate);
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
        BookStatus bookStatus = ELUtility.getStatus(bookIssueRequest.getExpiryDate());
        holder.txtStatus.setText(bookStatus.getMsg());
        holder.txtStatus.setTextColor(ContextCompat.getColor(mContext, bookStatus.getColor()));
    }

    public static class MyBookViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.txt_book_name)
        TextView txtBookName;

        @Bind(R.id.txt_expiry_date)
        TextView txtExpiryDate;

        @Bind(R.id.txt_status)
        TextView txtStatus;

        public MyBookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
