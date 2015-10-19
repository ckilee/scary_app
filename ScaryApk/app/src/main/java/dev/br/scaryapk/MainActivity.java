package dev.br.scaryapk;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends Activity {
//public class MainActivity extends Activity {
    private Spinner imageSpinner;
    private Spinner audioSpinner;
    private ImageView imageView;
    private Spinner timeSpinner;
    private Button scareButton;
    private Button playButton;
    private int currentTime;
    public static final String TIME_EXTRA = "TimeExtra";
    public static final String IMAGE_EXTRA = "ImageExtra";
    public static final String SOUND_EXTRA = "SoundExtra";
    private int currentImageID = 0;
    private int currentSoundID = 0;
    private InterstitialAd mInterstitialAd;
    private static MainActivity Instance;

    public MainActivity(){
        super();
        Instance = this;
    }

    public static MainActivity getInstance(){
        return Instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadAdware();

        imageSpinner = (Spinner)findViewById(R.id.spinner_image);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.image_arrays, R.layout.spinnertext);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        imageSpinner.setAdapter(adapter);
        //when an item is selected call the selectSensor method
        //imageSpinner.setOnItemSelectedListener(new ImageOnItemSelectedListener());

        imageView = (ImageView)findViewById(R.id.imageView);

        scareButton = (Button)findViewById(R.id.button_scare);
        playButton = (Button)findViewById(R.id.button_play);
        playButton.setWidth(playButton.getHeight());
        audioSpinner = (Spinner)findViewById(R.id.spinner_audio);
        ArrayAdapter adapterAudio = ArrayAdapter.createFromResource(this, R.array.audio_arrays, R.layout.spinnertext);
        adapterAudio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        audioSpinner.setAdapter(adapterAudio);


        timeSpinner = (Spinner)findViewById(R.id.spinner_time);
        ArrayAdapter adapterTime = ArrayAdapter.createFromResource(this, R.array.time_arrays, R.layout.spinnertext);
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(adapterTime);

        currentTime = 5;
        configureViews();
    }

    private void loadAdware(){
        //Adware Main Activity
        AdView mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setAdUnitId("ca-app-pub-9505366133372164/4738060338");
        AdRequest adRequest = new AdRequest.Builder().build();

        /*AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("E112885C2D32D31690C7B60F25C89356")
                .addTestDevice("13E7A5DDF2981F979D554ED02BC571B3")
                .build();*/
        mAdView.loadAd(adRequest);

        //Adware interstitial ads
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(this.getString(R.string.banner_ad_unit_id));
        /*AdRequest adRequestInterstitialAd = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("E112885C2D32D31690C7B60F25C89356")
                .addTestDevice("13E7A5DDF2981F979D554ED02BC571B3")
                .build();*/
        AdRequest adRequestInterstitialAd = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequestInterstitialAd);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void configureViews(){
        imageSpinner.setOnItemSelectedListener(new ImageOnItemSelectedListener());

        timeSpinner.setOnItemSelectedListener(new TimeOnItemSelectedListener());

        audioSpinner.setOnItemSelectedListener(new SoundOnItemSelectedListener());

        //Scare Button
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                doScare();
            }
        };
        scareButton.setOnClickListener(listener);

        //Play Button
        View.OnClickListener listenerPlay = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                playSound();
            }
        };
        playButton.setOnClickListener(listenerPlay);
    }

    private void doScare(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, currentTime);
        Intent myIntent = new Intent(MainActivity.this, ScareReceiver.class);
        myIntent.putExtra(TIME_EXTRA,currentTime);
        myIntent.putExtra(IMAGE_EXTRA, currentImageID);
        myIntent.putExtra(SOUND_EXTRA, currentSoundID);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);

        this.finish();

    }

    private void playSound(){
        String selected = audioSpinner.getSelectedItem().toString();
        int rawAudio = 0;
        if(selected.equals("Audio 1"))
            rawAudio = R.raw.audio1;
        if(selected.equals("Audio 2"))
            rawAudio = R.raw.audio2;
        if(selected.equals("Audio 3"))
            rawAudio = R.raw.audio3;
        if(selected.equals("Audio 4"))
            rawAudio = R.raw.audio4;
        currentSoundID = rawAudio;

        MediaPlayer mp = MediaPlayer.create(this,rawAudio);
        mp.start();
    }

    public class ImageOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {

            String selected = parent.getItemAtPosition(pos).toString();
            int imageResource = 0;
            if(selected.equals("Daughter")){
                imageResource = R.drawable.daughter;
            } else if(selected.equals("Clown")){
                imageResource = R.drawable.clown;
            } else if(selected.equals("Daemon")){
                imageResource = R.drawable.demon;
            } else if(selected.equals("Scary Face 1")){
                imageResource = R.drawable.scaryface1;
            } else if(selected.equals("Scary Face 2")){
                imageResource = R.drawable.scaryface2;
            }
            currentImageID = imageResource;
            Drawable image = getResources().getDrawable(imageResource);
            if(imageView!=null)  //imageView is a ImageView
                imageView.setImageDrawable(image);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }

    public class TimeOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {

            String selected = parent.getItemAtPosition(pos).toString();
            if(selected.equals("10 seconds")){
                currentTime = 10;
            } else if(selected.equals("15 seconds")){
                currentTime = 15;
            } else if(selected.equals("20 seconds")){
                currentTime = 20;
            } else if(selected.equals("25 seconds")){
                currentTime = 25;
            } else if(selected.equals("30 seconds")){
                currentTime = 30;
            } else if(selected.equals("35 seconds")){
                currentTime = 35;
            } else if(selected.equals("60 seconds")){
                currentTime = 60;
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }

    public class SoundOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {

            String selected = parent.getItemAtPosition(pos).toString();
            if(selected.equals("Audio 1"))
                currentSoundID = R.raw.audio1;
            if(selected.equals("Audio 2"))
                currentSoundID = R.raw.audio2;
            if(selected.equals("Audio 3"))
                currentSoundID = R.raw.audio3;
            if(selected.equals("Audio 4"))
                currentSoundID = R.raw.audio4;

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }

    public InterstitialAd getMInterstitialAd(){
        return mInterstitialAd;
    }
}


