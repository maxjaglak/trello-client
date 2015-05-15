package org.max.trello.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import org.max.trello.R;

public class MessageFragment extends DialogFragment {

    public static final String MESSAGE = "message";
    public static final String TAG = "message_fragment";

    public static void showError(FragmentManager fragmentManager, String message) {
        MessageFragment messageFragment = new MessageFragment();
        messageFragment.setArguments(buildArguments(message));
        messageFragment.show(fragmentManager, TAG);
    }

    private static Bundle buildArguments(String message) {
        Bundle bundle = new Bundle();
        bundle.putString(MESSAGE, message);
        return bundle;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString(MESSAGE);
        return new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setNeutralButton(getActivity().getString(R.string.ok), null)
                .show();
    }
}
