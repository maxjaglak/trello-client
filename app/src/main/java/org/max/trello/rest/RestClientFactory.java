package org.max.trello.rest;


import org.max.trello.GsonManager;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class RestClientFactory {

    public TrelloApiClient getTrelloApiRestClient() {
        return new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("https://api.trello.com")
                .setConverter(new GsonConverter(GsonManager.getGson()))
                .build().create(TrelloApiClient.class);
    }

}
