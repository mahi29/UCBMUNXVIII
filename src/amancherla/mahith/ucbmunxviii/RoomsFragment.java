package amancherla.mahith.ucbmunxviii;

import java.util.ArrayList;
import java.util.HashMap;

import amancherla.mahith.ucbmunxviii.R;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

public class RoomsFragment extends Fragment {
	//The order of names, rooms, and pictures (in GridAdapter.java) all need to match up. Be careful!
	//TODO: Figure out how to open the downloaded file from the notification manager.
	//TODO: Re-factor the committee information into one class for easy management
	private ArrayList<CommitteeItem> sbga;
	private ArrayList<CommitteeItem> crisis;
	private ArrayList<CommitteeItem> jcc;
	Button rulesButton;
	private String[] committeeTypes = {
			"Specialized Bodies and General Assembly",
			"Crisis Committees",
			"Joint Crisis Committees"
	};
	private HashMap<String,ArrayList<CommitteeItem>> committees;
	ExpandableListView expandableListView;
	private DownloadManager dm;
	private static final String URL_PREFIX = "http://ucbmun.org/bgGuides/";
	private static final String RULES_PREFIX = "http://ucbmun.org/";
	private static final String UPDATE_PREFIX = "http://ucbmun.org/updatePapers/";

	public RoomsFragment(){}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_rooms, container, false);
		populateData();
		expandableListView = (ExpandableListView) rootView.findViewById(R.id.committeeExp);
		rulesButton = (Button) rootView.findViewById(R.id.rules);
		expandableListView.setAdapter(new CommitteeAdapter(getActivity(),committeeTypes, committees));
		expandableListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				final CommitteeItem committee = committees.get(committeeTypes[groupPosition]).get(childPosition);
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK);
				builder.setCancelable(true);
				builder.setCustomTitle(customText(committee.name));
				String msg = "Location: " + committee.location;
				msg += "\n\nHead Chair: " + committee.chair;
				builder.setMessage(msg);
				builder.setNegativeButton("View Map", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Fragment fpFrag = new FloorPlan();
						if (fpFrag != null) {
							FragmentManager fragmentManager = getFragmentManager();
							fragmentManager.beginTransaction()
							.replace(R.id.frame_container, fpFrag).addToBackStack(null).commit();
							getActivity().getActionBar().setTitle("Hotel Map");
						}
					}
				});

				builder.setPositiveButton("Download Guide", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {		
						String suffix = committee.suffixUrl;
						String guideURL = URL_PREFIX + suffix;
						if (dm == null)
							dm = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
						Request request = new Request(Uri.parse(guideURL));
						request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,suffix);
						request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
						request.setVisibleInDownloadsUi(true);
						dm.enqueue(request);
					}
				});
				if (!committee.name.equals("Ad-Hoc")) {
					builder.setNeutralButton("Download Update Paper", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							String suffix = committee.updateURL;
							String guideURL = UPDATE_PREFIX + suffix;
							if (dm == null)
								dm = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
							Request request = new Request(Uri.parse(guideURL));
							request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,suffix);
							request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
							request.setVisibleInDownloadsUi(true);
							dm.enqueue(request);
						}

					});
				}


				AlertDialog dialog = builder.create();
				dialog.show();
				//Centers message text
				TextView messageView = (TextView)dialog.findViewById(android.R.id.message);
				messageView.setGravity(Gravity.CENTER);
				return false;
			}
		});
		rulesButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String suffix = "rules.pdf";
				String guideURL = RULES_PREFIX + suffix;
				if (dm == null)
					dm = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
				Request request = new Request(Uri.parse(guideURL));
				request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,suffix);
				request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
				request.setVisibleInDownloadsUi(true);
				dm.enqueue(request);
			}

		});        


		return rootView;
	}
	BroadcastReceiver onNotificationClick = new BroadcastReceiver() {
		@Override
		public void onReceive(Context ctxt, Intent intent) {
			Log.d("onNotificationClick","onReceive");
		}
	};
	
	/*
	 * Creates a custom title
	 * Centers the text and changes the color to gold
	 * INPUT: grid position clicked
	 * OUTPUT: TextView to be displayed
	 */
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


	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LocalBroadcastManager.getInstance(getActivity()).registerReceiver(onNotificationClick, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
	}
	private void populateData() {
		committees = new HashMap<String, ArrayList<CommitteeItem>>();
		sbga = new ArrayList<CommitteeItem>();
		crisis = new ArrayList<CommitteeItem>();
		jcc = new ArrayList<CommitteeItem>();
		sbga.add(new CommitteeItem("European Court of Human Rights","Columbus II","Hoori Kim","ECHR"));
		sbga.add(new CommitteeItem("UN General Assembly","Grand Ballroom","Miriam Arghavani","GA"));
		sbga.add(new CommitteeItem("Nuclear Safety Summit","Pine","Naomi Egel","NSS"));
		sbga.add(new CommitteeItem("Committee on World Food Security","Columbus I","Katie McCloskey","CWFS"));
		crisis.add(new CommitteeItem("UN Security Council","Columbus III","Shannon Thomas","UNSC"));
		crisis.add(new CommitteeItem("Pinochet's Chile","Mason I","Ramit Malhotra","Chile"));
		crisis.add(new CommitteeItem("Chevron Executive Board","Mason II","Rashi Jindani","Chevron"));
		crisis.add(new CommitteeItem("Sri Lanka 2006","Montgomery","Antoine Bichara","SriLanka"));
		crisis.add(new CommitteeItem("Ad-Hoc","Pyramid","Lynn Yu","AdHoc"));
		jcc.add(new CommitteeItem("Covert Ops - CIA","Front","Noah Efron","CIA"));
		jcc.add(new CommitteeItem("Covert Ops - MSS","Sansome","Sahil Gupta","MSS"));
		jcc.add(new CommitteeItem("Covert Ops - Mossad","Washington","Alizeh Paracha","Mossad"));
		jcc.add(new CommitteeItem("Covert Ops - VEVAK","Davis","Divit Sood","VEVAK"));
		committees.put(committeeTypes[0],sbga);
		committees.put(committeeTypes[1], crisis);
		committees.put(committeeTypes[2], jcc);

	}

	public class CommitteeItem {
		String name;
		String location;
		String chair;
		String suffixUrl;
		String updateURL;

		public CommitteeItem(String name, String location, String chair, String url) {
			this.name = name;
			this.location = location;
			this.chair = chair;
			this.suffixUrl = url+"_BG.pdf";
			this.updateURL = url+"_UP.pdf";
		}
	}
}
