package com.cainwong.flowdemo.reviewdetails;

import com.cainwong.flowdemo.R;
import com.cainwong.flowdemo.core.Screen;
import com.cainwong.flowdemo.models.Review;

/**
 * Created by cwong on 3/24/16.
 */
public class ReviewDetailsScreen implements Screen {

    private final int reviewIndex;

    public ReviewDetailsScreen(int reviewIndex) {
        this.reviewIndex = reviewIndex;
    }

    public int getReviewIndex(){
        return reviewIndex;
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_review_details;
    }
}
