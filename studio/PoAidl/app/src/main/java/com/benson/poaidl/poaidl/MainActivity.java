package com.benson.poaidl.poaidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "Po_add";
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "--onCreate()--");
        setContentView(R.layout.activity_main);

        Log.i(TAG, "--bindService()--");
        if (aidlServiceCmd == null) {
            bindService(new Intent("com.benson.poaidl.poaidl.PoAidlTest"), serviceConnection, BIND_AUTO_CREATE);
            Log.i(TAG, "--bindService()-- call intent");
        }

        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    Log.i(TAG, "Button PID=" + aidlServiceCmd.getPid());

                } catch (RemoteException e) {
                    e.printStackTrace();
                    Log.i(TAG, "Button PID err");
                }
            }
        });

        int id = android.os.Process.myPid();
        Log.i(TAG, "MainActivity PID=" + id);

    }

    private PoAidlTest aidlServiceCmd = null;
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            aidlServiceCmd = PoAidlTest.Stub.asInterface(service);
            try {
                aidlServiceCmd.getPid();
                Log.i(TAG, "onServiceConnected() PID=" + aidlServiceCmd.getPid());
            } catch (RemoteException e) {
                Log.i(TAG, "onServiceConnected err");
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            aidlServiceCmd = null;
            Log.i(TAG, "ccc");
        }

    };
}
