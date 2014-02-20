package com.example.ucbmunxviii;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SecretariatAdapter extends android.widget.BaseAdapter {
	Activity activity;
	ArrayList<SecretariatMember> listData;
	LayoutInflater inflater;
	private HashMap<String, Integer> imgIds = new HashMap<String,Integer>();

	public SecretariatAdapter(Activity activity, ArrayList<SecretariatMember> data) {
		this.activity = activity;
		this.listData = data;
		populate();
	}
	private void populate() {
		imgIds.put(activity.getString(R.string.SG), R.drawable.adam);
		imgIds.put(activity.getString(R.string.DSG), R.drawable.anirudh);
		imgIds.put(activity.getString(R.string.COSE),R.drawable.akash);
		imgIds.put(activity.getString(R.string.COSI),R.drawable.zahra);
		imgIds.put(activity.getString(R.string.USGC),R.drawable.benji);
		imgIds.put(activity.getString(R.string.USGJC),R.drawable.chirag);
		imgIds.put(activity.getString(R.string.USGSB),R.drawable.eviane);
		imgIds.put(activity.getString(R.string.USGDD),R.drawable.lynn);
		imgIds.put(activity.getString(R.string.DBR),R.drawable.redha);
		imgIds.put(activity.getString(R.string.DBR),R.drawable.harsh);
		imgIds.put(activity.getString(R.string.DOP),R.drawable.naomi);
		imgIds.put(activity.getString(R.string.DBMR),R.drawable.hoori);
		imgIds.put(activity.getString(R.string.DOM),R.drawable.rajit);
		imgIds.put(activity.getString(R.string.DOT),R.drawable.mahith);
	}
	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public Object getItem(int pos) {
		return pos;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View cV = convertView;
		if (cV == null) {
			inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			cV = inflater.inflate(R.layout.sec_list, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.name = (TextView) cV.findViewById(R.id.name);
			viewHolder.position = (TextView) cV.findViewById(R.id.position);
			viewHolder.image = (ImageView) cV.findViewById(R.id.list_image);
			cV.setTag(viewHolder);
		}
		ViewHolder holder = (ViewHolder) cV.getTag();
		SecretariatMember member = listData.get(position);
		
		//Setting values for the row
		holder.position.setText(member.position);
		holder.name.setText(member.name);
		//Hack to deal with Harsh and Redha having the same position
		if (member.name.equals("Redha Qabazard")) {
			holder.image.setImageResource(R.drawable.redha);
		} else {
			holder.image.setImageResource(imgIds.get(member.position));
		}
		return cV;
		
	}

	static class ViewHolder {
		public TextView name;
		public TextView position;
		public ImageView image;
	}

}
