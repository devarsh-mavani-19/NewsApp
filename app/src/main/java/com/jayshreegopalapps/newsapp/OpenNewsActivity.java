package com.jayshreegopalapps.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class OpenNewsActivity extends AppCompatActivity {
    ImageView imageView;
    TextView title, content, description;
    NewsModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_news);

        imageView = findViewById(R.id.news_image);
        title = findViewById(R.id.news_title);
//        content = findViewById(R.id.news_content);
//        description = findViewById(R.id.news_description);

        model = (NewsModel) getIntent().getSerializableExtra("NewsModel");
        if (model != null) {
            title.setText(model.title);
            content.setText(model.content);
            description.setText(model.description);
            Picasso.get().load(model.imageUrl).into(imageView);
        }

    }
}
