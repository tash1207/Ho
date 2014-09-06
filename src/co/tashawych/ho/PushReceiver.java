package co.tashawych.ho;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import co.tashawych.ho.activity.MainActivity;

public class PushReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent != null) {
			try {
				JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
				String username = json.getString("username");
				String title = context.getString(R.string.app_name);
				if (json.has("title_text")) {
					title = json.getString("title_text");
				}
				
				Uri sound = Uri.parse("android.resource://co.tashawych.ho/raw/ho");
				
				Intent notificationIntent = new Intent(context, MainActivity.class);
				notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, 
			    		notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
			    
			    boolean sound_on = context.getSharedPreferences("ho", 0).getBoolean("sound", true);
			    
				NotificationCompat.Builder builder =
			            new NotificationCompat.Builder(context)
			            .setContentIntent(pendingIntent)
			            .setAutoCancel(true)
			            .setSmallIcon(R.drawable.ic_launcher)
			            .setLights(0xffff0000, 300, 1000)
			            .setVibrate(new long[]{0, 200, 250, 200})
			            .setContentTitle(title)
			            .setContentText("From " + username);
				if (sound_on) builder.setSound(sound);
								
				NotificationManager mgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
				int received_hos = context.getSharedPreferences("ho", 0).getInt("received_hos", 0);
			    mgr.notify(received_hos, builder.build());
			    context.getSharedPreferences("ho", 0).edit().putInt("received_hos", ++received_hos).commit();	
			    
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

}
