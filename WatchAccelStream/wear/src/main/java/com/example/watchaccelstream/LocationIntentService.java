package com.example.watchaccelstream;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.wearable.DataClient;

public class LocationIntentService extends IntentService {

    private static final String TAG = LocationIntentService.class.getSimpleName() + "_debug";
    public LocationIntentService() {
        super("");
    }

    private DataClient mDataClient;

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "on handle intent");
        if(Looper.myLooper() == Looper.getMainLooper()) {
            // Current Thread is Main Thread.
            Log.d(TAG, "on main thread");
        }else {
            Log.d(TAG, "background thread");
        }


        SensorService sensorService = new SensorService(getApplicationContext());
        sensorService.registerSensors();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "intent service is going to be destroyed");
    }
}
