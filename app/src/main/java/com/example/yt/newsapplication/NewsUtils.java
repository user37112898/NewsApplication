package com.example.yt.newsapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yt on 03-Mar-18.
 */

public class NewsUtils {

    public static String url[]=new String[10000];
    public static String urlToImage[]=new String[10000];
    public static String description[]=new String[10000];
    public static String title[]=new String[10000];
    public static int n;

    static String json=null;

    public static ArrayList<News> news;
    public static ArrayList<News> fetchNews(String url){
        news = new ArrayList<News>();
        try {
            json = fetchJson(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return extractFromJson(json);
    }

    //From OkHttpClient API
    public static String fetchJson(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static ArrayList<News> extractFromJson(String json){
        try {
            JSONObject root = new JSONObject(json);
            n = root.getInt("totalResults");
            JSONArray articles = root.getJSONArray("articles");
            for(int i=0;i<=n;i++){
                JSONObject currentObject = articles.getJSONObject(i);
                title[i] = currentObject.getString("title");
                description[i] = currentObject.getString("description");
                url[i] = currentObject.getString("url");
                urlToImage[i] = currentObject.getString("urlToImage");
                String publishedAt = currentObject.getString("publishedAt");
                news.add(new News(urlToImage[i],title[i],description[i],url[i]));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return news;
    }
}
