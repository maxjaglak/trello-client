package org.max.trello.activities;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import org.max.trello.R;
import org.max.trello.TrelloApplication;
import org.max.trello.events.FailedToSync;
import org.max.trello.fragments.MessageFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public abstract class BaseActivity extends FragmentActivity {

    @Inject
    EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TrelloApplication.getApp(this).inject(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            eventBus.registerSticky(this);
        } catch (Exception e) {
            Log.e(TrelloApplication.TAG, e.getMessage());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            eventBus.unregister(this);
        } catch (Exception e) {
            Log.e(TrelloApplication.TAG, e.getMessage());
        }
    }

    public void onEventMainThread(FailedToSync event) {
        eventBus.removeStickyEvent(event);
        MessageFragment.showError(getSupportFragmentManager(), getString(R.string.failed_to_sync));
    }
}
