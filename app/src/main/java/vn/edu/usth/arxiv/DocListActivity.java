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

    private static ArrayList<String> articleID = new ArrayList<>();
    private static ArrayList<String> articleTitle = new ArrayList<>();
    private static ArrayList<String> articleDate = new ArrayList<>();
    private static ArrayList<String> articleAuthor = new ArrayList<>();

    private static int offset = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doclist);

        TextView screenTitle = findViewById(R.id.listField);
        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);

        articleID.clear();
        articleTitle.clear();
        articleDate.clear();
        articleAuthor.clear();

        loadAPI();
        setUpArticleModels();

        Article_RecyclerViewAdapter adapter = new Article_RecyclerViewAdapter(this, articleModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        screenTitle.setText(title);
    }

    private void loadAPI(){
        for (int i = 0; i < APIHandle.docs.size(); i++){
            articleID.add(APIHandle.docs.get(i).getId());
            articleTitle.add(APIHandle.docs.get(i).getTitle());
            articleDate.add(APIHandle.docs.get(i).getDate().toString());
            articleAuthor.add(APIHandle.docs.get(i).getAuthors());
        }
    }


    private void setUpArticleModels() {
        if (offset + 30 < articleID.size()) {
            for(int i = offset; i < articleID.size(); i++) {
                articleModels.add(new ArticleModel(articleID.get(i),articleTitle.get(i), articleDate.get(i), articleAuthor.get(i)));
            }
        }
        else {
            Log.i(TAG, String.valueOf(articleID.size()));
            for(int i = offset; i < articleID.size(); i++) {
                articleModels.add(new ArticleModel(articleID.get(i),articleTitle.get(i), articleDate.get(i), articleAuthor.get(i)));
            }
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