package com.Alex.diary.ui.home;

import android.content.Context;
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
import com.Alex.diary.XLSParserItog;


public class HomeFragment extends Fragment {
    public static WebView webView;
    public static String diary;
    public static String Url;
    static String cookies;
    static Context contextt;

    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle data) {
        View view = inflater.inflate(R.layout.fragment_home, vg, false);
        webView = (WebView) view.findViewById(R.id.webViewHome);
        contextt = getActivity().getBaseContext();
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
                cookies = CookieManager.getInstance().getCookie(url);
                // save cookies or call new fun to handle actions
                //  newCookies(cookies);
                //webView.loadUrl("javascript:alert(DataControl.getGuid())");

            }

            @Override
            public void Download(String url){
                    Intent switchActivityIntent = new Intent(contextt, XLSParser.class);
                    switchActivityIntent.putExtra("gfjxsasd", cookies); //like obfuscation
                    switchActivityIntent.putExtra("djhjgfj", url); //too like obfuscation
                    startActivity(switchActivityIntent);
            }
            @Override
            public void DownloadItog(String url){
                    Intent switchActivityIntent = new Intent(contextt, XLSParserItog.class);
                    switchActivityIntent.putExtra("gfjxsasd", cookies); //like obfuscation
                    switchActivityIntent.putExtra("djhjgfj", url); //too like obfuscation
                    startActivity(switchActivityIntent);
            }
        });
        webView.setWebChromeClient(new MyWebChromeClient());
        diary = "https://one.pskovedu.ru/edv/index/participant";

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
