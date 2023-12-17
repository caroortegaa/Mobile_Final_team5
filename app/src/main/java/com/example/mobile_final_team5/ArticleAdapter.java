package com.example.mobile_final_team5;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile_final_team5.Article;
import com.example.mobile_final_team5.exceptions.ServerCommunicationError;

import java.util.ArrayList;
import java.util.List;
public class ArticleAdapter extends BaseAdapter {

    private Context ctx;
    private List<Article> data;
    private List<Article> filteredData;
    private String selectedCategory;

    public ArticleAdapter(Context ctx, List<Article> data){
        this.ctx = ctx;
        this.data=data;
        this.selectedCategory = "All";
        this.filteredData = new ArrayList<>(data);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setSelectedCategory(String category) {
        selectedCategory = category;
        filterDataByCategory();
    }
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void filterDataByCategory() {
        Log.d("ArticleAdapter", "Filtering data for category: " + selectedCategory);

        filteredData.clear();

        if ("All".equals(selectedCategory)) {
            filteredData.addAll(data);
        } else {
            for (Article article : data) {
                if (selectedCategory.equals(article.getCategory())) {
                    filteredData.add(article);
                }
            }
        }

        notifyDataSetChanged();// Notify the adapter that the data set has changed
        Log.d("ArticleAdapter", "Filtered data size: " + filteredData.size());
    }
    public void setFilteredData(List<Article> filteredData) {
        this.filteredData = filteredData;
        notifyDataSetChanged();
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            //set layout to the itemrow of the list
            LayoutInflater inflater = ((Activity) ctx).getLayoutInflater();
            view = inflater.inflate(R.layout.article_details, null, true);
        }

        Article articleShown = filteredData.get(i);
        TextView articleTitle = view.findViewById(R.id.article_title);
        articleTitle.setText(articleShown.getTitleText() + "");

        ((TextView) view.findViewById(R.id.article_subtitle)).setText(articleShown.getSubtitleText() + "");
        ((TextView) view.findViewById(R.id.article_category)).setText(articleShown.getCategory() + "");
        //((TextView) view.findViewById(R.id.article_abstract)).setText(articleShown.getAbstractText() + "");
        ((TextView)view.findViewById(R.id.article_abstract)).setText(Html.fromHtml(articleShown.getAbstractText())+"");
       // ((TextView) view.findViewById(R.id.article_body)).setText(articleShown.getBodyText() + "");
        try {
            if(articleShown.getImage() != null) {
                ((ImageView) view.findViewById(R.id.article_image)).setImageBitmap(articleShown.getImage().getBitmap());
            } else{
                ((ImageView) view.findViewById(R.id.article_image)).setImageResource(R.drawable.ic_launcher_background);
            }



        } catch (ServerCommunicationError e) {
            throw new RuntimeException(e);
        }

        Button btnOpenArticleDetails = view.findViewById(R.id.edit_button);
        btnOpenArticleDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, ArticleDetailsActivity.class);
                intent.putExtra("title", articleShown.getTitleText());
                intent.putExtra("subtitle", articleShown.getSubtitleText());
                intent.putExtra("category", articleShown.getCategory());
                intent.putExtra("abstract", articleShown.getAbstractText());
                intent.putExtra("body", articleShown.getBodyText());
                String imageData = null;
                try {
                    imageData = articleShown.getImage().getImage();
                } catch (ServerCommunicationError e) {
                    throw new RuntimeException(e);
                }
                intent.putExtra("imageData", imageData);
                ctx.startActivity(intent);
            }
        });
        return view;

        }

}
