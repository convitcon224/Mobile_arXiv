package vn.edu.usth.arxiv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;


public class MoreFragment extends Fragment {

    private ListView listView;

    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        String[] items = {"Astrophysics of Galaxies", "Condensed Matter", "General Relativity and Quantum Cosmology", "Mathematical Physics",
                "Quantum Physics", "High Energy Physics", "Algebraic Geometry", "Computer Sciences", "Nonlinear Sciences", "Quantitative Biology",
                "Quantitative Finance", "Statistics Theory", "Electrical Engineering and Systems Science", "General Economics"};
        listView = (ListView)view.findViewById(R.id.list_more);
        tvMorAdap adapter = new tvMorAdap(getActivity(),items);
        listView.setAdapter(adapter);



        return view;
    }

}