package org.max.trello;


import java.io.IOException;
import java.util.Properties;

import lombok.Getter;

public class Config  {

    @Getter
    private String mainBoardName;

    @Getter
    private String appKey;

    @Getter
    private String secret;

    @Getter
    private String token;

    @Getter
    private String userId;

    private TrelloApplication trelloApplication;

    public Config(TrelloApplication trelloApplication) {
        this.trelloApplication = trelloApplication;
        try {
            loadProperties();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(trelloApplication.getResources().openRawResource(R.raw.config));
        mainBoardName = properties.getProperty("mainBoardName");
        appKey = properties.getProperty("appKey");
        secret = properties.getProperty("secret");
        token = properties.getProperty("token");
        userId = properties.getProperty("userId");
    }
}
