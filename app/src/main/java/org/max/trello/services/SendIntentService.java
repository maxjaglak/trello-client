package org.max.trello.services;

import android.content.Intent;

public class SendIntentService extends BaseIntentService {

    public SendIntentService() {
        super("SaveDataIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        DataSender dataSender = new DataSender(this);
        dataSender.sendData();
    }


}
