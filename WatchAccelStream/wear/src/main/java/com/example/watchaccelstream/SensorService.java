package com.example.watchaccelstream;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;

import static com.google.android.gms.wearable.Wearable.getDataClient;

public class SensorService implements SensorEventListener{

    private static final String TAG = SensorService.class.getSimpleName() + "_debug";

    private Context context;

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;

    private HandlerThread mSensorThread;
    private Handler mSensorHandler;
    private DataClient mDataClient;
    private FileWriter fileWriter;
    private static final String ACCEL_KEY = "com.example.key.ACCEL";
    public SensorService(Context context) {
        if(Looper.myLooper() == Looper.getMainLooper()) {
            // Current Thread is Main Thread.
            Log.d(TAG, "on main thread");
        }else {
            Log.d(TAG, "on back thread");
        }
        this.context = context;
        mDataClient = getDataClient(context);
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        mSensorThread = new HandlerThread("Sensor thread", Thread.MAX_PRIORITY);
        mSensorThread.start();
        mSensorHandler = new Handler(mSensorThread.getLooper()); //Blocks until looper is prepared, which is fairly quick
        //yourSensorManager.registerListener(yourListener, yourSensor, interval, mSensorHandler);

        fileWriter = new FileWriter(this.context, "apidata", "sensordata.txt");
    }

    protected void registerSensors(){
        Log.d(TAG, "register sensor "+ (Looper.myLooper() == Looper.getMainLooper()));
        registerAccelerometer();
    }

    private void unregisterSensors(){
        unregisterAccelerometer();
    }

    private void registerAccelerometer(){
        int freq = 100;
        int samplingPeriodUs = (1000000 / freq);
        sensorManager.registerListener(this, accelerometerSensor, samplingPeriodUs, mSensorHandler);
    }

    private void unregisterAccelerometer(){
        sensorManager.unregisterListener(this, accelerometerSensor);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //Log.d(TAG, "x:"+sensorEvent.values[0] + (Looper.myLooper() == Looper.getMainLooper()));

        String acceldata=""+sensorEvent.timestamp+ "," + sensorEvent.values[0] + ","+ sensorEvent.values[1] + ","+ sensorEvent.values[2];
        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/ACCEL");

            putDataMapReq.getDataMap().putString(ACCEL_KEY, acceldata);
            PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
            Task<DataItem> putDataTask = mDataClient.putDataItem(putDataReq);
            Log.d("rawdatachange", "in onHARrawdatachagen");

        //fileWriter.write("x:" + sensorEvent.values[0] + ",y:"+ sensorEvent.values[1] + ",z:"+ sensorEvent.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
