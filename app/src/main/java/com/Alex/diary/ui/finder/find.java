package com.Alex.diary.ui.finder;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

import com.Alex.diary.R;
import com.Alex.diary.ui.home.MyWebViewClient;

public class find extends Fragment {
        public static WebView webView;
        public  View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle data) {
            View view = inflater.inflate(R.layout.fragment_teachers, vg, false);
            webView = (WebView) view.findViewById(R.id.webViewTFind);
            webView.setWebViewClient(new MyWebViewClient());
            switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
                case Configuration.UI_MODE_NIGHT_YES:
                    if(WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                        WebSettingsCompat.setForceDark(webView.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
                    }
                    break;
                case Configuration.UI_MODE_NIGHT_NO:
                    // process
                    break;
            } //Dark theme
        webView.loadUrl(getString(R.string.TFind));
        webView.getSettings().setJavaScriptEnabled(true);
        return view;
        }
        }