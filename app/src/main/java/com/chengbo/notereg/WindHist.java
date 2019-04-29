package com.chengbo.notereg;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class WindHist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wind_hist);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public  void  UpMov (View view){

        Intent intent = new Intent(this, UpdtMov.class);
        Toast.makeText(this, (R.string.title_activity_updt_mov), Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public  void  DeMov (View view){
        Intent intent = new Intent(this, DeltMov.class);
        Toast.makeText(this,(R.string.title_activity_delt_mov), Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void Cancel_Button (View view){

        finish();
    }

}
