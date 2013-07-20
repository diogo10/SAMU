package br.com.samupe;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import br.com.samupe.dao.AmigoDAO;
import br.com.samupe.model.Amigo;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.haarman.listviewanimations.itemmanipulation.contextualundo.ContextualUndoAdapter;
import com.haarman.listviewanimations.itemmanipulation.contextualundo.ContextualUndoAdapter.DeleteItemCallback;
import com.squareup.picasso.Picasso;

@SuppressLint("NewApi")
public class ListaAmigos extends SherlockActivity {

	private GoogleCardsAdapter mGoogleCardsAdapter;
	private List<Amigo> amigos = new ArrayList<Amigo>();
	private AmigoDAO amigodao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_amigos);

		getSupportActionBar().setTitle("Amigos");

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			BitmapDrawable bg = (BitmapDrawable) getResources().getDrawable(
					R.drawable.bg_striped);
			bg.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
			getSupportActionBar().setBackgroundDrawable(bg);
		} else {
			ActionBar bar = getActionBar();
			bar.setBackgroundDrawable(new ColorDrawable(Color.rgb(0, 142, 206)));

		}

		ListView listView = (ListView) findViewById(R.id.activity_googlecards_listview);
		amigodao = new AmigoDAO(this);
		amigos = amigodao.listarAmigos();
		mGoogleCardsAdapter = new GoogleCardsAdapter(this, amigos);
		ContextualUndoAdapter contextualUndoAdapter = new ContextualUndoAdapter(
				mGoogleCardsAdapter, R.layout.undo_row,
				R.id.undo_row_undobutton);
		listView.setAdapter(contextualUndoAdapter);
		contextualUndoAdapter.setDeleteItemCallback(new MyDeleteItemCallback());

	}
	
	private class MyDeleteItemCallback implements DeleteItemCallback {

		@Override
		public void deleteItem(int position) {
			
		}
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {

		menu.add("Plus")
				.setIcon(R.drawable.add_person)
				.setShowAsAction(
						MenuItem.SHOW_AS_ACTION_ALWAYS
								| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

		return true;

	}

	private static class GoogleCardsAdapter extends BaseAdapter {

		private Context mContext;
		private LruCache<Integer, Bitmap> mMemoryCache;
		private List<Amigo> amigos;

		public GoogleCardsAdapter(Context context, List<Amigo> amigos) {
			mContext = context;
			this.amigos = amigos;
			final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

			// Use 1/8th of the available memory for this memory cache.
			final int cacheSize = maxMemory;
			mMemoryCache = new LruCache<Integer, Bitmap>(cacheSize) {
				@Override
				protected int sizeOf(Integer key, Bitmap bitmap) {
					// The cache size will be measured in kilobytes rather than
					// number of items.
					return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
				}
			};
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return amigos != null ? amigos.get(position) : null;
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return amigos != null ? amigos.size() : 0;
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			View view = convertView;
			if (view == null) {
				view = LayoutInflater.from(mContext).inflate(
						R.layout.list_amigos_card, parent, false);

				viewHolder = new ViewHolder();
				viewHolder.textView = (TextView) view
						.findViewById(R.id.tv_nome_card);
				viewHolder.textView1 = (TextView) view
						.findViewById(R.id.tv_telefone_card);
				view.setTag(viewHolder);

				viewHolder.imageView = (ImageView) view
						.findViewById(R.id.img_card);
			} else {
				viewHolder = (ViewHolder) view.getTag();
			}

			viewHolder.textView.setText(amigos.get(position).getNome());
			viewHolder.textView1.setText("(83) 3454-3456");
			Picasso.with(mContext)
					.load(amigos.get(position).getUrl())
					// .placeholder(R.drawable.placeholder)
					.error(R.drawable.profile)
					.resizeDimen(R.dimen.list_detail_image_size,
							R.dimen.list_detail_image_size)
					.into(viewHolder.imageView);

			setImageView(viewHolder, position);

			return view;
		}

		private void setImageView(ViewHolder viewHolder, int position) {
			int imageResId;
			imageResId = R.drawable.profile;
			Bitmap bitmap = getBitmapFromMemCache(imageResId);
			if (bitmap == null) {
				bitmap = BitmapFactory.decodeResource(mContext.getResources(),
						imageResId);
				addBitmapToMemoryCache(imageResId, bitmap);
			}
			viewHolder.imageView.setImageBitmap(bitmap);
		}

		private void addBitmapToMemoryCache(int key, Bitmap bitmap) {
			if (getBitmapFromMemCache(key) == null) {
				mMemoryCache.put(key, bitmap);
			}
		}

		private Bitmap getBitmapFromMemCache(int key) {
			return mMemoryCache.get(key);
		}

		private static class ViewHolder {
			TextView textView, textView1;
			ImageView imageView;
		}
	}

}
