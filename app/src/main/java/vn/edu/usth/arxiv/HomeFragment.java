package vn.edu.usth.arxiv;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.fragment.app.Fragment;


public class HomeFragment extends Fragment {
    private ListView listView;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ImageButton uploadBtn = view.findViewById(R.id.main_upload);

        String[] items = {"Astrophysics of Galaxies", "Condensed Matter", "General Relativity and Quantum Cosmology", "Mathematical Physics",
                "Quantum Physics", "High Energy Physics", "Algebraic Geometry", "Computer Sciences", "Nonlinear Sciences", "Quantitative Biology",
                "Quantitative Finance", "Statistics Theory", "Electrical Engineering and Systems Science", "General Economics"};
        listView = (ListView)view.findViewById(R.id.list_view);
        tvAdapter adapter = new tvAdapter(getActivity(),items);
        listView.setAdapter(adapter);

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent upload = new Intent(getActivity(),UploadActivity.class);
                startActivity(upload);
            }
        });


        return view;
    }


}