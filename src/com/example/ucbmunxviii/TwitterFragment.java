package com.example.ucbmunxviii;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;

public class TwitterFragment extends Fragment {
	
	WebView webView;
	private static final String URL = "https://twitter.com/UCBMUN";
	
	public TwitterFragment() {};
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_twitter, container, false);
        webView = (WebView) rootView.findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient(){
			@Override
	        public void onPageStarted(WebView view, String url, Bitmap favicon) {
	            super.onPageStarted(view, url, favicon);
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
