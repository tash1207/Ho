package co.tashawych.ho.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.parse.ParsePush;
import com.parse.ParseUser;

import co.tashawych.ho.R;

public class Settings extends Activity {
    String username;
    int hos;
    boolean sound;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        username = getSharedPreferences("ho", 0).getString("username", "");
        hos = getSharedPreferences("ho", 0).getInt("hos", 0);
        sound = getSharedPreferences("ho", 0).getBoolean("sound", true);

        TextView current_user = (TextView) findViewById(R.id.current_user);
        current_user.setText("USER:" + username);
        TextView num_hos = (TextView) findViewById(R.id.num_hos);
        num_hos.setText("HO'S:" + hos);
        TextView toggle_sound = (TextView) findViewById(R.id.sound);
        String on_or_off = sound ? "OFF" : "ON";
        toggle_sound.setText("TURN SOUND " + on_or_off);
    }

    public void toggle_sound(View view) {
        sound = !sound;
        getSharedPreferences("ho", 0).edit().putBoolean("sound", sound).commit();

        TextView toggle_sound = (TextView) findViewById(R.id.sound);
        String on_or_off = sound ? "OFF" : "ON";
        toggle_sound.setText("TURN SOUND " + on_or_off);
    }

    public void what_is_ho(View view) {
        Intent what_is_ho = new Intent(this, WhatIsHo.class);
        startActivity(what_is_ho);
    }

    public void logout(View v) {
        ParseUser.logOut();
        ParsePush.unsubscribeInBackground(username);
        getSharedPreferences("ho", 0).edit().putString("username", "").commit();

        Intent initial = new Intent(this, InitialActivity.class);
        initial.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(initial);
        MainActivity.activity.finish();
        finish();
    }

    public void done(View v) {
        finish();
    }

}
