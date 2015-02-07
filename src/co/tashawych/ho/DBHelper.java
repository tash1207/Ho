package co.tashawych.ho;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
	public final static String DB_NAME = "ho";
	public final static int DB_VERSION = 1;
	public final static String TABLE_NAME = "users";

	private static DBHelper helper = null;
	private static SQLiteDatabase db = null;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	public static DBHelper getHelper(Context context) {
		if (helper == null) {
			helper = new DBHelper(context);
			db = helper.getWritableDatabase();
		}
		return helper;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
				+ "username TEXT PRIMARY KEY, " + "num_hos INTEGER);";
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public void insertUser(String username) {
		ContentValues cv = new ContentValues();
		cv.put("username", username);
		cv.put("num_hos", 0);
		db.insert(TABLE_NAME, null, cv);
	}
	
	public void increaseHos(String username) {
		db.execSQL("UPDATE " + TABLE_NAME + " SET num_hos = num_hos + 1 WHERE username = ?", new String[] { username });
	}

	public void deleteUser(String username) {
		String DELETE = "DELETE FROM " + TABLE_NAME + " WHERE username = '" + username + "'";
		db.execSQL(DELETE);
		//db.delete(TABLE_NAME, "username = ?", new String[] { username });
	}

	public ArrayList<String> getUsers() {
		String QUERY = "SELECT username FROM " + TABLE_NAME;
		Cursor cursor = db.rawQuery(QUERY, null);
		ArrayList<String> users = new ArrayList<String>();

		for (boolean hasItem = cursor.moveToFirst(); hasItem; hasItem = cursor.moveToNext()) {
			users.add(cursor.getString(cursor.getColumnIndex("username")));
		}
		return users;
	}

}
