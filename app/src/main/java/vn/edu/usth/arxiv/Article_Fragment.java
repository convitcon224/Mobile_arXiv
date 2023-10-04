package vn.edu.usth.arxiv;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;


public class Article_Fragment extends Fragment {

    public static String subDate = "%%%";
    public static String artTit = "###";
    public static String auName = "&&&";
    public static String absText = "***";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);

        TextView submitDate = view.findViewById(R.id.submit_date);
        TextView articleTitle = view.findViewById(R.id.article_title);
        TextView authorsName = view.findViewById(R.id.author);
        TextView abstractText = view.findViewById(R.id.description);

        submitDate.setText(subDate);
        articleTitle.setText(artTit);
        authorsName.setText(auName);
        abstractText.setText(absText);


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
        DocListActivity.onDocList = true;
    }
}