package com.cainwong.flowdemo.rss;

import android.net.Uri;

import com.cainwong.flowdemo.models.Review;
import com.cainwong.flowdemo.models.models.Item;
import com.cainwong.flowdemo.models.models.RssRoot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by cwong on 3/21/16.
 */
@Singleton
public class PitchforkRssClient {

    private static final String SCHEME = "http";
    private static final String AUTHORITY = "pitchfork.com";
    public static final String RSS_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss Z";

    private final PitchforkRssService service;


    @Inject
    public PitchforkRssClient(OkHttpClient okHttpClient) {
        Uri uri = (new Uri.Builder()).scheme(SCHEME)
                .authority(AUTHORITY).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(uri.toString())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(okHttpClient)
                .build();
        service = retrofit.create(PitchforkRssService.class);
    }


    public Observable<List<Review>> getAlbumReviews(){
        return service.getAlbumReviews().map(new RssRootToReviewsListMapper());
    }

    interface PitchforkRssService {
        @GET("/rss/reviews/albums")
        Observable<RssRoot> getAlbumReviews();
    }

    static class RssRootToReviewsListMapper implements Func1<RssRoot, List<Review>> {
        @Override
        public List<Review> call(RssRoot rssRoot) {
            List<Review> reviews = null;
            if(rssRoot !=null && rssRoot.channel!=null && rssRoot.channel.items!=null){
                SimpleDateFormat sdf = new SimpleDateFormat(RSS_DATE_FORMAT);
                reviews = new ArrayList(rssRoot.channel.items.size());
                for(Item item: rssRoot.channel.items){
                    Review review = new Review();
                    review.id = item.guid;
                    String title = item.title.trim();
                    review.title = title;
                    String[] s = title.split(": ", 2);
                    review.artist = s[0].trim();
                    review.album = s[1].trim();
                    review.imgUrl = item.enclosure.url;
                    try {
                        review.postedTimestamp = sdf.parse(item.pubDate).getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    review.body = item.description;
                    reviews.add(review);
                }
            }
            return reviews;
        }
    }
}
