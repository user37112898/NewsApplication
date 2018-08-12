package com.example.yt.newsapplication;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;

/**
 * Created by yt on 03-Mar-18.
 */

public class NewsLoader extends AsyncTaskLoader {

    String murl;
    public NewsLoader(Context context,String url) {
        super(context);
        murl=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Object loadInBackground() {
        if(murl==null) {
            return null;
        }else {
            //todo
            return NewsUtils.fetchNews(murl);
        }
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();

    }
}
