package vn.edu.usth.arxiv;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class UploadActivity extends AppCompatActivity {

    private final String TAG = "Upload Activity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        getSupportActionBar().hide();

        Spinner spinner;

        TextView title = findViewById(R.id.title);
        EditText editTitle = findViewById(R.id.editTitle);

        TextView author = findViewById(R.id.author);
        EditText editAuthor = findViewById(R.id.editAuthor);

        TextView description = findViewById(R.id.description);
        EditText editDescription = findViewById(R.id.editDescription);


        editTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editTitle.setHint("");
                    title.setText("Title");
                    title.setTextSize(15);
                } else {
                    editTitle.setHint("Title");
                    title.setText("");
                }
            }
        });

        editAuthor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editAuthor.setHint("");
                    author.setText("Author");
                    author.setTextSize(15);
                } else {
                    editAuthor.setHint("Author");
                    author.setText("");
                }
            }
        });

        editDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editDescription.setHint("");
                    description.setText("Description");
                    description.setTextSize(15);
                } else {
                    editDescription.setHint("Description");
                    description.setText("");
                }
            }
        });

        spinner = findViewById(R.id.categoryList);
        String[] category = {"Choose category", "Math", "Physics", "Biology"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                category
        );
        spinner.setAdapter(adapter);
        Log.i(TAG, "onCreate: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    ActivityResultLauncher<Intent> sActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Uri uri = data.getData();
                    }
                }
            }
    );

    public void openFileDialog(View view) {
        Intent data = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        data.setType("pdf/*");
        data = Intent.createChooser(data, "Choose a file");
        sActivityResultLauncher.launch(data);
    }
}