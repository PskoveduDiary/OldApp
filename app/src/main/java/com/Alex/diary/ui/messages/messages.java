package com.Alex.diary.ui.messages;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

import com.Alex.diary.PurshaseCore;
import com.Alex.diary.R;
import com.Alex.diary.ui.home.MyWebViewClient;



public class messages extends Fragment {
        public PurshaseCore pc = new PurshaseCore();
        public static WebView webView;
        public  View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle data) {
            if(!pc.Check(this.getContext()))
            {
                Intent intent = new Intent(getActivity(), PurshaseCore.class);
                startActivity(intent);
            }
            View view = inflater.inflate(R.layout.fragment_messages, vg, false);
            webView = (WebView) view.findViewById(R.id.webViewMsg);
            webView.setWebViewClient(new MyWebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {

                    webView.evaluateJavascript("document.getElementById(\"dataview-1069\").style.display = 'none'", null); //hide background buttons
                    webView.evaluateJavascript("document.getElementById('taskbar-1024').style.display = 'none'", null); // hide taskbar
                    webView.evaluateJavascript("document.getElementById(\"tool-1088\").style.display = 'none'", null); //hide head buttons
                    webView.evaluateJavascript("document.getElementById(\"tool-1091\").style.display = 'none'", null);
                    webView.evaluateJavascript("document.getElementById(\"button-1086\").style.display = 'none'", null); //hide groups button
                    webView.evaluateJavascript("document.getElementsByClassName(\"ux-notification-light\")[0].remove()", null); // hide notifications
                    webView.evaluateJavascript("document.getElementById(\"tool-1089-toolEl\").click()", null); //on full screen
                    webView.evaluateJavascript("document.getElementById(\"tool-1090\").style.display = 'none'", null); // hide head button
                    //webView.evaluateJavascript("document.getElementById(\"toolbar-1084\").style=\"top: 580px;\";", null); // show add button
                    //webView.evaluateJavascript("document.getElementById(\"messaging\").style=\"top: 50px; left:0px;\";", null);//move window to up(now not work)
                    super.onPageFinished(view, url);
                }
            });
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
            try {
                webView.setWebChromeClient(new WebChromeClient() {

                    @Override
                    public void onProgressChanged(WebView view,
                                                  int newProgress) {

                        if (newProgress >= 100) {

                            webView.scrollTo(0,0);
                        }

                        super.onProgressChanged(view, newProgress);
                    };

                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl("https://one.pskovedu.ru/#messaging");

            //              KEEP
            //document.getElementById("messaging").style="width: 1090px; height: 907px; left: 0px; top: 30px; z-index: 19011";
            //document.getElementById("taskbar-1024").style.display = 'none'
            //document.getElementById("ext-gen1180").style.display = 'none'
            //document.getElementById("messaging_header").style.display = 'none'

            return view;
        }
}