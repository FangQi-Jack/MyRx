package com.jackfangqi.commonutil.utils;

import java.util.LinkedList;

/**
 * Compiler: Android Studio
 * Project: CommonUtil
 * Author: Jack Fang
 * Email: jackfangqi1314@gmail.com
 * Date: 2016/8/19 10:33
 */
public class SimpleStack {
    private LinkedList<Object> list;

    public SimpleStack() {
        this.list = new LinkedList<>();
    }

    public void add(Object o) {
        this.list.push(o);
    }

    public Object get() {
        return this.list == null ? null : this.list.pop();
    }

    public int size() {
        return this.list == null ? 0 : this.list.size();
    }
}
