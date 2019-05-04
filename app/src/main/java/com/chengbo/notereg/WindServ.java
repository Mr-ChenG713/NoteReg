package com.chengbo.notereg;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

public class WindServ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wind_serv);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public  void  InsSev (View view){

        Intent intent = new Intent(this, InsServ.class);
        Toast.makeText(this, (R.string.title_activity_ins_serv), Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public  void  EDSev (View view){

        Intent intent = new Intent(this,DelAndUpServ.class);
        Toast.makeText(this,(R.string.title_activity_del_and_up_serv), Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }


    public void Cancel_Button (View view){

        finish();
    }

}
