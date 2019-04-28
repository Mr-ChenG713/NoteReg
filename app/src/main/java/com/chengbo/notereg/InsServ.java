package com.chengbo.notereg;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InsServ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ins_serv);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void Save_Button(View view) {

        EditText editTextServ = (EditText) findViewById(R.id.InsServ);

        String message = editTextServ.getText().toString();

        if (message.trim().length() == 0){

            editTextServ.setError(getString(R.string.title_activity_ins_serv));
            editTextServ.requestFocus();
            return;

        }

        Toast.makeText(this,(R.string.State_s_Inser), Toast.LENGTH_SHORT).show();

        finish();
    }

    public void Cancel_Button (View view){

        finish();
    }

}
