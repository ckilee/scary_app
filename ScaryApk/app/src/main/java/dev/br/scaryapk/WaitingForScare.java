package dev.br.scaryapk;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class WaitingForScare extends Service {
    private final String LOG_TAG = "WaitingForScare";
    public WaitingForScare() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int timeDelay = intent.getIntExtra(MainActivity.TIME_EXTRA,5);
        timeDelay = timeDelay*1000;

        try {
            Thread.sleep(timeDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent scareIntent = new Intent();
        scareIntent.setAction("dev.br.scaryapk.OPEN_SCARE_SCREEN");
        sendBroadcast(scareIntent);
        return START_STICKY;
    }
}
