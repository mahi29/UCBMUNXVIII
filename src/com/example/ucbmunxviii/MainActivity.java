package com.example.ucbmunxviii;

//import info.androidhive.slidingmenu.adapter.NavDrawerListAdapter;
//import info.androidhive.slidingmenu.model.NavDrawerItem;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends Activity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	
	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	
	//Fragment Tags
	private static final String HOME = "0";
	private static final String SCHEDULE = "1";
	private static final String INFO = "2";
	private static final String MAP = "3";
	private static final String CONTACT = "4";
	private static final String SPONSORS = "5";
	private static final String TWITTER = "6";

	MapsFrag mapFragment;
	TwitterFragment  twitterFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		navMenuTitles[6] = "@UCBMUN";
		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// Conference Schedule
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		// Committee Rooms
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		// Map
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
		// Contact Us
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
		// Sponsors
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
		// Twitter Feed
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}
	
	public void openDrawer() {
		mDrawerLayout.openDrawer(mDrawerList);  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		String tag = null;
		Log.d("MainActivity","displayView");
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			tag = HOME;
			break;
		case 1:
			fragment = new ScheduleFragment();
			tag = SCHEDULE;
			break;
		case 2:
			fragment = new RoomsFragment();
			tag = INFO;
			break;
		case 3:
			if (mapFragment == null) {
				Log.d("MainActivity","mapFrag created");
				mapFragment = new MapsFrag();
			}
			fragment = mapFragment;
			tag = MAP;
			break;
		case 4:
			fragment = new ContactFragment();
			tag = CONTACT;
			break;
		case 5:
			fragment = new SponsorFragment();
			tag = SPONSORS;
			break;
		case 6:
			if (twitterFragment == null) {
				Log.d("MainActivity","twitterFragment created");
				twitterFragment = new TwitterFragment();
			}
			fragment = twitterFragment;
			tag = TWITTER;
		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment, tag).addToBackStack(null).commit();
			// update selected item and title, then close the drawer
			updateChoice(position);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}
	
	private void updateChoice(int position) {
		mDrawerList.setItemChecked(position, true);
		mDrawerList.setSelection(position);
		setTitle(navMenuTitles[position]);
	}
	

	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		HomeFragment home = (HomeFragment) getFragmentManager().findFragmentByTag(HOME);
		if (home!= null && home.isVisible()) {
			updateChoice(Integer.parseInt(HOME));
			return;
		}
		ScheduleFragment schedule = (ScheduleFragment) getFragmentManager().findFragmentByTag(SCHEDULE);
		if (schedule != null && schedule.isVisible()) {
			updateChoice(Integer.parseInt(SCHEDULE));
			return;
		}
		RoomsFragment rooms = (RoomsFragment) getFragmentManager().findFragmentByTag(INFO);
		if (rooms != null && rooms.isVisible()) {
			updateChoice(Integer.parseInt(INFO));
			return;
		}
		MapsFrag maps = (MapsFrag) getFragmentManager().findFragmentByTag(MAP);
		if (maps != null && maps.isVisible()) {
			updateChoice(Integer.parseInt(MAP));
			return;
		}
		ContactFragment contact = (ContactFragment) getFragmentManager().findFragmentByTag(CONTACT);
		if (contact != null && contact.isVisible()) {
			updateChoice(Integer.parseInt(CONTACT));
			return;
		}
		SponsorFragment sponsor = (SponsorFragment) getFragmentManager().findFragmentByTag(SPONSORS);
		if (sponsor != null && sponsor.isVisible()) {
			updateChoice(Integer.parseInt(SPONSORS));
			return;
		}
		TwitterFragment twitter = (TwitterFragment) getFragmentManager().findFragmentByTag(TWITTER);
		if (twitter != null && twitter.isVisible()) {
			updateChoice(Integer.parseInt(TWITTER));
			return;
		}
	}
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

}
