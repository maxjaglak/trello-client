package org.max.trello.services;


import android.app.IntentService;

import org.max.trello.TrelloApplication;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

public abstract class BaseIntentService extends IntentService{

    @Inject
    protected EventBus eventBus;

    public BaseIntentService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TrelloApplication.getApp(this).inject(this);
    }

}
