package vn.edu.usth.arxiv;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PolicyFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_policy, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        arXiv.onarXiv = true;
    }
}