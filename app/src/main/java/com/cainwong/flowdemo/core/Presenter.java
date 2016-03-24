package com.cainwong.flowdemo.core;

import android.view.View;

import flow.Flow;

/**
 * Created by cwong on 3/21/16.
 */
public class Presenter<V extends View, S extends Screen> {

    protected V view;
    protected S screen;

    public void init(V view) {
        this.view = view;
        this.screen = Flow.getKey(view);
    }

    public void destroy() {
        view = null;
        screen = null;
    }

}
