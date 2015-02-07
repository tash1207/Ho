package co.tashawych.ho;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

import co.tashawych.ho.activity.InitialActivity;

public class Application extends android.app.Application {

    public void onCreate() {
        Parse.initialize(this, "ce4aFxEKMnJvkIRPXCfOwpN6sMIwTZyGoEzfyUuI",
                "dwOLdihNIXvYC3MxFwUxniSl7fjMOXhNrlLLJAc6");
        PushService.setDefaultPushCallback(this, InitialActivity.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

}
