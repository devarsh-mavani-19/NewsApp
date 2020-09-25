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

public class DownloadHeadlines extends AsyncTask<String, Void, ArrayList> {
    Context context;
    MyListener myListener;
    public DownloadHeadlines(Context context, MyListener myListener) {
        this.myListener= myListener;
        this.context =context;
    }
    @Override
    protected ArrayList<NewsModel> doInBackground(String... strings) {
        ArrayList<NewsModel> arrayList = new ArrayList<>();
            int newsCount = 0;
            String url = "https://inshorts.com/en/read";
            try{
                Document document = Jsoup.connect(url).get();
                Elements elements = document.select(".news-card");
                for(Element element : elements) {
                    if(newsCount >= 6) {
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


                    NewsModel m = new NewsModel("", title, body, newsSource, imgUrl, "", "");

                    arrayList.add(m);
                    newsCount++;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        if(arrayList.isEmpty()) {
            return null;
        }
        else{
            return arrayList;
        }
    }

    @Override
    protected void onPostExecute(ArrayList s) {

        this.myListener.mylistener(s);
    }

    public interface MyListener{
        void mylistener(ArrayList s);
    }
}
