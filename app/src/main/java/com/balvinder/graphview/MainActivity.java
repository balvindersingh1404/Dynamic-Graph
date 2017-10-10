package com.balvinder.graphview;


import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final Handler mHandler = new Handler();
    double mLastRandom = 1;
    Random mRand = new Random();
    private Runnable mTimer;
    private LineGraphSeries<DataPoint> mSeries;
    private TextView temperature;
    private int dataPointIntX = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        GraphView graph = (GraphView) findViewById(R.id.graph);
        temperature =(TextView) findViewById(R.id.temperature);

        initGraph(graph);
    }

    public void initGraph(GraphView graph) {

        graph.getViewport().setScalable(false);

        graph.getViewport().setDrawBorder(true);
        graph.getViewport().setBorderColor(getResources().getColor(R.color.colorLightGrey));

        // set manual X bounds
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(10);


        // set manual Y bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(10);

        graph.getGridLabelRenderer().setGridColor(getResources().getColor(R.color.colorLightGrey));


        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setVerticalLabels(new String[]{"", "43°C", "", "46°C", ""});
        staticLabelsFormatter.setHorizontalLabels(new String[]{"", "", "", "", "", ""});
        graph.getGridLabelRenderer().setVerticalLabelsAlign(Paint.Align.LEFT);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);


        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        graph.getGridLabelRenderer().setLabelVerticalWidth(6);


        mSeries = new LineGraphSeries<>();
        mSeries.setDrawDataPoints(true);
        mSeries.setDataPointsRadius(7);
        mSeries.setDrawBackground(true);
        mSeries.setColor(Color.WHITE);
        mSeries.setThickness(5);
        graph.addSeries(mSeries);


    }

    @Override
    public void onResume() {
        super.onResume();
        mTimer = new Runnable() {
            @Override
            public void run() {
                //graphLastXValue += 0.25d;
                dataPointIntX = dataPointIntX + 1;
                if (dataPointIntX == 500) {
                    dataPointIntX = 5;
                    mSeries.resetData(generateData());

                    mHandler.removeCallbacks(mTimer);

                } else {
                    mSeries.appendData(new DataPoint(dataPointIntX, getRandom()), true, 22);
                 //   temperature.setText(dataPointIntX);
                    mHandler.postDelayed(this, 800);
                }

            }
        };
        mHandler.postDelayed(mTimer, 800);
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mTimer);
    }

    private int getRandom() {

        int n = mRand.nextInt(5) + 3;
        Random generator = new Random();
        double number = generator.nextDouble() * .08;
        double temp = 38.3+n+number;
        temperature.setText(String.format( "%.1f", temp ));
        return n;

    }

    private DataPoint[] generateData() {
        return new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)};
    }

}
