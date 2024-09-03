package com.example.final_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class record extends AppCompatActivity {
    private ImageButton btnRecHome;
    private List<CostB> mCostBeanList;
    private DBHelper mdatabaseHelper;
    private CostListAdapter mAdapter;
    Calendar calendar =Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         */

        mdatabaseHelper=new DBHelper(this);
        mCostBeanList=new ArrayList<>();
        ListView costList=(ListView)findViewById(R.id.lv_main);
        initCostData();


        mAdapter = new CostListAdapter(this, mCostBeanList);
        costList.setAdapter(mAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder=new AlertDialog.Builder(record.this);

                LayoutInflater inflater=LayoutInflater.from(record.this);
                View viewDialog=inflater.inflate(R.layout.new_cost_record,null);
                final EditText title=(EditText) viewDialog.findViewById(R.id.et_cost_title);
                final EditText money=(EditText)viewDialog.findViewById(R.id.et_cost_money);
                final DatePicker date=(DatePicker) viewDialog.findViewById(R.id.dp_cost_data);
                final Spinner type=(Spinner) viewDialog.findViewById(R.id.cost_spinner);

                ArrayAdapter adapter1 = ArrayAdapter.createFromResource(type.getContext()
                        ,R.array.cost_type_array,android.R.layout.simple_dropdown_item_1line);
                type.setAdapter(adapter1);


                builder.setView(viewDialog);
                builder.setTitle("New Cost");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CostB costBean = new CostB();
                        costBean.costTitle = title.getText().toString();
                        costBean.costMoney = money.getText().toString();
                        costBean.costDate = date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDayOfMonth();
                        costBean.costType = type.getSelectedItem().toString();
                        mdatabaseHelper.insertCost(costBean);
                        mCostBeanList.add(costBean);
                        if (CheckBudget() == false && CostB.budget > 0) {
                            System.out.println(CostB.getBudget());
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(record.this);
                            alertDialog.setTitle("您已超出該月預算");
                            alertDialog.setMessage("預算:" + CostB.budget + " 當月支出:" + getAllMoney());
                            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alertDialog.create().show();
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.create().show();
            }
        });
        btnRecHome = findViewById(R.id.btnRecHome);
        btnRecHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(record.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    @SuppressLint("Range")
    private void initCostData() {
        // mdatabaseHelper.deleteAllData();
       /* CostBean costBean=new CostBean();
        for (int i=0;i<6;i++) {
            costBean.costTitle=i+"imooc";
            costBean.costDate="11-11";
            costBean.costMoney="20";
            mdatabaseHelper.insertCost(costBean);
        }*/
        Cursor cursor=mdatabaseHelper.getAllCostData();
        if(cursor!=null){
            while(cursor.moveToNext()){
                CostB costBean1=new CostB();
                costBean1.costTitle=cursor.getString(cursor.getColumnIndex("cost_title"));
                costBean1.costDate=cursor.getString(cursor.getColumnIndex("cost_date"));
                costBean1.costMoney=cursor.getString(cursor.getColumnIndex("cost_money"));
                costBean1.costType=cursor.getString(cursor.getColumnIndex("cost_type"));
                mCostBeanList.add(costBean1);
            }
            cursor.close();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @SuppressLint("Range")
    public int getAllMoney(){
        int AllMoney = 0;
        int month = calendar.get(Calendar.MONTH)+1;
        String monthString = Integer.toString(month);
        String date;
        Cursor cursor=mdatabaseHelper.getAllCostData();

        if(cursor != null){
            while (cursor.moveToNext()){
                date = cursor.getString(cursor.getColumnIndex("cost_date"));
                if ((date.subSequence(5,7).equals(monthString+"-")) || (date.subSequence(5,7).equals(monthString))){
                        AllMoney = AllMoney + Integer.parseInt(cursor.getString(cursor.getColumnIndex("cost_money")));
                }
            }
        }
        cursor.close();
        return AllMoney;
    }

    public boolean CheckBudget(){
        int AllMoney = this.getAllMoney();
        System.out.println(AllMoney);
        if (AllMoney > CostB.budget){
            return false;
        }
        return true;
    }




}
