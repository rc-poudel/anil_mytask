package com.example.anee.mytask.Service;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.anee.mytask.R;

public class Alert extends AppCompatActivity {
    MediaPlayer mp;
    int reso=R.raw.chec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        mp=MediaPlayer.create(getApplicationContext(),reso);
        mp.start();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String msg = getString(R.string.alarmtext) + getIntent().getExtras().getString(getString(R.string.title_msg));
        builder.setMessage(msg).setCancelable(
                false).setPositiveButton(getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Alert.this.finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    @Override

    public void onDestroy() {

        super.onDestroy();

        mp.release();

    }

}