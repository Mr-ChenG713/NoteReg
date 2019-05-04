package com.chengbo.notereg;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpdtMov extends AppCompatActivity {

    Spinner spinnerTip;
    Spinner spinnerServ;
    TextView ShowDat;
    int mYear, mMonth, mDay;
    ImageButton AltDat;
    final int DATE_DIALOG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updt_mov);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinnerTip = findViewById(R.id.spinnerTE);
        spinnerServ = findViewById(R.id.spinnerSE);

        ArrayAdapter<CharSequence> adapterTip = ArrayAdapter.createFromResource(this, R.array.Type, android.R.layout.simple_spinner_item);
        adapterTip.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTip.setAdapter(adapterTip);

        ArrayAdapter<CharSequence> adapterServ = ArrayAdapter.createFromResource(this, R.array.Service, android.R.layout.simple_spinner_item);
        adapterServ.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServ.setAdapter(adapterServ);

        ShowDat = (TextView) findViewById(R.id.ShowDatE);
        AltDat = (ImageButton) findViewById(R.id.AltDatE);

        AltDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG);
            }
        });

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd / M / yyyy");
        String dateString = sdf.format(date);
        ShowDat.setText(dateString);

        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    public void display (){
        ShowDat.setText(new StringBuffer().append(mDay).append(" / ")
                .append(mMonth + 1).append(" / ").append(mYear).append(""));
    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            display();
        }
    };

    public void Update_Button (View view){

        EditText editTextAmoun = (EditText) findViewById(R.id.InserAmtE);

        String message = editTextAmoun.getText().toString();

        long selectedItemId = spinnerTip.getSelectedItemId();
        long selectedItemId2 = spinnerServ.getSelectedItemId();

        if (selectedItemId == 0){

            spinnerTip.requestFocus();
            ((TextView) spinnerTip.getSelectedView()).setError("?");
            Toast.makeText(this, (R.string.Ins_RegMov_SpiT), Toast.LENGTH_SHORT).show();
            return;

        }else  if (selectedItemId2 == 0){

            spinnerServ.requestFocus();
            ((TextView) spinnerServ.getSelectedView()).setError("?");
            Toast.makeText(this, (R.string.Ins_RegMov_SpiS), Toast.LENGTH_SHORT).show();
            return;

        }

        double valor = 0;

        try {
            valor = Double.parseDouble(editTextAmoun.getText().toString());
        } catch (NumberFormatException e) {
            editTextAmoun.setError(getString(R.string.Ins_RegMov_erro_amo));
            editTextAmoun.requestFocus();
            return;
        }

        Toast.makeText(this,(R.string.State_s_upda), Toast.LENGTH_SHORT).show();

        finish();
    }

    public void Cancel_Button (View view){

        finish();
    }

}