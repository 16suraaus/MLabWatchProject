package com.example.watchaccelstream;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

public class FileWriter {

    private static final String TAG = FileWriter.class.getSimpleName() + "_debug";

    private File directory;
    private String fileName;
    private String filePath;
    private FileOutputStream outputStream;
    private Context context;

    public FileWriter(Context context, String fileName) {
        this.directory = context.getFilesDir();
        this.fileName = fileName;
        this.filePath = this.directory + "/" + fileName;
        this.context = context;
    }

    public FileWriter(Context context, String directory, String fileName){
        this.directory = context.getDir(directory, Context.MODE_PRIVATE);
        this.fileName = fileName;
        this.filePath = this.directory + "/" + fileName;
        this.context = context;
    }

    public void write(String dataToAppend){
        try{
            outputStream = context.openFileOutput(this.fileName, Context.MODE_APPEND);
            outputStream.write(dataToAppend.getBytes());
            outputStream.close();
        }catch (Exception e){
            Log.d(TAG, "error in file writing "+ e.getMessage());
        }
    }

}
