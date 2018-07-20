package com.example.anee.mytask.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.anee.mytask.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;

    private Context mContext;

    public CustomInfoWindowAdapter(Context context) {
        this.mContext = context;
        mWindow = LayoutInflater.from(mContext).inflate(R.layout.custom_info_wondow, null);
    }

    private void rendowWindowText(Marker marker, View view) {

        String title = marker.getTitle();
        TextView textViewTitle = view.findViewById(R.id.title);
        if (!title.equals("")) {
            textViewTitle.setText(title);


        }
        String snippet = marker.getTitle();
        TextView textViewSnippet = view.findViewById(R.id.snippet);
        if (!snippet.equals("")) {
            textViewSnippet.setText(snippet);


        }

    }

    @Override
    public View getInfoWindow(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }
}
