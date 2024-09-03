package com.example.final_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


public class setting  extends AppCompatActivity {
    public EditText budgetText;
    public Button btnBudgetConfirm;
    private ImageButton btnSetHome;
    private DBHelper mdatabaseHelper;
    public int budgetInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        mdatabaseHelper=new DBHelper(this);
        budgetText = (EditText) findViewById(R.id.budgetText);
        btnSetHome = (ImageButton) findViewById(R.id.btnSetHome);
        btnBudgetConfirm = (Button) findViewById(R.id.btnBudgetConfirm);
        btnBudgetConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                budgetInput = Integer.parseInt(budgetText.getText().toString());
                CostB.setBudget(budgetInput);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(setting.this);
                alertDialog.setTitle("預算設置");
                alertDialog.setMessage("成功");
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which){dialog.dismiss();}
                });
                alertDialog.create().show();

            }

        });
        btnSetHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(setting.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }


}