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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Article_Fragment extends Fragment {

    public static String subDate = "%%%";
    public static String artTit = "###";
    public static String auName = "&&&";
    public static String absText = "***";
    public static String trackID = "";
    private Context mContext;
    private DatabaseReference mDatabase;
    private FirebaseDatabase mInstance;

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

        mInstance = FirebaseDatabase.getInstance();
        mDatabase = mInstance.getReference("users");

        mContext = getContext();

        ImageView favorite = view.findViewById(R.id.favorite_button);
        Button download = view.findViewById(R.id.download_button);

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            if (FavoriteFragment.favorites.contains("null")){
                FavoriteFragment.favorites = FavoriteFragment.favorites.replaceFirst("null","");
                FavoriteFragment.titFav = FavoriteFragment.titFav.replaceFirst("null", "");
                FavoriteFragment.datFav = FavoriteFragment.datFav.replaceFirst("null", "");
                FavoriteFragment.auFav = FavoriteFragment.auFav.replaceFirst("null", "");
                uptoDB();
            }
            if (FavoriteFragment.favorites.contains(trackID)){
                favorite.setImageResource(R.drawable.baseline_favorite_24);
                favorite.setTag("favorite");
            } else {
                favorite.setImageResource(R.drawable.baseline_favorite_border_24);
                favorite.setTag("unfavorite");
            }
        }

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If not login yet
                if (FirebaseAuth.getInstance().getCurrentUser() != null){
                    switch (favorite.getTag().toString()) {
                        case "unfavorite": {
                            favorite.setImageResource(R.drawable.baseline_favorite_24);
                            favorite.setTag("favorite");
                            addToFavorite();
                            break;
                        }
                        case "favorite": {
                            favorite.setImageResource(R.drawable.baseline_favorite_border_24);
                            favorite.setTag("unfavorite");
                            removeFromFavorite();
                            break;
                        }
                    }

                } else {
                    Intent login = new Intent(getActivity().getApplicationContext(),LoginActivity.class);
                    startActivity(login);
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
//    public static String subDate = "%%%";
//    public static String artTit = "###";
//    public static String auName = "&&&";
//    public static String absText = "***";
//    public static String trackID = "";
    private void addToFavorite(){
        FavoriteFragment.favorites += "," + trackID;
        FavoriteFragment.titFav += "," + artTit + "-" + trackID;
        FavoriteFragment.datFav += "," + subDate + "-" + trackID;
        FavoriteFragment.auFav += "," + auName + "-" + trackID;
        uptoDB();
    }

    private void removeFromFavorite(){
        FavoriteFragment.favorites = FavoriteFragment.favorites.replace("," + trackID, "");
        FavoriteFragment.titFav = FavoriteFragment.titFav.replace("," + artTit + "-" + trackID, "");
        FavoriteFragment.datFav = FavoriteFragment.datFav.replace("," + subDate + "-" + trackID, "");
        FavoriteFragment.auFav = FavoriteFragment.auFav.replace("," + auName + "-" + trackID, "");
        uptoDB();
    }

    private void uptoDB(){
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase.child(id).child("favorite").setValue(FavoriteFragment.favorites);
        mDatabase.child(id).child("title").setValue(FavoriteFragment.titFav);
        mDatabase.child(id).child("date").setValue(FavoriteFragment.datFav);
        mDatabase.child(id).child("author").setValue(FavoriteFragment.auFav);
    }


    private void downloadPDF(){
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse("https://export.arxiv.org/pdf/" + trackID));
        request.setTitle("PDF Download");
        request.setDescription("Downloading the PDF file");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"arXivPDF/"+artTit.substring(0,10)+
                "_"+auName.substring(0,10).replace("[","").replace("]","")+".pdf");
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