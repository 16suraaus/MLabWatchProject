package com.rockcode.har;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.wearable.Wearable.getDataClient;

/**
 * Collect the accelerometer sensor data for a period of time(one recognition cycle).
 */
class SensorDataCollector  implements DataClient.OnDataChangedListener{

	/**
	 * use to label target activity on collect mode
	 */
	private String mActivity = HumanActivity.ACTIVITY_NOLABEL;


	private static final String ACCEL_KEY = "com.example.key.ACCEL";
	/**
	 * the collect sensor data list on this cycle
	 */
	private List<RawData> mRawDataList;

	/**
	 * sensor data collect listener
	 */

	private SensorDataListener mSensorDataListener;


	boolean start(Context context) {
		getDataClient(context).addListener(this);
		return true;
	}
	boolean stop(Context context){
		getDataClient(context).removeListener(this);
		return true;
	}
	/**
	 *	SensorDataCollector
	 * @param context Context
	 * @param sensorDataListener Sensor data Collect Listener
	 */
	SensorDataCollector(Context context, SensorDataListener sensorDataListener) {
		mSensorDataListener = sensorDataListener;
		mRawDataList = new ArrayList<>();

	}

	/**
	 * set label target activity on collect mode
	 * @param activity need label activity
	 */
	void setLabelActivity(String activity) {
		mActivity = activity;
	}

	public void onDataChanged(DataEventBuffer dataEvents) {
		Log.d("inDataChanged","in data changed");
		for (DataEvent event : dataEvents) {
			if (event.getType() == DataEvent.TYPE_CHANGED) {
				// DataItem changed
				DataItem item = event.getDataItem();
				if (item.getUri().getPath().compareTo("/ACCEL") == 0) {
					DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();

					onSensorChanged(dataMap.getString(ACCEL_KEY));
				}
			} else if (event.getType() == DataEvent.TYPE_DELETED) {
				// DataItem deleted
			}
		}
	}



		public void onSensorChanged(String event) {
			// add to RawDataList
			String [] eventarray = event.split(",");
			RawData rawData = addRawData(Long.parseLong(eventarray[0]), Float.parseFloat(eventarray[1]), Float.parseFloat(eventarray[2]), Float.parseFloat(eventarray[3]));
			// get diff time of this collect cycle
			long alreadyCollectTime = mRawDataList.get(0).diffTimeAbs(rawData);
			// has collect enought time ?
			if( ( alreadyCollectTime > HarConfigs.getSensorCollectTime()) ){
				// has collect enough data number ?
				if(mRawDataList.size() >= HarConfigs.getLeastSampleNumber()) {
					LogUtil.info("HarLib - " + "collect time: " + alreadyCollectTime
							+ " RawData Num: " + mRawDataList.size());
					// notify data collect ok
					mSensorDataListener.onSensorDataChange(mRawDataList);
					mRawDataList.clear();
				}
			}
		}




	/**
	 * add RawData to List
	 * @param timestamp Sensor event timestamp
	 * @param x	The X axis value
	 * @param y	The Y axis value
	 * @param z The Z axis value
	 * @return RawData
	 */
	private RawData addRawData(long timestamp, float x, float y, float z) {
		RawData rawData = new RawData(mActivity, timestamp, x, y, z);
		mRawDataList.add(rawData);
		return rawData;
	}

}
