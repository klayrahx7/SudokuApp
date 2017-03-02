package cpsc463sudoku.sudoku;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseAdapter
{
	private static final String TAG = "Sudoku";
	
	private SQLiteDatabase database;
	private SQLHelper dbHelper;
	private Cursor boardsCursor;
	private Cursor usersCursor;
	private Cursor scoresCursor;
	private Cursor savesCursor;
	private Cursor settingsCursor;

	private boolean isProcessing;

	public DatabaseAdapter(Context c)
	{
		dbHelper = new SQLHelper(c);
		open();
	}

	public void addBoard(BoardAdapter newBoardAdapter)
	{
		ContentValues values = new ContentValues();

		values.put(SQLHelper.COLUMN_BOARDID, newBoardAdapter.getBoardID());
		values.put(SQLHelper.COLUMN_BOARD_INITIAL_STATE, newBoardAdapter.getBoardStateList().get(0));

		database.insertWithOnConflict(dbHelper.GetBoardsTable(), null, values, SQLiteDatabase.CONFLICT_REPLACE);
		Log.i(TAG, "Inserted BoardAdapter : " + newBoardAdapter.getBoardID() + " Into Database.");
	}

	public void addUser(User newUser)
	{
		ContentValues values = new ContentValues();

		values.put(SQLHelper.COLUMN_USERID, newUser.getUserID());
		values.put(SQLHelper.COLUMN_USER_NAME, newUser.getUserName());
		values.put(SQLHelper.COLUMN_USER_PASSWORD, newUser.getUserPassword());

		database.insertWithOnConflict(dbHelper.GetBoardsTable(), null, values, SQLiteDatabase.CONFLICT_REPLACE);
		Log.i(TAG, "Inserted User : " + newUser.getUserID() + " Into Database.");
	}

	public void close()
	{
		dbHelper.close();
	}

	public BoardAdapter deleteBoard(BoardAdapter boardAdapter)
	{
		long id = boardAdapter.getBoardID();
		database.delete(SQLHelper.TABLE_BOARDS, SQLHelper.COLUMN_BOARDID + "=" + id, null);
		return boardAdapter;
	}

	public Cursor getBoard(long boardID)
	{
		Cursor boards = database.query(SQLHelper.TABLE_BOARDS, null, " ( _boardID = " + boardID + " )", null, null, null, null);
		if(boards.moveToFirst())
			return boards;
		else
			return null;
	}

	public Cursor getBoardsCursor()
	{
		return boardsCursor;
	}

	public Cursor getUsersCursor()
	{
		return usersCursor;
	}

	public Cursor getScoresCursor()
	{
		return scoresCursor;
	}

	public Cursor getSavesCursor()
	{
		return savesCursor;
	}

	public Cursor getSettingsCursor()
	{
		return settingsCursor;
	}

	public void open()
	{
		database = dbHelper.getWritableDatabase();
		boardsCursor = database.query(SQLHelper.TABLE_BOARDS, null, null, null, null, null, null);
		usersCursor = database.query(SQLHelper.TABLE_USERS, null, null, null, null, null, null);
		scoresCursor = database.query(SQLHelper.TABLE_BOARDS, null, null, null, null, null, null);
		savesCursor = database.query(SQLHelper.TABLE_USERS, null, null, null, null, null, null);
		settingsCursor = database.query(SQLHelper.TABLE_BOARDS, null, null, null, null, null, null);
		this.isProcessing = false;
	}

	public void startProcessing()
	{
		this.isProcessing = true;
	}

	public boolean isProcessing()
	{
		return this.isProcessing;
	}

	public void stopProcessing()
	{
		this.isProcessing = false;
	}

	public void refresh()
	{
		close();
		open();
	}
}