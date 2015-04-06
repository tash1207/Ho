package co.tashawych.ho.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.ParseUser;

import co.tashawych.ho.R;

public class Login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        EditText edit_username = (EditText) findViewById(R.id.edit_username);
        final String username = edit_username.getText().toString();
        EditText edit_password = (EditText) findViewById(R.id.edit_password);
        final String password = edit_password.getText().toString();

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    // Save username to SharedPrefs
                    SharedPreferences prefs = getSharedPreferences("ho", 0);
                    prefs.edit().putString("username", username).commit();

                    // Subscribe to push notifications
                    ParsePush.subscribeInBackground(username);
                    finishLogin();
                } else {
                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void finishLogin() {
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
        InitialActivity.activity.finish();
        finish();
    }

}
