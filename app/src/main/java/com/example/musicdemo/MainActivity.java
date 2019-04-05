package com.example.musicdemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MusicService mMusicService;
    private boolean mCheck;
    private Button mButtonStart, mButtonStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        msetfindbyid();
        msetClick();


    }

    private void msetfindbyid() {
        mButtonStart = (Button) findViewById(R.id.btn_start);
        mButtonStop = (Button) findViewById(R.id.btn_stop);
    }


    private void msetClick() {
        mButtonStart.setOnClickListener(this);
        mButtonStop.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MusicService.class);
        switch (v.getId()) {
            case R.id.btn_start:
                intent.setAction(MusicService.ACTION_START_SERVICE);
                startService(intent);
                bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.btn_stop:
                stopService(intent);
                unbindService(mServiceConnection);
                break;
        }
    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MyConnections myConnections = (MusicService.MyConnections) service;
            mMusicService = myConnections.getConnection();
            mCheck = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mCheck = false;
        }
    };

    @Override
    protected void onStop() {
        unbindService(mServiceConnection);
        super.onStop();
    }
}
