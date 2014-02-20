package com.example.ucbmunxviii;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFrag extends Fragment {
	private GoogleMap mMap;
	static final LatLng HILTON = new LatLng(37.7951149, -122.4046388);
	static final LatLng HORIZON = new LatLng(37.798170, -122.405453);
	private MapSpot hilton;
	private MapSpot horizon;
	private HashSet<Marker> barMarkers;
	private HashSet<Marker> foodMarkers;
	private HashSet<Marker> attractionMarkers;
	private HashMap<String,MapSpot> barSpots;
	private HashMap<String,MapSpot> foodSpots;
	private HashMap<String,MapSpot> attractionSpots;
	private final int FOOD = 0;
	private final int BAR = 1;
	private final int ATTRACTIONS = 2;
	private final int NEITHER = -1;
	private boolean firstFood = true;
	private boolean firstAttraction = true;
	private boolean firstBar = true;
	
	
	CheckBox barsCheckBox;
	CheckBox foodCheckBox;
	CheckBox attractionCheckBox;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_map, container, false);
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HILTON, 16));
		//Initialize Checkboxes
		foodCheckBox = (CheckBox) rootView.findViewById(R.id.food);
		barsCheckBox = (CheckBox) rootView.findViewById(R.id.bars);
		attractionCheckBox = (CheckBox) rootView.findViewById(R.id.attraction);
		//Add the hotel and club
		hilton = new MapSpot("Hilton San Francisco","(415) 433-6600",HILTON);
		horizon = new MapSpot("Horizon Ultra Lounge", HORIZON);
		addMarker(HILTON,"Hilton San Francisco","Home of UCBMUN XVIII",NEITHER);
		addMarker(HORIZON,"Horizon Ultra Lounge","Delegate Soiree, Saturday",NEITHER);
		//Initialize the markerSets
		foodMarkers = new HashSet<Marker>();
		barMarkers = new HashSet<Marker>();
		attractionMarkers = new HashSet<Marker>();
		//Initialize the spotMaps
		barSpots = new HashMap<String,MapSpot>();
		foodSpots = new HashMap<String,MapSpot>();
		attractionSpots = new HashMap<String,MapSpot>();
		
		//Set listeners for the checkboxes
		foodCheckBox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (foodCheckBox.isChecked()) {
					if (firstFood) {
						firstFood = false;
						addFoodMarkers();
					} else {
						changeMarkerVisibility(true, FOOD);
					}
				} else {
					changeMarkerVisibility(false, FOOD);
				}
			}		
		});
		
		barsCheckBox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (barsCheckBox.isChecked()) {
					if (firstBar) {
						firstBar = false;
						addBarMarkers();
					} else {
						changeMarkerVisibility(true, BAR);
					}
				} else {
					changeMarkerVisibility(false, BAR);
				}
			}		
		});
		
		attractionCheckBox.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if (attractionCheckBox.isChecked()) {
					mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
					if (firstAttraction) {
						firstAttraction = false;
						addAttractionMarkers();
					} else {
						changeMarkerVisibility(true, ATTRACTIONS);
					}
				} else {
					changeMarkerVisibility(false, ATTRACTIONS);
				}
			}
		});
		
		mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker marker) {
				String title = marker.getTitle();
				MapSpot temp;
				if (foodSpots != null && foodSpots.containsKey(title)) {
					temp = foodSpots.get(title);
				} else if (barSpots != null && barSpots.containsKey(title)) {
					temp = barSpots.get(title);
				} else if (attractionSpots != null && attractionSpots.containsKey(title)) {
					temp = attractionSpots.get(title);
				} else if (title.equals("Horizon Ultra Lounge")) {
					temp = horizon;
				} else {
					temp = hilton;
				}
				final MapSpot spot = temp;
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK);
				builder.setCancelable(true);
				builder.setCustomTitle(customText(title));	
				StringBuilder msg = new StringBuilder();
				if (spot.phone != null) {
					msg.append("Phone Number: " + spot.phone);
				}
				if (spot.cuisine != null) {
					String cuis = "\n\nCuisine: " + spot.cuisine;
					msg.append(cuis);
				}
				builder.setMessage(msg.toString());
				if (spot.phone != null) {
					builder.setNegativeButton("Call", new DialogInterface.OnClickListener() {	
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent call = new Intent(Intent.ACTION_CALL);
							call.setData(Uri.parse("tel:"+spot.phone));
							startActivity(call);
						}
					});
				}
				
				builder.setPositiveButton("Directions", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String url  = String.format("http://maps.google.com/maps?daddr=%s,%s",spot.loc.latitude,spot.loc.longitude);
						Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
								Uri.parse(url));
						startActivity(intent);
					}
				});
				AlertDialog dialog = builder.create();
				dialog.show();
				TextView messageView = (TextView)dialog.findViewById(android.R.id.message);
				messageView.setGravity(Gravity.CENTER);
			}
			
		});
        return rootView;
    }
	
	private void changeMarkerVisibility(boolean state, int filter) {
		Iterator<Marker> it = foodMarkers.iterator();
		if (filter == BAR) {
			it = barMarkers.iterator();
		} else if (filter == ATTRACTIONS) {
			it = attractionMarkers.iterator();
		}
		while(it.hasNext()) {
			Marker tmp = it.next();
			tmp.setVisible(state);
		}
	}

	private void addMarker (LatLng address, String title, String msg, int filter) {
		MarkerOptions mO = new MarkerOptions();
		mO.position(address);
		mO.title(title);
		mO.snippet(msg);
		if (filter == FOOD) {
			mO.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
			Marker tmp = mMap.addMarker(mO);
			foodMarkers.add(tmp);
		} else if (filter == BAR) {
			mO.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
			Marker tmp = mMap.addMarker(mO);
			barMarkers.add(tmp);
		} else if (filter == ATTRACTIONS) {
			mO.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
			Marker tmp = mMap.addMarker(mO);
			attractionMarkers.add(tmp);
		} else if (filter == NEITHER) {
			mMap.addMarker(mO);
		}
	}
	
	public void onPause() {
		Log.d("MapsFrag","onPause() called");
		firstBar = true;
		firstFood = true;
		firstAttraction = true;
		barsCheckBox.setChecked(false);
		foodCheckBox.setChecked(false);
		attractionCheckBox.setChecked(false);
		super.onPause();
	}
	public void onResume() {
		Log.d("MapsFrag","onResume() called");
		super.onResume();
	}
	public void onDestroyView() {
	    super.onDestroyView();
	    Fragment fragment = (getFragmentManager().findFragmentById(R.id.map));  
	    FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
	    ft.remove(fragment);
	    ft.commit();
	}
	
	private void addFoodLocations() {
		MapSpot f1 = new MapSpot("Kokkari Estiatorio","Greek, Mediterranean","(415) 981-0983",new LatLng(37.797137,-122.399648));
		MapSpot f2 = new MapSpot("The House","Asian Fusion","(415) 986-8612",new LatLng(37.798427,-122.407089));
		MapSpot f3 = new MapSpot("Hunan Homes Restaurant","Chinese","(415) 982-2844",new LatLng(37.796207,-122.405676));
		MapSpot f4 = new MapSpot("BIX","American(Traditional)","(415) 433-6300",new LatLng(37.796897,-122.40289));
		MapSpot f5 = new MapSpot("E' Tutto Qua","Italian","(415) 989-1002", new LatLng(37.797753,-122.406291));
		MapSpot f6 = new MapSpot("Sushirrito","Sushi Bars, Asian Fusion","(415) 544-9868", new LatLng(37.790228	,-122.403783));
		MapSpot f7 = new MapSpot("R&G Lounge","Chinese,Seafood","(415) 982-7877", new LatLng(37.794106,-122.404909));
		MapSpot f8 = new MapSpot("Caffe Macaroni","Italian","(415) 956-9737",new LatLng(37.796139,-122.404388));
		MapSpot f9 = new MapSpot("Wayfare Tavern","American (Traditional)","(415) 772-9060", new LatLng(37.793982,-122.402331));
		MapSpot f10 = new MapSpot("Raavi Pakistani Indian Cuisine","Indian, Pakistani","(415) 693-0499",new LatLng(37.796215,-122.404587));
		MapSpot f11 = new MapSpot("Yan's Kitchen","Chinese","(415) 989-8820",new LatLng(37.796104,-122.404399));
		MapSpot f12 = new MapSpot("Bocadillos","Spanish","(415) 982-2622",new LatLng(37.795824,-122.403283));
		MapSpot f13 = new MapSpot("House of Nanking","Chinese","(415) 421-1429",new LatLng(37.796455,-122.405235));
		MapSpot f14 = new MapSpot("Taqueria Zorro","Mexican","(415) 392-9677",new LatLng(37.798132,-122.406842));
		foodSpots.put(f1.name,f1);
		foodSpots.put(f2.name,f2);
		foodSpots.put(f3.name,f3);
		foodSpots.put(f4.name,f4);
		foodSpots.put(f5.name,f5);
		foodSpots.put(f6.name,f6);
		foodSpots.put(f7.name,f7);
		foodSpots.put(f8.name,f8);
		foodSpots.put(f9.name,f9);
		foodSpots.put(f10.name,f10);
		foodSpots.put(f11.name,f11);
		foodSpots.put(f12.name,f12);
		foodSpots.put(f13.name,f13);
		foodSpots.put(f14.name,f14);
	}
	
	private void addBarLocations() {
		//MapSpot b1 = new MapSpot("Name","Phone",new LatLng(3,3));
		MapSpot b0 = new MapSpot("Buddha Lounge","(415) 362-1792",new LatLng(37.795279,-122.40667));
		barSpots.put(b0.name,b0);
		MapSpot b1 = new MapSpot("Comstock Saloon","(415) 617-0071",new LatLng(37.796965,-122.405583));
		barSpots.put(b1.name,b1);
		MapSpot b2 = new MapSpot("Li Po Cocktail Lounge","(415) 982-0072",new LatLng(37.79542,-122.406473));
		barSpots.put(b2.name,b2);
		MapSpot b3 = new MapSpot("Roka Bar","(415) 362-8887",new LatLng(37.796533,-122.403845));
		barSpots.put(b3.name,b3);
		MapSpot b4 = new MapSpot("Grasslands Bar & Lounge","(415) 288-8636",new LatLng(37.796461,-122.405236));
		barSpots.put(b4.name,b4);
		MapSpot b5 = new MapSpot("Barrique","(415) 421-9200",new LatLng(37.797305,-122.403168));
		barSpots.put(b5.name,b5);
		MapSpot b6 = new MapSpot("Vesuvio Cafe","(415) 362-3370",new LatLng(37.797568,-122.406311));
		barSpots.put(b6.name,b6);
		MapSpot b7 = new MapSpot("Elephant & Castle","(415) 268-3900",new LatLng(37.795021,-122.400806));
		barSpots.put(b7.name,b7);
		MapSpot b8 = new MapSpot("Antologia Vinoteca","(415) 274-8423",new LatLng(37.79783,-122.405806));
		barSpots.put(b8.name,b8);
		MapSpot b9 = new MapSpot("15 Romolo","(415) 398-1359",new LatLng(37.798226,-122.406437));
		barSpots.put(b9.name,b9);
		MapSpot b10 = new MapSpot("EZ5","(415) 362-9321",new LatLng(37.794072,-122.404467));
		barSpots.put(b10.name,b10);
		MapSpot b11 = new MapSpot("Score! Bar and Lounge","(415) 368-2136",new LatLng(37.798088,-122.405147));
		barSpots.put(b11.name,b11);
		MapSpot b12 = new MapSpot("Bamboo Hut","(415) 989-8555",new LatLng(37.798055,-122.40518));
		barSpots.put(b12.name,b12);
	}
	
	private void addAttractionLocations() {
		MapSpot a0 = new MapSpot("Alcatraz Island","(415) 561-4900",new LatLng(37.826977,-122.422956));
		attractionSpots.put(a0.name,a0);
		MapSpot a1 = new MapSpot("Angel Island","(415) 438-8361",new LatLng(37.860909,-122.432568));
		attractionSpots.put(a1.name,a1);
		MapSpot a2 = new MapSpot("Golden Gate Park","(415) 831-2700",new LatLng(37.769421,-122.486214));
		attractionSpots.put(a2.name,a2);
		MapSpot a3 = new MapSpot("Exploratorium","(415) 528-4444 ",new LatLng(37.8018,-122.397095));
		attractionSpots.put(a3.name,a3);
		MapSpot a4 = new MapSpot("de Young Museum","(415) 750-3600 ",new LatLng(37.771517,-122.468733));
		attractionSpots.put(a4.name,a4);
		MapSpot a5 = new MapSpot("Fisherman's Wharf (Pier 39)","(415) 981-7437 ",new LatLng(37.809147,-122.410012));
		attractionSpots.put(a5.name,a5);
		MapSpot a6 = new MapSpot("Golden Gate Bridge","(415) 921-5858",new LatLng(37.819878,-122.478503));
		attractionSpots.put(a6.name,a6);
		MapSpot a7 = new MapSpot("California Academy of Sciences","(415) 379-8000",new LatLng(37.769979,-122.466288));
		attractionSpots.put(a7.name,a7);
		MapSpot a8 = new MapSpot("Lombard Street",new LatLng(37.80199,-122.419655));
		attractionSpots.put(a8.name,a8);
		MapSpot a9 = new MapSpot("Powell St. Cable Cars",new LatLng(37.785735,-122.40629));
		attractionSpots.put(a9.name,a9);
		MapSpot a10 = new MapSpot("Ghirardelli Square","(415) 775-5500",new LatLng(37.805745,-122.422976));
		attractionSpots.put(a10.name,a10);
		MapSpot a11 = new MapSpot("Coit Tower","(415) 362-0808 ",new LatLng(37.802374,-122.405818));
		attractionSpots.put(a11.name,a11);
		MapSpot a12 = new MapSpot("Twin Peaks","(415) 864-9470",new LatLng(37.75456,-122.446421));
		attractionSpots.put(a12.name,a12);
		MapSpot a13 = new MapSpot("The Presidio","(415) 561-4323",new LatLng(37.798093,-122.466794));
		attractionSpots.put(a13.name,a13);
		MapSpot a14 = new MapSpot("Aquarium of the Bay","(415) 623-5300",new LatLng(37.808521,-122.409277));
		attractionSpots.put(a14.name,a14);
		MapSpot a15 = new MapSpot("The Embarcadero Ferry Building","(415) 983-8030",new LatLng(37.795105,-122.393861));
		attractionSpots.put(a15.name,a15);
	}
	
	private void addFoodMarkers() {
		addFoodLocations();
		Iterator<MapSpot> it = foodSpots.values().iterator();
		while(it.hasNext()) {
			MapSpot temp = it.next();
			String msg = temp.cuisine;
			addMarker(temp.loc, temp.name, msg, FOOD);
		}
	}
	
	private void addBarMarkers() {
		addBarLocations();
		Iterator<MapSpot> it = barSpots.values().iterator();
		while(it.hasNext()) {
			MapSpot temp = it.next();
			String msg = "Phone Number: " + temp.phone;
			addMarker(temp.loc, temp.name, msg, BAR);
		}
	}
	
	private void addAttractionMarkers() {
		addAttractionLocations();
		Iterator<MapSpot> it = attractionSpots.values().iterator();
		while(it.hasNext()) {
			MapSpot temp = it.next();
			String msg = "";
			if (temp.phone != null) {
				msg = "Phone Number: " + temp.phone;
			}
			addMarker(temp.loc, temp.name, msg, ATTRACTIONS);
		}
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
	
	private class MapSpot { 
		String name;
		String cuisine;
		String phone;
		LatLng loc;
		
		public MapSpot(String name, LatLng loc) {
			this.name = name;
			this.loc = loc;
		}
		public MapSpot(String name, String cuisine, String phone, LatLng loc) {
			this.name = name;
			this.loc = loc;
			this.cuisine = cuisine;
			this.phone = phone;
		}
		public MapSpot(String name, String phone, LatLng loc) {
			this.name = name;
			this.loc = loc;
			this.phone = phone;
		}
	}
}
