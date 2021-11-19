package com.Alex.diary.ui.messages;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import com.Alex.diary.R;
import com.Alex.diary.ui.home.MyWebViewClient;



public class messages extends Fragment {
        public static WebView webView;
        public  View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle data) {
            View view = inflater.inflate(R.layout.fragment_messages, vg, false);
            webView = (WebView) view.findViewById(R.id.webViewMsg);
            webView.setWebViewClient(new MyWebViewClient());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl("https://one.pskovedu.ru/#messaging");
            webView.loadUrl
                    ("javascript:(function() { " +
                            "document.getElementsByClassName('x-panel-default')[0].style.display='none'; " +
                            "}"
                    );
            return view;
        }
}