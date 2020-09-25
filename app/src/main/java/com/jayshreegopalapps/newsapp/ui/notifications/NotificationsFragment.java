package com.jayshreegopalapps.newsapp.ui.notifications;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.jayshreegopalapps.newsapp.R;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {
    SQLiteDatabase database;
    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        database = getContext().openOrCreateDatabase("UsageDB", Context.MODE_PRIVATE, null);
        PieChart pieChart = root.findViewById(R.id.piechart);

        String select = "select * from CategoryTable";
        Cursor cursor = database.rawQuery(select, null);
        ArrayList categoryUsage = new ArrayList();
        ArrayList categoryNameList = new ArrayList();
        if(cursor.moveToNext()) {
            int i = 0;
            do{
                String categoryName = cursor.getString(0);
                int value = cursor.getInt(1);
                if(value > 0) {
                    categoryNameList.add(categoryName);
                    categoryUsage.add(new Entry(value, i++));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        PieDataSet dataSet = new PieDataSet(categoryUsage, "In Seconds");

        PieData data = new PieData(categoryNameList, dataSet);
        pieChart.setData(data);
        pieChart.setCenterText("Category Wise Usage");
        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        pieChart.animateXY(1000, 1000);

        BarChart chart = root.findViewById(R.id.barchart);
        ArrayList daysUsage = new ArrayList();
        ArrayList daysDate = new ArrayList();
        String query = "select * from UsageStats";
        Cursor cursor1 = database.rawQuery(query, null);
        if(cursor1.moveToNext()){
            int j = 0;
            do{
                String day = cursor1.getString(0);
                int value = cursor1.getInt(1);
                daysUsage.add(new BarEntry(value, j++));
                daysDate.add(day);
                System.out.println(value);
            }
            while(cursor1.moveToNext());
        }
        cursor1.close();

        BarDataSet bardataset = new BarDataSet(daysUsage, "Seconds Used");
        chart.animateY(3000);
        BarData data2 = new BarData(daysDate, bardataset);
        bardataset.setColors(ColorTemplate.LIBERTY_COLORS);
        chart.setData(data2);
        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }

}