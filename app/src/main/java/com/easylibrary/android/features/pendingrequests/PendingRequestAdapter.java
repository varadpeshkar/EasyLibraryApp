package com.easylibrary.android.features.pendingrequests;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easylibrary.android.R;
import com.easylibrary.android.api.models.Book;
import com.easylibrary.android.api.models.BookIssueRequest;
import com.easylibrary.android.utils.ELUtility;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rohan on 22/3/17.
 */

public class PendingRequestAdapter extends RecyclerView.Adapter<PendingRequestAdapter.RequestViewHolder> {

    private ArrayList<BookIssueRequest> mBookIssueRequests;

    private Context mContext;

    public PendingRequestAdapter(ArrayList<BookIssueRequest> bookIssueRequests, Context context) {
        mBookIssueRequests = bookIssueRequests;
        mContext = context;
    }


    @Override
    public RequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RequestViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_layout_pending_requests, parent, false));
    }

    @Override
    public void onBindViewHolder(RequestViewHolder holder, int position) {
        BookIssueRequest bookIssueRequest = getItem(position);
        if (bookIssueRequest == null) {
            return;
        }

        Book book = bookIssueRequest.getBook();

        holder.txtBookName.setText(book.getName());
        holder.txtAuthor.setText(book.getAuthor());
        holder.txtDepartment.setText(book.getCategory());
        holder.txtAvailability.setText("Request " + bookIssueRequest.getStatus());
        holder.txtAvailability.setBackground(ContextCompat
                .getDrawable(mContext, R.drawable.rounded_corner_background_orange));
        holder.txtLocation.setText(ELUtility.getFormattedLocation(book.getBookLocation()
                        .getSection(), book.getBookLocation().getShelf(),
                book.getBookLocation().getRow(), book.getBookLocation().getRow()));
    }

    public BookIssueRequest getItem(int position) {
        return mBookIssueRequests.get(position);
    }

    @Override
    public int getItemCount() {
        return mBookIssueRequests.size();
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {


        @Bind(R.id.txt_book_name)
        TextView txtBookName;

        @Bind(R.id.txt_author)
        TextView txtAuthor;

        @Bind(R.id.txt_location)
        TextView txtLocation;

        @Bind(R.id.txt_availability)
        TextView txtAvailability;

        @Bind(R.id.txt_department)
        TextView txtDepartment;


        public RequestViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
