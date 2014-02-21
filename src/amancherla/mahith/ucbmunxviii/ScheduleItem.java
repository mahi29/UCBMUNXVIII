package amancherla.mahith.ucbmunxviii;

import java.util.Calendar;
import android.util.Log;

public class ScheduleItem {
	public String event;
	public String time;
	public boolean bold;
	public String day;
	public String location;
	public Calendar dateStart;
	public Calendar dateEnd;
	
	public ScheduleItem(String item, String time, String day) {
		this(item,time,false,day);
	}
	public ScheduleItem(String item, String time, boolean bold, String day) {
		this.event = item;
		this.time = time;
		this.day = day;
		this.bold = bold;
		setDate();
	}
	public ScheduleItem(String item, String time, String location,  String day) {
		this.event = item;
		this.time = time;
		this.day = day;
		this.location = location;
		bold = false;
		setDate();
	}
	private void setDate() {
		//time is of the format "XX:XXpm - YY:YYpm"
		String[] dates = time.split("-");
		String start = dates[0].trim(); //XX:XXpm
		String end = dates[1].trim(); //XX:XXpm
		String startTime = start.substring(0,start.length()-2); //XX:XX
		String endTime = end.substring(0,end.length()-2);//XX:XX
		String ap = start.substring(start.length()-2);// 'am'/'pm'
		String eap = end.substring(start.length()-2); // 'am/'pm'
		String[] startTimes = startTime.split(":");
		int startHour = Integer.parseInt(startTimes[0]);
		int startMinute = Integer.parseInt(startTimes[1]);
		String[] endTimes = endTime.split(":");
		int endHour = Integer.parseInt(endTimes[0]);
		int endMinute = Integer.parseInt(endTimes[1]);

		//Setting AM/PM for startDate;
		if (ap.equals("pm") && startHour != 12) {
			startHour += 12;
		}
		//Setting AM/PM for the endDate
		if (eap.equals("pm") && endHour != 12) {
			endHour += 12;
		}
		Log.d("ScheduleItem","Event Name: " + event + "\nEnd Time: " + Integer.toString(endHour)+":"+Integer.toString(endMinute));
		dateStart = Calendar.getInstance();
		dateEnd = Calendar.getInstance();
		int date = getDate(day);
		dateStart.set(2014, Calendar.MARCH, date, startHour, startMinute);
		dateEnd.set(2014, Calendar.MARCH, date, endHour, endMinute);
	}
	
	private int getDate(String day) {
		if (day.equals("Thursday")) return 6;
		if (day.equals("Friday")) return 7;
		if (day.equals("Saturday")) return 8;
		if (day.equals("Sunday")) return  9;
		return 0;
	}
}
