package com.jackfangqi.myrxframework.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jackfangqi.myrxframework.R;

/**
 * Compiler: Android Studio
 * Project: MyRxFramework
 * Author: Jack Fang
 * Email: fangqi@xywy.com
 * Date: 2016/8/12 9:17
 */
public class LoadingDialog extends AlertDialog.Builder {

    @SuppressLint("InflateParams")
    private View contentView;
    private TextView dialogTitle;

    public LoadingDialog(@NonNull Context context) {
        super(context);
        contentView = LayoutInflater.from(context).inflate(R.layout.loading_layout, null);
        dialogTitle = (TextView) contentView.findViewById(R.id.dialog_title);
    }

    public LoadingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public void setTitle(String msg) {
        dialogTitle.setText(msg);
    }

    public AlertDialog createDialog() {
        setView(contentView);
        setCancelable(true);

        return create();
    }
}
