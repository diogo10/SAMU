package br.com.samupe.cadastro;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import br.com.samupe.R;

import com.actionbarsherlock.app.SherlockActivity;

@SuppressLint("NewApi")
public class CadastroUsuario1 extends SherlockActivity implements
		OnSeekBarChangeListener {

	private Button bt_proximo, bt_seeker1_mais, bt_seeker1_menos;
	private SeekBar seekerbar_idade;
	private TextView tv_idade;
	private boolean flag = false;
	private EditText nome;
	private CheckBox cb_mascu;
	private CheckBox cb_femi;
	private String sexo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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

		bt_proximo = (Button) findViewById(R.id.bt_proximo);

		bt_seeker1_mais = (Button) findViewById(R.id.buttonmaisidade);
		bt_seeker1_menos = (Button) findViewById(R.id.buttonmenosidade);
		seekerbar_idade = (SeekBar) findViewById(R.id.seekBarIdade);
		seekerbar_idade.setProgress(99);
		seekerbar_idade.setProgress(24);
		tv_idade = (TextView) findViewById(R.id.tv_idade);

		cb_mascu = (CheckBox) findViewById(R.id.cb_mascu);
		cb_mascu.setOnCheckedChangeListener(listener);
		cb_femi = (CheckBox) findViewById(R.id.cb_femi);
		cb_femi.setOnCheckedChangeListener(listener);

		nome = (EditText) findViewById(R.id.et_nome);

		bt_seeker1_mais.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				seekerbar_idade.setProgress(seekerbar_idade.getProgress() + 1);
				tv_idade.setText(" " + seekerbar_idade.getProgress() + " anos");

			}
		});

		bt_seeker1_menos.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				seekerbar_idade.setProgress(seekerbar_idade.getProgress() - 1);
				tv_idade.setText(" " + seekerbar_idade.getProgress() + " anos");
			}
		});

		seekerbar_idade.setOnSeekBarChangeListener(this);

		bt_proximo.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				if (cb_mascu.isChecked()) {
					sexo = "M";
					flag = true;
				} else if (cb_femi.isChecked()) {
					sexo = "F";
					flag = true;
				} else {
					flag = false;
				}

				Intent intent = new Intent(getApplicationContext(),
						CadastroUsuario2.class);
				intent.putExtra("nome", nome.getText().toString());
				intent.putExtra("sexo", sexo);
				intent.putExtra("idade", tv_idade.getText().toString().trim().substring(0,3));

				if (flag) {
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(getApplicationContext(),
							"Complete o Cadastro", Toast.LENGTH_LONG).show();
				}

			}

		});

	}

	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {

		if (seekBar.getId() == R.id.seekBarIdade) {
			tv_idade.setText(" " + progress + " anos");
		}

	}

	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	private OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
			if (isChecked) {
				switch (arg0.getId()) {

				case R.id.cb_mascu:
					cb_mascu.setChecked(true);
					cb_femi.setChecked(false);

					break;

				case R.id.cb_femi:
					cb_femi.setChecked(true);
					cb_mascu.setChecked(false);

					break;

				}
			}

		}
	};
	
	

	
	
	
	
	
	
	

}
