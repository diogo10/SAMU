package br.com.samupe;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import br.com.samupe.dao.UsuarioDAO;
import br.com.samupe.db.DataBaseHelper;
import br.com.samupe.cadastro.CadastroUsuario1;

/*
 * SplashScreen onde e feita a abertura da base de dados
 * 
 */

public class SplashScreen extends Activity implements Runnable {

	// 5 segundos
	private final int DELAY = 3000;// variavel responsavel pela duração da Tela
	private boolean flag = false;
	private UsuarioDAO user_dao = new UsuarioDAO(this);

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Exibe o layout com a imagem...
		setContentView(R.layout.layout_splash);
		setProgress(R.id.progressBar);

		Handler h = new Handler();

		DataBaseHelper myDbHelper = new DataBaseHelper(this);

		try {

			myDbHelper.createDataBase();

		} catch (IOException ioe) {

			throw new Error("Unable to create database");

		}

		try {

			myDbHelper.openDataBase();

		} catch (SQLException sqle) {

			throw sqle;

		}

		boolean retur = user_dao.verificaUser();

		if (retur) {

			flag = true;
		} else {
			flag = false;
		}

		myDbHelper.close();
		h.postDelayed(this, DELAY);

	}

	@Override
	public void run() {

		if (flag) {

			startActivity(new Intent(this, Menu.class));

		} else {

			startActivity(new Intent(this, CadastroUsuario1.class));

		}

		finish();

	}

}
