package com.example.lindsay.implicitintents;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class L {
    public static void logMessage(String message){
        Log.d("APP", message);
    }
    public static void displayToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
