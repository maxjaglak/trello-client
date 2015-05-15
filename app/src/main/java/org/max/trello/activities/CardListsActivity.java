package org.max.trello.activities;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import org.max.trello.R;
import org.max.trello.dao.CardListsDao;
import org.max.trello.entities.CardList;
import org.max.trello.events.DataModified;
import org.max.trello.fragments.CardListFragment;
import org.max.trello.loaders.DataLoader;
import org.max.trello.loaders.LoadingCallback;
import org.max.trello.services.AllSyncIntentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.InjectView;


public class CardListsActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<List<CardList>> {

    public static final int REQUEST_CODE = 123;

    @InjectView(R.id.lists_pager)
    ViewPager listsPager;

    @Inject
    CardListsDao cardListsDao;

    private List<CardList> cardLists;

    private Map<Integer, CardListFragment> fragments = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_lists);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<List<CardList>> onCreateLoader(int id, Bundle args) {
        return new DataLoader<List<CardList>>(this, new LoadingCallback<List<CardList>>() {
            @Override
            public List<CardList> loadData() {
                return cardListsDao.getAll();
            }
        });
    }

    @Override
    public void onLoadFinished(Loader<List<CardList>> loader, List<CardList> cardLists) {
        this.cardLists = cardLists;
        showCardLists();
    }

    private void showCardLists() {
        listsPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                CardListFragment cardListFragment = new CardListFragment();
                cardListFragment.setArguments(getCardListIdArguments(position));
                fragments.put(position, cardListFragment);
                return cardListFragment;
            }

            @Override
            public int getCount() {
                return cardLists.size();
            }
        });
    }

    private Bundle getCardListIdArguments(int position) {
        Bundle bundle = new Bundle();
        CardList cardList = cardLists.get(position);
        bundle.putString(CardListFragment.LIST_WEBID_ARGUMENT, cardList.getWebId());
        return bundle;
    }

    @Override
    public void onLoaderReset(Loader<List<CardList>> loader) {
        //nothing
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_add) {
            startNewCardActivity();
        } else if(item.getItemId() == R.id.menu_sync) {
            startSyncingService();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startNewCardActivity() {
        Intent intent = new Intent(this, NewCardActivity.class);
        intent.putExtra(CardActivity.CARD_LIST_WEBID_PARAM, getCurrentListWebId());
        startActivityForResult(intent, REQUEST_CODE);
    }

    private String getCurrentListWebId() {
        return cardLists.get(listsPager.getCurrentItem()).getWebId();
    }

    private void startSyncingService() {
        startService(new Intent(this, AllSyncIntentService.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(REQUEST_CODE == requestCode) {
            reloadFragmentsData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onEventMainThread(DataModified event) {
        eventBus.removeStickyEvent(event);
        reloadFragmentsData();
    }

    public void reloadFragmentsData() {
        for (Map.Entry<Integer, CardListFragment> entry : fragments.entrySet()) {
            entry.getValue().reloadData();
        }
    }
}
