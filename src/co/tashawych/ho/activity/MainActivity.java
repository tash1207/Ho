package co.tashawych.ho.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import co.tashawych.ho.DBHelper;
import co.tashawych.ho.R;
import co.tashawych.ho.UserListAdapter;

public class MainActivity extends Activity {
	public static Activity activity;
	ListView lvw_users;
	ArrayList<String> users;
	String username;
	int hos;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		activity = this;
		username = getSharedPreferences("ho", 0).getString("username", "");
		hos = getSharedPreferences("ho", 0).getInt("hos", 0);
		
		updateUserList(this);
	}
	
	public static int getListViewHeight(ListView list) {
		ListAdapter adapter = list.getAdapter();
		int listviewHeight = 0;
		list.measure(MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED),
                   MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		listviewHeight = list.getMeasuredHeight() * adapter.getCount() + (adapter.getCount() * list.getDividerHeight());
		return listviewHeight;
    }

	public void add(View v) {
		final EditText add = (EditText) findViewById(R.id.add);
		add.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
					String text = add.getText().toString();
					InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(add.getWindowToken(), 0);
					if (text.length() >= 3 && text.length() <= 20) {
						DBHelper.getHelper(MainActivity.this).insertUser(text.trim());
						updateUserList(MainActivity.this);
						add.setText("");
						add.setHint("+");
					}
					else {
						Toast.makeText(MainActivity.this, "Usernames are always between 3-20 characters", 
								Toast.LENGTH_SHORT).show();
					}
				}
				return false;
			}
		});
	}
	
	public static void updateUserList(Context context) {
		ArrayList<String> users = DBHelper.getHelper(context).getUsers();
		users.add(0, context.getString(R.string.ho_yourself));
		
		String username = context.getSharedPreferences("ho", 0).getString("username", "");
		int hos = context.getSharedPreferences("ho", 0).getInt("hos", 0);
		
		ListView lvw_users = (ListView) MainActivity.activity.findViewById(R.id.lvw_users);
		lvw_users.setAdapter(new UserListAdapter(context, R.layout.user_view, users, username, hos));
		lvw_users.getLayoutParams().height = getListViewHeight(lvw_users);
	}

	public void settings(View v) {
		Intent settings = new Intent(this, Settings.class);
		startActivity(settings);
	}

}
