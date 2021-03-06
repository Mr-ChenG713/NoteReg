package com.chengbo.notereg;


import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;


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
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class InsMov extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>   {

    private static final int ID_CURSOR_LOADER_TIPO= 0;
    private static final int ID_CURSOR_LOADER_SERVICO= 1;

    private Spinner spinnerTipo;
    private Spinner spinnerServico;
    private EditText editTextData;
    private EditText editTextAmout;
    private EditText editTextNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ins_mov);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportLoaderManager().initLoader(ID_CURSOR_LOADER_TIPO, null, this);
        getSupportLoaderManager().initLoader(ID_CURSOR_LOADER_SERVICO, null, this);

         spinnerTipo = (Spinner) findViewById(R.id.SpiTip);
         spinnerServico = (Spinner) findViewById(R.id.SpiServ);
         editTextData = (EditText) findViewById(R.id.InserData);
         editTextAmout = (EditText) findViewById(R.id.InserAmt);
         editTextNote = (EditText)findViewById(R.id.InserNote);
    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSOR_LOADER_TIPO, null, this);
        getSupportLoaderManager().restartLoader(ID_CURSOR_LOADER_SERVICO, null, this);

        super.onResume();
    }

    private void showTipoSpinner(Cursor cursorTipo) {
        SimpleCursorAdapter adaptadorTipo = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursorTipo,
                new String[]{BdTableTipo.CAMPO_NOME},
                new int[]{android.R.id.text1}
        );
        spinnerTipo.setAdapter(adaptadorTipo);
    }

    private void showServicoSpinner(Cursor cursorServico) {
        SimpleCursorAdapter adaptadorServico = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursorServico,
                new String[]{BdTableServico.CAMPO_NOME},
                new int[]{android.R.id.text1}
        );
        spinnerServico.setAdapter(adaptadorServico);
    }

    public void Cancel_Button (View view){

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_save) {
            save();
            return true;
        } else if (id == R.id.action_cancel) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void save() {
        String conteudoData = editTextData.getText().toString();

        if (conteudoData.trim().isEmpty()) {
            editTextData.setError("Please insert a date !!!");
            editTextData.requestFocus();
            return;
        }

        double valor = 0;
        String conteudoAmt = editTextAmout.getText().toString();
        valor = Double.parseDouble(conteudoAmt);

        if (conteudoAmt.trim().length() == 0){
            editTextAmout.setError(getString(R.string.Ins_RegMov_erro_amo));
            editTextAmout.requestFocus();
            return;
        } else  if (valor == 0) {
            editTextAmout.setError(getString(R.string.Ins_RegMov_erro_amoUP0));
            editTextAmout.requestFocus();
            return;
        }

        String conteudoNote = editTextNote.getText().toString();

        long idTipo = spinnerTipo.getSelectedItemId();
        long idServico = spinnerServico.getSelectedItemId();

        // Save the data
        Movimento movimento = new Movimento();

        movimento.setTipo(idTipo);
        movimento.setServico(idServico);
        movimento.setData(conteudoData);
        movimento.setMontante(valor);
        movimento.setDescricao(conteudoNote);

        try {
            getContentResolver().insert(NoteRegContentProvider.ENDERECO_MOVIMENTO, movimento.getContentValues());

            Toast.makeText(this, (R.string.State_s_Inser), Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Snackbar.make(editTextData, "Error while saving!", Snackbar.LENGTH_LONG).show();
            e.printStackTrace();
        }
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

        if (id == ID_CURSOR_LOADER_TIPO){

            androidx.loader.content.CursorLoader cursorLoaderTipo = new androidx.loader.content.CursorLoader(this, NoteRegContentProvider.ENDERECO_TIPO, BdTableTipo.TODAS_COLUNAS_TIPOS, null, null, BdTableTipo.CAMPO_NOME
            );
            return cursorLoaderTipo;

        } else if (id == ID_CURSOR_LOADER_SERVICO){

            androidx.loader.content.CursorLoader cursorLoaderServico = new androidx.loader.content.CursorLoader(this, NoteRegContentProvider.ENDERECO_SERVICO, BdTableServico.TODAS_COLUNAS_SERVICOS, null, null, BdTableServico.CAMPO_NOME
            );
            return cursorLoaderServico;
        }

        return null;
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

        int id = loader.getId();
        if (id == ID_CURSOR_LOADER_TIPO){

            showTipoSpinner(data);
        }else if (id == ID_CURSOR_LOADER_SERVICO) {

            showServicoSpinner(data);
        }

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

        int id = loader.getId();

        if (id == ID_CURSOR_LOADER_TIPO){

            showTipoSpinner(null);

        }else if (id == ID_CURSOR_LOADER_SERVICO) {

            showServicoSpinner(null);
        }

    }
}
