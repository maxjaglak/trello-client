package org.max.trello;


import android.view.View;
import android.widget.TextView;

import org.max.trello.adapters.CardsAdaper;
import org.max.trello.base.AbstractTest;
import org.max.trello.base.CardProvider;
import org.max.trello.entities.Card;
import org.max.trello.views.CardView;

import java.util.Collections;

public class CardsAdapterTest extends AbstractTest {

    private CardsAdaper adaper;
    private Card mockCard;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mockCard = CardProvider.getMockCard();
        adaper = new CardsAdaper(getSystemContext(), Collections.singletonList(mockCard), null);
    }

    public void testAdapterCount() {
        assertEquals(1, adaper.getCount());
    }

    public void testAdapterViewNotNull() {
        assertNotNull(adaper.getView(0, null, null));
    }

    public void testCreatedView() {
        View view = adaper.getView(0, null, null);

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView desc = (TextView) view.findViewById(R.id.description);

        assertEquals(CardView.class, view.getClass());
        assertEquals(mockCard.getName(), name.getText().toString());
        assertEquals(mockCard.getDesc(), desc.getText().toString());
    }

    public void testSyncVisibility() {
        CardView view = (CardView) adaper.getView(0, null, null);

        TextView sync = (TextView) view.findViewById(R.id.edited);

        assertEquals(View.GONE, sync.getVisibility());
    }

    public void testSyncVisibility2() {
        CardView view = (CardView) adaper.getView(0, null, null);

        mockCard.setEdited(true);
        view.setCard(mockCard);

        TextView sync = (TextView) view.findViewById(R.id.edited);

        assertEquals(View.VISIBLE, sync.getVisibility());
    }

}
