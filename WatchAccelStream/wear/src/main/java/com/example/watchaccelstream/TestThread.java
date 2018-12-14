package com.example.watchaccelstream;

import android.content.Context;
import android.util.Log;

import java.util.Date;

public class TestThread extends Thread{

    private static final String TAG = TestThread.class.getSimpleName() + "_debug";
    private FileWriter fileWriter;
    private Context context;


    public TestThread(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        super.run();

        int counter = 0;
        fileWriter = new FileWriter(context, "threadlog.txt");
        while(true){
            try {
                sleep(5000);
                counter ++;
                Log.d(TAG, "I am test thread "+ new Date());
                //if (counter>10){

                //}
                fileWriter.write(new Date() + " ------- I am from test thread.....");

            }catch (Exception e){
                Log.d(TAG, "thread error "+ e.getMessage());
            }

        }
    }
}
