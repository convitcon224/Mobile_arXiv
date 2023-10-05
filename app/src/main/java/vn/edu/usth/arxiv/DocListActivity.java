package vn.edu.usth.arxiv;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

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

    private int offset = 0;
    private final int limit = 10;
    private int start = 0;
    private int max = 20;

    private Article_RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar loadingPB;
    private NestedScrollView nestedSV;

    ImageButton imageButton;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doclist);

        getSupportActionBar().hide();
        articleID.clear();
        articleTitle.clear();
        articleDate.clear();
        articleAuthor.clear();

        TextView screenTitle = findViewById(R.id.listField);
        recyclerView = findViewById(R.id.mRecyclerView);
        loadingPB = findViewById(R.id.idPBLoading);
        nestedSV = findViewById(R.id.idNestedSV);



        loadAPI();
        setUpArticleModels();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        nestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // on scroll change we are checking when users scroll as bottom.
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // on below line we are making our progress bar visible.

                    if ((offset+limit)<articleID.size()){
                        offset += limit;
                        loadingPB.setVisibility(View.VISIBLE);
                        setUpArticleModels();
                    } else {
                        loadingPB.setVisibility(View.VISIBLE);
                        start = articleID.size();
                        max += 20;
                        loadMoreData();
                    }

                }
            }
        });

        screenTitle.setText(title);

        imageButton = findViewById(R.id.backBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void loadAPI(){
        for (int i = 0; i < APIHandle.docs.size(); i++){
            articleID.add(APIHandle.docs.get(i).getId());
            articleTitle.add(APIHandle.docs.get(i).getTitle());
            articleDate.add(APIHandle.docs.get(i).getDate().toString());
            articleAuthor.add(APIHandle.docs.get(i).getAuthors());
        }
    }

    private void loadMoreAPI(){
        for (int i = articleID.size(); i < APIHandle.docs.size(); i++){
            articleID.add(APIHandle.docs.get(i).getId());
            articleTitle.add(APIHandle.docs.get(i).getTitle());
            articleDate.add(APIHandle.docs.get(i).getDate().toString());
            articleAuthor.add(APIHandle.docs.get(i).getAuthors());
        }
    }


    private void setUpArticleModels() {
        recyclerView.setVisibility(View.VISIBLE);
        if (offset + limit < articleID.size()) {
            for(int i = offset; i < offset+limit; i++) {
                articleModels.add(new ArticleModel(i,articleID.get(i),articleTitle.get(i), articleDate.get(i), articleAuthor.get(i)));
            }
        }
        else {
            Log.i(TAG, String.valueOf(articleID.size()));
            for(int i = offset; i < articleID.size(); i++) {
                articleModels.add(new ArticleModel(i,articleID.get(i),articleTitle.get(i), articleDate.get(i), articleAuthor.get(i)));
            }
        }
        adapter = new Article_RecyclerViewAdapter(DocListActivity.this, articleModels);
        recyclerView.setAdapter(adapter);
        loadingPB.setVisibility(View.INVISIBLE);
    }


    public void rowItemOnclick(View view) {
        if (onDocList) {
            TextView tv = view.findViewById(R.id.article_position);

            int t = Integer.parseInt(tv.getText().toString());
//            Toast.makeText(this, String.valueOf(t), Toast.LENGTH_SHORT).show();
//            DocListActivity dl = new DocListActivity();
//            dl.title = t;
//            dl = null;
//            Intent docList = new Intent(getApplicationContext(),DocListActivity.class);
//            startActivity(docList);
            Article_Fragment article = new Article_Fragment();
            article.subDate = APIHandle.docs.get(t).getDate().toString();
            article.artTit = APIHandle.docs.get(t).getTitle();
            article.auName = APIHandle.docs.get(t).getAuthors();
            article.absText = APIHandle.docs.get(t).getContent();
            article.trackID = APIHandle.docs.get(t).getId().replace("http://arxiv.org/abs/","");
            onDocList = false;
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.activity_doclist, article).addToBackStack(null).commit();
            getSupportActionBar().hide();
        }
    }


    private void loadMoreData(){
        arXiv temp = new arXiv();
        String url = arXiv.url.substring(0,arXiv.url.indexOf("&start=")) + "&start=" + start + "&max_results=" + max;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        APIHandle apiHandle = new APIHandle();
                        apiHandle.loadMore(response);
                        loadMoreAPI();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                textView.setText("That didn't work!");
                if (error instanceof TimeoutError) {
                    //For example your timeout is 3 seconds but the operation takes longer
                    Toast.makeText(getApplicationContext(), "Timeout Error", Toast.LENGTH_SHORT).show();
                }

                else if (error instanceof ServerError) {
                    //error in server
                    Toast.makeText(getApplicationContext(), "Sever Error", Toast.LENGTH_SHORT).show();
                }

                else if (error instanceof NetworkError) {
                    //network is disconnect
                    Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                }

                else if (error instanceof ParseError) {
                    //for cant convert data
                    Toast.makeText(getApplicationContext(), "Parse Error", Toast.LENGTH_SHORT).show();
                }

                else {
                    //other error
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Add the request to the RequestQueue.
        temp.getRequestQueue().add(stringRequest);
    }


    public void onBackPressed() {
        super.onBackPressed();
        if (onDocList)
            getSupportActionBar().hide();
    }
}