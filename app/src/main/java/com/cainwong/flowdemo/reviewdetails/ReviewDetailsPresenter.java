package com.cainwong.flowdemo.reviewdetails;

import com.cainwong.flowdemo.core.Presenter;
import com.cainwong.flowdemo.models.Review;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by cwong on 3/24/16.
 */
@Singleton
public class ReviewDetailsPresenter extends Presenter<ReviewDetailsView, ReviewDetailsScreen> {

    @Inject
    public ReviewDetailsPresenter() {
    }

    @Override
    public void init(ReviewDetailsView view) {
        super.init(view);
        Review review = screen.getReview();
        view.setArtist(review.artist);
        view.setAlbum(review.album);
        view.setBody(review.body);
        view.setImage(review.imgUrl);
    }

}
