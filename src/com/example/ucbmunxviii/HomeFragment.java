package com.example.ucbmunxviii;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment {
	
	private static final String FACEBOOK_ID= "354319247949175";
	ArrayList<String> days;
	HashMap<String, ArrayList<ScheduleItem>> schedule;
	TextView message;
	TextView sessionText;
	
	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ImageView twitter = (ImageView) rootView.findViewById(R.id.twitterLogo);
        ImageView facebook = (ImageView) rootView.findViewById(R.id.fbLogo);
        ImageView logo = (ImageView) rootView.findViewById(R.id.ucbmunlogo);
        message = (TextView) rootView.findViewById(R.id.sessionTime);
        sessionText = (TextView) rootView.findViewById(R.id.sessionText);
        nextSession();
        twitter.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(),"Launching Twitter!",Toast.LENGTH_SHORT).show();
				try {
					Intent intent = new Intent(Intent.ACTION_VIEW,
					    Uri.parse("twitter://user?screen_name=UCBMUN"));
						startActivity(intent);
					}catch (Exception e) {
					    startActivity(new Intent(Intent.ACTION_VIEW,
					         Uri.parse("https://twitter.com/#!/UCBMUN"))); 
					} 				
			}
		});
        facebook.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(),"Launching Facebook!",Toast.LENGTH_SHORT).show();			
			   try {
				    getActivity().getPackageManager().getPackageInfo("com.facebook.katana", 0);
				    Intent  i = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/"+FACEBOOK_ID));
				    startActivityForResult(i,0);
				   } catch (Exception e) {
				    Intent j = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"+FACEBOOK_ID));
				    startActivityForResult(j,1);
				   }			
			}
        	
        });
        
        logo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity act = (MainActivity) getActivity();
				act.openDrawer();
			}
        	
        });
        
        return rootView;
    }
	
	private void nextSession() {
		ScheduleFragment.getData();
		days = ScheduleFragment.days;
		schedule = ScheduleFragment.schedule;
		HashMap<Calendar, ScheduleItem> dates = new HashMap<Calendar,ScheduleItem>();
		//ArrayList<Calendar> dates = new ArrayList<Calendar>();
		for (String day:days) {
			ArrayList<ScheduleItem> daySchedule = schedule.get(day);
			for (ScheduleItem item:daySchedule) {
				if (item.bold) {
					Calendar date = item.dateStart;
					dates.put(date,item);
				}
			}
		}
		
		Calendar currentTime = Calendar.getInstance();
		long milliTime = currentTime.getTimeInMillis();
		Calendar closestSession = null;
		long shortestTime = Long.MAX_VALUE;
		Iterator<Calendar> it = dates.keySet().iterator();
		while(it.hasNext()) {
			Log.d("HomeFrag","reached inside iterator");
			Calendar session = it.next();
			long tempTime = session.getTimeInMillis();
			long timeLeft = tempTime - milliTime; //eventTime - currentTime (should be positive)
			if (timeLeft > 0 && timeLeft < shortestTime) {
				closestSession = session;
			}
		}
		if (closestSession == null) {
			message.setText("");
			sessionText.setText("There are no more committee sessions");
			return;
		}
		ScheduleItem nextItem = dates.get(closestSession);
		String day = nextItem.day;
		Calendar cal = nextItem.dateStart; //The next committee session
		String ampm = (cal.get(Calendar.AM_PM) == Calendar.AM) ? "am" : "pm";
		String time = cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE)+ampm;
		String msg = day + " at " + time;
		message.setText(msg);
		return;
	}
//	
//	public void getData() {
//		days = new ArrayList<String>();
//		schedule = new HashMap<String, ArrayList<ScheduleItem>>();
//		days.add("Thursday");
//		days.add("Friday");
//		days.add("Saturday");
//		days.add("Sunday");
//
//		//Thursday
//		ArrayList<ScheduleItem> thurs = new ArrayList<ScheduleItem>();
//		ScheduleItem t1 = new ScheduleItem("Registration","5:00pm - 6:30pm", "3rd Floor Parlor", "Thursday");
//		ScheduleItem t2 = new ScheduleItem("MUN 101 Workshop","5:00pm - 6:00pm", "Pine", "Thursday");
//		ScheduleItem t3 = new ScheduleItem("Opening Ceremonies","6:30pm - 7:30pm", "Grand Ballroom", "Thursday");
//		ScheduleItem t4 = new ScheduleItem("Committee Session I","7:45pm - 9:45pm", true, "Thursday");
//		ScheduleItem t5 = new ScheduleItem("Committee Feedback","10:00pm - 10:30pm", "Pine", "Thursday");
//		ScheduleItem t6 = new ScheduleItem("Social Events", "11:00pm - 1:00am", "Thursday");
//		ScheduleItem[] temp = {t1,t2,t3,t4,t5,t6};
//		thurs = new ArrayList<ScheduleItem>(Arrays.asList(temp));
//		
//		//Friday
//		ArrayList<ScheduleItem> fri = new ArrayList<ScheduleItem>();
//		ScheduleItem f1 = new ScheduleItem("Berkeley Tours", "8:30am - 12:00pm", "Friday");
//		ScheduleItem f9 = new ScheduleItem("San Francisco Tours", "9:00am - 12:00pm", "Friday");
//		ScheduleItem f2 = new ScheduleItem("Lunch", "12:00pm - 1:00pm", "Friday");
//		ScheduleItem f3 = new ScheduleItem("Committee Session II","1:00pm - 5:00pm",true, "Friday");
//		ScheduleItem f4 = new ScheduleItem("Advisor Feedback Forum","3:00pm - 3:30pm", "Friday");
//		ScheduleItem f5 = new ScheduleItem("Dinner","5:00pm - 6:30pm", "Friday");
//		ScheduleItem f6 = new ScheduleItem("Committee Session III","6:30pm - 9:30pm",true, "Friday");
//		ScheduleItem f7 = new ScheduleItem("Committee Feedback","9:45pm - 10:15pm", "Pine", "Friday");
//		ScheduleItem f8 = new ScheduleItem("Jazz Night","10:30pm - 1:30am","Grand Ballroom","Friday");
//		ScheduleItem[] temp2 = {f1,f9,f2,f3,f4,f5,f6,f7,f8};
//		fri = new ArrayList<ScheduleItem>(Arrays.asList(temp2));
//		
//		//Saturday
//		ArrayList<ScheduleItem> sat = new ArrayList<ScheduleItem>();
//		ScheduleItem s1 = new ScheduleItem("Committee Session IV","10:00am - 1:00pm",true, "Saturday");
//		ScheduleItem s4 = new ScheduleItem("Advisor Feedback Forum","11:30am - 12:00pm", "Saturday");
//		ScheduleItem s5 = new ScheduleItem("Committee Feedback","1:15pm - 1:45pm", "Pine", "Saturday");
//		ScheduleItem s2 = new ScheduleItem("Lunch","1:00pm - 3:00pm", "Saturday");
//		ScheduleItem s3 = new ScheduleItem("Committee Session V","3:00pm - 7:00pm",true, "Saturday");
//		ScheduleItem s6 = new ScheduleItem("Delegate Soiree","10:00pm - 2:00am","Horizon Ultra Lounge", "Saturday");
//		ScheduleItem[] temp3 = {s1,s4,s5,s2,s3,s6};
//		sat = new ArrayList<ScheduleItem>(Arrays.asList(temp3));
//		
//		//Sunday
//		ArrayList<ScheduleItem> sun = new ArrayList<ScheduleItem>();
//		ScheduleItem ss1 = new ScheduleItem("Closing Ceremonies","10:00am - 12:00pm", "Grand Ballroom", "Sunday");
//		sun.add(ss1);
//		
//		schedule.put("Thursday",thurs);
//		schedule.put("Friday", fri);
//		schedule.put("Saturday",sat);
//		schedule.put("Sunday",sun);
//	}	
//	

	
	
}
