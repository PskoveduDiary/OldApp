package com.Alex.diary.ui.home;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

import com.Alex.diary.R;
import com.Alex.diary.XLSParser;


public class HomeFragment extends Fragment {
    public static WebView webView;
    public static String diary;
    public static String Url;
    static String cookies;

    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle data) {
        View view = inflater.inflate(R.layout.fragment_home, vg, false);
        webView = (WebView) view.findViewById(R.id.webViewHome);
        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES:
                if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                    WebSettingsCompat.setForceDark(webView.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
                }
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                // process
                break;
        } //Dark theme
        webView.setWebViewClient(new MyWebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                cookies = CookieManager.getInstance().getCookie(view.getUrl());
                // save cookies or call new fun to handle actions
                //  newCookies(cookies);
            }
            @Override
            public void Download(String url){
                    Intent switchActivityIntent = new Intent(getContext(), XLSParser.class);
                    switchActivityIntent.putExtra("gfjxsasd", cookies); //like obfuscation
                    switchActivityIntent.putExtra("djhjgfj", url); //too like obfuscation
                    startActivity(switchActivityIntent);
            }
        });
        diary = getString(R.string.Diary);

        webView.loadUrl(diary); //load Diary
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);//Diary Requires DOM
        return view;
    }

    public static void Login() {
        webView.loadUrl("https://passport.pskovedu.ru/?returnTo=http://one.pskovedu.ru");
    }

    public static void Logout() {
        webView.loadUrl("https://one.pskovedu.ru/auth/logout");
    }

    public static void Pass() {
        webView.loadUrl("https://one.pskovedu.ru");//For login
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                webView.loadUrl(diary);//Return to diary
            }
        }, 1500); //specify the number of milliseconds
    }
}