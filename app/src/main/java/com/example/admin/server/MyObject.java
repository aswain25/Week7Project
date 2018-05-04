package com.example.admin.server;

import android.os.Parcel;
import android.os.Parcelable;

public class MyObject implements Parcelable {

    final public static DataContract Contract = new DataContract(
            "MyObject",
            "Id",
            "INTEGER",
            new String[]{ "Name"},
            new String[]{ "TEXT"},
            1);

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

    public static DataContract getContract() {
        return Contract;
    }

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
