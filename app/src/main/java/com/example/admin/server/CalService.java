package com.example.admin.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalService extends Service {
    @Override
    public IBinder onBind(Intent arg0) {
        return binder;
    }
    private final ICalService.Stub binder = new ICalService.Stub() {
        @Override
        public int getResult(int val1, int val2) throws RemoteException {
            return val1 * val2;
        }

        @Override
        public List<MyObject> getAllObjects() throws RemoteException {


            try {
                return MainActivity.db.getAllPerson(MyObject.class);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        }

        @Override
            public String getMessage(String name) throws RemoteException {
            return "Hello "+ name+", Result is:";
        }
    };
}
