//package com.example.yt.newsapplication;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//
///**
// * Created by yt on 03-Mar-18.
// */
//
//public class NewsAdapter extends ArrayAdapter<News> {
//
//    private ArrayList<News> news;
//    public NewsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<News> objects) {
//        super(context, resource, objects);
//        news = objects;
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
//        News news = getItem(position);
//
//    }
//
//    @Nullable
//    @Override
//    public News getItem(int position) {
//        return super.getItem(position);
//    }
//}
