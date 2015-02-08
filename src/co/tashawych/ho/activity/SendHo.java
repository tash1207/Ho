package co.tashawych.ho.activity;

import android.app.Activity;
import android.os.Bundle;

import com.parse.ParseInstallation;
import com.parse.ParsePush;

import org.json.JSONException;
import org.json.JSONObject;

import co.tashawych.ho.DBHelper;

/**
 * Activity with no UI that simply sends a Ho and updates the DB.
 * Created by Tasha on 2/6/15.
 */
public class SendHo extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParsePush push = new ParsePush();
        String user_to_ho = getIntent().getStringExtra("user_to_ho");
        push.setChannel(user_to_ho);

        String username = getSharedPreferences("ho", 0).getString("username", "");
        int id = ParseInstallation.getCurrentInstallation().hashCode();
        int num_hos = getSharedPreferences("ho", 0).getInt("hos", 0);

        try {
            JSONObject data = new JSONObject();
            data.put("action", "co.tashawych.ho.SEND_HO");
            data.put("username", username);
            data.put("id", id);
            push.setData(data);
        } catch (JSONException e) {
            e.printStackTrace();
            push.setMessage("From " + username);
        }
        push.sendInBackground();

        getSharedPreferences("ho", 0).edit().putInt("hos", ++num_hos).commit();
        DBHelper.getHelper(this).increaseHos(user_to_ho);

        finish();
    }
}
