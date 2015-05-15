package org.max.trello.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.max.trello.R;
import org.max.trello.dao.CardListsDao;
import org.max.trello.dao.CardsDao;
import org.max.trello.entities.Card;
import org.max.trello.entities.CardList;
import org.max.trello.loaders.LoadingCallback;
import org.max.trello.loaders.SupportDataLoader;
import org.max.trello.services.SendIntentService;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;

public class MoveCardActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<List<CardList>>{

    public static final String CARD_WEB_ID_PARAM = "card_web_id";
    private String cardWebId;

    @Inject
    CardListsDao cardListsDao;

    @Inject
    CardsDao cardsDao;

    @InjectView(R.id.list)
    ListView list;

    private List<CardList> cardLists;
    private Card card;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_card);
        getCardIdFromIntent();
        loadLists();
    }

    private void getCardIdFromIntent() {
        cardWebId = getIntent().getStringExtra(CARD_WEB_ID_PARAM);
    }

    private void loadLists() {
        getSupportLoaderManager().initLoader(100, null, this);
    }

    @Override
    public Loader<List<CardList>> onCreateLoader(int id, Bundle args) {
        return new SupportDataLoader<>(this, new LoadingCallback<List<CardList>>() {
            @Override
            public List<CardList> loadData() {
                card = cardsDao.getByWebId(cardWebId);
                return cardListsDao.getAll();
            }
        });
    }

    @Override
    public void onLoadFinished(Loader<List<CardList>> loader, List<CardList> cardLists) {
        this.cardLists = cardLists;
        list.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getListsNames()));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                moveCardToList(position);
                finish();
            }
        });
    }

    private void moveCardToList(int position) {
        CardList targetList = cardLists.get(position);
        card.setIdList(targetList.getWebId());
        card.setEdited(true);
        cardsDao.save(card);
        startService(new Intent(this, SendIntentService.class));
    }

    private List<String> getListsNames() {
        List<String> names = new LinkedList<>();
        for (CardList cardList : cardLists) {
            names.add(cardList.getName());
        }
        return names;
    }

    @Override
    public void onLoaderReset(Loader<List<CardList>> loader) {

    }
}
