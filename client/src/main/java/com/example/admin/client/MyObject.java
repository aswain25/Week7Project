package com.example.admin.client;

import android.os.Parcel;
import android.os.Parcelable;

public class MyObject implements Parcelable {

    int id;
    String name;

    public MyObject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    protected MyObject(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<MyObject> CREATOR = new Creator<MyObject>() {
        @Override
        public MyObject createFromParcel(Parcel in) {
            return new MyObject(in);
        }

        @Override
        public MyObject[] newArray(int size) {
            return new MyObject[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }
}
