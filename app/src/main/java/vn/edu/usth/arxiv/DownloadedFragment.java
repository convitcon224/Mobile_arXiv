package vn.edu.usth.arxiv;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.io.File;


public class DownloadedFragment extends Fragment {
    private ListView listView;

    public DownloadedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_downloaded, container, false);

        listView = view.findViewById(R.id.list_view);

        // Get the download folder path.
        File downloadFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        // Get the Arxiv folder path.
        File arxivFolder = new File(downloadFolder, "arXivPDF");

        // Get all files from the Arxiv folder.
        File[] files = arxivFolder.listFiles();


        // Set the list adapter to the list view.
        listView.setAdapter(new DownloadedListAdapter(getActivity(), files));



        return view;
    }
}