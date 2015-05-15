package org.max.trello.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.max.trello.R;
import org.max.trello.events.DataDownloaded;
import org.max.trello.services.DownloadIntentService;

import butterknife.InjectView;

public class SplashActivity extends BaseActivity {

    @InjectView(R.id.loader)
    View loaderIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        loaderIndicator.setVisibility(View.VISIBLE);
        startDataLoadingService();
    }

    private void startDataLoadingService() {
        startService(new Intent(this, DownloadIntentService.class));
    }

    public void onEventMainThread(DataDownloaded event) {
        eventBus.removeStickyEvent(event);
        startCardsListActivity();
        finish();
    }

    private void startCardsListActivity() {
        startActivity(new Intent(this, CardListsActivity.class));
    }
}
