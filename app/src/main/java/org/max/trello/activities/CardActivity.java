package org.max.trello.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.max.trello.R;
import org.max.trello.dao.CardListsDao;
import org.max.trello.dao.CardsDao;
import org.max.trello.events.DataModified;
import org.max.trello.services.SendIntentService;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;

public abstract class CardActivity extends BaseActivity {

    public static final String CARD_ID_PARAM = "cardIdParam";
    public static final String CARD_LIST_WEBID_PARAM = "cardListIdParam";

    @Inject
    CardsDao cardsDao;

    @Inject
    CardListsDao cardListsDao;

    @InjectView(R.id.name)
    EditText name;

    @InjectView(R.id.description)
    EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        hideSoftKeyboard();
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @OnClick(R.id.save)
    public void onSaveClicked() {
        saveData();
        startDataSyncingService();
        eventBus.postSticky(new DataModified());
        finish();
    }

    private void startDataSyncingService() {
        startService(new Intent(this, SendIntentService.class));
    }

    public abstract void saveData();

}
