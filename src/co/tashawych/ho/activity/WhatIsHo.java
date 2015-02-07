package co.tashawych.ho.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import co.tashawych.ho.R;

public class WhatIsHo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_is_ho);
    }

    public void done(View v) {
        finish();
    }

}
