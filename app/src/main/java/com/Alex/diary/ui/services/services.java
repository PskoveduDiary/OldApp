package com.Alex.diary.ui.services;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.Alex.diary.R;
import com.Alex.diary.ui.home.MyWebViewClient;

public class services extends Fragment {

    public static WebView webView;
    public  View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle data) {
        View view = inflater.inflate(R.layout.fragment_services, vg, false);
        webView = (WebView) view.findViewById(R.id.webViewServices);
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(getString(R.string.Services));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        return view;
    }
}