package com.example.ucbmunxviii;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

public class ScheduleFragment extends Fragment {
	
	public static ArrayList<String> days;
	public static HashMap<String, ArrayList<ScheduleItem>> schedule;
	
	public ScheduleFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
        ExpandableListView scheduleView = (ExpandableListView) rootView.findViewById(R.id.schedExp);
        getData();
        ScheduleAdapter adapter = new ScheduleAdapter(getActivity(), days, schedule);
        scheduleView.setAdapter(adapter);
        scheduleView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				final ScheduleItem item = schedule.get(days.get(groupPosition)).get(childPosition);
				boolean hasLocation = (item.location != null) ? true :false;
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK);
				builder.setCancelable(true);
				builder.setCustomTitle(customText(item.event));
				StringBuilder msg = new StringBuilder(getDate(days.get(groupPosition)));
				msg.append("\n\n" + item.time);
				if (hasLocation) msg.append("\n\n"+item.location);
				builder.setMessage(msg.toString());
				builder.setPositiveButton("Add to Calendar", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
											
						Calendar startDate = item.dateStart;
						Calendar endDate = item.dateEnd;
						Log.d("ScheduleFragment","SD: " + startDate.toString());
						Log.d("ScheduleFragment","ED: " + endDate.toString());
						//Create the Intent
						Intent scheduleEvent = new Intent(Intent.ACTION_INSERT);
						scheduleEvent.setData(CalendarContract.Events.CONTENT_URI);
						scheduleEvent.putExtra(Events.TITLE, item.event);
						if (item.location != null) 
							scheduleEvent.putExtra(Events.EVENT_LOCATION, item.location);
						scheduleEvent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
						scheduleEvent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
						     startDate.getTimeInMillis());
						scheduleEvent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
						     endDate.getTimeInMillis());
						startActivity(scheduleEvent);
					}
					
				});
				if (hasLocation) {
					builder.setNegativeButton("View Map", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Fragment newFrag;
							String fragTitle;
							if (item.event.equals("Delegate Soiree")) {
								newFrag = new MapsFrag();
								fragTitle = item.event;
							} else {
								newFrag = new FloorPlan();
								fragTitle = "Hotel Map";
							}
							if (newFrag != null) {
								FragmentManager fragmentManager = getFragmentManager();
								fragmentManager.beginTransaction()
										.replace(R.id.frame_container, newFrag).addToBackStack(null).commit();
								getActivity().getActionBar().setTitle(fragTitle);
							}
						}
					});
				}
				AlertDialog dialog = builder.create();
				dialog.show();
				return false;
			}
        	
        });
        return rootView;
    }
	private String getDate(String day) {
		if (day.equals("Thursday")) return "March 6, 2014";
		if (day.equals("Friday")) return "March 7, 2014";
		if (day.equals("Saturday")) return "March 8, 2014";
		if (day.equals("Sunday")) return  "March 9, 2014";
		return null;
	}
	public TextView customText(String titleText) {
		TextView title = new TextView(getActivity());
		title.setText(titleText);
		title.setBackgroundColor(getResources().getColor(R.color.dialog_background));
		title.setPadding(10, 10, 10, 10);
		title.setGravity(Gravity.CENTER);
		title.setTextColor(getResources().getColor(R.color.list_background_pressed));
		title.setTextSize(20);
		return title;
	}

	
	public void getData() {
		days = new ArrayList<String>();
		schedule = new HashMap<String, ArrayList<ScheduleItem>>();
		days.add("Thursday");
		days.add("Friday");
		days.add("Saturday");
		days.add("Sunday");

		//Thursday
		ArrayList<ScheduleItem> thurs = new ArrayList<ScheduleItem>();
		ScheduleItem t1 = new ScheduleItem("Registration","5:00pm - 6:30pm", "3rd Floor Parlor", "Thursday");
		ScheduleItem t2 = new ScheduleItem("MUN 101 Workshop","5:00pm - 6:00pm", "Pine", "Thursday");
		ScheduleItem t3 = new ScheduleItem("Opening Ceremonies","6:30pm - 7:30pm", "Grand Ballroom", "Thursday");
		ScheduleItem t4 = new ScheduleItem("Committee Session I","7:45pm - 9:45pm", true, "Thursday");
		ScheduleItem t5 = new ScheduleItem("Committee Feedback","10:00pm - 10:30pm", "Pine", "Thursday");
		ScheduleItem t6 = new ScheduleItem("Social Events", "11:00pm - 1:00am", "Thursday");
		ScheduleItem[] temp = {t1,t2,t3,t4,t5,t6};
		thurs = new ArrayList<ScheduleItem>(Arrays.asList(temp));
		
		//Friday
		ArrayList<ScheduleItem> fri = new ArrayList<ScheduleItem>();
		ScheduleItem f1 = new ScheduleItem("Berkeley Tours", "8:30am - 12:00pm", "Friday");
		ScheduleItem f9 = new ScheduleItem("San Francisco Tours", "9:00am - 12:00pm", "Friday");
		ScheduleItem f2 = new ScheduleItem("Lunch", "12:00pm - 1:00pm", "Friday");
		ScheduleItem f3 = new ScheduleItem("Committee Session II","1:00pm - 5:00pm",true, "Friday");
		ScheduleItem f4 = new ScheduleItem("Advisor Feedback Forum","3:00pm - 3:30pm", "Friday");
		ScheduleItem f5 = new ScheduleItem("Dinner","5:00pm - 6:30pm", "Friday");
		ScheduleItem f6 = new ScheduleItem("Committee Session III","6:30pm - 9:30pm",true, "Friday");
		ScheduleItem f7 = new ScheduleItem("Committee Feedback","9:45pm - 10:15pm", "Pine", "Friday");
		ScheduleItem f8 = new ScheduleItem("Jazz Night","10:30pm - 1:30am","Grand Ballroom","Friday");
		ScheduleItem[] temp2 = {f1,f9,f2,f3,f4,f5,f6,f7,f8};
		fri = new ArrayList<ScheduleItem>(Arrays.asList(temp2));
		
		//Saturday
		ArrayList<ScheduleItem> sat = new ArrayList<ScheduleItem>();
		ScheduleItem s1 = new ScheduleItem("Committee Session IV","10:00am - 1:00pm",true, "Saturday");
		ScheduleItem s4 = new ScheduleItem("Advisor Feedback Forum","11:30am - 12:00pm", "Saturday");
		ScheduleItem s5 = new ScheduleItem("Committee Feedback","1:15pm - 1:45pm", "Pine", "Saturday");
		ScheduleItem s2 = new ScheduleItem("Lunch","1:00pm - 3:00pm", "Saturday");
		ScheduleItem s3 = new ScheduleItem("Committee Session V","3:00pm - 7:00pm",true, "Saturday");
		ScheduleItem s6 = new ScheduleItem("Delegate Soiree","10:00pm - 2:00am","Horizon Ultra Lounge", "Saturday");
		ScheduleItem[] temp3 = {s1,s4,s5,s2,s3,s6};
		sat = new ArrayList<ScheduleItem>(Arrays.asList(temp3));
		
		//Sunday
		ArrayList<ScheduleItem> sun = new ArrayList<ScheduleItem>();
		ScheduleItem ss1 = new ScheduleItem("Closing Ceremonies","10:00am - 12:00pm", "Grand Ballroom", "Sunday");
		sun.add(ss1);
		
		schedule.put("Thursday",thurs);
		schedule.put("Friday", fri);
		schedule.put("Saturday",sat);
		schedule.put("Sunday",sun);
	}
}
