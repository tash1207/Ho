package co.tashawych.ho.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import co.tashawych.ho.R;

public class InitialActivity extends Activity {
    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        if (!getSharedPreferences("ho", 0).getString("username", "").equals("")) {
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
            finish();
        }

        activity = this;
    }

    public void signup(View view) {
        Intent signup = new Intent(this, Signup.class);
        startActivity(signup);
    }

    public void login(View view) {
        Intent login = new Intent(this, Login.class);
        startActivity(login);
    }

    public void what_is_ho(View view) {
        Intent what_is_ho = new Intent(this, WhatIsHo.class);
        startActivity(what_is_ho);
    }

}
