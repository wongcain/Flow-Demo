package com.cainwong.flowdemo.reviewlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.cainwong.flowdemo.core.Presenter;
import com.cainwong.flowdemo.models.Review;
import com.cainwong.flowdemo.reviewdetails.ReviewDetailsScreen;
import com.cainwong.flowdemo.rss.PitchforkRssClient;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import flow.Flow;
import rx.functions.Action1;

/**
 * Created by cwong on 3/22/16.
 */
@Singleton
public class ReviewsListPresenter extends Presenter<ReviewsListView, ReviewsListScreen> {

    private static final String TAG = ReviewsListPresenter.class.getSimpleName();

    private final PitchforkRssClient pitchforkRssClient;
    private final List<Review> reviews;
    private ReviewsListAdapter adapter;

    @Inject
    public ReviewsListPresenter(PitchforkRssClient pitchforkRssClient) {
        this.pitchforkRssClient = pitchforkRssClient;
        reviews = new ArrayList<>();

    }

    @Override
    public void init(ReviewsListView view) {
        super.init(view);
        adapter = new ReviewsListAdapter(view.getContext(), reviews);
        view.setAdapter(adapter);
        pitchforkRssClient.getAlbumReviews()
                .subscribe(new Action1<List<Review>>() {
                    @Override
                    public void call(List<Review> list) {
                        Log.d(TAG, "Successfully fetched " + list.size() + " reviews");
                        reviews.clear();
                        reviews.addAll(list);
                        adapter.notifyDataSetChanged();
                    }

                });
    }

    static class ReviewsListAdapter extends RecyclerView.Adapter<ReviewsListView.ReviewsListItemViewHolder> {

        private final Context context;
        private final List<Review> reviews;

        ReviewsListAdapter(Context context, List<Review> reviews) {
            this.context = context;
            this.reviews = reviews;
        }

        @Override
        public ReviewsListView.ReviewsListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return ReviewsListView.ReviewsListItemViewHolder.newInstance(parent);
        }

        @Override
        public void onBindViewHolder(ReviewsListView.ReviewsListItemViewHolder holder, final int position) {
            if(position < reviews.size()) {
                Review review = reviews.get(position);
                holder.setAlbum(review.album);
                holder.setArtist(review.artist);
                holder.setDate(DateFormat.getDateInstance().format(new Date(review.postedTimestamp)));
                holder.getItemClicks().subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Flow.get(context).set(new ReviewDetailsScreen(position));
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return reviews.size();
        }
    }

}
