package com.jayshreegopalapps.newsapp;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class CustomNewsAdapter extends RecyclerView.Adapter<CustomNewsAdapter.MyHolder> {
    Context context;
    ArrayList<NewsModel> arrayList;
    public CustomNewsAdapter(Context context, ArrayList<NewsModel> arrayList) {
            this.context = context;
            this.arrayList =arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_news_recycler_view, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {

        URL url = null;
        try {
            if(arrayList.get(position).imageUrl != null) {
//                url = new URL(arrayList.get(position).imageUrl);
//                final URL finalUrl = url;
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {

//                            Bitmap myBitmap = BitmapFactory.decodeStream(finalUrl.openConnection().getInputStream());
//                            Bitmap imageRounded = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), myBitmap.getConfig());
//                            Canvas canvas = new Canvas(imageRounded);
//                            Paint mpaint = new Paint();
//                            mpaint.setAntiAlias(true);
//                            mpaint.setShader(new BitmapShader(myBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
//                            canvas.drawRoundRect((new RectF(0, 0, myBitmap.getWidth(), myBitmap.getHeight())), 20, 10, mpaint); // Round Image Corner 100 100 100 100
//                            holder.imageView.setImageBitmap(imageRounded);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Picasso.get().load(arrayList.get(position).imageUrl).transform(new RoundedTransformation(40, 0)).into(holder.imageView);

//        Bitmap mbitmap=((BitmapDrawable) context.getResources().getDrawable(R.drawable.babu)).getBitmap();
//        BitmapFactory.decodeStream(new Input)


        final NewsModel model = arrayList.get(position);
//        Picasso.get().load(model.imageUrl).placeholder(R.drawable.loading_image_placholder).error(R.drawable.error_loading_image).into(holder.imageView);
        holder.title.setText(model.title);
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.getUrl().equals("")) return;
                Intent i = new Intent(context, BrowserActvity.class);
                i.putExtra("url", model.getUrl());
                i.putExtra("NewsModel", model);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.custom_news_recycler_view_image);
            title = itemView.findViewById(R.id.custom_news_recycler_view_text);
        }
    }
}
