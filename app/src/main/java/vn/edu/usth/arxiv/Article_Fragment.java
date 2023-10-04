package vn.edu.usth.arxiv;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public class Article_Fragment extends Fragment {

    public static String subDate = "%%%";
    public static String artTit = "###";
    public static String auName = "&&&";
    public static String absText = "***";
    public static String trackID = "";
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);

        TextView submitDate = view.findViewById(R.id.submit_date);
        TextView articleTitle = view.findViewById(R.id.article_title);
        TextView authorsName = view.findViewById(R.id.author);
        TextView abstractText = view.findViewById(R.id.description);

        submitDate.setText(subDate);
        articleTitle.setText(artTit);
        authorsName.setText(auName);
        abstractText.setText(absText);

        mContext = getContext();

        ImageView favorite = view.findViewById(R.id.favorite_button);
        Button download = view.findViewById(R.id.download_button);

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If not login yet
                Intent login = new Intent(getActivity().getApplicationContext(),LoginActivity.class);
                startActivity(login);

                // TODO: logined
                switch (favorite.getTag().toString()) {
                    case "unfavorite":
                        favorite.setImageResource(R.drawable.baseline_favorite_24);
                        favorite.setTag("favorite");
                        break;
                    case "favorite":
                        favorite.setImageResource(R.drawable.baseline_favorite_border_24);
                        favorite.setTag("unfavorite");
                        break;
                }
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadPDF();
            }
        });

        return view;
    }

    private void downloadPDF(){
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse("https://export.arxiv.org/pdf/" + trackID));
        request.setTitle("PDF Download");
        request.setDescription("Downloading the PDF file");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"arXivPDF/pdf_file.pdf");
        DownloadManager manager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
        Toast.makeText(mContext, "Download Started", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        DocListActivity.onDocList = true;
    }
}