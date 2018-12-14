package com.example.watchaccelstream;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.rockcode.har.HarDataListener;
import com.rockcode.har.HarMode;
import com.rockcode.har.HumanActivity;
import com.rockcode.har.HumanActivityRecognizer;
import com.rockcode.har.RawData;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.wearable.Wearable.getDataClient;

public class MainActivity extends AppCompatActivity {//implements DataClient.OnDataChangedListener{
    private static final String ACCEL_KEY = "com.example.key.ACCEL";
    private TextView mtextView;
    private List<String> accelData;
    private List<RawData> mRawDataList = new ArrayList<>();
    int mCollectCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtextView = findViewById(R.id.mystream);

        initHAR();

    }

    HumanActivityRecognizer mHAR;
    private HarDataListener mHarDataListener = new HarDataListener() {
        @Override
        public void onHarDataChange(HumanActivity ha) {

            Log.d("HAdatachange", "ha = " + ha);
            mtextView.setText(ha.mActivity);
        }

        public void onHarRawDataChange(List<RawData> rawDataList) {

            Log.d("rawdatachange", "in onHARrawdatachagen");
            mCollectCount += rawDataList.size();
            //mTextViewCollectNum.setText(mCollectCount + "");
            // save data
            mRawDataList.addAll(rawDataList);
            if (mRawDataList.size() > 1200) {
                mRawDataList.clear();
            }
            // raw sensor data
        }


    };
/*
    @Override
    protected void onResume() {
        super.onResume();
        getDataClient(this).addListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getDataClient(this).removeListener(this);
    }
*/

    private void initHAR() {
        mHAR = new HumanActivityRecognizer(this, true, HarMode.CLASSIFY, mHarDataListener);
        mHAR.start();
    }
}
    /*
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.d("inDataChanged","in data changed");
        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // DataItem changed
                DataItem item = event.getDataItem();
                if (item.getUri().getPath().compareTo("/ACCEL") == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();

                    ProcessString(dataMap.getString(ACCEL_KEY));
                }
            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
            }
        }
    }


    public void ProcessString(String acceldata){
        //accelData.add(acceldata);
        String [] eventarray =acceldata.split(",");
        Log.d("arraydata",eventarray[0]);
        Log.d("arraydata",eventarray[1]);
        Log.d("arraydata",eventarray[2]);
        Log.d("arraydata",eventarray[3]);
        mtextView.setText(mtextView.getText()+"\n"+acceldata);
    }
    */

