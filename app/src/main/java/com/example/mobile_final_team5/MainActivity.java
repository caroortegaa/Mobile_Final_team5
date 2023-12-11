package com.example.mobile_final_team5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.mobile_final_team5.Article;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DOWNLOAD HERE THE LIST OF ARTICLES FROM THE SERVER
        DownloadNewsListThread myTask = new DownloadNewsListThread(MainActivity.this);
        Thread thread = new Thread(myTask);
        thread.start();

        protected void prepareUIForDownload() {
            ((ProgressBar)findViewById(R.id.progressBar2)).setVisibility(View.VISIBLE);
            ((Button)findViewById(R.id.main_btn_download)).setEnabled(false);
        }

        protected void showResultsForDownload(List<Planet> planets) {
            ListView lv = findViewById(R.id.main_list_planets);
            //Show the list of planets in the listview of ui
            //ArrayAdapter<Planet> adapter = new ArrayAdapter<Planet>(this, android.R.layout.simple_list_item_1, planets);
            PlanetAdapter adapter = new PlanetAdapter(this, planets);
            lv.setAdapter(adapter);
            ((ProgressBar)findViewById(R.id.progressBar2)).setVisibility(View.INVISIBLE);
            ((Button)findViewById(R.id.main_btn_download)).setEnabled(true);
        }
    }
}