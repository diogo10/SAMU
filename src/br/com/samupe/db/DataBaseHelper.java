package br.com.samupe.db;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*
 * Classe padrão para ler a base de dados
 * 
 * 
 */

public class DataBaseHelper extends SQLiteOpenHelper {

	// The Android's default system path of your application database.
	private static String DB_PATH = "/data/data/br.com.samupe/databases/";
	private static String DB_NAME = "samupe.db";
	private SQLiteDatabase myDataBase;
	private static String myPa = DB_PATH + DB_NAME;
	private Context myContext;
	private static final String tag = "samupe";

	public DataBaseHelper(Context context) {

		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}

	public void createDataBase() throws IOException {
		boolean dbExist = checkDataBase();

		if (dbExist) {
			// do nothing - database already exist
			Log.i(tag, "entrou no método dbExiste");

			//criaTabelaComentarios();

		} else {
			Log.i(tag, "não existe o banco");
			this.getReadableDatabase();

			try {
				copyDataBase();

			} catch (IOException e) {
				throw new Error("Error copying database");

			}
		}

	}

	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;

		try {
			// checkDB.execSQL("PRAGMA foreign_keys=ON;");
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
			// database does't exist yet.

		}

		if (checkDB != null) {
			checkDB.close();

		}
		return checkDB != null ? true : false;
	}

	private void copyDataBase() throws IOException {
		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);
		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException {
		// Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);

	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public boolean insert(String nome_tabela, ContentValues ctv) {
		myDataBase = SQLiteDatabase.openDatabase(myPa, null,
				SQLiteDatabase.OPEN_READWRITE);

		if ((myDataBase.insert(nome_tabela, null, ctv) > 0)) {
			myDataBase.close();
			return true;
		} else {
			myDataBase.close();
			return false;
		}

	}

	


}
