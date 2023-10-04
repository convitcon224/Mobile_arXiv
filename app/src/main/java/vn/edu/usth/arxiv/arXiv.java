package vn.edu.usth.arxiv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class arXiv extends AppCompatActivity{
    private static final String TAG = "arXivActivity";

    public static boolean onarXiv = true;

    private ListView listView;

    private final Dialog loadingdialog = new Dialog(arXiv.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.arxiv);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.account);
        actionBar.setDisplayHomeAsUpEnabled(true);

        String[] items = {"Astrophysics of Galaxies", "Condensed Matter", "General Relativity and Quantum Cosmology", "Mathematical Physics",
                "Quantum Physics", "High Energy Physics", "Algebraic Geometry", "Computer Sciences", "Nonlinear Sciences", "Quantitative Biology",
                "Quantitative Finance", "Statistics Theory", "Electrical Engineering and Systems Science", "General Economics"};
        listView = (ListView)findViewById(R.id.list_view);
        tvAdapter adapter = new tvAdapter(this,items);
        listView.setAdapter(adapter);


        Log.i(TAG, "App Created");
    }


    public void itemOnClick(View view){
        if (onarXiv){
            TextView tv = view.findViewById(R.id.tv_item);
            String tit = tv.getText().toString();
//            Toast.makeText(this, a, Toast.LENGTH_SHORT).show();
//            Log.i(TAG, "itemOnClick: onarXiv"+onarXiv);
            DocListActivity.title = tit;

            loadingdialog.startLoadingdialog();

//            final Handler handler = new Handler(Looper.getMainLooper()) {
//                @Override
//                public void handleMessage(Message msg) {
//                    // This method is executed in main thread
//                    String content = msg.getData().getString("server_response");
//
//                    if (content.equals("getDataDone")){
//                        loadingdialog.dismissdialog();
//                        Intent docList = new Intent(getApplicationContext(),DocListActivity.class);
//                        startActivity(docList);
//                    }
//                }
//            };


            // Thread gets data
//            Thread t = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    getData();
////                    Bundle bundle = new Bundle();
////                    bundle.putString("server_response", "getDataDone");
////                    Message msg = new Message();
////                    msg.setData(bundle);
////                    handler.sendMessage(msg);
//                }
//            });
//
//            if ( t.getState() == Thread.State.NEW ){
//                t.start();
//                Log.i(TAG, "itemOnClick: jjj");
//            }

            getData("cat",tit);
        }
    }

    public void getData(String prefix, String detail){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "https://export.arxiv.org/api/query?search_query=all:electron+AND+all:proton";
        String url = "https://export.arxiv.org/api/query?search_query=" + prefix +":"+ detail + "&sortBy=lastUpdatedDate&sortOrder=ascending";
//        "http://export.arxiv.org/api/query?search_query=ti:"electron thermal conductivity"&sortBy=lastUpdatedDate&sortOrder=ascending"


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        APIHandle apiHandle = new APIHandle();
                        apiHandle.getDocument(response);
                        Log.i("TAG response", String.valueOf(response.length()));
                        loadingdialog.dismissdialog();
                        Intent docList = new Intent(getApplicationContext(),DocListActivity.class);
                        startActivity(docList);
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
        queue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void removeFragments(){
//        for (String tag : tags) {
//            Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
//            if (fragment != null)
//                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
//        }

        // Remove backstack
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        String[] menuFragmentTag = {"AboutisOpened", "ContectisOpened", "PolicyisOpened", "DonateisOpened", "AccountisOpened", "CopyrightisOpened"};
        int id = item.getItemId();
        if (id==R.id.about){
            removeFragments();
            AboutFragment aboutFragment = new AboutFragment();
            onarXiv = false;
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.arxiv_activity, aboutFragment,"AboutisOpened").addToBackStack(null).commit();
        } else if (id==R.id.contact) {
            removeFragments();
            Fragment fragment = new ContactFragment();
            onarXiv = false;
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.arxiv_activity, fragment,"ContectisOpened").addToBackStack(null).commit();
        } else if (id==R.id.report) {
            Toast.makeText(this, "Report Clicked", Toast.LENGTH_SHORT).show();
        } else if (id==R.id.policy) {
            removeFragments();
            Fragment fragment = new PolicyFragment();
            onarXiv = false;
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.arxiv_activity, fragment,"PolicyisOpened").addToBackStack(null).commit();
        } else if (id==R.id.copyright) {
            removeFragments();
            Fragment fragment = new CopyrightFragment();
            onarXiv = false;
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.arxiv_activity, fragment,"CopyrightisOpened").addToBackStack(null).commit();
        } else if (id==R.id.donate) {
            removeFragments();
            Fragment fragment = new DonateFragment();
            onarXiv = false;
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.arxiv_activity, fragment,"DonateisOpened").addToBackStack(null).commit();
        } else if (id==android.R.id.home) {
//            removeFragments(menuFragmentTag);
//            Fragment fragment = new Login_Fragment();
//            getSupportActionBar().hide();
//            onarXiv = false;
//            getSupportFragmentManager().beginTransaction().replace(
//                    R.id.arxiv_activity, fragment,"AccountisOpened").addToBackStack(null).commit();
            Intent login = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(login);
        } else if (id==R.id.search) {
            Intent searching = new Intent(getApplicationContext(),SearchActivity.class);
            startActivityForResult(searching,2);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2)
        {
            if (resultCode == AppCompatActivity.RESULT_OK){
                String prefix = "all";
                final String[] result = data.getStringArrayExtra("MESSAGE");

                if (result[0] == "Title"){
                    prefix = "ti";
                } else if (result[0] == "Author") {
                    prefix = "au";
                } else if (result[0] == "Keywords") {
                    prefix = "all";
                }
                loadingdialog.startLoadingdialog();
                getData(prefix,result[1]);
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (onarXiv)
            getSupportActionBar().show();
    }

    public void uploadButtonOnClick(View view) {
        Intent upload = new Intent(getApplicationContext(),UploadActivity.class);
        startActivity(upload);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "App Started");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "App Resumed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "App Paused");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "App Stopped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "App Destroyed");
//        System.gc();
    }

}