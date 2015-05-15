package org.max.trello.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class SupportDataLoader<T> extends AsyncTaskLoader<T> {

    private final LoadingCallback<T> loadingCallback;

    public SupportDataLoader(Context context, LoadingCallback<T> loadingCallback) {
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
