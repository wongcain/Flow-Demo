package com.cainwong.flowdemo.core;

import flow.Services;
import flow.ServicesFactory;

/**
 * Created by cwong on 3/23/16.
 */
public class AppServices extends ServicesFactory {

    public static final String APP_COMPONENT = "APP_COMPONENT";

    private final AppComponent appComponent;

    public AppServices() {
        appComponent = DaggerAppComponent.builder().appModule(new AppModule()).build();
    }

    @Override
    public void bindServices(Services.Binder services) {
        services.bind(APP_COMPONENT, appComponent);
    }
}
