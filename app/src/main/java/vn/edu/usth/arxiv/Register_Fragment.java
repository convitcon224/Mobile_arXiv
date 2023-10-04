package vn.edu.usth.arxiv;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Register_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        TextView back_login = view.findViewById(R.id.back_login);

        back_login.setOnClickListener(new View.OnClickListener() {
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