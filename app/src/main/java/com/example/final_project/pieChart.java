package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.Calendar;

public class pieChart extends AppCompatActivity{
    private DBHelper PieDB;
    Calendar calendar =Calendar.getInstance();
    private ImageButton btnPieHome;


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.pie_chart);
        double [] costType = new double[4];
        costType = getCostType();
        for(int i = 0; i < 4 ; i++) {
            costType[i] = costType[i] * 100;
        }
        int t1 = (int) costType[0];
        int t2 = (int) costType[1];
        int t3 = (int) costType[2];
        int t4 = (int) costType[3];

        PieChart mPieChart = (PieChart) findViewById(R.id.piechart);
        mPieChart.addPieSlice(new PieModel("旅遊", t1, Color.parseColor("#FE6DA8")));
        mPieChart.addPieSlice(new PieModel("食物", t2, Color.parseColor("#56B7F1")));
        mPieChart.addPieSlice(new PieModel("交通", t3, Color.parseColor("#CDA67F")));
        mPieChart.addPieSlice(new PieModel("購物", t4, Color.parseColor("#FED70E")));

        mPieChart.startAnimation();

        btnPieHome = findViewById(R.id.btnPieHome);
        btnPieHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(pieChart.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    @SuppressLint("Range")
    public  double [] getCostType() {
        PieDB = new DBHelper(this);
        double [] percent = new double[4];
        double sum = 0;
        double typeOf1 = 0;
        double typeOf2 = 0;
        double typeOf3 = 0;
        double typeOf4 = 0;
        String date;
        int month = calendar.get(Calendar.MONTH)+1;
        String monthString = Integer.toString(month);
        try {
            Cursor cursorType = PieDB.getAllCostData();
            if (cursorType != null) {
                while (cursorType.moveToNext()) {
                    date = cursorType.getString(cursorType.getColumnIndex("cost_date"));
                    if ((cursorType.getString(cursorType.getColumnIndex("cost_type"))).equals("travel") && (date.subSequence(5,7).equals(monthString+"-"))) {
                        typeOf1 += Double.parseDouble(cursorType.getString(cursorType.getColumnIndex("cost_money")));
                    } else if ((cursorType.getString(cursorType.getColumnIndex("cost_type"))).equals("eating") && (date.subSequence(5,7).equals(monthString+"-"))) {
                        typeOf2 += Double.parseDouble(cursorType.getString(cursorType.getColumnIndex("cost_money")));
                    } else if ((cursorType.getString(cursorType.getColumnIndex("cost_type"))).equals("traffic") && (date.subSequence(5,7).equals(monthString+"-"))) {
                        typeOf3 += Double.parseDouble(cursorType.getString(cursorType.getColumnIndex("cost_money")));
                    } else if ((cursorType.getString(cursorType.getColumnIndex("cost_type"))).equals("shopping") && (date.subSequence(5,7).equals(monthString+"-"))) {
                        typeOf4 += Double.parseDouble(cursorType.getString(cursorType.getColumnIndex("cost_money")));
                    }
                    sum += Double.parseDouble(cursorType.getString(cursorType.getColumnIndex("cost_money")));
                }
                cursorType.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        percent [0] = typeOf1 / sum;
        percent [1] = typeOf2 / sum;
        percent [2] = typeOf3 / sum;
        percent [3] = typeOf4 / sum;


        return percent;
    }
}
