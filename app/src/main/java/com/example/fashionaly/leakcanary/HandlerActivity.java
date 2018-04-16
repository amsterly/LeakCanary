package com.example.fashionaly.leakcanary;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.squareup.leakcanary.RefWatcher;

public class HandlerActivity extends AppCompatActivity {
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // do something you want
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        mHandler.sendMessageDelayed(Message.obtain(), 10000);
        //just finish this activity
        try {
            Thread.sleep(2000);
            finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MApplication.getRefWatcher(this);
        refWatcher.watch(this);
    }

}
