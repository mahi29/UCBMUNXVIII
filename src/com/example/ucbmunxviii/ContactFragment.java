package com.example.ucbmunxviii;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class ContactFragment extends ListFragment {
	ArrayList<SecretariatMember> data;
	public ContactFragment(){}
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contact, container, false);
        data = new ArrayList<SecretariatMember>();
        populateMembers();
        SecretariatAdapter adapter = new SecretariatAdapter(getActivity(),data);
        setListAdapter(adapter);
        return rootView;
    }
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		final SecretariatMember member = data.get(position);
		String[] nameA = member.name.split("");
		String firstName = nameA[0];
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK);
		builder.setCancelable(true);
		TextView title = new TextView(getActivity());
		title.setText(member.name);
		title.setBackgroundColor(getResources().getColor(R.color.dialog_background));
		title.setPadding(10, 10, 10, 10);
		title.setGravity(Gravity.CENTER);
		title.setTextColor(getResources().getColor(R.color.list_background_pressed));
		title.setTextSize(20);
		builder.setCustomTitle(title);
		String msg = "Email: " +  member.email;
		if (member.number != null) msg += "\n\nPhone Number: " + member.number;
		if (member.number == null) msg = "\n"+msg;
		builder.setMessage(msg);
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		builder.setPositiveButton("Email " + firstName, new DialogInterface.OnClickListener() {	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent i  = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				i.putExtra(Intent.EXTRA_EMAIL  , new String[]{member.email});
				startActivity(Intent.createChooser(i, "Send e-mail")); 
			}
		});
		if (member.number != null) {
			builder.setNeutralButton("Call " + firstName, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent call = new Intent(Intent.ACTION_CALL);
					call.setData(Uri.parse("tel:"+member.number));
					startActivity(call);
				}
			});
		}
		AlertDialog dialog = builder.create();
		dialog.show();
		TextView messageView = (TextView)dialog.findViewById(android.R.id.message);
		messageView.setGravity(Gravity.CENTER);
	}

	private void populateMembers() {
		SecretariatMember sg = new SecretariatMember(
				"Adam B. Spaulding",
				getActivity().getString(R.string.SG),
				"sg@ucbmun.org",
				"707-685-4241"
		);
		SecretariatMember dsg = new SecretariatMember(
				"Anirudh Garg",
				getActivity().getString(R.string.DSG),
				"dsg@ucbmun.org",
				"510-517-8201"
		);	
		SecretariatMember cose = new SecretariatMember(
				"Akash Sharma",
				getActivity().getString(R.string.COSE),
				"cos-external@ucbmun.org",
				"917-704-9970"
		);
		SecretariatMember cosi = new SecretariatMember(
				"Zahra AbouKhalil",
				getActivity().getString(R.string.COSI),
				"cos-internal@ucbmun.org",
				"925-330-2004"
		);
		SecretariatMember usgc = new SecretariatMember(
				"Benjamin Wigley",
				getActivity().getString(R.string.USGC),
				"usg-crisis@ucbmun.org"
		);
		SecretariatMember usgjc = new SecretariatMember(
				"Chirag Soni",
				getActivity().getString(R.string.USGJC),
				"usg-jcc@ucbmun.org"
		);
		SecretariatMember usgsb = new SecretariatMember(
				"Eviane Leidig",
				getActivity().getString(R.string.USGSB),
				"usg-sbga@ucbmun.org"
		);
		SecretariatMember usgdd = new SecretariatMember(
				"Lynn Yu",
				getActivity().getString(R.string.USGDD),
				"usg-dd@ucbmun.org"
		);
		SecretariatMember dbr1 = new SecretariatMember(
				"Redha Qabazard",
				getActivity().getString(R.string.DBR),
				"dbr@ucbmun.org"
		);
		SecretariatMember dbr2 = new SecretariatMember(
				"Harsh Shah",
				getActivity().getString(R.string.DBR),
				"dbr@ucbmun.org"
		);
		SecretariatMember dop = new SecretariatMember(
				"Naomi Egel",
				getActivity().getString(R.string.DOP),
				"programming@ucbmun.org"
		);
		SecretariatMember dbmr = new SecretariatMember(
				"Hoori Kim",
				getActivity().getString(R.string.DBMR),
				"branding@ucbmun.org"
		);
		SecretariatMember dom = new SecretariatMember(
				"Rajit Kinra",
				getActivity().getString(R.string.DOM),
				"merchandise@ucbmun.org"
		);
		SecretariatMember dot = new SecretariatMember(
				"Mahith Amancherla",
				getActivity().getString(R.string.DOT),
				"technology@ucbmun.org"
		);
		data.add(sg);
		data.add(dsg);
		data.add(cose);
		data.add(cosi);
		data.add(usgc);
		data.add(usgjc);
		data.add(usgsb);
		data.add(usgdd);
		data.add(dbr1);
		data.add(dbr2);
		data.add(dop);
		data.add(dbmr);
		data.add(dom);
		data.add(dot);
	}
}
