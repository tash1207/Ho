package co.tashawych.ho.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.apache.commons.lang3.StringUtils;

import co.tashawych.ho.R;

public class Signup extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void signup(View view) {
        EditText edit_username = (EditText) findViewById(R.id.edit_username);
        final String username = edit_username.getText().toString();
        EditText edit_password = (EditText) findViewById(R.id.edit_password);
        final String password = edit_password.getText().toString();
        if (isValidUsername(username) && isValidPassword(password)) {
            createNewUser(username, password);
        }
    }

    /**
     * Checks if username is between 3 and 20 characters long and if it only contains
     * alphanumeric characters.
     * @param username
     * @return Whether or not the username is valid
     */
    public boolean isValidUsername(String username) {
        if (username.length() < 3 && username.length() > 20) {
            Toast.makeText(this, getString(R.string.warning_invalid_length),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (!StringUtils.isAlphanumeric(username)) {
            Toast.makeText(this, getString(R.string.warning_invalid_chars),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks if password is at least 3 characters long.
     * @param password
     * @return Whether or not the password is valid
     */
    public boolean isValidPassword(String password) {
        if (password.length() < 3) {
            Toast.makeText(this, getString(R.string.warning_password_length),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public void createNewUser(final String username, final String password) {
        // Save user to Parse
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Subscribe to push notifications
                    ParsePush.subscribeInBackground(username);

                    // Save username to SharedPrefs
                    SharedPreferences prefs = getSharedPreferences("ho", 0);
                    prefs.edit().putString("username", username).commit();

                    finishSignup();
                } else {
                    Toast.makeText(Signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void finishSignup() {
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
        InitialActivity.activity.finish();
        finish();
    }

}
