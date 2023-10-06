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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favorite, container, false);

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
            if (tempTits.contains(",")){
                tempTit = tempTits.substring(0, tempTits.indexOf(","));
                tempTits = tempTits.replace(tempTit, "");
            } else {
                tempTit = tempTits;
                tempTits = "";
            }
            if (tempDats.contains(",")){
                tempDat = tempDats.substring(0, tempDats.indexOf(","));
                tempDats = tempDats.replace(tempDat, "");
            } else {
                tempDat = tempDats;
                tempDats = "";
            }
            if (tempAus.contains(",")){
                tempAu = tempAus.substring(0, tempAus.indexOf(","));
                tempAus = tempAus.replace(tempAu, "");
            } else {
                tempAu = tempAus;
                tempAus = "";
            }
            articleModels.add(new ArticleModel(-1,tempid,"tempTit",
                    "tempDat", "tempAu"));
            Log.i("TAG", "getDocsFav: "+tempid+" "+tempDat+" "+tempTit+" "+tempAu+" "+articleModels.size());
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        articleModels.clear();
        setUpArticleModels();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
//            String[] items = {"About us", "Contact", "Report", "Policy", "Copyright", "Donate", "Reset password", "Log out"};
//            listView = (ListView)view.findViewById(R.id.list_favorite);
//            rvFavAdap adapter = new rvFavAdap(getActivity(),items);
//            listView.setAdapter(adapter);
        } else {
//            String[] items = {"About us", "Contact", "Report", "Policy", "Copyright", "Donate"};
//            listView = (ListView)view.findViewById(R.id.list_favorite);
//            rvFavAdap adapter = new rvFavAdap(getActivity(),items);
//            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("TAG", "onPause: Fav");
    }
}












