package vn.edu.usth.arxiv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;

public class DownloadedListAdapter extends BaseAdapter {
    private Context context;
    private File[] files;

    public DownloadedListAdapter(Context context, File[] files) {
        this.context = context;
        this.files = files;
    }

    @Override
    public int getCount() {
        return files.length;
    }

    @Override
    public File getItem(int position) {
        return files[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.tv_downloaded_item, parent, false);
        }

        TextView textView = view.findViewById(R.id.text_item);
        textView.setText(files[position].getName());

        return view;
    }
}