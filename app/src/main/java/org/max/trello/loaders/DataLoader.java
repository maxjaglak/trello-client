package org.max.trello.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;


public class DataLoader<T> extends AsyncTaskLoader<T> {

    private final LoadingCallback<T> loadingCallback;

    public DataLoader(Context context, LoadingCallback<T> loadingCallback) {
        super(context);
        this.loadingCallback = loadingCallback;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public T loadInBackground() {
        return loadingCallback.loadData();
    }


}
