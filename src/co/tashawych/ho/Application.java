package co.tashawych.ho;

import co.tashawych.ho.activity.InitialActivity;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

public class Application extends android.app.Application {

	public void onCreate() {
		Parse.initialize(this, "ce4aFxEKMnJvkIRPXCfOwpN6sMIwTZyGoEzfyUuI",
				"dwOLdihNIXvYC3MxFwUxniSl7fjMOXhNrlLLJAc6");
		PushService.setDefaultPushCallback(this, InitialActivity.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
	}

}
