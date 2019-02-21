package com.example.modernartui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    static private final String URL = "http://www.moma.org";
    private SeekBar ctrlBar;
    private ArrayList<FrameLayout> boxes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boxes = new ArrayList<>();
        ctrlBar = findViewById(R.id.seekBar);
        boxes.add((FrameLayout) findViewById(R.id.blue));
        boxes.add((FrameLayout) findViewById(R.id.white));
        boxes.add((FrameLayout) findViewById(R.id.pink));
        boxes.add((FrameLayout) findViewById(R.id.melon));
        boxes.add((FrameLayout) findViewById(R.id.sofgreen));
        boxes.add((FrameLayout) findViewById(R.id.pink));
        boxes.add((FrameLayout) findViewById(R.id.sbox1));
        boxes.add((FrameLayout) findViewById(R.id.sbox2));
        boxes.add((FrameLayout) findViewById(R.id.sbox3));

        ctrlBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                for(FrameLayout box: boxes){
                    box.setBackgroundColor(Color.argb(255, Color.red(((ColorDrawable)
                                    box.getBackground()).getColor()),
                                    Color.green(((ColorDrawable) box.getBackground()).getColor()),
                                    255-progress
                                    ));
                }
            }
        });
    }




    public boolean onCreateOptionsMenu (Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_options_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                new AlertDialog.Builder(this)
                        .setTitle("Would you like to visit MOMA?")
                        .setMessage("Check it out!")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent baseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
                                startActivity(baseIntent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.btn_star_big_on)
                        .show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
