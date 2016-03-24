package com.cainwong.flowdemo.reviewdetails;

import com.cainwong.flowdemo.R;
import com.cainwong.flowdemo.core.Screen;
import com.cainwong.flowdemo.models.Review;

/**
 * Created by cwong on 3/24/16.
 */
public class ReviewDetailsScreen implements Screen {

    private final Review review;

    public ReviewDetailsScreen(Review review) {
        this.review = review;
    }

    public Review getReview(){
        return review;
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_review_details;
    }
}
