package com.example.ucbmunxviii;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ScheduleItem {
	public String event;
	public String time;
	public boolean bold;
	public String day;
	public String location;
	public GregorianCalendar dateStart;
	public GregorianCalendar dateEnd;
	
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
		String[] dates = time.split("-");
		String start = dates[0].trim();
		String end = dates[1].trim();
		start = start.substring(0,start.length()-2);
		end = end.substring(0,end.length()-2);
		String[] startTimes = start.split(":");
		int startHour = Integer.parseInt(startTimes[0]);
		int startMinute = Integer.parseInt(startTimes[1]);
		String[] endTimes = end.split(":");
		int endHour = Integer.parseInt(endTimes[0]);
		int endMinute = Integer.parseInt(endTimes[1]);
		if (day.equals("Thursday")) {
			dateStart = new GregorianCalendar(2014, Calendar.MARCH, 6, startHour, startMinute);
			dateEnd = new GregorianCalendar(2014, Calendar.MARCH, 6,endHour, endMinute);
		} else {
			dateStart = new GregorianCalendar(2014, Calendar.MARCH, 9, startHour, startMinute);
			dateEnd = new GregorianCalendar(2014, Calendar.MARCH, 9,endHour, endMinute);
		}
		
	}
}
