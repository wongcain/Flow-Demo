package com.cainwong.flowdemo.mvp;

import android.view.View;

/**
 * Created by cwong on 3/21/16.
 */
public class Presenter<V extends View> {

    private V view;

    public void init(V view) {
        this.view = view;
    }

    public void destroy() {
        view = null;
    }

    protected V getView(){
        return view;
    }

}
