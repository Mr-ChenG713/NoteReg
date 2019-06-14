package com.chengbo.notereg;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

public class DelMov extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_mov);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void Delete_Button (View view){
        Toast.makeText(this, (R.string.State_s_dele), Toast.LENGTH_SHORT).show();
        finish();
    }

    public void Cancel_Button (View view){

        finish();
    }



}
