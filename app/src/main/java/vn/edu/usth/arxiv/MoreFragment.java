package vn.edu.usth.arxiv;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;


public class MoreFragment extends Fragment {

    private View view;
    private ListView listView;

    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_more, container, false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            String[] items = {"About us", "Contact", "Report", "Policy", "Copyright", "Donate", "Reset password", "Log out"};
            listView = (ListView)view.findViewById(R.id.list_more);
            tvMorAdap adapter = new tvMorAdap(getActivity(),items);
            listView.setAdapter(adapter);
        } else {
            String[] items = {"About us", "Contact", "Report", "Policy", "Copyright", "Donate"};
            listView = (ListView)view.findViewById(R.id.list_more);
            tvMorAdap adapter = new tvMorAdap(getActivity(),items);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}