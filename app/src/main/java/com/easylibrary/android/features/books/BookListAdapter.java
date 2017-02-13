package com.easylibrary.android.features.books;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.easylibrary.android.R;
import com.easylibrary.android.api.models.Book;
import com.easylibrary.android.utils.ELUtility;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by rohan on 14/2/17.
 */

public class BookListAdapter extends RealmRecyclerViewAdapter<Book, BookListAdapter.BookViewHolder> {

    private Context mContext;

    public BookListAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<Book> data, boolean autoUpdate) {
        super(context, data, autoUpdate);
        mContext = context;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BookViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_layout_book, parent, false));
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Book book = getItem(position);
        if (book == null) {
            return;
        }
        holder.txtBookName.setText(book.getName());
        holder.txtAuthor.setText(book.getAuthor());
        if (book.getBookLocation().getCurrentCount() > 0) {
            holder.txtAvailability.setText("Available");
            holder.txtAvailability.setBackground(ContextCompat
                    .getDrawable(mContext, R.drawable.rounded_corner_background_green));
            holder.btnIssue.setVisibility(View.VISIBLE);
        } else {
            holder.txtAvailability.setText("Unavailable");
            holder.txtAvailability.setBackground(ContextCompat
                    .getDrawable(mContext, R.drawable.rounded_corner_background_red));
            holder.btnIssue.setVisibility(View.GONE);
        }

        holder.txtLocation.setText(ELUtility.getFormattedLocation(book.getBookLocation()
                        .getSection(), book.getBookLocation().getShelf(),
                book.getBookLocation().getRow(), book.getBookLocation().getRow()));

    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {


        @Bind(R.id.txt_book_name)
        TextView txtBookName;

        @Bind(R.id.txt_author)
        TextView txtAuthor;

        @Bind(R.id.txt_location)
        TextView txtLocation;

        @Bind(R.id.txt_availability)
        TextView txtAvailability;

        @Bind(R.id.btn_issue)
        Button btnIssue;


        public BookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
