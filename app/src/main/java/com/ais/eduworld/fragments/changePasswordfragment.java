package com.ais.eduworld.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ais.eduworld.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class changePasswordfragment extends Fragment {

    WebView webView;


    public changePasswordfragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_change_passwordfragment, container, false);

        webView=(WebView)view.findViewById(R.id.WebView_Reset);
        webView.loadUrl("http://aravalieduworld.com/PasswordReset.aspx");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        return view;

    }
}
