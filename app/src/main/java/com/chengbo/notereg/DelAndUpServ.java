package com.chengbo.notereg;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

public class DelAndUpServ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_and_up_serv);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void Up_Button (View view){

        Intent intent = new Intent(this, EditServ.class);
        Toast.makeText(this, (R.string.title_activity_edit_serv), Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }

    public void Del_Button (View view){

        Toast.makeText(this, (R.string.State_s_dele), Toast.LENGTH_SHORT).show();
        finish();
    }

    public void Cancel_Button (View view){

        finish();
    }

}
