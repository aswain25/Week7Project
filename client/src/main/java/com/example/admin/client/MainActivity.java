package com.example.admin.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.server.ICalService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected ICalService calService = null;
    private RecyclerView.Adapter adapter;
    private List<MyObject> objects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        startService();
    }

    private void bindViews() {
        RecyclerView recyclerView = findViewById(R.id.rvObjects);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        objects = new ArrayList<>();
        adapter = new CustomAdapter(objects);
        recyclerView.setAdapter(adapter);
    }

    private void startService() {
        ComponentName aidlComponent = new ComponentName("com.example.admin.server", "com.example.admin.server.CalService");
        Intent it = new Intent();
        it.setComponent(aidlComponent);
        bindService(it, connection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            calService = ICalService.Stub.asInterface(service);
            try {
                Log.d("TOINHOIH",calService.getAllObjects().size()+"");
                objects.clear();
                objects.addAll(calService.getAllObjects());
                adapter.notifyDataSetChanged();
            } catch (RemoteException e) {
                //nothing
            }
            Toast.makeText(getApplicationContext(),	"Service Connected", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            calService = null;
            Toast.makeText(getApplicationContext(), "Service Disconnected", Toast.LENGTH_SHORT).show();
        }
    };
}
