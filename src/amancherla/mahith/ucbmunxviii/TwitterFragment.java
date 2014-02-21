package amancherla.mahith.ucbmunxviii;

import amancherla.mahith.ucbmunxviii.R;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class TwitterFragment extends Fragment {
	
	WebView webView;
	ProgressDialog mProgress;
	private static final String URL = "https://twitter.com/UCBMUN";
	
	public TwitterFragment() {};
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_twitter, container, false);
        webView = (WebView) rootView.findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true);
		mProgress = new ProgressDialog(getActivity(), AlertDialog.THEME_HOLO_DARK);
		mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgress.setTitle("Loading");
		mProgress.setMessage("Please wait for a moment");
		mProgress.show();	
        webView.setWebViewClient(new WebViewClient() {
            // load URL
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            // when finish loading page
            public void onPageFinished(WebView view, String url) {
                if(mProgress.isShowing()) {
                    mProgress.dismiss();
                }
            }
        });
		webView.loadUrl(URL);
        return rootView;
    }
	
	@Override
	public void onResume() {
		super.onResume();
	}
}
