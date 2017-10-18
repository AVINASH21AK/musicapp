package com.smartpocket.musicwidget;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Avinash Kahal on 18-Oct-17.
 */

public class App extends Application {

    Context context;


    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
    }


    public static void showLog(String ActivityName, String strMessage) {
        Log.d("From: ", ActivityName+" -- "+strMessage);
    }


    public static void showSnackBar(View view, String strMessage) {
        Snackbar snackbar = Snackbar.make(view, strMessage, Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.BLACK);
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }
}
