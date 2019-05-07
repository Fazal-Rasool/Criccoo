package com.adaxiom.criccoo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SelectOver extends AppCompatActivity {



    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SelectOver.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_over);
        setTitle("");
    }
}
