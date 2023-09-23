package vn.edu.usth.arxiv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

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