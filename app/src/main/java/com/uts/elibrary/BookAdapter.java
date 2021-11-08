package com.uts.elibrary;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<BookModel> books;
    public BookAdapter(Activity activity, List<BookModel> books) {
        this.activity = activity;
        this.books = books;
    }
    @Override
    public int getCount() {
        return books.size();
    }
    @Override
    public Object getItem(int location) {
        return books.get(location);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.book_row, null);
        TextView id = convertView.findViewById(R.id.id);
        TextView title = convertView.findViewById(R.id.title);
        TextView author = convertView.findViewById(R.id.author);
        BookModel book = books.get(position);
        id.setText(String.valueOf(book.getId()));
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        return convertView;
    }
}
