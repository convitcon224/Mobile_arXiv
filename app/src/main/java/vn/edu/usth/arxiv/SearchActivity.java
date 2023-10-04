package vn.edu.usth.arxiv;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
        TextView detail = findViewById(R.id.field1);
        Spinner mySpinner = (Spinner) findViewById(R.id.spinner1);
        String text = mySpinner.getSelectedItem().toString();

//        Intent docl = new Intent(getApplicationContext(), DocListActivity.class);
//        startActivity(docl);
//        Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();

        if(detail.getText().toString().length() < 1){
            Toast.makeText(this, "Cannot query nothing", Toast.LENGTH_SHORT).show();
        } else {
            DocListActivity.title = text+" "+detail.getText().toString();
            String[] message= {text,detail.getText().toString()};
            Intent intent=new Intent();
            intent.putExtra("MESSAGE",message);
            setResult(AppCompatActivity.RESULT_OK, intent);
            finish();//finishing activity
        }
    }

    @Override
    public void onBackPressed() {
        setResult(AppCompatActivity.RESULT_CANCELED);
        super.onBackPressed();
    }
}