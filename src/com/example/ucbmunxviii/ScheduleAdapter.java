package com.example.ucbmunxviii;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


public class ScheduleAdapter extends BaseExpandableListAdapter {

	Activity activity;
	HashMap<String, ArrayList<ScheduleItem>> schedule; //Day,schedule items 
	ArrayList<String> days; 
	ArrayList<String> times;
	LayoutInflater inflater;
	
	public ScheduleAdapter(Activity act, ArrayList<String> days, HashMap<String, ArrayList<ScheduleItem>> schedule) {
		this.activity = act;
		this.days = days;
		this.schedule = schedule;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return schedule.get(days.get(groupPosition))
                .get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ScheduleItem schedItem = (ScheduleItem) getChild(groupPosition, childPosition);
		if (convertView == null) {
			inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.schedule_item, null);
		}
		TextView item = (TextView) convertView.findViewById(R.id.scheduleItem);
		TextView time = (TextView) convertView.findViewById(R.id.scheduleTime);
		item.setText(schedItem.event);
		time.setText(schedItem.time);
		if (schedItem.bold) {
			item.setTypeface(null, Typeface.BOLD);
			time.setTypeface(null, Typeface.BOLD);
		} else {
			item.setTypeface(null, Typeface.NORMAL);
			time.setTypeface(null, Typeface.NORMAL);
		}	
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return schedule.get(days.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return days.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return days.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String dayText = days.get(groupPosition);
		if (convertView == null) {
			inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.schedule_day, null);
		}
		TextView day = (TextView) convertView.findViewById(R.id.scheduleDay);
		day.setTypeface(null, Typeface.BOLD);
		day.setText(dayText);
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
