package co.tashawych.ho;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

import co.tashawych.ho.activity.InitialActivity;

public class Application extends android.app.Application {

    public void onCreate() {
        Parse.initialize(this, getString(R.string.application_id), getString(R.string.client_key));
        PushService.setDefaultPushCallback(this, InitialActivity.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

}
