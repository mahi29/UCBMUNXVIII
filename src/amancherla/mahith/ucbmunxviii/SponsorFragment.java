package amancherla.mahith.ucbmunxviii;

import java.util.ArrayList;
import java.util.HashMap;

import amancherla.mahith.ucbmunxviii.R;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class SponsorFragment extends Fragment {

	String[] sponsorLevels = {"Gold","Silver","Bronze"};;
	HashMap<String,ArrayList<SponsorItem>> sponsors;
	ExpandableListView expandableListView;
	public SponsorFragment() {};
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
        View rootView = inflater.inflate(R.layout.fragment_sponsors, container, false);
        populate();
        expandableListView = (ExpandableListView) rootView.findViewById(R.id.sponsorExp);
        SponsorAdapter adapter = new SponsorAdapter(getActivity(),sponsorLevels, sponsors);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				String sponsorType = sponsorLevels[groupPosition];
				SponsorItem sponsor =  sponsors.get(sponsorType).get(childPosition);
				String url = sponsor.url;
				Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
				startActivity(i);
				return true;
			}
        	
        });
        return rootView;
    }
	
	private void populate() {
		sponsors = new HashMap<String,ArrayList<SponsorItem>>();
		//Gold
		ArrayList<SponsorItem> g = new ArrayList<SponsorItem>();
		g.add(new SponsorItem("Kuwaiti Cultural Office",R.drawable.kuwait,"http://www.kuwaitculture.com/"));
		sponsors.put(sponsorLevels[0],g);
		//Silver
		ArrayList<SponsorItem> s = new ArrayList<SponsorItem>();
		s.add(new SponsorItem("Berkeley Law",R.drawable.berkeley_law,"http://www.law.berkeley.edu/"));
		s.add(new SponsorItem("Kaplan",R.drawable.kaplan,"http://www.kaplan.com/"));
		s.add(new SponsorItem("Trachtenberg School of Public Policy & Public Administration",R.drawable.gw,"http://tspppa.gwu.edu/"));
		sponsors.put(sponsorLevels[1],s);
		//Bronze
		ArrayList<SponsorItem> b = new ArrayList<SponsorItem>();
		b.add(new SponsorItem("Boston University, Graduate School of Arts & Sciences, Department of International Relations",R.drawable.bucas,"http://www.bu.edu/ir/"));
		sponsors.put(sponsorLevels[2],b);
		
	}
	public class SponsorItem {
		String name;
		int image;
		String url;
		
		public SponsorItem(String name, int image, String url) {
			this.name = name;
			this.image = image;
			this.url = url;
		}
	}
}
