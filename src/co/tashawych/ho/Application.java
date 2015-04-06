package co.tashawych.ho;

import com.parse.Parse;
import com.parse.ParseInstallation;

public class Application extends android.app.Application {

    public void onCreate() {
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, getString(R.string.application_id), getString(R.string.client_key));
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

}
