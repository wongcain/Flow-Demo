package com.cainwong.flowdemo.rss;

import android.net.Uri;
import android.util.Log;
import android.widget.ScrollView;

import com.cainwong.flowdemo.models.Review;
import com.cainwong.flowdemo.rss.models.Item;
import com.cainwong.flowdemo.rss.models.RssRoot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;

/**
 * Created by cwong on 3/21/16.
 */
@Singleton
public class PitchforkRssClient {

    private static final String TAG = PitchforkRssService.class.getSimpleName();
    private static final String SCHEME = "http";
    private static final String AUTHORITY = "pitchfork.com";
    public static final String RSS_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss Z";

    private final PitchforkRssService service;
    private final BehaviorSubject<List<Review>> reviewsObservable;
    private final Scheduler mainScheduler;
    private final Scheduler ioScheduler;

    @Inject
    public PitchforkRssClient(OkHttpClient okHttpClient,
                              @Named("mainThread") Scheduler mainScheduler,
                              @Named("ioThread") Scheduler ioScheduler) {
        Uri uri = (new Uri.Builder()).scheme(SCHEME)
                .authority(AUTHORITY).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(uri.toString())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(okHttpClient)
                .build();
        service = retrofit.create(PitchforkRssService.class);
        reviewsObservable = BehaviorSubject.create();
        this.mainScheduler = mainScheduler;
        this.ioScheduler = ioScheduler;
        queryReviews();
    }

    public void queryReviews(){
        service.getAlbumReviews()
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .map(new RssRootToReviewsListMapper())
                .subscribe(new Action1<List<Review>>() {
                    @Override
                    public void call(List<Review> reviews) {
                        reviewsObservable.onNext(reviews);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "Error fetching reviews: " + throwable.toString());
                    }
                });
    }

    public Observable<List<Review>> getAlbumReviews(){
        return reviewsObservable;
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
