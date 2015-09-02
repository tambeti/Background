package com.litl.background;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MainActivity.this,
                        Service1.class);
                intent.putExtra(Service1.EXTRA_START, true);
                startService(intent);
            }
        });

        final Button stopButton = (Button) findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MainActivity.this,
                        Service1.class);
                intent.putExtra(Service1.EXTRA_STOP, true);
                startService(intent);
            }
        });


        final Button startButton2 = (Button) findViewById(R.id.startButton2);
        startButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MainActivity.this,
                        Service2.class);
                intent.putExtra(Service2.EXTRA_START, true);
                startService(intent);
            }
        });

        final Button stopButton2 = (Button) findViewById(R.id.stopButton2);
        stopButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MainActivity.this,
                        Service2.class);
                intent.putExtra(Service2.EXTRA_STOP, true);
                startService(intent);
            }
        });

        final Button startButton3 = (Button) findViewById(R.id.startButton3);
        startButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MainActivity.this,
                        Service3.class);
                intent.putExtra(Service3.EXTRA_START, true);
                startService(intent);
            }
        });

        final Button stopButton3 = (Button) findViewById(R.id.stopButton3);
        stopButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MainActivity.this,
                        Service3.class);
                intent.putExtra(Service3.EXTRA_STOP, true);
                startService(intent);
            }
        });
    }
}
