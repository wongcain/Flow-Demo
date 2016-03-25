package com.cainwong.flowdemo.reviewdetails;

import android.util.Log;

import com.cainwong.flowdemo.core.Presenter;
import com.cainwong.flowdemo.models.Review;
import com.cainwong.flowdemo.rss.PitchforkRssClient;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import flow.Flow;
import rx.functions.Action1;

/**
 * Created by cwong on 3/24/16.
 */
@Singleton
public class ReviewDetailsPresenter extends Presenter<ReviewDetailsView, ReviewDetailsScreen> {

    private static final String TAG = ReviewDetailsPresenter.class.getSimpleName();
    private final PitchforkRssClient pitchforkRssClient;

    @Inject
    public ReviewDetailsPresenter(PitchforkRssClient pitchforkRssClient) {
        this.pitchforkRssClient = pitchforkRssClient;
    }

    @Override
    public void init(final ReviewDetailsView view) {
        super.init(view);
        final int reviewIndex = screen.getReviewIndex();
        pitchforkRssClient.getAlbumReviews()
                .subscribe(new Action1<List<Review>>() {
                    @Override
                    public void call(List<Review> list) {
                        if(list.size() > reviewIndex){
                            Review review = list.get(reviewIndex);
                            view.setArtist(review.artist);
                            view.setAlbum(review.album);
                            view.setBody(review.body);
                            view.setImage(review.imgUrl);

                            view.setHasNext(list.size() > (reviewIndex+1));
                            view.setHasPrev(reviewIndex > 1);

                            view.getNextClicks().subscribe(new Action1<Void>() {
                                @Override
                                public void call(Void aVoid) {
                                    Flow.get(view).set(new ReviewDetailsScreen(reviewIndex+1));
                                }
                            });

                            view.getPrevClicks().subscribe(new Action1<Void>() {
                                @Override
                                public void call(Void aVoid) {
                                    Flow.get(view).set(new ReviewDetailsScreen(reviewIndex-1));
                                }
                            });
                        } else {
                            Log.e(TAG, "Review index " + reviewIndex + " out of range. List size: " + list.size());
                        }
                    }

                });
    }

}
