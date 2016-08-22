package com.jackfangqi.commonutil.utils;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jackfangqi.commonutil.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoadingDialogFragment extends DialogFragment {
    private static final String MSG_TAG_KEY = "msg";

    private String mDialogMsg;

    public static LoadingDialogFragment newInstance(String msg) {
        LoadingDialogFragment fragment = new LoadingDialogFragment();
        Bundle args = new Bundle();
        args.putString(MSG_TAG_KEY, msg);
        fragment.setArguments(args);

        return fragment;
    }

    public LoadingDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mDialogMsg = args.getString(MSG_TAG_KEY);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_loading_dialog, null);

        TextView dialogMsgText = (TextView) view.findViewById(R.id.loading_msg);
        if (mDialogMsg == null || "".equals(mDialogMsg))
            mDialogMsg = getActivity().getResources().getString(R.string.loading_dialog_default_msg);
        dialogMsgText.setText(mDialogMsg);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        return builder.create();
    }

}
