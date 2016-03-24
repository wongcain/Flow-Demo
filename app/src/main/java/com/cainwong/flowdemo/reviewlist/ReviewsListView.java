package com.cainwong.flowdemo.reviewlist;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cainwong.flowdemo.R;
import com.cainwong.flowdemo.core.AppComponent;
import com.cainwong.flowdemo.core.AppServices;
import com.jakewharton.rxbinding.view.RxView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import flow.Flow;

/**
 * Created by cwong on 3/21/16.
 */
public class ReviewsListView extends RecyclerView {

    @Inject
    ReviewsListPresenter presenter;

    public ReviewsListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setLayoutManager(new LinearLayoutManager(context));
        ((AppComponent) Flow.getService(AppServices.APP_COMPONENT, context)).inject(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.init(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        presenter.destroy();
        super.onDetachedFromWindow();
    }

    public static class ReviewsListItemViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.album)
        TextView albumView;

        @Bind(R.id.artist)
        TextView artistView;

        @Bind(R.id.date)
        TextView dateView;

        public ReviewsListItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setAlbum(String album){
            albumView.setText(album);
        }

        public void setArtist(String artist){
            artistView.setText(artist);
        }

        public void setDate(String date){
            dateView.setText(date);
        }

        public rx.Observable<Void> getClickObservable(){
            return RxView.clicks(itemView);
        }

        public static ReviewsListItemViewHolder newInstance(ViewGroup parent){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_review_list_item, parent, false);
            return new ReviewsListItemViewHolder(v);
        }
    }


}
