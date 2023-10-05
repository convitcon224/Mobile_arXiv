package vn.edu.usth.arxiv;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class arXiv extends AppCompatActivity {

    private final Dialog loadingdialog = new Dialog(arXiv.this);

    private static final String TAG = "arXivActivity";

    public static boolean onarXiv = true;

    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;

    private static RequestQueue mRequestQueue;
    public static final String requestTAG = "stringRequestTAG";
    public static String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arxiv);

        mRequestQueue = Volley.newRequestQueue(this);

        viewPager2 = findViewById(R.id.view_pager2);
        bottomNavigationView = findViewById(R.id.bottom_navigation);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.account);
        actionBar.setDisplayHomeAsUpEnabled(true);


        Log.i(TAG, "App Created");

        Viewpager2Adapter adapter = new Viewpager2Adapter(this);
        viewPager2.setAdapter(adapter);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.page_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.page_favorite).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.page_downloaded).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.page_more).setChecked(true);
                        break;
                }
            }
        });
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.page_home) {
                    viewPager2.setCurrentItem(0);
                } else if (item.getItemId() == R.id.page_favorite) {
                    viewPager2.setCurrentItem(1);
                } else if (item.getItemId() == R.id.page_downloaded) {
                    viewPager2.setCurrentItem(2);
                } else if (item.getItemId() == R.id.page_more) {
                    viewPager2.setCurrentItem(3);
                }
                return true;
            }
        });

    }


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

    public void itemOnClick(View view) {
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

    private void getData(String prefix, String detail){
        // Instantiate the RequestQueue.
//        RequestQueue queue = ((VolleyRequestQueue) getApplicationContext()).getRequestQueue();
//        String url = "https://export.arxiv.org/api/query?search_query=all:electron+AND+all:proton";
        url = "https://export.arxiv.org/api/query?search_query=" + prefix +":"+ detail + "&sortBy=lastUpdatedDate&sortOrder=descending&start=0&max_results=20";
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
                    loadingdialog.dismissdialog();
                }

                else if (error instanceof ServerError) {
                    //error in server
                    Toast.makeText(getApplicationContext(), "Sever Error", Toast.LENGTH_SHORT).show();
                    loadingdialog.dismissdialog();
                }

                else if (error instanceof NetworkError) {
                    //network is disconnect
                    Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                    loadingdialog.dismissdialog();
                }

                else if (error instanceof ParseError) {
                    //for cant convert data
                    Toast.makeText(getApplicationContext(), "Parse Error", Toast.LENGTH_SHORT).show();
                    loadingdialog.dismissdialog();
                }

                else {
                    //other error
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    loadingdialog.dismissdialog();
                }
            }
        });
        stringRequest.setTag(requestTAG);

        // Add the request to the RequestQueue.
        mRequestQueue.add(stringRequest);
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (onarXiv)
            getSupportActionBar().show();
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