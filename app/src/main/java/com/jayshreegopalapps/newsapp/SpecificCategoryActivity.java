package com.jayshreegopalapps.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SpecificCategoryActivity extends AppCompatActivity {
    RecyclerView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<NewsModel> newsList = new ArrayList<>();
    CustomNewsAdapter arrayAdapter;
    String mCategory;
    private VerticalViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_category);
        listView = findViewById(R.id.news_list_view);

        extractBundle();
//        downloadNews();
//        viewPager = findViewById(R.id.view_pager);
//        adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        viewPager.setAdapter(adapter);

    }

    private void extractBundle() {
        mCategory = getIntent().getStringExtra("category");
    }
}

//    private void downloadNews() {
//        DownloadNewsAsyncTask task = new DownloadNewsAsyncTask(SpecificCategoryActivity.this, new DownloadNewsAsyncTask.MyListener() {
//            @Override
//            public void mylistener(String[] s) {
//                if(s!=null) {
//                    try{
//                        JSONObject object = new JSONObject(s);
////                        int totalResults = (object.getInt("length"));
//                        JSONArray array = object.getJSONArray("articles");
//                        for(int i = 0;i < array.length();i ++) {
//                            JSONObject object1 = array.getJSONObject(i);
//                            String author = object1.getString("author");
//                            String title = object1.getString("title");
//                            String description = object1.getString("description");
//                            String url = object1.getString("url");
//                            String imageUrl = object1.getString("urlToImage");
//                            String content = object1.getString("content");
//
//                            NewsModel newsModel = new NewsModel(author, title, description, url, imageUrl, content);
//                            newsList.add(newsModel);
//                            arrayList.add(title);
//
//                        }
//                        arrayAdapter = new CustomNewsAdapter(SpecificCategoryActivity.this,  newsList);
//                        listView.setAdapter(arrayAdapter);
//                        listView.setLayoutManager(new LinearLayoutManager(SpecificCategoryActivity.this));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                else {
//                    Toast.makeText(SpecificCategoryActivity.this, "No news Found", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        task.execute(mCategory);
//    }
//}
