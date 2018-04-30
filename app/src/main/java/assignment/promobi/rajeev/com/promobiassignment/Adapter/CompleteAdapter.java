package assignment.promobi.rajeev.com.promobiassignment.Adapter;

/**
 * Created by rspl-rajeev on 20/4/18.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import assignment.promobi.rajeev.com.promobiassignment.Activity.ArticleActivity;
import assignment.promobi.rajeev.com.promobiassignment.Activity.MainActivity;
import assignment.promobi.rajeev.com.promobiassignment.Models.Article;
import assignment.promobi.rajeev.com.promobiassignment.R;



public class CompleteAdapter extends RecyclerView.Adapter<CompleteAdapter.ViewHolder> implements Filterable
{


    List<Article> articleList;

    Context context;



    public CompleteAdapter(Context c, List<Article> articles) {

        this.articleList = articles;
        this.context=c;

    }

    //Clear articles
    public void clearArticles(){
        articleList.clear();
    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public CompleteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_details, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ImageView imageView=holder.imageView;
        Article article=articleList.get(position);
        holder.bookName.setText(articleList.get(position).getHeadline().getMain());
        holder.bookAuther.setText(articleList.get(position).getHeadline().getPrintHeadline());
        if(article.getMultimedia() != null && article.getMultimedia().size() > 0) {
            String imageUrl = article.getMultimedia().get(1).getUrl();
            if (TextUtils.isEmpty(imageUrl)) {
                Glide.with(context).load("http://www.vishmax.com/en/innovattive-cms/themes/themax-theme-2015/images/no-image-found.gif")
                        .placeholder(R.mipmap.ic_launcher)
                        .into(holder.imageView);}
            else {
                Glide.with(context).load(imageUrl)
                        .placeholder(R.mipmap.ic_launcher)
                        .into(holder.imageView);
            }
        }


    }






    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public void clearAllRows() {
        articleList.clear();

    }



    @Override
    public Filter getFilter() {
        return null;
    }




    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView bookName;
        public ImageView imageView;

        private TextView bookAuther;


        public ViewHolder(View convertView) {
            super(convertView);
            convertView.setOnClickListener(this);
            bookName = (TextView)convertView.findViewById(R.id.tv_bookname);
            bookAuther = (TextView)convertView.findViewById(R.id.tv_bookauther);
            imageView=(ImageView)convertView.findViewById(R.id.bookimageid);


        }




        @Override
        public void onClick(View view) {

            int position = getLayoutPosition();

            // We can access the data within the views
            Toast.makeText(context, "Loading article...", Toast.LENGTH_SHORT).show();
            Log.e("MYURL",articleList.get(position).getWebUrl());
            Intent i = new Intent(context, ArticleActivity.class);
            i.putExtra("webUrl", articleList.get(position).getWebUrl());
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);

        }
    }















}




