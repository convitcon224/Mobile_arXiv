package vn.edu.usth.arxiv;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;


public class Article_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);

        ImageView favorite = view.findViewById(R.id.favorite_button);

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If not login yet
                Intent login = new Intent(getActivity().getApplicationContext(),LoginActivity.class);
                startActivity(login);

                // TODO: logined
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        DocListActivity temp = new DocListActivity();
        temp.onDocList = true;
        temp = null;
    }
}