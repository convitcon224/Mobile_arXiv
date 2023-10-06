package vn.edu.usth.arxiv;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {


    public final DialogNCancelable loadingdialog = new DialogNCancelable(LoginActivity.this);

    private final String TAG = "Login Activity";
    public static boolean onLogin = true;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        TextView create_acc = findViewById(R.id.create_acc);
        TextView forgot_pass = findViewById(R.id.forgot_pass);
        Button guest_login = findViewById(R.id.btn_guest);
        Button btn_login = findViewById(R.id.btn_login);

        EditText usernameText = findViewById(R.id.input_username);
        EditText passText = findViewById(R.id.input_password);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        create_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onLogin){
                    Register_Fragment register_fragment = new Register_Fragment();
                    getSupportFragmentManager().beginTransaction().replace(
                            R.id.activity_login, register_fragment).addToBackStack(null).commit();
                }
            }
        });

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onLogin){
                    Get_password_Fragment get_password_fragment = new Get_password_Fragment();
                    getSupportFragmentManager().beginTransaction().replace(
                            R.id.activity_login, get_password_fragment).addToBackStack(null).commit();
                }
            }
        });

        guest_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                progressBar.setVisibility(View.VISIBLE);
                loadingdialog.startLoadingdialog();
                String email, password;
                email = String.valueOf(usernameText.getText());
                password = String.valueOf(passText.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    loadingdialog.dismissdialog();
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    loadingdialog.dismissdialog();
                    return;
                }


                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT ).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Log.i("TAG test", "onComplete: "+ user.getUid());
//                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                    startActivity(intent);
                                    getFavorite();
                                } else {
                                    loadingdialog.dismissdialog();
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void getFavorite(){
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase.child(id).child("favorite").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
//                    String value = task.getResult().getValue(String.class);
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    FavoriteFragment.favorites = String.valueOf(task.getResult().getValue());
                }
            }
        });
        mDatabase.child(id).child("title").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
//                    String value = task.getResult().getValue(String.class);
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    FavoriteFragment.titFav = String.valueOf(task.getResult().getValue());
                }
            }
        });
        mDatabase.child(id).child("date").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
//                    String value = task.getResult().getValue(String.class);
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    FavoriteFragment.datFav = String.valueOf(task.getResult().getValue());
                }
            }
        });
        mDatabase.child(id).child("author").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
//                    String value = task.getResult().getValue(String.class);
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    FavoriteFragment.auFav = String.valueOf(task.getResult().getValue());
                }
            }
        });
        loadDocFavs();
        loadingdialog.dismissdialog();
        finish();
    }


    private void loadDocFavs(){
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                getDocsFav();
//            }
//        });
//        if ( t.getState() == Thread.State.NEW ){
//            t.start();
//            Log.i(TAG, "itemOnClick: jjj");
//        }
    }

    private void getDocsFav(){
        FavoriteFragment.docsFav.clear();
        String temps = FavoriteFragment.favorites;
        String tempid;
        while (!temps.equals("")) {
            temps = temps.replaceFirst(",","");
            if (temps.contains(",")){
                tempid = temps.substring(0, temps.indexOf(","));
                temps = temps.replace(tempid, "");
            } else {
                tempid = temps;
                temps = "";
            }

            String url = "https://export.arxiv.org/api/query?id_list="+tempid;

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            APIHandle apiHandle = new APIHandle();
                            apiHandle.loadFavs(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i(TAG, "onErrorResponse: Network Error");
                }
            });
            // Add the request to the RequestQueue.
            stringRequest.setTag(arXiv.requestTAGFav);
            arXiv.mRequestQueue.add(stringRequest);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: oo");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: pp");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        System.gc();
    }
}