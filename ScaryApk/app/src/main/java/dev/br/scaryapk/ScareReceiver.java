package dev.br.scaryapk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScareReceiver extends BroadcastReceiver {
    public ScareReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //start activity
        Intent i = new Intent();
        i.setClassName("dev.br.scaryapk", "dev.br.scaryapk.ScareActivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
