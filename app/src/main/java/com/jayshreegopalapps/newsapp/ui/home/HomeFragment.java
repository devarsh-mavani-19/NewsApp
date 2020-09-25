package com.jayshreegopalapps.newsapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jayshreegopalapps.newsapp.BrowserActvity;
import com.jayshreegopalapps.newsapp.CustomNewsAdapter;
import com.jayshreegopalapps.newsapp.DownloadHeadlines;
import com.jayshreegopalapps.newsapp.DownloadNewsAsyncTask;
import com.jayshreegopalapps.newsapp.NewsModel;
import com.jayshreegopalapps.newsapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<NewsModel> arrayList = new ArrayList<>();
    CustomNewsAdapter adapter;
    private HomeViewModel homeViewModel;
    ArrayList<NewsModel> trendingList = new ArrayList<>();
    CardView cardView1, cardView2, cardView3, cardView4, cardView5, cardView6;
    ImageView cImage1, cImage2, cImage3, cImage4, cImage5, cImage6;
    TextView cText1, cText2, cText3, cText4, cText5, cText6;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //init views
        recyclerView = root.findViewById(R.id.recycler_view_news);

        cardView1 = root.findViewById(R.id.card1);
        cardView2 = root.findViewById(R.id.card2);
        cardView3 = root.findViewById(R.id.card3);
        cardView4 = root.findViewById(R.id.card4);
        cardView5 = root.findViewById(R.id.card5);
        cardView6 = root.findViewById(R.id.card6);

        cImage1 = root.findViewById(R.id.cardimg1);
        cImage2 = root.findViewById(R.id.cardimg2);
        cImage3 = root.findViewById(R.id.cardimg3);
        cImage4 = root.findViewById(R.id.cardimg4);
        cImage5 = root.findViewById(R.id.cardimg5);
        cImage6 = root.findViewById(R.id.cardimg6);

        cText1 = root.findViewById(R.id.cardtextview1);
        cText2 = root.findViewById(R.id.cardtextview2);
        cText3 = root.findViewById(R.id.cardtextview3);
        cText4 = root.findViewById(R.id.cardtextview4);
        cText5 = root.findViewById(R.id.cardtextview5);
        cText6 = root.findViewById(R.id.cardtextview6);

        refreshPage();
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }

    private void refreshPage() {
        arrayList.clear();
        trendingList.clear();
        fetchNews();
        fetchHeadlines();
    }

    private void fetchHeadlines() {
        DownloadHeadlines task = new DownloadHeadlines(getContext(), new DownloadHeadlines.MyListener() {
            @Override
            public void mylistener(ArrayList s) {
                if(s!=null) {
                    try{

                        trendingList = (ArrayList<NewsModel>) s.clone();

                        Picasso.get().load(trendingList.get(0).getImageUrl()).into(cImage1);
                        Picasso.get().load(trendingList.get(1).getImageUrl()).into(cImage2);
                        Picasso.get().load(trendingList.get(2).getImageUrl()).into(cImage3);
                        Picasso.get().load(trendingList.get(3).getImageUrl()).into(cImage4);
                        Picasso.get().load(trendingList.get(4).getImageUrl()).into(cImage5);
                        Picasso.get().load(trendingList.get(5).getImageUrl()).into(cImage6);

                        cText1.setText(trendingList.get(0).getTitle());
                        cText2.setText(trendingList.get(1).getTitle());
                        cText3.setText(trendingList.get(2).getTitle());
                        cText4.setText(trendingList.get(3).getTitle());
                        cText5.setText(trendingList.get(4).getTitle());
                        cText6.setText(trendingList.get(5).getTitle());

                        cardView1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(getContext(), BrowserActvity.class);
                                i.putExtra("url", trendingList.get(0).getUrl());
                                startActivity(i);
                            }
                        });
                        cardView2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(getContext(), BrowserActvity.class);
                                i.putExtra("url", trendingList.get(1).getUrl());
                                startActivity(i);
                            }
                        });
                        cardView3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(getContext(), BrowserActvity.class);
                                i.putExtra("url", trendingList.get(2).getUrl());
                                startActivity(i);
                            }
                        });
                        cardView4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(getContext(), BrowserActvity.class);
                                i.putExtra("url", trendingList.get(3).getUrl());
                                startActivity(i);
                            }
                        });
                        cardView5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(getContext(), BrowserActvity.class);
                                i.putExtra("url", trendingList.get(4).getUrl());
                                startActivity(i);
                            }
                        });
                        cardView6.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(getContext(), BrowserActvity.class);
                                i.putExtra("url", trendingList.get(5).getUrl());
                                startActivity(i);
                            }
                        });


                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        task.execute();

    }

    private void fetchNews() {
        DownloadNewsAsyncTask task = new DownloadNewsAsyncTask(getContext(), new DownloadNewsAsyncTask.MyListener() {
            @Override
            public void mylistener(ArrayList<NewsModel> list) {
                if(list!=null) {
                    arrayList = (ArrayList<NewsModel>) list.clone();
                    adapter = new CustomNewsAdapter(getContext(), arrayList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                }
                else {
                    Toast.makeText(getContext(), "No news Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
        task.execute("business");
    }

}