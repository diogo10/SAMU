package br.com.samupe;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import br.com.samupe.dao.UsuarioDAO;
import br.com.samupe.model.Usuario;

import com.actionbarsherlock.app.SherlockActivity;

@SuppressLint("NewApi")
public class MeusDados extends SherlockActivity {
	
	private TextView tv_nome,tv_idade,tv_sangue,tv_alergico,tv_alergia;
	private UsuarioDAO usuarioDAO;
	private Usuario usuario;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meus_dados);
		
		getSupportActionBar().setTitle("Meus Dados");
		
		
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			BitmapDrawable bg = (BitmapDrawable) getResources().getDrawable(
					R.drawable.bg_striped);
			bg.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
			getSupportActionBar().setBackgroundDrawable(bg);
		} else {
			ActionBar bar = getActionBar();
			bar.setBackgroundDrawable(new ColorDrawable(Color.rgb(0, 142, 206)));

		}
		
		usuarioDAO = new UsuarioDAO(this);
		usuario = usuarioDAO.pegaUsuario();
		
		tv_nome = (TextView) findViewById(R.id.tv_nome_base);
		tv_nome.setText(usuario.getNome());		
		tv_idade = (TextView) findViewById(R.id.tv_idade_base);
		tv_idade.setText(""+usuario.getIdade()+" anos");
		tv_sangue = (TextView) findViewById(R.id.tv_sangue_base);
		tv_sangue.setText("Sangue " + usuario.getSangue());
		tv_alergico = (TextView) findViewById(R.id.tv_alergico_base);
		tv_alergia = (TextView) findViewById(R.id.tv_alergia_base);
		
		if(usuario.getAlergico().equals("S")){
			tv_alergia.setText("Alergica a " + usuario.getAlergia());
		}else{
			tv_alergico.setText("Não Alergico");
			tv_alergia.setVisibility(View.GONE);
		}
		
		
		
		
		
		
		
	}

}
