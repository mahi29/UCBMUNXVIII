package com.example.ucbmunxviii;

import java.util.HashMap;

import com.example.ucbmunxviii.SecretariatAdapter.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
	private HashMap<String, Integer> gridIds = new HashMap<String,Integer>();
	private Activity activity;
	private String[] names;
	private LayoutInflater inflater;
	private Integer[] images = {
			R.drawable.echr,
			R.drawable.unga,
			R.drawable.nss,
			R.drawable.cfs,
			R.drawable.unsc,
			R.drawable.chile,
			R.drawable.chevron,
			R.drawable.srilanka,
			R.drawable.adhoc,
			R.drawable.cia,
			R.drawable.mss,
			R.drawable.mossad,
			R.drawable.vevak,
	};
	public GridAdapter(Activity act, String[] names) {
		activity = act;
		this.names = names;
	}

	@Override
	public int getCount() {
		return names.length;
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View gridView;
			if (convertView == null) {
				gridView = new View(activity);
				gridView = inflater.inflate(R.layout.committee_item, null);
			} else {
				gridView = (View) convertView;
			}
			TextView textView = (TextView) gridView.findViewById(R.id.grid_title);
			textView.setText(names[position]);
			ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_image);
			imageView.setImageResource(images[position]);
			return gridView;
	}
	static class ViewHolder {
		public ImageView gridImage;
		public TextView gridTitle;
	}

}
