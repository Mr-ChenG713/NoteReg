package com.chengbo.notereg;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditTy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ty);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void Up_Button(View view) {

        EditText editTextServ = (EditText) findViewById(R.id.InsTy);

        String message = editTextServ.getText().toString();

        if (message.trim().length() == 0){

            editTextServ.setError(getString(R.string.title_activity_ins_ty));
            editTextServ.requestFocus();
            return;

        }

        Toast.makeText(this,(R.string.State_s_upda), Toast.LENGTH_SHORT).show();

        finish();
    }

    public void Cancel_Button (View view){

        finish();
    }

}
