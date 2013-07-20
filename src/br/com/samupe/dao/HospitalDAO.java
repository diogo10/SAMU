package br.com.samupe.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.samupe.db.DataBaseHelper;
import br.com.samupe.model.Hospitais;

public class HospitalDAO {
	
	private static String table_name = "hospitais";
	private static Context ctx;
	DataBaseHelper myDbHelper;

	private static String DB_PATH = "/data/data/br.com.samupe/databases/";
	private static String DB_NAME = "samupe.db";
	private static String myPa = DB_PATH + DB_NAME;
	private SQLiteDatabase myDataBase;

	@SuppressWarnings("static-access")
	public HospitalDAO(Context ctx) {
		this.ctx = ctx;
	}
	
	
	
	public List<Hospitais> listarHospitais(){
		
		myDataBase = SQLiteDatabase.openDatabase(myPa, null,
				SQLiteDatabase.OPEN_READONLY);
		
		StringBuilder sql = new StringBuilder();
		sql.append("select * from hospitais");
		
		Cursor rs = myDataBase.rawQuery(sql.toString(), null);
		List<Hospitais> lista;
		try {
			lista = new ArrayList<Hospitais>();

			while (rs.moveToNext()) {
				Hospitais vo = new Hospitais(rs.getInt(0),rs.getString(1),rs.getString(2),rs.getDouble(3),rs.getDouble(4));
				lista.add(vo);
			}
			
			return lista;
			
		} catch (Exception e) {
			Log.i("samupe", "e = " + e.getMessage());
		}finally {

			if (rs != null) {
				rs.close();
				myDataBase.close();
			}
		}

		return null;
		
	}
	
	
	
	

}
