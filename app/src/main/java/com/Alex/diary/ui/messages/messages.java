package com.Alex.diary.ui.messages;

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

            /*if(!pc.Check(this.getContext()) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                Intent intent = new Intent(getActivity(), PurshaseCore.class);
                startActivity(intent);
            }*/
            View view = inflater.inflate(R.layout.fragment_messages, vg, false);
            webView = (WebView) view.findViewById(R.id.webViewMsg);
            webView.setWebViewClient(new MyWebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    webView.loadUrl("javascript:document.getElementsByClassName(\"x-tool-minimize\")[0].remove()"); //hide head buttons
                    webView.loadUrl("javascript:document.getElementsByClassName(\"x-tool-close\")[0].remove()");
                    webView.loadUrl("javascript:document.getElementById(\"taskbar-1024-innerCt\").remove()"); //hide taskbar
                    webView.loadUrl("javascript:document.getElementById(\"button-2134\").remove()"); //hide groups button
                    webView.loadUrl("javascript:document.getElementsByClassName(\"ux-notification-light\")[0].remove()"); // hide notifications

                    webView.loadUrl("javascript:document.getElementsByClassName(\"x-tool-maximize\")[0].click()"); //on full screeni.
                    webView.loadUrl("javascript:document.getElementById(\"x-tool-restore\").remove()"); // hide head button
                    webView.loadUrl("javascript:document.getElementById(\"ext-comp-1017-body\").remove()"); //hide background buttons
                    //webView.loadUrl("javascript:document.getElementsByClassName(\"x-panel x-fit-item x-panel-default\")[0].remove()"); // hide taskbar
                    //webView.evaluateJavascript("document.getElementById(\"toolbar-1084\").style=\"top: 580px;\";", null); // show add button
                    //webView.evaluateJavascript("document.getElementById(\"messaging\").style=\"top: 50px; left:0px;\";", null);//move window to up(now not work)
                    super.onPageFinished(view, url);

                }
            });if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
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
            webView.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");
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