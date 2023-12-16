package com.example.mobile_final_team5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ArticleDetailsActivity extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_edit_create);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String subtitle = intent.getStringExtra("subtitle");
        String category = intent.getStringExtra("category");
        String abstractText = intent.getStringExtra("abstract");
        String body = intent.getStringExtra("body");


        TextView titleTextView = findViewById(R.id.article_title);
        TextView subtitleTextView = findViewById(R.id.article_subtitle);
        TextView categoryTextView = findViewById(R.id.article_category);
        TextView abstractTextView = findViewById(R.id.article_abstract);
        TextView bodyTextView = findViewById(R.id.article_body);

        titleTextView.setText(title);
        subtitleTextView.setText(subtitle);
        categoryTextView.setText(category);
        abstractTextView.setText(abstractText);
        bodyTextView.setText(body);

    }


}
