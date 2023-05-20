package com.chandra.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class FragmentTwo extends Fragment {

    TextView phNum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_two, container, false);

        phNum = (TextView) view.findViewById(R.id.phValue);

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String phVal = "https://api.thingspeak.com/channels/1648232/feeds.json?results=1";

        StringRequest PhRequest = new StringRequest(Request.Method.GET, phVal,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        phNum.setText("PhLevel: " + response.substring(response.length()-15).replaceAll("[^\\d.]", ""));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                phNum.setText("That didn't work!");
            }
        });
        queue.add(PhRequest);

        WebView ph = view.findViewById(R.id.ph);

        ph.loadUrl("https://thingspeak.com/channels/1648232/charts/1?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&type=line&update=15");
        ph.getSettings().setJavaScriptEnabled(true);
        ph.setWebViewClient(new WebViewClient());
        return view;
    }
}