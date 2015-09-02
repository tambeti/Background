package com.litl.background;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Service2 extends Service {
    public static final String EXTRA_START = "start";
    public static final String EXTRA_STOP = "stop";

    private static final String TAG = Service1.class.getSimpleName();
    private AlarmManager mAlarmManager;
    private boolean mStarted = false;
    private SoundPool mSoundPool;
    private int mSound;
    private PendingIntent mPendingIntent;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            doWork();
            schedule();
        }
    };

    public Service2() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");

        mSoundPool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 0);
        try {
            final AssetFileDescriptor fd = getAssets().openFd("beep.wav");
            mSound = mSoundPool.load(fd, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        mPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent("tick"), 0);
        registerReceiver(mReceiver, new IntentFilter("tick"));
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        mAlarmManager.cancel(mPendingIntent);
        unregisterReceiver(mReceiver);
        mSoundPool.release();

        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");

        if (intent.getBooleanExtra(EXTRA_START, false) && !mStarted) {
            Log.i(TAG, "Starting");
            schedule();
            mStarted = true;
        } else if (intent.getBooleanExtra(EXTRA_STOP, false) && mStarted) {
            mAlarmManager.cancel(mPendingIntent);
            mStarted = false;
        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void schedule() {
        mAlarmManager.setExact(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(4),
                mPendingIntent);
    }

    private void doWork() {
        try {
            mSoundPool.play(mSound, 0.5f, 0.5f, 1, 0, 1);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }
}
