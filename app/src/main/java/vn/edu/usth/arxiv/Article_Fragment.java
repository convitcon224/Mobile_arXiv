package vn.edu.usth.arxiv;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


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
        ImageView download = view.findViewById(R.id.download_button);

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If not login yet
                Intent login = new Intent(getActivity().getApplicationContext(),LoginActivity.class);
                startActivity(login);

                // TODO: logined
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

//    public void downloadPDF(){
//        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
////        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
////        + "/arXivPDF/"
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
////                    String URL = "https://export.arxiv.org/pdf/" + trackID;
////                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL)));
//                File dir = new File(path,"/arXivPDF");
//                if (!dir.exists()){
//                    dir.mkdirs();
//                }
//
//                try {
//                    URL u = new URL("https://export.arxiv.org/pdf/" + trackID);
//                    HttpURLConnection c = (HttpURLConnection) u.openConnection();
//                    c.setRequestMethod("GET");
//                    c.setDoOutput(true);
//                    c.connect();
//                    String pdfName = artTit.substring(0,7)+"_"+auName.substring(0,7)+".pdf";
//                    FileOutputStream f = new FileOutputStream(new File(dir,pdfName.replace(" ","").
//                            replace("[","").replace("]","")));
//
//
//                    InputStream in = c.getInputStream();
//
//                    byte[] buffer = new byte[1024];
//                    int len1 = 0;
//                    while ( (len1 = in.read(buffer)) > 0 ) {
//                        f.write(buffer,0, len1);
//                    }
//                    f.close();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//
//            }
//        });
//        if ( t.getState() == Thread.State.NEW ){
//            t.start();
//        }
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        DocListActivity.onDocList = true;
    }
}