package com.jackfangqi.commonutil.utils;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Compiler: Android Studio
 * Project: CommonUtils
 * Author: Jack Fang
 * Email: jackfangqi1314@gmail.com
 * Date: 2016/8/18 17:45
 */

/**
 * TextWatcher的简单（空）实现，实际使用中可直接使用此类，根据需要重写相应方法，减少项目中的方法数
 */
public class SimpleTextWatherImpl implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
