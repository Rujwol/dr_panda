package com.example.drakinosh.drpanda;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import java.util.Collections;
import java.util.Comparator;

public class ShowGraph extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_graph);

        ArrayList<String> petDateCheckStrings = getIntent().getStringArrayListExtra("date_strings_check");
        Integer len = petDateCheckStrings.size();

        //Date[] petCheckDates = new Date[len];
        List<Date> petCheckDates = new ArrayList<>();

        Integer i = 0;
        for (String s : petDateCheckStrings) {
            DateFormat date_form = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            try {
                Date date = date_form.parse(s); //ignore ParseException
                //petCheckDates[i++] = date;
                petCheckDates.add(date);
            } catch (Exception e) {
                ;
            }
        }

        Collections.sort(petCheckDates, new Comparator<Date>() {
            @Override
            public int compare(Date o1, Date o2) {
                return o1.compareTo(o2);
            }
        });


        GraphView gv = findViewById(R.id.date_graph);


        i = 0;
        DataPoint[] dataPoints = new DataPoint[len];
        for ( ; i < len; i++) {
            dataPoints[i] = new DataPoint(petCheckDates.get(i), 1);
        }
        PointsGraphSeries<DataPoint> series  = new PointsGraphSeries<>(dataPoints);

        gv.addSeries(series);

        // set date label formatter
        gv.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(ShowGraph.this));
        //gv.getGridLabelRenderer().setNumHorizontalLabels(len);
        //gv.getGridLabelRenderer().setNumHorizontalLabels(len-1);

        // set manual x bounds
        gv.getViewport().setMinX(petCheckDates.get(0).getTime());
        gv.getViewport().setMaxX(petCheckDates.get(len-1).getTime());
        gv.getViewport().setMinY(0);
        gv.getViewport().setMaxY(8);

        gv.getViewport().setXAxisBoundsManual(true);
        gv.getViewport().setYAxisBoundsManual(true);
        gv.getGridLabelRenderer().setHumanRounding(false);

        // scrolling and zooming for horizontal axis
        gv.getViewport().setScalable(true);
        gv.getViewport().setScrollable(true);



        // rotate labels
        GridLabelRenderer renderer = gv.getGridLabelRenderer();
        renderer.setHorizontalLabelsAngle(80);

    }
}