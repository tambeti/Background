package com.litl.background;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

public class Service1 extends Service {
    public static final String EXTRA_START = "start";
    public static final String EXTRA_STOP = "stop";

    private static final String TAG = Service1.class.getSimpleName();
    private Thread mThread;
    private boolean mStarted = false;
    private SoundPool mSoundPool;
    private int mSound;

    public Service1() {
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

        if (intent.getBooleanExtra(EXTRA_START, false) && !mStarted) {
            Log.i(TAG, "Starting");
            mThread.start();
            mStarted = true;
        } else if (intent.getBooleanExtra(EXTRA_STOP, false) && mStarted) {
            mThread.interrupt();
            mStarted = false;
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
