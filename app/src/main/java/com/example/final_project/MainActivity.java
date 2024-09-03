package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ImageButton btnAddRecord;
    private ImageButton btnChart;
    private ImageButton btnSetting;
    private ImageButton NextMonth,FrontMonth;
    private TextView tv1,tv2;
    private DBHelper mdatabaseHelper;
    public static int homeMonth = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1=(TextView)findViewById(R.id.textView);
        tv2=(TextView)findViewById(R.id.textView2);
        NextMonth=(ImageButton) findViewById(R.id.NextMonthButton);
        FrontMonth = (ImageButton) findViewById(R.id.FrontMonthButton);
        setBtn1Listener();
        setBtn2Listener();

        btnAddRecord = findViewById(R.id.btnAddRecord);
        btnChart = findViewById(R.id.btnChart);
        btnSetting = findViewById(R.id.btnSetting);
        btnAddRecord.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,record.class);
                startActivity(intent);
            }
        });
        btnChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, pieChart.class);
                startActivity(intent);
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, setting.class);
                startActivity(intent);
            }
        });
    }

    public void SetHomeMonth(int m){
        MainActivity.homeMonth = m;
    }

    public int getHomeMonth(){
        return MainActivity.homeMonth;
    }

    private void setBtn1Listener(){
        mdatabaseHelper=new DBHelper(this);

        FrontMonth.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View view) {
                String date;
                int AllMoney=0;
                if (getHomeMonth()==1){
                    SetHomeMonth(12);
                }else {
                    SetHomeMonth((getHomeMonth()-1));
                }
                tv1.setText(getHomeMonth()+"月份總花費");
                Cursor cursor=mdatabaseHelper.getAllCostData();

                if(cursor != null){
                    while (cursor.moveToNext()){
                        date = cursor.getString(cursor.getColumnIndex("cost_date"));
                        if ((date.subSequence(5,7).equals(getHomeMonth()+"-")) || (date.subSequence(5,7).equals(Integer.toString(getHomeMonth())))){
                            System.out.println("200");
                            AllMoney = AllMoney + Integer.parseInt(cursor.getString(cursor.getColumnIndex("cost_money")));
                        }
                    }
                }
                cursor.close();
                tv2.setText("$"+AllMoney);
            }
        });
    }

    private void setBtn2Listener(){
        mdatabaseHelper=new DBHelper(this);

        NextMonth.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View view) {
                String date;
                int AllMoney=0;
                if (getHomeMonth()==12){
                    SetHomeMonth(1);
                }else {
                    SetHomeMonth((getHomeMonth()+1));
                }
                tv1.setText(getHomeMonth()+"月份總花費");
                Cursor cursor=mdatabaseHelper.getAllCostData();

                if(cursor != null){
                    while (cursor.moveToNext()){
                        date = cursor.getString(cursor.getColumnIndex("cost_date"));
                        if ((date.subSequence(5,7).equals(getHomeMonth()+"-")) || (date.subSequence(5,7).equals(Integer.toString(getHomeMonth())))){
                            AllMoney = AllMoney + Integer.parseInt(cursor.getString(cursor.getColumnIndex("cost_money")));
                        }
                    }
                }
                cursor.close();
                tv2.setText("$"+AllMoney);
            }
        });
    }

}