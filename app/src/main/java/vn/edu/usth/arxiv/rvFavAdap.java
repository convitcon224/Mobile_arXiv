package vn.edu.usth.arxiv;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class rvFavAdap extends RecyclerView.Adapter<rvFavAdap.MyViewHolder> {
    Context context;
    ArrayList<ArticleModel> articleModels;

    public rvFavAdap(Context context, ArrayList<ArticleModel> articleModels) {
        this.context = context;
        this.articleModels = articleModels;
    }

    @NonNull
    @Override
    public rvFavAdap.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rv_fav_item, parent, false);
        return new rvFavAdap.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull rvFavAdap.MyViewHolder holder, int position) {
        holder.tvIDList.setText(articleModels.get(position).getArticleID());
        holder.tvTitle.setText(articleModels.get(position).getArticleTitle());
        holder.tvDate.setText(articleModels.get(position).getArticleDate());
        holder.tvAuthor.setText(articleModels.get(position).getArticleAuthor());
        holder.tvPosition.setText(articleModels.get(position).getArticlePosition());
    }

    @Override
    public int getItemCount() {
        return articleModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvIDList, tvTitle, tvDate, tvAuthor, tvPosition;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIDList = itemView.findViewById(R.id.idArticleFav);
            tvTitle = itemView.findViewById(R.id.titleTextFav);
            tvDate = itemView.findViewById(R.id.dateTextFav);
            tvAuthor = itemView.findViewById(R.id.authorTextFav);
            tvPosition = itemView.findViewById(R.id.article_positionFav);
        }
    }
}
