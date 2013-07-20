package br.com.samupe;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import br.com.samupe.dao.HospitalDAO;
import br.com.samupe.model.Hospitais;
import br.com.samupe.util.GPSTracker;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

@SuppressLint("NewApi")
public class MapaHospitais extends SherlockFragmentActivity implements
		OnInfoWindowClickListener {

	private HospitalDAO hospitalDAO;
	private List<Hospitais> hospitais = new ArrayList<Hospitais>();
	private SupportMapFragment fragment;
	private GPSTracker gps;
	private GoogleMap mapa;

	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapa);
		
		getSupportActionBar().setTitle("Hospitais");

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			BitmapDrawable bg = (BitmapDrawable) getResources().getDrawable(
					R.drawable.bg_striped);
			bg.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
			getSupportActionBar().setBackgroundDrawable(bg);
		} else {
			ActionBar bar = getActionBar();
			bar.setBackgroundDrawable(new ColorDrawable(Color.rgb(0, 142, 206)));
		}

		gps = new GPSTracker(this);
		fragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.mapa);
		mapa = fragment.getMap();
		mapa.setMyLocationEnabled(true);
		mapa.setOnInfoWindowClickListener(this);

		Location loc = gps.getLocation();
		if (loc != null) {

			configuraPosicao(mapa,
					new LatLng(loc.getLatitude(), loc.getLongitude()));

			hospitalDAO = new HospitalDAO(this);
			hospitais = hospitalDAO.listarHospitais();
			for (int i = 0; i < hospitais.size(); i++) {

				mapa.addMarker(new MarkerOptions()
						.position(
								new LatLng(hospitais.get(i).getX(), hospitais
										.get(i).getY()))
						.title(hospitais.get(i).getDescricao())
						.snippet(hospitais.get(i).getTelefone())
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.ponto)));

			}

		}

	}

	private void configuraPosicao(GoogleMap map, LatLng latLng) {
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 22));
		map.animateCamera(CameraUpdateFactory.zoomTo(17), 100, null);
	}

	@Override
	public void onInfoWindowClick(Marker marker) {

		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + marker.getSnippet()));
		startActivity(callIntent);

	}

}
