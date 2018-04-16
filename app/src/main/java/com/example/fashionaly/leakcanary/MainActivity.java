package com.example.fashionaly.leakcanary;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.squareup.leakcanary.RefWatcher;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(
                R.layout.activity_main);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent intent=new Intent(this,HandlerActivity.class);

            startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        startAsyncTask();
    }

    public void onClick(View view)
    {
        finish();
    }
    void startAsyncTask() {

        // This async task is an anonymous class and therefore has a hidden reference to the outer
        // class MainActivity. If the activity gets destroyed before the task finishes (e.g. rotation),
        // the activity instance will leak.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                // Do some slow work in background
                SystemClock.sleep(200000);
                return null;
            }
        }.execute();
        Toast.makeText(this, "请关闭这个A完成泄露", Toast.LENGTH_SHORT).show();
    }
    @Override public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MApplication.getRefWatcher(this);
        refWatcher.watch(this);
    }
}
