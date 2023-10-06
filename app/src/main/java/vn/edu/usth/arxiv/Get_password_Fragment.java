package vn.edu.usth.arxiv;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;


public class Get_password_Fragment extends Fragment {

    private final String TAG = "Get Password";

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_get_password, container, false);
        LoginActivity.onLogin = false;

        Button btn_cancle = view.findViewById(R.id.btn_cancle);
        Button btn_search_pass = view.findViewById(R.id.btn_search_pass);

        EditText editTextEmail = view.findViewById(R.id.input_emailReset);


        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        btn_search_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail = editTextEmail.getText().toString().trim();
                if (!TextUtils.isEmpty(strEmail)) {
                    ResetPassword(strEmail);
                } else {
                    editTextEmail.setError("Email field can't be empty");
                }
            }
        });

        return view;
    }

    private void ResetPassword(String strEmail) {
//        progressBar.setVisibility(View.VISIBLE);
//        buttonReset.setVisibility(View.INVISIBLE);

        mAuth.sendPasswordResetEmail(strEmail)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Reset Password link has been sent to your registered Email", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(Forgot.this, Login.class);
//                        startActivity(intent);
//                        finish();
                        getActivity().onBackPressed();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error :- " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        progressBar.setVisibility(View.INVISIBLE);
//                        buttonReset.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LoginActivity.onLogin = true;
    }
}