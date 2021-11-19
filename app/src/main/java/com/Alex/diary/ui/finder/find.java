package com.Alex.diary.ui.finder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import com.Alex.diary.R;
import com.Alex.diary.ui.home.MyWebViewClient;

public class find extends Fragment {
        public static WebView webView;
        public  View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle data) {
            View view = inflater.inflate(R.layout.fragment_teachers, vg, false);
            webView = (WebView) view.findViewById(R.id.webViewTFind);
            webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(getString(R.string.TFind));
        webView.getSettings().setJavaScriptEnabled(true);
        return view;
        }
        }