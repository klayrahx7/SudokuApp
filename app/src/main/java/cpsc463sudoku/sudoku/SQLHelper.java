package cpsc463sudoku.sudoku;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLHelper extends SQLiteOpenHelper
{
	protected static final String DATABASE_NAME = "SudokuDB";
	private static final int DATABASE_VERSION = 1;

	public static final String TABLE_BOARDS = "boards";
	public static final String TABLE_USERS = "users";
	public static final String TABLE_SCORES = "scores";
	public static final String TABLE_SAVES = "saves";
	public static final String TABLE_SETTINGS = "settings";

	/*Boards table*/
	public static final String COLUMN_BOARDID = "_boardID";
	public static String COLUMN_BOARD_INITIAL_STATE = "boardInitialState";

	/*Users table*/
	public static final String COLUMN_USERID = "_userID";
	public static String COLUMN_USER_NAME = "";
	public static String COLUMN_USER_PASSWORD = "";

	/*Scores table*/
	public static final String COLUMN_SCOREID = "_scoreID";
	public static String COLUMN_SCORE = "";

	/*Saves table*/
	public static final String COLUMN_SAVEID = "_saveID";
	public static String COLUMN_SAVE_BOARD_STATE = "boardState";
	// Please store a save state in the following CSV format:
	// {COLUMN_SAVEID, COLUMN_USERNAME, COLUMN_BOARD_STATE}
	public static String COLUMN_SAVE_STATES = "";

	/*Settings table*/
	public static final String COLUMN_SETTINGSID = "_settingsID";
	public static String COLUMN_SETTINGS_DIFFICULTY = "difficulty";

	// Database creation sql statements
	private final String CREATE_BOARDS_TABLE = "create table " + TABLE_BOARDS + "(" + COLUMN_BOARDID
			+ " integer primary key autoincrement, " + COLUMN_BOARD_INITIAL_STATE + " text not null);";

	private final String CREATE_USERS_TABLE = "create table " + TABLE_USERS + "(" + COLUMN_USERID
			+ " integer primary key autoincrement, " + COLUMN_USER_NAME + "text not null, " + COLUMN_USER_PASSWORD + " text not null);";

	private final String CREATE_SCORES_TABLE = "create table " + TABLE_SCORES + "(" + COLUMN_SCOREID
			+ " integer primary key autoincrement, " + COLUMN_SCORE + " text not null);";

	private final String CREATE_SAVES_TABLE = "create table " + TABLE_SAVES + "(" + COLUMN_SAVEID
			+ " integer primary key autoincrement, " + COLUMN_SAVE_BOARD_STATE + "text not null , " + COLUMN_SAVE_STATES + " text not null);";

	private final String CREATE_SETTINGS_TABLE = "create table " + TABLE_SETTINGS + "(" + COLUMN_SETTINGSID
			+ " integer primary key autoincrement, " + COLUMN_SETTINGS_DIFFICULTY + " text not null);";

	public SQLHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database)
	{
		database.execSQL(CREATE_BOARDS_TABLE);
		database.execSQL(CREATE_USERS_TABLE);
		database.execSQL(CREATE_SCORES_TABLE);
		database.execSQL(CREATE_SAVES_TABLE);
		database.execSQL(CREATE_SETTINGS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.w(SQLHelper.class.getName(), "Upgrading com.sudoku.csuf.database from version " + oldVersion + " to " + newVersion
				+ ", which will destroy all old data");

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOARDS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);
		onCreate(db);
	}

	public String GetBoardsTable()
	{
		return TABLE_BOARDS;
	}
	public String GetUsersTable()
	{
		return TABLE_USERS;
	}
	public String GetScoresTable()
	{
		return TABLE_SCORES;
	}
	public String GetSavesTable()
	{
		return TABLE_SAVES;
	}
	public String GetSettingsTable()
	{
		return TABLE_SETTINGS;
	}
}
