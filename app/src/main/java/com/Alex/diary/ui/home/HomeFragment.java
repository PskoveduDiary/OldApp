package com.Alex.diary.ui.home;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;

import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;


import com.Alex.diary.R;
import android.webkit.WebView;
public class HomeFragment extends Fragment {
    public static WebView webView;
    public static String diary;

    public  View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle data) {
        View view = inflater.inflate(R.layout.fragment_home, vg, false);
        webView = (WebView) view.findViewById(R.id.webViewHome);
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
        webView.setWebViewClient(new MyWebViewClient());
        diary = getString(R.string.Diary);
        webView.loadUrl(diary); //load Diary
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);//Diary Requires DOM
        return view;
    }
    public static void Login(){
        webView.loadUrl("https://passport.pskovedu.ru/?returnTo=http://one.pskovedu.ru");
    }
    public static void Logout(){
        webView.loadUrl("https://one.pskovedu.ru/auth/logout");
    }
    public static void Pass(){
        webView.loadUrl("https://one.pskovedu.ru");//For login
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                webView.loadUrl(diary);//Return to diary
                }
            }, 1500); //specify the number of milliseconds
        }
}
