package co.tashawych.ho;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import co.tashawych.ho.activity.MainActivity;

import com.parse.ParsePush;

public class UserListAdapter extends ArrayAdapter<String> {
	private Context context;
	private ArrayList<String> users;
	private String username;
	private int num_hos;

	public UserListAdapter(Context context, int layout, ArrayList<String> users, String username, int num_hos) {
		super(context, layout, users);
		this.context = context;
		this.users = users;
		this.username = username;
		this.num_hos = num_hos;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View userView = layoutInflater.inflate(R.layout.user_view, parent, false);
		
		TextView txt_user_to_ho = (TextView) userView.findViewById(R.id.username);
		txt_user_to_ho.setText(users.get(position));
		
		if (position % 3 == 0) {
			userView.setBackgroundResource(R.drawable.selector2);
		}
		else if (position % 3 == 1) {
			userView.setBackgroundResource(R.drawable.selector3);
		}
		else {
			userView.setBackgroundResource(R.drawable.selector1);
		}
		
		userView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("UserListAdapter", "onClick");
				ParsePush push = new ParsePush();
				String user_to_ho = users.get(position);
				if (user_to_ho.equals("HO YOURSELF")) {
					user_to_ho = username;
				}
				push.setChannel(user_to_ho);
				
				try {
					JSONObject data = new JSONObject();
					data.put("action", "co.tashawych.ho.SEND_HO");
					data.put("username", username);
					data.put("id", num_hos);
					push.setData(data);
				} catch (JSONException e) {
					e.printStackTrace();
					push.setMessage("From " + username);
				}
				push.sendInBackground();

				context.getSharedPreferences("ho", 0).edit().putInt("hos", ++num_hos).commit();
				DBHelper.getHelper(context).increaseHos(user_to_ho);
			}
		});
		
		userView.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				String user_to_delete = users.get(position);
				if (user_to_delete.equals("HO YOURSELF")) {
					return false;
				}
				// 1. Instantiate an AlertDialog.Builder with its constructor
				AlertDialog.Builder builder = new AlertDialog.Builder(context);

				// 2. Chain together various setter methods to set the dialog characteristics
				builder.setMessage(
						"Are you sure you want to delete " + user_to_delete + "?")
						.setTitle("Delete User")
						.setNegativeButton("NO", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}})
						.setPositiveButton("YES", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								DBHelper.getHelper(context).deleteUser(users.get(position));
								MainActivity.updateUserList(context);
								dialog.dismiss();
							}});

				// 3. Get the AlertDialog from create()
				AlertDialog dialog = builder.create();
				dialog.show();
				/*
				
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
			    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			    View view = inflater.inflate(R.layout.dialog_delete_user, null);
			    builder.setView(view);
				
			    final AlertDialog dialog = builder.create();
			    TextView dialog_text = (TextView) view.findViewById(R.id.dialog_text);
		    	dialog_text.setText("Are you sure you want to delete " + username + "?");
		    	
		    	Button btn_yes = (Button) view.findViewById(R.id.btn_yes);
		    	btn_yes.setText("YES");
		    	btn_yes.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						DBHelper.getHelper(context).deleteUser(users.get(position));
						users = DBHelper.getHelper(context).getUsers();
						notifyDataSetChanged();
						dialog.dismiss();
					}
				});
		    	
		    	Button btn_no = (Button) view.findViewById(R.id.btn_no);
		    	btn_no.setText("NO");
		    	btn_no.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
		    	
		    	dialog.show();
			    
				/*
				final Dialog dialog = new Dialog(context);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		    	dialog.setContentView(R.layout.dialog_delete_user);
		    	dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		    	
		    	TextView dialog_text = (TextView) dialog.findViewById(R.id.dialog_text);
		    	dialog_text.setText("Are you sure you want to delete " + username + "?");
		    	
		    	Button btn_yes = (Button) dialog.findViewById(R.id.btn_yes);
		    	btn_yes.setText("YES");
		    	btn_yes.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						DBHelper.getHelper(context).deleteUser(users.get(position));
						users = DBHelper.getHelper(context).getUsers();
						notifyDataSetChanged();
						dialog.dismiss();
					}
				});
		    	
		    	Button btn_no = (Button) dialog.findViewById(R.id.btn_no);
		    	btn_no.setText("NO");
		    	btn_no.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
		    	
		    	dialog.show();
		    	*/
				return true;
			}
		});
		return userView;
	}

}
