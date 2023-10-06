package vn.edu.usth.arxiv;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    public final DialogNCancelable loadingdialog = new DialogNCancelable(LoginActivity.this);

    private final String TAG = "Login Activity";
    public static boolean onLogin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

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
                                loadingdialog.dismissdialog();
                                if (task.isSuccessful()) {
//                                    loadingdialog.dismissdialog();
                                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT ).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Log.i("TAG test", "onComplete: "+ user.getUid());
//                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                    startActivity(intent);
                                    finish();
                                } else {
//                                    loadingdialog.dismissdialog();
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
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