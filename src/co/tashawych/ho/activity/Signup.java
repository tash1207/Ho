package co.tashawych.ho.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.PushService;

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
        String username = edit_username.getText().toString();
        if (username.length() < 3 && username.length() > 20) {
            Toast.makeText(this, getString(R.string.warning_invalid_length),
                    Toast.LENGTH_SHORT).show();
        } else if (!StringUtils.isAlphanumeric(username)) {
            Toast.makeText(this, getString(R.string.warning_invalid_chars),
                    Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences prefs = getSharedPreferences("ho", 0);
            prefs.edit().putString("username", username).commit();
            PushService.subscribe(this, username, MainActivity.class);

            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
            InitialActivity.activity.finish();
            finish();
        }
    }

}
