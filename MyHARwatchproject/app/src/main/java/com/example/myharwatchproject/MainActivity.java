package com.example.myharwatchproject;

import android.os.Bundle;
import android.os.Environment;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.wearable.DataClient;
import com.rockcode.har.HarDataListener;
import com.rockcode.har.HarMode;
import com.rockcode.har.HumanActivity;
import com.rockcode.har.HumanActivityRecognizer;
import com.rockcode.har.RawData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends WearableActivity {

    private TextView mTextView;
    private TextView myResultTextView;
    private int mCollectCount = 0;
    private long mStartTime = 0;
    private long mFinishTime = 0;
    private String mSaveFileName = null;
    private TextView mTextViewLabelActivity;
    private TextView mTextViewCollectNum;
    private TextView mTextViewStartTime;
    private DataClient mDataClient;
    private TextView mTextViewFinishTime;
    private List<RawData> mRawDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);

        // Enables Always-on
        setAmbientEnabled();
        initHAR();
    }
    HumanActivityRecognizer mHAR;

    private void initHAR() {
        mHAR = new HumanActivityRecognizer(this, true, HarMode.CLASSIFY, mHarDataListener);
        mHAR.start();
    }

    private HarDataListener mHarDataListener = new HarDataListener() {
        @Override
        public void onHarDataChange(HumanActivity ha) {

            Log.d("datachange","ha = "+ha);
            mTextView.setText(ha.mActivity);
        }


        @Override
        public void onHarRawDataChange(List<RawData> rawDataList) {

            Log.d("rawdatachange","in onHARrawdatachagen");
            mCollectCount += rawDataList.size();
//            mTextViewCollectNum.setText(mCollectCount + "");
            // save data
            mRawDataList.addAll(rawDataList);
            if (mRawDataList.size() > 1200) {
                mRawDataList.clear();
            }
            // raw sensor data
        }


    };



}
