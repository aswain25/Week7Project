package com.example.admin.server;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.reflect.InvocationTargetException;

public class MainActivity extends AppCompatActivity {

    public static LocalDataSource db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new LocalDataSource(getApplicationContext(), MyObject.getContract());
        try {
            db.savePerson(new MyObject(0, "person"));
            db.savePerson(new MyObject(1, "qwerty"));
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
