package com.example.yt.newsapplication;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.yt.newsapplication.NewsUtils.n;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,LoaderCallbacks {

    String url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=ecc20d8c7e6c4779b493de6b45ec3814";
    TextView title,description,source;
    LinearLayout noConnection;
    ImageView imageView;
    ProgressBar progressBar;
    int id=0;
    int newsId=1;
    float x1=0, x2=0, y1=0, y2=0;
    int minDist = 100;
    News n;
    LoaderManager loaderManager;
    boolean webViewON,load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        title = (TextView)findViewById(R.id.title);
        description = (TextView)findViewById(R.id.description);
        source = (TextView)findViewById(R.id.url);
        imageView = (ImageView)findViewById(R.id.image);
        progressBar=(ProgressBar)findViewById(R.id.progress);
        noConnection=(LinearLayout) findViewById(R.id.noConnection);

        progressBar.setVisibility(View.VISIBLE);

        loaderManager = getLoaderManager();
        connection();

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.mainActivity);
        linearLayout.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(load) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        x1 = event.getX();
                        y1 = event.getY();
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        x2 = event.getX();
                        y2 = event.getY();


                        if (x1 - x2 > minDist) {
                            swipeLeft();
                        } else if (x2 - x1 > minDist) {
                            swipeRight();
                        }
                    }
                }
                return true;
            }
        });
            source.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(webViewON) {
                        load=false;
                        Intent myIntent = new Intent(MainActivity.this, webView.class);
                        myIntent.putExtra("source", source.getText().toString()); //Optional parameters
                        MainActivity.this.startActivity(myIntent);
                    }
                    Toast.makeText(getApplicationContext(),"source",Toast.LENGTH_SHORT).show();
                }
            });
    }

    public void swipeLeft(){
        if(id<=18) {
            id = id + 1;
        } else{
            id=0;
        }
        Toast toast = Toast.makeText(getApplicationContext(), "swiped left", Toast.LENGTH_SHORT);
        toast.show();
        updateView(id);
    }

    public void swipeRight(){
        if(id>=1) {
            id = id - 1;
        }else {
            id=19;
        }
        Toast toast = Toast.makeText(getApplicationContext(), "swiped right", Toast.LENGTH_SHORT);
        toast.show();
        updateView(id);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if(id == R.id.action_refresh){
            connection();
            Toast.makeText(getApplicationContext(),"Refreshed",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id == R.id.topHeadlines) {
            url="https://newsapi.org/v2/top-headlines?country=in&apiKey=ecc20d8c7e6c4779b493de6b45ec3814";
        } else if (id == R.id.business) {
            url="https://newsapi.org/v2/top-headlines?country=in&apiKey=ecc20d8c7e6c4779b493de6b45ec3814&category=business";
        } else if (id == R.id.entertainment) {
            url="https://newsapi.org/v2/top-headlines?country=in&apiKey=ecc20d8c7e6c4779b493de6b45ec3814&category=entertainment";
        }  else if (id == R.id.general) {
            url="https://newsapi.org/v2/top-headlines?country=in&apiKey=ecc20d8c7e6c4779b493de6b45ec3814&category=general";
        } else if (id == R.id.technology) {
            url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=ecc20d8c7e6c4779b493de6b45ec3814&category=technology";
        } else if (id == R.id.sports) {
            url="https://newsapi.org/v2/top-headlines?country=in&apiKey=ecc20d8c7e6c4779b493de6b45ec3814&category=sports";
        }  else if (id == R.id.health) {
            url="https://newsapi.org/v2/top-headlines?country=in&apiKey=ecc20d8c7e6c4779b493de6b45ec3814&category=health";
        } else if (id == R.id.science) {
            url="https://newsapi.org/v2/top-headlines?country=in&apiKey=ecc20d8c7e6c4779b493de6b45ec3814&category=science";
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        load = false;
        connection();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        progressBar.setVisibility(View.VISIBLE);
        return new NewsLoader(this,url);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        //final NewsUtils newsUtils = null;
        //Todo update Views
        progressBar.setVisibility(View.GONE);
        updateView(id);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    void updateView(int i){
            n = NewsUtils.news.get(i);
            title.setText(n.getTitle());
            description.setText(n.getDesc());
            source.setText(n.getSource());
            Picasso.with(getApplicationContext())
                    .load(n.getImg())
                    .error(R.drawable.ic_menu_camera)
                    .into(imageView);
            webViewON=true;
            load=true; //Used for stopping the swipe of unloaded news
    }
    public void connection(){
        if(gotConnection()){
            noConnection.setVisibility(View.GONE);
            loaderManager.initLoader(newsId,null,MainActivity.this);
            newsId++;
        }else {
            progressBar.setVisibility(View.GONE);
            noConnection.setVisibility(View.VISIBLE);
        }
    }
    private boolean gotConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
