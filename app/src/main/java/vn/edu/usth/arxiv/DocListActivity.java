package vn.edu.usth.arxiv;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DocListActivity extends AppCompatActivity {

    private final String TAG = "Doc List Ac";
    ArrayList<ArticleModel> articleModels = new ArrayList<>();
    public static boolean onDocList = true;
    public static String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doclist);

        TextView screenTitle = findViewById(R.id.listField);
        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);

        setUpArticleModels();

        Article_RecyclerViewAdapter adapter = new Article_RecyclerViewAdapter(this, articleModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        screenTitle.setText(title);

    }

    private void setUpArticleModels() {
        String[] articleTitle = getResources().getStringArray(R.array.title);
        String[] articleDate = getResources().getStringArray(R.array.submit_date);
        String[] articleAuthor = getResources().getStringArray(R.array.author);

        for(int i = 0; i<articleTitle.length; i++) {
            articleModels.add(new ArticleModel(articleTitle[i], articleDate[i], articleAuthor[i]));
        }
    }


    public void rowItemOnclick(View view) {
        if (onDocList) {
            TextView tv = view.findViewById(R.id.titleText);
            String t = tv.getText().toString();
            Toast.makeText(this, t, Toast.LENGTH_SHORT).show();
//            DocListActivity dl = new DocListActivity();
//            dl.title = t;
//            dl = null;
//            Intent docList = new Intent(getApplicationContext(),DocListActivity.class);
//            startActivity(docList);
            Fragment article = new Article_Fragment();
            onDocList = false;
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.activity_doclist, article).addToBackStack(null).commit();
            getSupportActionBar().hide();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (onDocList)
            getSupportActionBar().show();
    }
}