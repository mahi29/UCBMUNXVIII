package amancherla.mahith.ucbmunxviii;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import amancherla.mahith.ucbmunxviii.R;
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
	private static final int BUFFER = 10*60*1000;
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
				Toast.makeText(getActivity(),"Launching Twitter!",Toast.LENGTH_LONG).show();
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
				Toast.makeText(getActivity(),"Launching Facebook!",Toast.LENGTH_LONG).show();			
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
			long tempTime = session.getTimeInMillis() + BUFFER;
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
		String minute = Integer.toString(cal.get(Calendar.MINUTE));
		if (minute.equals("0")) minute = "00";
		String time = cal.get(Calendar.HOUR) + ":" + minute+ampm;
		String msg = day + " at " + time;
		message.setText(msg);
		return;
	}
	
}
