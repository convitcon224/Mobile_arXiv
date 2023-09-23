package vn.edu.usth.arxiv;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.fragment_about, container, false);
//        return v;
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        arXiv ax = new arXiv();
        ax.onarXiv = true;
        ax = null;
    }
}