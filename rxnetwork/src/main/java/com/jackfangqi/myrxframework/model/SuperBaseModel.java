package com.jackfangqi.myrxframework.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Compiler: Android Studio
 * Project: MyRxFramework
 * Author: Jack Fang
 * Email: fangqi@xywy.com
 * Date: 2016/8/12 9:07
 */
public class SuperBaseModel implements Parcelable {
    private String code;
    private String msg;

    protected SuperBaseModel(Parcel in) {
        code = in.readString();
        msg = in.readString();
    }

    public static final Creator<SuperBaseModel> CREATOR = new Creator<SuperBaseModel>() {
        @Override
        public SuperBaseModel createFromParcel(Parcel in) {
            return new SuperBaseModel(in);
        }

        @Override
        public SuperBaseModel[] newArray(int size) {
            return new SuperBaseModel[size];
        }
    };

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public boolean success() {
        return code.equals("0");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(code);
        parcel.writeString(msg);
    }
}
