package org.max.trello;


import android.content.Context;
import android.util.Log;

import com.orm.SugarApp;

import org.max.trello.injecting.DaggerInjectingModule;

import dagger.ObjectGraph;
import lombok.Getter;

public class TrelloApplication extends SugarApp implements Thread.UncaughtExceptionHandler{

    public static final String TAG = "TrelloApplication";

    @Getter
    private Config config;

    @Getter
    private DaggerInjectingModule injectingModule;
    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(this);
        buildObjectGraph();
        loadConfig();
    }

    private void loadConfig() {
        config = new Config(this);
    }

    private void buildObjectGraph() {
        injectingModule = new DaggerInjectingModule(this);
        objectGraph = ObjectGraph.create(injectingModule);
    }

    public void inject(Object target) {
        objectGraph.inject(target);
    }

    public static TrelloApplication getApp(Context context) {
        return (TrelloApplication) context.getApplicationContext();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e(TAG, ex.getMessage(), ex);
    }
}
