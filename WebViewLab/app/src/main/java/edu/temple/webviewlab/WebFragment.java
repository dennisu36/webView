package edu.temple.webviewlab;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class WebFragment extends Fragment {

    //variables, views, widgets, etc.
    View view;
    WebView webView;
    Context context;
    Button previousButton;
    Button forwardButton;
    String URL;
    public static String UrlHold = "";
    public static String UrlHold2 = "";
    
    public WebFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        URL = bundle.getString(UrlHold);
    }

    public static WebFragment newInstance(String URL){
        WebFragment webFragment = new WebFragment();
        Bundle bundle = new Bundle();
        bundle.putString(webFragment.UrlHold, URL);
        webFragment.setArguments(bundle);
        return webFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_web, container, false);
        previousButton = view.findViewById(R.id.previousButton);
        forwardButton = view.findViewById(R.id.forwardButton);
        webView = view.findViewById(R.id.webView);
        webView.setWebViewClient(new HelloWebViewClient());

        if(savedInstanceState != null){
            URL = savedInstanceState.getString(UrlHold2);
        }
        loadUrlFromTextView();
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(webView.canGoBack()) webView.goBack();
            }
        });

        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(webView.canGoForward()) webView.goForward();
            }
        });
        return view;
    }

    public void loadURL(String url){
        URL = url;
        loadUrlFromTextView();
    }

    private void loadUrlFromTextView() {
        webView.loadUrl(URL);
    }
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            ((GetURL)context).getURL(url, view);
            URL = url;
        }

    }
    interface GetURL {
        void getURL(String Current, WebView Success);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(UrlHold2, URL);
        super.onSaveInstanceState(outState);
    }
}