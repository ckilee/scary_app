package dev.br.scaryapk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.Map;


public class ScareActivity extends Activity {
    private ImageView scareImageView;
    private int currentImageID = 0;
    private int currentSoundID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scare);

        AudioManager am =
                (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        am.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                0);

        Intent i = getIntent();

        currentImageID = i.getIntExtra(MainActivity.IMAGE_EXTRA,0);
        currentSoundID = i.getIntExtra(MainActivity.SOUND_EXTRA,0);
        scareImageView = (ImageView)findViewById(R.id.imageViewScare);

        MediaPlayer mp = MediaPlayer.create(this,currentSoundID);
        mp.start();

        Vibrator v = (Vibrator) this.getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(1000);

        Drawable image = getResources().getDrawable(currentImageID);
        if(scareImageView!=null)  //imageView is a ImageView
            scareImageView.setImageDrawable(image);

        stopService(new Intent(ScareActivity.this, WaitingForScare.class));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scare, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
