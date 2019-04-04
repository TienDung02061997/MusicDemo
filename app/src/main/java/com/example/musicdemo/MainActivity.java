package com.example.musicdemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButtonStart, mButtonStop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonStart = (Button) findViewById(R.id.btn_start);
        mButtonStop = (Button) findViewById(R.id.btn_stop);

        mButtonStop.setOnClickListener(this);
        mButtonStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent =new Intent(this,MusicService.class);
        switch (v.getId()){
            case R.id.btn_start:
                intent.setAction(MusicService.ACTION_START_SERVICE);
                startService(intent);
                bindService(intent,mServiceConnection,BIND_AUTO_CREATE);
                break;
            case R.id.btn_stop:
                intent.setAction(MusicService.ACTION_STOP_SERVICE);
                startService(intent);
                break;
        }
    }
    ServiceConnection mServiceConnection =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
//
//    @Override
//    protected void onPause() {
//        unbindService(mServiceConnection);
//        super.onPause();
//    }

    @Override
    protected void onStop() {
       unbindService(mServiceConnection);
        super.onStop();
    }
}
