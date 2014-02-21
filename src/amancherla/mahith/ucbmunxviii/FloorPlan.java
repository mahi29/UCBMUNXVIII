package amancherla.mahith.ucbmunxviii;


import amancherla.mahith.ucbmunxviii.R;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FloorPlan extends Fragment {
	//TODO: Add Zoom capabilities to the map
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_floorplan, container, false);
        return rootView;
	}
	public void onPause() {
		super.onPause();
	}
}
