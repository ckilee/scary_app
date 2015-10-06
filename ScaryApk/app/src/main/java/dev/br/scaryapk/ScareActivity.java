package dev.br.scaryapk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class ScareActivity extends Activity {
    private ImageView scareImageView;
    private int currentImageID = 0;
    private int currentSoundID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scare);

        Intent i = getIntent();

        currentImageID = i.getIntExtra(MainActivity.IMAGE_EXTRA,0);
        currentSoundID = i.getIntExtra(MainActivity.SOUND_EXTRA,0);

        scareImageView = (ImageView)findViewById(R.id.imageViewScare);


        Drawable image = getResources().getDrawable(currentImageID);
        if(scareImageView!=null)  //imageView is a ImageView
            scareImageView.setImageDrawable(image);

        MediaPlayer mp = MediaPlayer.create(this,currentSoundID);
        mp.start();

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
