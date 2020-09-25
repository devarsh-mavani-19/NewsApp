package com.jayshreegopalapps.newsapp;

import android.content.Context;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadNewsAsyncTask extends AsyncTask<String, Void, ArrayList<NewsModel>> {
    Context context;
    MyListener myListener;
    public DownloadNewsAsyncTask(Context context, MyListener myListener) {
        this.myListener= myListener;
        this.context =context;
    }
    @Override
    protected ArrayList<NewsModel> doInBackground(String... strings) {
        ArrayList<NewsModel> arrayList = new ArrayList<>();
        for(String category : Constants.categories) {
            int newsCount = 0;
            String url = "https://inshorts.com/en/read/" + category;
            try{
                Document document = Jsoup.connect(url).get();
                Elements elements = document.select(".news-card");
                for(Element element : elements) {
                    if(newsCount >= 5) {
                        break;
                    }
                    String title;
                    String body;
                    String imgUrl;
                    String newsSource;

                    boolean titleBoolean = element.select("span[itemprop=headline]").isEmpty();
                    if(!titleBoolean) {
                        title = element.select("span[itemprop=headline]").get(0).ownText();
                    }
                    else {
                        title = "";
                    }
                    boolean bodyBoolean = element.select("div[itemprop=articleBody]").isEmpty();
                    if(!bodyBoolean) {
                        body = element.select("div[itemprop=articleBody]").get(0).ownText();
                    }
                    else{
                        body = "";
                    }
                    boolean imgurlBoolean =element.select("div[class=news-card-image]").isEmpty();
                    if(!imgurlBoolean) {
                        imgUrl = element.select("div[class=news-card-image]").get(0).attr("style").replace("background-image: url('", "");
                        imgUrl = imgUrl.substring(0, imgUrl.length() - 2);
                    }
                    else{
                        imgUrl = "";
                    }
                    boolean newsSourceBoolean = element.select("a[class=source]").isEmpty();
                    if (!newsSourceBoolean) {
                        newsSource = element.select("a[class=source]").get(0).attr("href");
                    }
                    else{
                        newsSource = "";
                    }


                    NewsModel m = new NewsModel("", title, body, newsSource, imgUrl, "", category);

                    arrayList.add(m);
                    newsCount++;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(arrayList.isEmpty()) {
            return null;
        }
        else{
            return arrayList;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<NewsModel> arrayList) {
        this.myListener.mylistener(arrayList);
    }

    public interface MyListener{
        void mylistener(ArrayList<NewsModel> arrayList);
    }
}
