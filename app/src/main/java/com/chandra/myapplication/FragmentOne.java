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


public class FragmentOne extends Fragment {

    TextView dthNum,dthNumHum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment


        View view =  inflater.inflate(R.layout.fragment_one, container, false);

        dthNum= (TextView) view.findViewById(R.id.dthValue);
        dthNumHum = (TextView) view.findViewById(R.id.dthValueHum);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String dthValTemp = "https://api.thingspeak.com/channels/1642277/fields/1.json?results=1";
        String dthValHum = "https://api.thingspeak.com/channels/1642277/fields/2.json?results=1";

        StringRequest dthTempRequest = new StringRequest(Request.Method.GET, dthValTemp,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        dthNum.setText("Temp: " + response.substring(response.length()-8).replaceAll("[^\\d.]", ""));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dthNum.setText("That didn't work!");
            }
        });
        queue.add(dthTempRequest);

        StringRequest dthTempHum = new StringRequest(Request.Method.GET, dthValHum,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        dthNumHum.setText("Hum: " + response.substring(response.length()-8).replaceAll("[^\\d.]", ""));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dthNumHum.setText("That didn't work!");
            }
        });
        queue.add(dthTempHum);



        WebView temperature = view.findViewById(R.id.temperature);
        WebView humidity = view.findViewById(R.id.humidity);


        temperature.loadUrl("https://thingspeak.com/channels/1642277/charts/1?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&type=line&update=15");
        temperature.getSettings().setJavaScriptEnabled(true);
        temperature.setWebViewClient(new WebViewClient());

        humidity.loadUrl("https://thingspeak.com/channels/1642277/charts/2?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&type=line&update=15");
        humidity.getSettings().setJavaScriptEnabled(true);
        humidity.setWebViewClient(new WebViewClient());

        return view;
    }
}