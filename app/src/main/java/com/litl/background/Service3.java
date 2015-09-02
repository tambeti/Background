package com.litl.background;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import java.io.IOException;

public class Service3 extends Service {
    public static final String EXTRA_START = "start";
    public static final String EXTRA_STOP = "stop";

    private static final String TAG = Service1.class.getSimpleName();
    private PowerManager mPowerManager;
    private PowerManager.WakeLock mWakeLock;
    private Thread mThread;
    private SoundPool mSoundPool;
    private int mSound;

    public Service3() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");

        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
            }
        });

        mSoundPool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 0);
        try {
            final AssetFileDescriptor fd = getAssets().openFd("beep.wav");
            mSound = mSoundPool.load(fd, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        mSoundPool.release();

        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");

        if (intent.getBooleanExtra(EXTRA_START, false) && mWakeLock == null) {
            Log.i(TAG, "Starting");
            mWakeLock = mPowerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
            mWakeLock.acquire();
            mThread.start();
        } else if (intent.getBooleanExtra(EXTRA_STOP, false) && mWakeLock != null) {
            mThread.interrupt();
            mWakeLock.release();
        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void doWork() {
        while (true) {
            try {
                Thread.sleep(5000);
                mSoundPool.play(mSound, 0.5f, 0.5f, 1, 0, 1);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
