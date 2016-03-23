package com.cainwong.flowdemo.dagger;

import com.cainwong.flowdemo.views.ReviewsListView;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by cwong on 3/21/16.
 */
@Component(modules = AppModule.class)
@Singleton
public interface AppComponent {

    void inject(ReviewsListView v);

}
