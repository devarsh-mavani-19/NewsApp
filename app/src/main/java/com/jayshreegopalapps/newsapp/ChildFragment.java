package com.jayshreegopalapps.newsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ChildFragment extends Fragment {
    ImageView imageView;
    TextView title;
    public ChildFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_child, container, false);
        title = v.findViewById(R.id.news_title);
        imageView = v.findViewById(R.id.news_image);

        Bundle b = getArguments();
        title.setText("hello");
        imageView.setImageResource(R.drawable.ic_launcher_background);
        return v;
    }
}
