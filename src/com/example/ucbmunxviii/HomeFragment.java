package com.example.ucbmunxviii;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class HomeFragment extends Fragment {
	
	private static final String FACEBOOK_ID= "354319247949175";
	
	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ImageView twitter = (ImageView) rootView.findViewById(R.id.twitterLogo);
        ImageView facebook = (ImageView) rootView.findViewById(R.id.fbLogo);
        ImageView logo = (ImageView) rootView.findViewById(R.id.ucbmunlogo);
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
}
