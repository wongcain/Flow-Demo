package com.cainwong.flowdemo.core;

import com.cainwong.flowdemo.reviewdetails.ReviewDetailsView;
import com.cainwong.flowdemo.reviewlist.ReviewsListView;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by cwong on 3/21/16.
 */
@Component(modules = AppModule.class)
@Singleton
public interface AppComponent {

    void inject(ReviewsListView v);
    void inject(ReviewDetailsView v);

}
