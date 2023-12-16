package com.example.mobile_final_team5;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.mobile_final_team5.Article;
import com.example.mobile_final_team5.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Array;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;
import com.example.mobile_final_team5.ArticleAdapter;

public class ArticleThread implements Runnable {
    private MainActivity context;
    private ModelManager modelManager;
    private java.net.HttpURLConnection HttpURLConnection;
    private ArticleAdapter adapter;


    public ArticleThread(MainActivity context) {
        this.context = context;
        this.modelManager = modelManager;
    }

    @Override
    public void run() {
        try {
            // Update UI, initialize download

            TrustModifier.relaxHostChecking((HttpURLConnection));

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    context.prepareUIForDownload();
                }
            });

            Thread.sleep(2000);
            String articlesJsonList = Utils.getURLText("https://sanger.dia.fi.upm.es/pui-rest-news/articles");
            Log.i(MainActivity.class.getName(), "Connected to server");
            Log.i(MainActivity.class.getName(), articlesJsonList);
            Log.i(MainActivity.class.getName(), "Raw Server Response: " + articlesJsonList);



            final Article[] articles = gson.fromJson(articlesJsonList, Article[].class);
            Log.i(MainActivity.class.getName(), String.valueOf(articles[0]));


            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    context.showResultsForDownload(Arrays.asList(articles));
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}