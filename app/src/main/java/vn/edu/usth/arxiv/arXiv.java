package vn.edu.usth.arxiv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class arXiv extends AppCompatActivity {
    private static final String TAG = "arXivActivity";

    public static boolean onarXiv = true;

    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.arxiv);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.account);
        actionBar.setDisplayHomeAsUpEnabled(true);

        String[] items = {"Astrophysics", "Condensed Matter", "General Relativity and Quantum Cosmology", "Physics", "Quantum Physics", "High Energy Physics", "Mathematics", "Computer Sciences", "Nonlinear Sciences", "Quantitative Biology", "Quantitative Finance", "Statistics", "Electrical Engineering and Systems Science", "Economics"};
        listView = (ListView)findViewById(R.id.list_view);
        tvAdapter adapter = new tvAdapter(this,items);
        listView.setAdapter(adapter);

        Log.i(TAG, "App Created");
    }


    public void itemOnClick(View view){
        if (onarXiv){
            TextView tv = view.findViewById(R.id.tv_item);
            String t = tv.getText().toString();
//            Toast.makeText(this, a, Toast.LENGTH_SHORT).show();
//            Log.i(TAG, "itemOnClick: onarXiv"+onarXiv);
            DocListActivity dl = new DocListActivity();
            dl.title = t;
            dl = null;
            Intent docList = new Intent(getApplicationContext(),DocListActivity.class);
            startActivity(docList);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void removeFragments(String[] tags){
        for (String tag : tags) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
            if (fragment != null)
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }

        // Remove backstack
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String[] menuFragmentTag = {"AboutisOpened", "ContectisOpened", "PolicyisOpened", "DonateisOpened", "AccountisOpened", "CopyrightisOpened"};
        int id = item.getItemId();
        if (id==R.id.about){
            removeFragments(menuFragmentTag);
            AboutFragment aboutFragment = new AboutFragment();
            onarXiv = false;
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.arxiv_activity, aboutFragment,"AboutisOpened").addToBackStack(null).commit();
        } else if (id==R.id.contact) {
            removeFragments(menuFragmentTag);
            Fragment fragment = new ContactFragment();
            onarXiv = false;
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.arxiv_activity, fragment,"ContectisOpened").addToBackStack(null).commit();
        } else if (id==R.id.report) {
            Toast.makeText(this, "Report Clicked", Toast.LENGTH_SHORT).show();
        } else if (id==R.id.policy) {
            removeFragments(menuFragmentTag);
            Fragment fragment = new PolicyFragment();
            onarXiv = false;
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.arxiv_activity, fragment,"PolicyisOpened").addToBackStack(null).commit();
        } else if (id==R.id.copyright) {
            removeFragments(menuFragmentTag);
            Fragment fragment = new CopyrightFragment();
            onarXiv = false;
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.arxiv_activity, fragment,"CopyrightisOpened").addToBackStack(null).commit();
        } else if (id==R.id.donate) {
            removeFragments(menuFragmentTag);
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
            startActivity(searching);
        }
        return super.onOptionsItemSelected(item);
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