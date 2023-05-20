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


public class FragmentThree extends Fragment {
    TextView waterNum;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_three, container, false);

        waterNum = (TextView) view.findViewById(R.id.h2oValue);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String waterVal = "https://api.thingspeak.com/channels/1648233/feeds.json?results=1";

        StringRequest WaterRequest = new StringRequest(Request.Method.GET, waterVal,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        waterNum.setText("WaterLevel: " + response.substring(response.length()-8).replaceAll("[^0-9]", ""));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                waterNum.setText("That didn't work!");
            }
        });
        queue.add(WaterRequest);

        WebView waterLevel = view.findViewById(R.id.waterlevel);

        waterLevel.loadUrl("https://thingspeak.com/channels/1648233/charts/1?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&type=line&update=15");
        waterLevel.getSettings().setJavaScriptEnabled(true);
        waterLevel.setWebViewClient(new WebViewClient());


        return view;
    }
}