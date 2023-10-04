package vn.edu.usth.arxiv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


public class Get_password_Fragment extends Fragment {

    private final String TAG = "Get Password";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_get_password, container, false);
        Button btn_cancle = view.findViewById(R.id.btn_cancle);
        Button btn_search_pass = view.findViewById(R.id.btn_search_pass);

        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        LoginActivity.onLogin = false;

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LoginActivity.onLogin = true;
    }
}