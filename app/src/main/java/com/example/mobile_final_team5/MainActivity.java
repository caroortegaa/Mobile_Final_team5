package com.example.mobile_final_team5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private ArticleAdapter adapter;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DOWNLOAD HERE THE LIST OF ARTICLES FROM THE SERVER
        ArticleThread myTask = new ArticleThread(this);
        Thread thread = new Thread(myTask);
        thread.start();
    }



    protected void prepareUIForDownload() {
        // You may want to show a progress bar or perform any UI setup before downloading
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
    }

    protected void showResultsForDownload(List<Article> articles) {
        // Hide progress bar or perform any UI cleanup
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        // Show the list of articles in the ListView
        ListView lv = findViewById(R.id.article_list);
        adapter = new ArticleAdapter(this, articles);
        lv.setAdapter(adapter);




        // category buttons
        Button allButton = findViewById(R.id.button_all);
        Button technologyButton = findViewById(R.id.button_tech);
        Button nationalButton = findViewById(R.id.button_national);
        Button sportsButton = findViewById(R.id.button_sports);
        Button economyButton = findViewById(R.id.button_economy);

        allButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCategoryFilter("All", articles);
            }
        });

        technologyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCategoryFilter("Technology", articles);
            }
        });

        nationalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCategoryFilter("National", articles);
            }
        });

        sportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCategoryFilter("Sports", articles);
            }
        });

        economyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCategoryFilter("Economy", articles);
            }
        });
    }
    public void updateCategoryFilter(String category, List<Article> articles) {
        // Update the selected category in the adapter
        adapter.setSelectedCategory(category);

        // Filter and update the adapter's data
        adapter.filterDataByCategory();

        ListView lv = findViewById(R.id.article_list);
        lv.setAdapter(adapter);
    }

}
