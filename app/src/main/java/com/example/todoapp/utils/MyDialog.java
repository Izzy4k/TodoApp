package com.example.todoapp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.View;

import com.example.todoapp.R;

public class MyDialog {
    private Dialog dialog ;

    public MyDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = activity.getLayoutInflater().inflate(R.layout.progress,null);
        builder.setView(view);
        dialog = builder.create();
    }

    public  void show (){
        dialog.show();
    }
    public void dismiss(){
        dialog.dismiss();
    }
}
