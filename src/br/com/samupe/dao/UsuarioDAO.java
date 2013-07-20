package br.com.samupe.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.samupe.db.DataBaseHelper;
import br.com.samupe.model.Usuario;

public class UsuarioDAO {
	

	private static String table_name = "usuario";
	private static Context ctx;
	DataBaseHelper myDbHelper;

	private static String DB_PATH = "/data/data/br.com.samupe/databases/";
	private static String DB_NAME = "samupe.db";
	private static String myPa = DB_PATH + DB_NAME;
	private SQLiteDatabase myDataBase;

	@SuppressWarnings("static-access")
	public UsuarioDAO(Context ctx) {
		this.ctx = ctx;
	}

	public boolean insert(Usuario usuario) {

		myDbHelper = new DataBaseHelper(ctx);
			
		ContentValues ctv = new ContentValues();
		ctv.put("nome", usuario.getNome());
		ctv.put("sexo", usuario.getSexo());
		ctv.put("idade", usuario.getIdade());
		ctv.put("sangue",usuario.getSangue());
		ctv.put("alergico",usuario.getAlergico());
		ctv.put("alergia",usuario.getAlergia());
		
		boolean retorno = myDbHelper.insert(table_name, ctv);

		return retorno;

	}
	
	
	
	
	
	public boolean verificaUser() {
		myDataBase = SQLiteDatabase.openDatabase(myPa, null,
				SQLiteDatabase.OPEN_READONLY);
		Cursor rs = myDataBase.rawQuery("SELECT * FROM usuario", null);

		try {

			if (rs.getCount() == 0) {

				return false;
			} else {

				return true;

			}

		} catch (Exception e) {
			Log.e("samupe", "e = " + e.getMessage());
		} finally {

			if (rs != null) {
				rs.close();
				myDataBase.close();
			}

		}

		return false;

	}
	
	
	
	
	public Usuario pegaUsuario() {

		myDataBase = SQLiteDatabase.openDatabase(myPa, null,
				SQLiteDatabase.OPEN_READONLY);
		Cursor rs = myDataBase.rawQuery("SELECT * FROM usuario", null);
		try {

			List<Usuario> lista_user = new ArrayList<Usuario>();
			
			while (rs.moveToNext()) {
			
				Usuario usuario = new Usuario(rs.getInt(0), rs.getString(1),rs.getString(2), rs.getInt(3),
						rs.getString(4), rs.getString(5), rs.getString(6));
				
				lista_user.add(usuario);
			}

			return lista_user.get(0);

		} catch (Exception e) {
			Log.e("samupe", "e = " + e.getMessage());

		} finally {

			if (rs != null) {
				rs.close();
				myDataBase.close();
			}
		}

		return null;

	}
	
	
	
	
	
	
	
	
	
	


}

