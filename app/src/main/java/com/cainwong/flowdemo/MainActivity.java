package com.cainwong.flowdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cainwong.flowdemo.screens.ReviewsListScreen;
import com.cainwong.flowdemo.screens.Screen;

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

    @Bind(R.id.main_container)
    ViewGroup mainContainer;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override protected void attachBaseContext(Context baseContext) {
        baseContext = Flow.configure(baseContext, this)
                .addServicesFactory(new AppServices(baseContext))
                .defaultKey(new ReviewsListScreen())
                .dispatcher(KeyDispatcher.configure(this, new Changer()).build())
//                .keyParceler(new BasicKeyParceler()) //
                .install();
        super.attachBaseContext(baseContext);
    }

    @Override public void onBackPressed() {
        if (!Flow.get(this).goBack()) {
            super.onBackPressed();
        }
    }

    private final class Changer extends KeyChanger {

        @Override public void changeKey(@Nullable State outgoingState, @NonNull State incomingState,
                                        @NonNull Direction direction, @NonNull Map<Object, Context> incomingContexts,
                                        @NonNull TraversalCallback callback) {

            Object key = incomingState.getKey();
            Context context = incomingContexts.get(key);

            if (outgoingState != null) {
                outgoingState.save(mainContainer.getChildAt(0));
            }

            View view;
            if (key instanceof Screen) {
                view = showLayout(context, ((Screen)key).getLayoutId());
            } else {
                view = showKeyAsText(context, key, null);
            }
            incomingState.restore(view);
            setContentView(view);
            callback.onTraversalCompleted();
        }

        private View showLayout(Context context, @LayoutRes int layout) {
            LayoutInflater inflater = LayoutInflater.from(context);
            return inflater.inflate(layout, null);
        }

        private View showKeyAsText(Context context, Object key, @Nullable final Object nextScreenOnClick) {
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
