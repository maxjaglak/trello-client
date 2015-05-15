package org.max.trello;


import org.max.trello.base.AbstractTest;

public class ConfigTest extends AbstractTest {

    private Config config;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        TrelloApplication application = (TrelloApplication) getSystemContext().getApplicationContext();
        config = application.getConfig();
    }

    public void testConfigValuesPresent() {
        assertNotNull(config.getMainBoardName());
        assertNotNull(config.getAppKey());
        assertNotNull(config.getSecret());
        assertNotNull(config.getToken());
        assertNotNull(config.getUserId());
    }
}
