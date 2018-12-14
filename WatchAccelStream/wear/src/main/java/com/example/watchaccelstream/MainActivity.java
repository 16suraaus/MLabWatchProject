package com.example.watchaccelstream;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.wearable.Wearable.getDataClient;

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

    private TextView mTextViewFinishTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.text);

        // Enables Always-on
        setAmbientEnabled();
        Intent secondIntent = new Intent(this, LocationIntentService.class);
        startService(secondIntent);

    }


}




