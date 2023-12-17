package com.example.mobile_final_team5;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
        String imageData = intent.getStringExtra("imageData");

        ImageView articleImageView = findViewById(R.id.article_image);
        if (imageData != null && !imageData.isEmpty()) {
            byte[] decodedString = Base64.decode(imageData, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            articleImageView.setImageBitmap(decodedByte);
        } else {
            // If there is no image, you can set a default image here
            articleImageView.setImageResource(R.drawable.ic_launcher_background);
        }



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


        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setContentView(R.layout.activity_main); doesnt work bc it only sets the layout to main doesnt do the activity
                finish(); // this is to finish te current activity and reload listview
            }
        });
    }


}
