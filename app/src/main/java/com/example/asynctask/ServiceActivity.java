package com.example.asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ServiceActivity extends AppCompatActivity {

    Button start, stop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        start = findViewById(R.id.startButton);
        stop = findViewById(R.id.stopButton);

        start.setOnClickListener(view -> {
            Intent i = new Intent(ServiceActivity.this, MyService.class);
            startService(i);
        });

        stop.setOnClickListener(view -> {
            Intent i = new Intent(ServiceActivity.this, MyService.class);
            stopService(i);
        });

    }
}