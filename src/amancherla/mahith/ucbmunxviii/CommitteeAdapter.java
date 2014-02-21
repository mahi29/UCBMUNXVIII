package amancherla.mahith.ucbmunxviii;

import java.util.ArrayList;
import java.util.HashMap;


import amancherla.mahith.ucbmunxviii.R;
import amancherla.mahith.ucbmunxviii.RoomsFragment.CommitteeItem;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommitteeAdapter extends BaseExpandableListAdapter {
	
	Activity activity;
	String[] types;
	HashMap<String, ArrayList<CommitteeItem>> committees;
	LayoutInflater inflater;
	private HashMap<String, Integer> committeeImages;
	public CommitteeAdapter(Activity act, String[] types, HashMap<String, ArrayList<CommitteeItem>> committees) {
		this.activity = act;
		this.types = types;
		this.committees = committees;
		populate();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		ArrayList<CommitteeItem> c = committees.get(types[groupPosition]);
		return c.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		CommitteeItem items = (CommitteeItem) getChild(groupPosition, childPosition);
		String committeeName = items.name;
		if (convertView == null) {
			inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.committee_list_item, null);
		} 
		TextView name = (TextView) convertView.findViewById(R.id.committee_name);
		ImageView image = (ImageView) convertView.findViewById(R.id.committee_image);
		name.setText(committeeName);
		image.setImageResource(committeeImages.get(committeeName));
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		ArrayList<CommitteeItem> tmp = committees.get(types[groupPosition]);
		return tmp.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return types[groupPosition];
	}

	@Override
	public int getGroupCount() {
		return types.length;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String type = types[groupPosition];
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
	
	private void populate() {
		committeeImages = new HashMap<String, Integer>();
		String[] comm = {
				"European Court of Human Rights",
				"UN General Assembly",
				"Nuclear Safety Summit",
				"Committee on World Food Security",
				"UN Security Council",
				"Pinochet's Chile",
				"Chevron Executive Board",
				"Sri Lanka 2006",
				"Ad-Hoc",
				"Covert Ops - CIA",
				"Covert Ops - MSS",
				"Covert Ops - Mossad",
				"Covert Ops - VEVAK",
		};
		Integer[] img = {
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
		for (int i = 0; i < img.length; i++) {
			committeeImages.put(comm[i],img[i]);
		}
	}

}
