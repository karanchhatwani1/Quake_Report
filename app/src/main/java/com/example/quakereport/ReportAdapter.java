package com.example.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.util.List;

public class ReportAdapter<T> extends ArrayAdapter<Report> {
    public ReportAdapter(Context context, List<Report>reports){
        super(context,0,reports);
    }
    private static final String LOCATION_SEPARATOR = " of ";

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_item, parent, false);
        }
        String primaryLocation;
        String locationOffset;

        Report currentWord = (Report) getItem(position);

        TextView textView3= (TextView) listItemView.findViewById(R.id.earthquakeMagnitude);
        String formatMagnitude = formatMagnitude(currentWord.getMagnitude());
        textView3.setText(formatMagnitude);

        TextView textView4 = (TextView) listItemView.findViewById(R.id.earthquakeTime);
        textView4.setText(currentWord.getPlace());

        TextView textView5 = (TextView) listItemView.findViewById(R.id.earthquakeDate);
        textView5.setText(currentWord.getDate());
        String originalLocation = currentWord.getPlace();
        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.app_name);
            primaryLocation = originalLocation;
        }
        TextView LocationOffSetView = (TextView) listItemView.findViewById(R.id.earthquakeLocation_Offset);
        LocationOffSetView.setText(locationOffset);
        TextView LocationPrimary = (TextView) listItemView.findViewById(R.id.earthquakePrimary_Location);
        LocationPrimary.setText(primaryLocation);

        GradientDrawable magnitudeCircle = (GradientDrawable)textView3.getBackground();
        int magnitudecolor = getMagnitudeColor(currentWord.getMagnitude());
        magnitudeCircle.setColor(magnitudecolor);
        return listItemView;
    }
    private int getMagnitudeColor(double magnitude){
        int magnitudeColorResourseId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch(magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourseId = R.color.magnitude1;
                break;

            case 2:
                magnitudeColorResourseId = R.color.magnitude2;
                break;

            case 3:
                magnitudeColorResourseId = R.color.magnitude3;
                break;

            case 4:
                magnitudeColorResourseId = R.color.magnitude4;
                break;

            case 5:
                magnitudeColorResourseId = R.color.magnitude5;
                break;

            case 6:
                magnitudeColorResourseId = R.color.magnitude6;
                break;

            case 7:
                magnitudeColorResourseId = R.color.magnitude7;
                break;

            case 8:
                magnitudeColorResourseId = R.color.magnitude8;
                break;

            case 9:
                magnitudeColorResourseId = R.color.magnitude9;
                break;

            default:
                magnitudeColorResourseId = R.color.magnitude10plus;
                break;
        }
         return ContextCompat.getColor(getContext(),magnitudeColorResourseId);

        }
        private String formatMagnitude(double magnitude) {
            DecimalFormat Magnitudeformat = new DecimalFormat("0.0");
            return Magnitudeformat.format(magnitude);
        }


}


