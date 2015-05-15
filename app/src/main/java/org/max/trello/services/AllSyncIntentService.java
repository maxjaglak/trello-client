package org.max.trello.services;


import android.content.Intent;

public class AllSyncIntentService extends BaseIntentService{


    public AllSyncIntentService() {
        super("AllSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        DataSender dataSender = new DataSender(this);
        dataSender.sendData();
        DataDownloader dataDownloader = new DataDownloader(this);
        dataDownloader.downloadData();
    }
}
