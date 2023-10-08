package vn.edu.usth.arxiv;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class FavoriteFragment extends Fragment {

    private ListView listView;
    private View view;

    public static String favorites;
    public static String titFav;
    public static String datFav;
    public static String auFav;
    public static ArrayList<mDocument> docsFav = new ArrayList<>();
    private ArrayList<ArticleModel> articleModels = new ArrayList<>();
    private rvFavAdap adapter;
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favorite, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        recyclerView = view.findViewById(R.id.mRecyclerViewFav);
        setUpArticleModels();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }

    private void setUpArticleModels() {
//        recyclerView.setVisibility(View.VISIBLE);
//        for(int i = 0; i < docsFav.size(); i++) {
//            articleModels.add(new ArticleModel(i,docsFav.get(i).getId(),docsFav.get(i).getTitle(),
//                    docsFav.get(i).getDate().toString(), docsFav.get(i).getAuthors()));
//        }

        getDocsFav();
        adapter = new rvFavAdap(getActivity(), articleModels);
        recyclerView.setAdapter(adapter);
//        loadingPB.setVisibility(View.INVISIBLE);
    }

    private void getDocsFav(){
        String tempids = favorites;
        String tempTits = titFav;
        String tempDats = datFav;
        String tempAus = auFav;
        String tempid, tempTit, tempDat, tempAu;
        while (!tempids.equals("")) {
            tempids = tempids.replaceFirst(",","");
            tempTits = tempTits.replaceFirst(",","");
            tempDats = tempDats.replaceFirst(",","");
            tempAus = tempAus.replaceFirst(",","");
            if (tempids.contains(",")){
                tempid = tempids.substring(0, tempids.indexOf(","));
                tempids = tempids.replace(tempid, "");
            } else {
                tempid = tempids;
                tempids = "";
            }
//            "," + auName + "-" + trackID
            if (tempTits.contains("-"+tempid+",")){
                tempTit = tempTits.substring(0, tempTits.indexOf("-"+tempid+","));
                tempTits = tempTits.replace(tempTit+"-"+tempid, "");
            } else {
                tempTit = tempTits.replace("-"+tempid,"");
                tempTits = "";
            }
            if (tempDats.contains("-"+tempid+",")){
                tempDat = tempDats.substring(0, tempDats.indexOf("-"+tempid+","));
                tempDats = tempDats.replace(tempDat+"-"+tempid, "");
            } else {
                tempDat = tempDats.replace("-"+tempid,"");
                tempDats = "";
            }
            if (tempAus.contains("-"+tempid+",")){
                tempAu = tempAus.substring(0, tempAus.indexOf("-"+tempid+","));
                tempAus = tempAus.replace(tempAu+"-"+tempid, "");
            } else {
                tempAu = tempAus.replace("-"+tempid,"");
                tempAus = "";
            }
            articleModels.add(new ArticleModel(-1,tempid,tempTit,
                    tempDat, tempAu));
            Log.i("TAG", "getDocsFav: "+tempid+" "+tempDat+" "+tempTit+" "+tempAu+" "+articleModels.size());
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            if (FavoriteFragment.favorites.contains("null")){
                String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FavoriteFragment.favorites = FavoriteFragment.favorites.replaceFirst("null","");
                FavoriteFragment.titFav = FavoriteFragment.titFav.replaceFirst("null", "");
                FavoriteFragment.datFav = FavoriteFragment.datFav.replaceFirst("null", "");
                FavoriteFragment.auFav = FavoriteFragment.auFav.replaceFirst("null", "");
                mDatabase.child(id).child("favorite").setValue(FavoriteFragment.favorites);
                mDatabase.child(id).child("title").setValue(FavoriteFragment.titFav);
                mDatabase.child(id).child("date").setValue(FavoriteFragment.datFav);
                mDatabase.child(id).child("author").setValue(FavoriteFragment.auFav);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        articleModels.clear();
        setUpArticleModels();

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("TAG", "onPause: Fav");
    }
}












