package com.benson.poaidl.poaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.util.Log;

public class PoService extends Service {
    private final static String TAG = "PoService";

    private RemoteCallbackList<PoAidlTest> aidlActivityCmd = new RemoteCallbackList<PoAidlTest>();

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.i(TAG, "--onBind()--");
        return aidlServiceCmd;
    }

    public PoAidlTest.Stub aidlServiceCmd = new PoAidlTest.Stub() {
        public int getPid() {

            int id = android.os.Process.myPid();
            Log.i(TAG, "--getPid()-- PID=" + id);
            return id;
        }
    };
}
