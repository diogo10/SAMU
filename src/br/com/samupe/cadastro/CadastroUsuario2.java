package br.com.samupe.cadastro;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import br.com.samupe.R;
import br.com.samupe.dao.UsuarioDAO;
import br.com.samupe.model.Usuario;
import br.com.samupe.Menu;
import com.actionbarsherlock.app.SherlockActivity;

@SuppressLint("NewApi")
public class CadastroUsuario2 extends SherlockActivity implements
		OnItemSelectedListener {

	private Spinner spinner1;
	private CheckBox cb_sim, cb_nao;
	private String alergia, sangue;
	private String nome, sexo, idade, alergico;
	private Button bt_finalizar;
	private boolean flag = false;
	private UsuarioDAO usuariodao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro2);

		Intent it = getIntent();
		if (it != null) {

			Bundle params = it.getExtras();

			if (params != null) {
				nome = params.getString("nome");
				sexo = params.getString("sexo");
				idade = params.getString("idade");
			}

		}

		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(R.layout.text_header_cadastro1);

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			BitmapDrawable bg = (BitmapDrawable) getResources().getDrawable(
					R.drawable.bg_striped);
			bg.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
			getSupportActionBar().setBackgroundDrawable(bg);
		} else {
			ActionBar bar = getActionBar();
			bar.setBackgroundDrawable(new ColorDrawable(Color.rgb(51, 51, 51)));
		}

		bt_finalizar = (Button) findViewById(R.id.bt_finalizar);
		bt_finalizar.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("UseValueOf")
			@Override
			public void onClick(View v) {

				usuariodao = new UsuarioDAO(getApplicationContext());

				if (cb_sim.isChecked()) {
					alergico = "S";
					flag = true;
				} else if (cb_nao.isChecked()) {
					alergico = "N";
					flag = true;
				} else {
					flag = false;
				}

				if (flag) {
					Usuario usuario = new Usuario(0, nome, sexo, Integer
							.valueOf(idade.trim()), sangue, alergico, alergia);
					usuariodao.insert(usuario);
					startActivity(new Intent(getApplicationContext(),Menu.class));
					finish();
				} else {
					Toast.makeText(getApplicationContext(),
							"Complete o Cadastro", Toast.LENGTH_SHORT).show();
				}

			}
		});

		spinner1 = (Spinner) findViewById(R.id.sp_sangue);
		spinner1.setOnItemSelectedListener(this);

		cb_sim = (CheckBox) findViewById(R.id.cb_sim);
		cb_sim.setOnCheckedChangeListener(listener);
		cb_nao = (CheckBox) findViewById(R.id.cb_nao);
		cb_nao.setOnCheckedChangeListener(listener);

	}

	public void onItemSelected(AdapterView<?> parent, View arg1, int p,
			long arg3) {

		sangue = parent.getItemAtPosition(p).toString();

	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	private OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
			if (isChecked) {
				switch (arg0.getId()) {

				case R.id.cb_sim:
					cb_sim.setChecked(true);
					cb_nao.setChecked(false);
					callDialog();

					break;

				case R.id.cb_nao:
					cb_sim.setChecked(false);
					cb_nao.setChecked(true);

					break;

				}
			}

		}
	};

	private void callDialog() {

		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog_tipo_alergia);
		dialog.setTitle("Qual ?");
		final EditText et_alergia = (EditText) dialog
				.findViewById(R.id.et_tipo_alergia);

		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alergia = et_alergia.getText().toString();
				dialog.dismiss();
			}
		});

		dialog.show();

	}

}
