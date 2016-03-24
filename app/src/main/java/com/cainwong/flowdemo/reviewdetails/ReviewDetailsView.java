package com.cainwong.flowdemo.reviewdetails;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cainwong.flowdemo.R;
import com.cainwong.flowdemo.core.AppComponent;
import com.cainwong.flowdemo.core.AppServices;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import flow.Flow;

/**
 * Created by cwong on 3/24/16.
 */
public class ReviewDetailsView extends ScrollView {

    @Inject
    ReviewDetailsPresenter presenter;

    @Bind(R.id.album)
    TextView albumView;

    @Bind(R.id.artist)
    TextView artistView;

    @Bind(R.id.body)
    TextView bodyView;

    @Bind(R.id.review_image)
    ImageView imageView;

    public ReviewDetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((AppComponent) Flow.getService(AppServices.APP_COMPONENT, context)).inject(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
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

    public void setAlbum(String album){
        albumView.setText(Html.fromHtml(album));
    }

    public void setArtist(String artist){
        artistView.setText(Html.fromHtml(artist));
    }

    public void setBody(String body){
        bodyView.setText(Html.fromHtml(body));
    }

    public void setImage(String imgUrl){
        Glide.with(getContext()).load(imgUrl)
                .placeholder(R.drawable.noartplaceholder)
                .thumbnail(0.25f)
                .dontAnimate()
                .fitCenter()
                .into(imageView);
    }
}
