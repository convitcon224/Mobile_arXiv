package vn.edu.usth.arxiv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Article_RecyclerViewAdapter extends RecyclerView.Adapter<Article_RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<ArticleModel> articleModels;

    public Article_RecyclerViewAdapter(Context context, ArrayList<ArticleModel> articleModels) {
        this.context = context;
        this.articleModels = articleModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvTitle.setText(articleModels.get(position).getArticleTitle());
        holder.tvDate.setText(articleModels.get(position).getArticleDate());
        holder.tvAuthor.setText(articleModels.get(position).getArticleAuthor());
    }

    @Override
    public int getItemCount() {
        return articleModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle, tvDate, tvAuthor;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.titleText);
            tvDate = itemView.findViewById(R.id.dateText);
            tvAuthor = itemView.findViewById(R.id.authorText);
        }
    }
}
