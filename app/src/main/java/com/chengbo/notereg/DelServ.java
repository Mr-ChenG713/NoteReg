package com.chengbo.notereg;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

public class DelServ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_serv);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void Up_Button (View view){

        Intent intent = new Intent(this, EditServ.class);
        Toast.makeText(this, (R.string.title_activity_edit_serv), Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }


    public void Cancel_Button (View view){

        finish();
    }

}
