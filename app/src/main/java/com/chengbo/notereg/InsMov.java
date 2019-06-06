package com.chengbo.notereg;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class InsMov extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>   {

    private static final int ID_CURSO_LOADER_MOVIMENTO = 0;

    Spinner spinnerTip;
    Spinner spinnerServ;
    TextView ShowDat;
    int mYear, mMonth, mDay;
    ImageButton AltDat;
    final int DATE_DIALOG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ins_mov);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_MOVIMENTO, null, this);

        ShowDat = (EditText) findViewById(R.id.ShowDat);
        AltDat = (ImageButton) findViewById(R.id.AltDat);

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

    public void Save_Button(View view) {

        double valor = 0;

        EditText editTextAmoun = (EditText) findViewById(R.id.InserAmt);
        EditText editTextData =(EditText) findViewById(R.id.ShowDat);


        String message = editTextAmoun.getText().toString();
        String data = editTextData.getText().toString();

        long selectedItemId = spinnerTip.getSelectedItemId();
        long selectedItemId2 = spinnerServ.getSelectedItemId();

        if (selectedItemId == 0){

            ((TextView) spinnerTip.getSelectedView()).setError("?");
            Toast.makeText(this, (R.string.Ins_RegMov_SpiT), Toast.LENGTH_SHORT).show();
            spinnerTip.requestFocus();
            return;

        }else  if (selectedItemId2 == 0){

            ((TextView) spinnerServ.getSelectedView()).setError("?");
            Toast.makeText(this, (R.string.Ins_RegMov_SpiS), Toast.LENGTH_SHORT).show();
            spinnerServ.requestFocus();
            return;

        } else if (data.trim().isEmpty()) {
            editTextData.setError("Please insert a date.");
            return;
        }else if (message.trim().length() == 0){
            editTextAmoun.setError(getString(R.string.Ins_RegMov_erro_amo));
            editTextAmoun.requestFocus();
            return;
        } else  if (valor == 0) {
            editTextAmoun.setError(getString(R.string.Ins_RegMov_erro_amoUP0));
            editTextAmoun.requestFocus();
            return;
        }

        // guardar os dados
        Movimento movimento = new Movimento();

        movimento.setData(data);
        movimento.setMontante(valor);
        movimento.setDescricao(message);
        movimento.setFktipo(selectedItemId);
        movimento.setFkservico(selectedItemId2);

        try {
            getContentResolver().insert(NoteRegContentProvider.ENDERECO_MOVIMENTO, movimento.getContentValues());

            Toast.makeText(this, getString(R.string.State_s_Inser), Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(
                    editTextAmoun,
                    getString(R.string.State_s_dele),
                    Snackbar.LENGTH_LONG)
                    .show();

            e.printStackTrace();
        }
    }

    public void Cancel_Button (View view){

        finish();
    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_MOVIMENTO, null, this);

        super.onResume();
    }

    private void mostraTipoSpinner(Cursor cursorTipo) {
        SimpleCursorAdapter adaptadorTipo = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursorTipo,
                new String[]{BdTableTipo.CAMPO_NOME},
                new int[]{android.R.id.text1}
        );
        spinnerTip.setAdapter(adaptadorTipo);
    }

    private void mostraServicoSpinner(Cursor cursorServico) {
        SimpleCursorAdapter adaptadorServico = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                cursorServico,
                new String[]{BdTableServico.CAMPO_NOME},
                new int[]{android.R.id.text2}
        );
        spinnerServ.setAdapter(adaptadorServico);
    }


    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        androidx.loader.content.CursorLoader cursorLoader = new androidx.loader.content.CursorLoader(this, NoteRegContentProvider.ENDERECO_TIPO, BdTableTipo.TODAS_COLUNAS_TIPOS, null, null, BdTableTipo.CAMPO_NOME
        );

        return cursorLoader;
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is <em>not</em> allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See {@link FragmentManager#beginTransaction()
     * FragmentManager.openTransaction()} for further discussion on this.
     *
     * <p>This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     * <ul>
     * <li> <p>The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a {@link Cursor}
     * and you place it in a {@link CursorAdapter}, use
     * the {@link CursorAdapter#CursorAdapter(Context,
     * Cursor, int)} constructor <em>without</em> passing
     * in either {@link CursorAdapter#FLAG_AUTO_REQUERY}
     * or {@link CursorAdapter#FLAG_REGISTER_CONTENT_OBSERVER}
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     * <li> The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a {@link Cursor} from a {@link CursorLoader},
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * {@link CursorAdapter}, you should use the
     * {@link CursorAdapter#swapCursor(Cursor)}
     * method so that the old Cursor is not closed.
     * </ul>
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data   The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
