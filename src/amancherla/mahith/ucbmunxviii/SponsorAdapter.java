package amancherla.mahith.ucbmunxviii;

import java.util.ArrayList;
import java.util.HashMap;


import amancherla.mahith.ucbmunxviii.R;
import amancherla.mahith.ucbmunxviii.SponsorFragment.SponsorItem;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SponsorAdapter extends BaseExpandableListAdapter {
	Activity activity;
	String[] sponsorLevels;
	HashMap<String, ArrayList<SponsorItem>> sponsors;
	LayoutInflater inflater;
	
	public SponsorAdapter(Activity act, String[] sponsorLevels, HashMap<String, ArrayList<SponsorItem>> sponsors) {
		this.activity = act;
		this.sponsorLevels = sponsorLevels;
		this.sponsors = sponsors;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		String sponsorType = sponsorLevels[groupPosition];
		return sponsors.get(sponsorType).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		SponsorItem sponsor = (SponsorItem) getChild(groupPosition, childPosition);
		String sponsorName = sponsor.name;
		if (convertView == null) {
			inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.sponsor_list_item, null);
		} 
		TextView name = (TextView) convertView.findViewById(R.id.sponsor_name);
		ImageView image = (ImageView) convertView.findViewById(R.id.sponsor_image);
		name.setText(sponsorName);
		image.setImageResource(sponsor.image);
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		ArrayList<SponsorItem> children = sponsors.get(sponsorLevels[groupPosition]);
		return children.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return sponsorLevels[groupPosition];
	}

	@Override
	public int getGroupCount() {
		return sponsorLevels.length;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String type = sponsorLevels[groupPosition];
		if (convertView == null) {
			inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.schedule_day, null);
		}
		TextView commType = (TextView) convertView.findViewById(R.id.scheduleDay);
		commType.setTypeface(null, Typeface.BOLD);
		commType.setText(type);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
