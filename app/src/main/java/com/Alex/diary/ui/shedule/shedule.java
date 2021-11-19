package com.Alex.diary.ui.shedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;


import com.Alex.diary.R;
import com.Alex.diary.ui.home.MyWebViewClient;


import android.webkit.WebView;
public class shedule extends Fragment {
        public static WebView webView;
        public  View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle data) {
            View view = inflater.inflate(R.layout.fragment_shedule, vg, false);
            webView = (WebView) view.findViewById(R.id.webViewShedule);
            webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(getString(R.string.Shedule));
        webView.getSettings().setJavaScriptEnabled(true);
        return view;
        }
        }