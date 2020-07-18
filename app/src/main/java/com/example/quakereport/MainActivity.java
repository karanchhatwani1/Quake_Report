package com.example.quakereport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.quakereport.QueryUtils;
import com.example.quakereport.R;
import com.example.quakereport.Report;
import com.example.quakereport.ReportAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**
     * Adapter for the list of earthquakes
     */
    private ReportAdapter mAdapter;
    /**
     * Sample URL response for a USGS query
     */
    private static final String USGS_REQUEST_URL= "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    private static final int EARTHQUAKE_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new ReportAdapter(this, new ArrayList<Report>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected earthquake.
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                Report currentEarthquake = (Report) mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
        // Start the AsyncTask to fetch the earthquake data
        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(USGS_REQUEST_URL);
    }

    //        ArrayList<EarthQuake> earthquakes = new ArrayList<>();
//        earthquakes.add(new EarthQuake("7.2", "San Francisco", "Feb 2, 2016"));
//        earthquakes.add(new EarthQuake("6.1", "London", "July 20, 2015"));
//        earthquakes.add(new EarthQuake("3.9", "Tokyo", "Nov 10, 2014"));
//        earthquakes.add(new EarthQuake("5.4", "Mexico City", "May 3, 2014"));
//        earthquakes.add(new EarthQuake("2.8", "Moscow", "Jan 31, 2013"));
//        earthquakes.add(new EarthQuake("4.9", "Rio de Janeiro", "Aug 19, 2012"));
//        earthquakes.add(new EarthQuake("1.6", "Paris", "Oct 30, 2011"));
//    ArrayList<EarthQuake> earthquakes = QueryUtils.extractEarthquakes(earthquakeJSON);
//    ListView earthquakeListView = (ListView) findViewById(R.id.list);
//    final EarthQuakeAdapter adapter = new EarthQuakeAdapter(this, earthquakes);
//        earthquakeListView.setAdapter(adapter);
//
//        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
//
//    {
//        @Override
//        public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
//        EarthQuake currentEarthquake = adapter.getItem(position);
//        Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());
//
//        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
//        startActivity(websiteIntent);
//
//    }
//    });
//
//}

    public class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Report>> {

        @Override
        protected List<Report> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            List<Report> result = QueryUtils.fetchEarthquakeData(urls[0]);
            return result;

        }

        @Override
        protected void onPostExecute(List<Report> earthQuakes) {
            // Clear the adapter of previous earthquake data
            mAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (earthQuakes != null && !earthQuakes.isEmpty()) {
                mAdapter.addAll(earthQuakes);
            }
        }
    }
}
