package com.cainwong.flowdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cainwong.flowdemo.core.AppServices;
import com.cainwong.flowdemo.core.GsonParceler;
import com.cainwong.flowdemo.core.Screen;
import com.cainwong.flowdemo.reviewlist.ReviewsListScreen;
import com.google.gson.Gson;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import flow.Direction;
import flow.Flow;
import flow.KeyChanger;
import flow.KeyDispatcher;
import flow.State;
import flow.TraversalCallback;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Nullable
    @Bind(R.id.main_container)
    ViewGroup mainContainer;

    @Nullable
    @Bind(R.id.review_list_container)
    ViewGroup reviewListContainer;

    @Nullable
    @Bind(R.id.review_detail_container)
    ViewGroup reviewDetailContainer;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override protected void attachBaseContext(Context baseContext) {
        Log.d(TAG, "attachBaseContext");
        baseContext = Flow.configure(baseContext, this)
                .addServicesFactory(new AppServices())
                .defaultKey(new ReviewsListScreen())
                .dispatcher(KeyDispatcher.configure(this, new Changer()).build())
                .keyParceler(new GsonParceler(new Gson()))
                .install();
        super.attachBaseContext(baseContext);
    }

    @Override public void onBackPressed() {
        if (!Flow.get(this).goBack()) {
            super.onBackPressed();
        }
    }

    boolean isLandscape(){
        return reviewDetailContainer != null;
    }

    private final class Changer extends KeyChanger {

        @Override public void changeKey(@Nullable State outgoingState, @NonNull State incomingState,
                                        @NonNull Direction direction, @NonNull Map<Object, Context> incomingContexts,
                                        @NonNull TraversalCallback callback) {

            Object key = incomingState.getKey();
            Context context = incomingContexts.get(key);

            ViewGroup detailContainer = isLandscape() ? reviewDetailContainer : mainContainer;
            ViewGroup listContainer = isLandscape() ? reviewListContainer : mainContainer;

            if (outgoingState != null && detailContainer.getChildCount()>0) {
                outgoingState.save(detailContainer.getChildAt(0));
            }

            View view;
            if (key instanceof Screen) {
                view = inflateLayout(context, ((Screen)key).getLayoutId());
            } else {
                view = createTextView(context, key, null);
            }
            incomingState.restore(view);

            if(!isLandscape() || !(key instanceof ReviewsListScreen)){
                detailContainer.removeAllViews();
                detailContainer.addView(view);
            }

            if(isLandscape()){
                if(listContainer.getChildCount() == 0){
                    if(key instanceof ReviewsListScreen){
                        listContainer.addView(view);
                    } else {
                        listContainer.addView(inflateLayout(context, new ReviewsListScreen().getLayoutId()));
                    }
                }
            }

            callback.onTraversalCompleted();
        }

        private View inflateLayout(Context context, @LayoutRes int layout) {
            LayoutInflater inflater = LayoutInflater.from(context);
            return inflater.inflate(layout, null);
        }

        private View createTextView(Context context, Object key, @Nullable final Object nextScreenOnClick) {
            TextView view = new TextView(context);
            view.setText(key.toString());

            if (nextScreenOnClick == null) {
                view.setOnClickListener(null);
            } else {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        Flow.get(v).set(nextScreenOnClick);
                    }
                });
            }
            return view;
        }
    }

}
