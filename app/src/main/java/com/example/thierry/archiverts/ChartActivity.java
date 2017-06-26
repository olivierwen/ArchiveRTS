package com.example.thierry.archiverts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.github.mikephil.charting.utils.ColorTemplate.*;

/**
 * Created by Thierry on 22.06.2017.
 */

public class ChartActivity extends MainActivity implements OnChartValueSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pie_chart);

        // Get the facette
        ArrayList<String> facette_for_pie = new ArrayList<String>();
        Intent ident = getIntent();
        facette_for_pie = ident.getStringArrayListExtra("facette_for_pie");

        PieChart pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        //ArrayList<Entry> yvalues = new ArrayList<Entry>();
        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        ArrayList<String> xVals = new ArrayList<String>();

        String[] stringArray = facette_for_pie.toArray(new String[facette_for_pie.size()]);

        int[] arrayOfValues = new int[facette_for_pie.size()/2];
        // Extract the number of articles by program
        for(int i = 0;i < stringArray.length/2;i++)
        {
            try
            {
                //Structure of stringArray
                arrayOfValues[i] = Integer.parseInt(stringArray[i*2+1]);
            }
            catch(Exception e)
            {
                System.out.println("Not an integer value");
            }
        }

        Iterator<String> it = facette_for_pie.iterator();
        int j = 0;
        while (it.hasNext()) {
           //Prepare the values to show
           xVals.add(j,it.next().toString());
           String s = it.next();
           yvalues.add(new PieEntry(arrayOfValues[j]));
           j++;
        }

        //Set the legend
        PieDataSet dataSet = new PieDataSet(yvalues, "Emissions");
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        pieChart.setData(data);

        Description description = new Description();
        description.setText("Liste des émissions");
        pieChart.setDescription(description);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(25f);
        pieChart.setHoleRadius(25f);

        dataSet.setColors(VORDIPLOM_COLORS);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);
        pieChart.setOnChartValueSelectedListener(this);

        // adding legends to the desigred positions
        /*
        Legend l = pieChart.getLegend();
        l.setTextSize(14f);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setTextColor(Color.BLACK);
        l.setEnabled(true);
        */
       /* Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        pieChart.setEntryLabelColor(Color.WHITE);
        //pieChart.setEntryLabelTypeface(mTfRegular);
        pieChart.setEntryLabelTextSize(12f);*/

        pieChart.animateXY(1400, 1400);
    }

    //@Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", xIndex: " + e.getX()
                        + ", DataSet index: " + dataSetIndex);
    }

    @Override
    public void onValueSelected(Entry entry, Highlight highlight) {

        if (entry == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + entry.getY() + ", xIndex: " + entry.getX());
    }

    //@Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

}