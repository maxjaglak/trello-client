package org.max.trello.injecting;


import android.content.Context;

import org.max.trello.Config;
import org.max.trello.TrelloApplication;
import org.max.trello.activities.CardActivity;
import org.max.trello.activities.CardListsActivity;
import org.max.trello.activities.EditCardActivity;
import org.max.trello.activities.MoveCardActivity;
import org.max.trello.activities.NewCardActivity;
import org.max.trello.activities.SplashActivity;
import org.max.trello.dao.BoardsDao;
import org.max.trello.dao.CardListsDao;
import org.max.trello.dao.CardsDao;
import org.max.trello.fragments.CardListFragment;
import org.max.trello.rest.RestClientFactory;
import org.max.trello.rest.TrelloApiClient;
import org.max.trello.services.AllSyncIntentService;
import org.max.trello.services.DataDownloader;
import org.max.trello.services.DataSender;
import org.max.trello.services.DownloadIntentService;
import org.max.trello.services.SendIntentService;

import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;

@Module(library = true, injects = {
        CardListsActivity.class,
        DownloadIntentService.class,
        SplashActivity.class,
        CardListFragment.class,
        CardActivity.class,
        EditCardActivity.class,
        SendIntentService.class,
        NewCardActivity.class,
        MoveCardActivity.class,
        DataSender.class,
        DataDownloader.class,
        AllSyncIntentService.class,
})
public class DaggerInjectingModule {

    private Context applicationContext;

    private EventBus eventBus = EventBus.getDefault();

    public DaggerInjectingModule(Context context) {
        applicationContext = context;
    }

    @Provides
    public EventBus provideEventBus() {
        return eventBus;
    }

    @Provides
    public TrelloApiClient provideTrelloApiClient() {
        return new RestClientFactory().getTrelloApiRestClient();
    }

    @Provides
    public BoardsDao provideBoardDao() {
        return new BoardsDao();
    }

    @Provides
    public CardListsDao provideCardListDao() {
        return new CardListsDao();
    }

    @Provides
    public CardsDao provideCardsDao() {
        return new CardsDao();
    }

    @Provides
    public Config provideConfig() {
        return TrelloApplication.getApp(applicationContext).getConfig();
    }


}
