package org.max.trello.services;

import android.content.Intent;

public class DownloadIntentService extends BaseIntentService {

    public DownloadIntentService() {
        super("GetContentIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        DataDownloader dataDownloader = new DataDownloader(this);
        dataDownloader.downloadData();
    }

}