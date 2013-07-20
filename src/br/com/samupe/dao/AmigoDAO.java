package br.com.samupe.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.samupe.db.DataBaseHelper;
import br.com.samupe.model.Amigo;

public class AmigoDAO {

	private static String table_name = "amigo";
	private static Context ctx;
	DataBaseHelper myDbHelper;

	private static String DB_PATH = "/data/data/br.com.samupe/databases/";
	private static String DB_NAME = "samupe.db";
	private static String myPa = DB_PATH + DB_NAME;
	private SQLiteDatabase myDataBase;

	@SuppressWarnings("static-access")
	public AmigoDAO(Context ctx) {
		this.ctx = ctx;
	}

	public boolean insert(Amigo amigo) {

		myDbHelper = new DataBaseHelper(ctx);

		ContentValues ctv = new ContentValues();
		ctv.put("nome", amigo.getNome());
		ctv.put("sexo", amigo.getSexo());
		ctv.put("idade", amigo.getIdade());
		ctv.put("sangue", amigo.getSangue());
		ctv.put("alergico", amigo.getAlergico());
		ctv.put("alergia", amigo.getAlergia());

		boolean retorno = myDbHelper.insert(table_name, ctv);

		return retorno;

	}

	public List<Amigo> listarAmigos() {

		myDataBase = SQLiteDatabase.openDatabase(myPa, null,
				SQLiteDatabase.OPEN_READONLY);

		StringBuilder sql = new StringBuilder();
		sql.append("select * from amigo");

		Cursor rs = myDataBase.rawQuery(sql.toString(), null);
		List<Amigo> lista;
		try {
			lista = new ArrayList<Amigo>();

			while (rs.moveToNext()) {
				Amigo vo = new Amigo(rs.getInt(0), rs.getString(1), rs.getString(2), rs.getInt(3),
						rs.getString(4), rs.getString(5), rs.getString(6),rs.getString(7),rs.getString(8));
				lista.add(vo);
			}

			return lista;

		} catch (Exception e) {
			Log.i("samupe", "e = " + e.getMessage());
		} finally {

			if (rs != null) {
				rs.close();
				myDataBase.close();
			}
		}

		return null;

	}

}
