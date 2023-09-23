package vn.edu.usth.arxiv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    private final String TAG = "Search Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().hide();

        Spinner spinner = findViewById(R.id.spinner1);
        String[] category = {"Title", "Author", "Keywords"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                category
        );
        spinner.setAdapter(adapter);

        // TODO
//        androidx.appcompat.widget.AppCompatButton search = findViewById(R.id.seachbutton);
//
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                searchList search_list = new searchList();
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_id, search_list).commit();
//            }
//        });
    }

    public void pressedDateButton(View view) {
        Toast.makeText(this, "Date", Toast.LENGTH_SHORT).show();
    }


    public void searchOnClick(View view) {
        TextView tit = findViewById(R.id.field1);

        DocListActivity temp = new DocListActivity();
        temp.title = tit.getText().toString();
        temp = null;

        Intent docl = new Intent(getApplicationContext(), DocListActivity.class);
        startActivity(docl);
        Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
    }
}