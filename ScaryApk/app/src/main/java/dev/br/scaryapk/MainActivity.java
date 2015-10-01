package dev.br.scaryapk;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
//public class MainActivity extends Activity {
    private Spinner imageSpinner;
    private ImageView imageView;
    private Spinner timeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageSpinner = (Spinner)findViewById(R.id.spinner_image);
        imageView = (ImageView)findViewById(R.id.imageView);
        configureViews();
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
    }

    public class ImageOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            Toast.makeText(parent.getContext(),
                    "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                    Toast.LENGTH_SHORT).show();
            String selected = parent.getItemAtPosition(pos).toString();
            int imageResource = 0;
            if(selected.equals("Doll")){
                imageResource = R.drawable.anabelle;
            } else if(selected.equals("Clown")){
                imageResource = R.drawable.clown;
            } else if(selected.equals("Daemon")){
                imageResource = R.drawable.demon;
            } else if(selected.equals("Scary Face 1")){
                imageResource = R.drawable.scaryface1;
            } else if(selected.equals("Scary Face 2")){
                imageResource = R.drawable.scaryface2;
            }
            Drawable image = getResources().getDrawable(imageResource);
            if(imageView!=null)
                imageView.setImageDrawable(image);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }
}
