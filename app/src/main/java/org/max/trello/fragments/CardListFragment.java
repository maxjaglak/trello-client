package org.max.trello.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.max.trello.R;
import org.max.trello.TrelloApplication;
import org.max.trello.activities.CardActivity;
import org.max.trello.activities.EditCardActivity;
import org.max.trello.activities.MoveCardActivity;
import org.max.trello.adapters.CardsAdaper;
import org.max.trello.dao.CardListsDao;
import org.max.trello.dao.CardsDao;
import org.max.trello.entities.Card;
import org.max.trello.entities.CardList;
import org.max.trello.loaders.LoadingCallback;
import org.max.trello.loaders.SupportDataLoader;
import org.max.trello.services.SendIntentService;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

public class CardListFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Card>> {

    public static final String LIST_WEBID_ARGUMENT = "listIdArgument";
    public static final int REQUEST_CODE = 12;
    public static final int LOADER_ID = 12;

    @InjectView(R.id.cards_list)
    ListView cardsListView;

    @Inject
    CardListsDao cardListsDao;

    @Inject
    EventBus eventBus;

    @Inject
    CardsDao cardsDao;

    @InjectView(R.id.list_name)
    TextView listNameText;

    private String listWebId;

    private CardList cardList;

    private List<Card> cards;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TrelloApplication.getApp(getActivity()).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_list, container, false);
        ButterKnife.inject(this, view);
        startLoadingCards();
        return view;
    }

    private void startLoadingCards() {
        listWebId = getArguments().getString(LIST_WEBID_ARGUMENT);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Card>> onCreateLoader(int id, Bundle args) {
        return new SupportDataLoader<List<Card>>(getActivity(), new LoadingCallback<List<Card>>() {
            @Override
            public List<Card> loadData() {
                cardList = cardListsDao.getByWebId(listWebId);
                cards = cardsDao.getCardsForList(listWebId);
                return cards;
            }
        });
    }

    @Override
    public void onLoadFinished(Loader<List<Card>> loader, List<Card> data) {
        listNameText.setText(cardList.getName());
        cardsListView.setAdapter(new CardsAdaper(getActivity(), data, this));
    }

    public void editCard(Card card) {
        Intent intent = new Intent(getActivity(), EditCardActivity.class);
        intent.putExtra(CardActivity.CARD_ID_PARAM, card.getId());
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void moveCard(Card card) {
        Intent intent = new Intent(getActivity(), MoveCardActivity.class);
        intent.putExtra(MoveCardActivity.CARD_WEB_ID_PARAM, card.getWebId());
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void deleteCard(Card card) {
        card.setToDelete(true);
        cardsDao.save(card);
        reloadData();
        getActivity().startService(new Intent(getActivity(), SendIntentService.class));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE) {
            reloadData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void reloadData() {
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    public void onLoaderReset(Loader<List<Card>> loader) {
        //nothing
    }
}
