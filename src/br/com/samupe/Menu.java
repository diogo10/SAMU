package br.com.samupe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

@SuppressLint("NewApi")
public class Menu extends SherlockActivity implements OnItemClickListener {

	private ImageView img;
	final ArrayList<String> lista_oc = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			BitmapDrawable bg = (BitmapDrawable) getResources().getDrawable(
					R.drawable.bg_striped);
			bg.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
			getSupportActionBar().setBackgroundDrawable(bg);
		} else {
			ActionBar bar = getActionBar();
			bar.setBackgroundDrawable(new ColorDrawable(Color.rgb(0, 142, 206)));

		}

		img = (ImageView) findViewById(R.id.call_);
		img.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				lista_oc.add("Acidentes de trânsito");
				lista_oc.add("Acidentes domésticos");
				lista_oc.add("Acidentes/traumas");
				lista_oc.add("Problemas cardiorrespiratórios");
				lista_oc.add("Intoxicação");

				final Dialog dialog = new Dialog(Menu.this);
				dialog.setContentView(R.layout.dialog_o);
				dialog.setTitle("Qual tipo de ocorrência ?");
				final ListView list = (ListView) dialog
						.findViewById(R.id.list_o);
				final StableArrayAdapter adapter = new StableArrayAdapter(
						Menu.this, android.R.layout.simple_list_item_1,
						lista_oc);
				list.setAdapter(adapter);
				list.setOnItemClickListener(Menu.this);

				dialog.show();

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {

		menu.add("Mapa")
				.setIcon(R.drawable.mapa)
				.setShowAsAction(
						MenuItem.SHOW_AS_ACTION_ALWAYS
								| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

		SubMenu subMenu1 = menu.addSubMenu("Action Item");
		subMenu1.add("Amigos");
		subMenu1.add("Meus Dados");

		MenuItem subMenu1Item = subMenu1.getItem();
		subMenu1Item.setIcon(R.drawable.ic_action_overflow);
		subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getTitle().equals("Amigos")) {
			startActivity(new Intent(getApplicationContext(), ListaAmigos.class));
		} else if (item.getTitle().equals("Meus Dados")) {
			startActivity(new Intent(getApplicationContext(), MeusDados.class));
		} else if (item.getTitle().equals("Mapa")) {
			startActivity(new Intent(getApplicationContext(),
					MapaHospitais.class));
		}

		return true;

	}

	

	private class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Toast.makeText(getApplicationContext(),
				"" + lista_oc.get(position).toString(), Toast.LENGTH_SHORT)
				.show();
		
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:192"));
		startActivity(callIntent);
		
		

	}

}
