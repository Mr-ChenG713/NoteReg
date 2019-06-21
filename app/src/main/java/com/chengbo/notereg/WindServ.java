package com.chengbo.notereg;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


public class WindServ extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ID_CURSOR_LOADER_SERVICO = 0;
    public static final String ID_SERVICO = "ID_SERVICO";

    private RecyclerView recyclerViewServico;
    private AdaptadorServico adaptadorServico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wind_serv);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportLoaderManager().initLoader(ID_CURSOR_LOADER_SERVICO, null, this);

        recyclerViewServico = (RecyclerView) findViewById(R.id.recyclerViewServicos);
        adaptadorServico = new AdaptadorServico(this);
        recyclerViewServico.setAdapter(adaptadorServico);
        recyclerViewServico.setLayoutManager(new LinearLayoutManager(this));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSOR_LOADER_SERVICO, null, this);

        super.onResume();
    }

    private Menu menu;

    public void refreshMenuOptions() {

        Servico servico = adaptadorServico.getServicoSelected();
        boolean showEditDelete = (servico != null);
        menu.findItem(R.id.action_edit).setVisible(showEditDelete);
        menu.findItem(R.id.action_delete).setVisible(showEditDelete);
        //menu.findItem(R.id.action_add).setVisible(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_operation, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_add) {

            Toast.makeText(this, R.string.Ins, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, InsServ.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.action_edit) {

            Toast.makeText(this, R.string.Upd, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, EditServ.class);
            intent.putExtra(ID_SERVICO, adaptadorServico.getServicoSelected().getId());
            startActivity(intent);
            return true;

        } else if (id == R.id.action_delete) {

            Toast.makeText(this, R.string.Del, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, DelServ.class);
            intent.putExtra(ID_SERVICO, adaptadorServico.getServicoSelected().getId());
            startActivity(intent);
            return true;

        }

        return super.onOptionsItemSelected(item);
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

        CursorLoader cursorLoader = new CursorLoader(this, NoteRegContentProvider.ENDERECO_SERVICO, BdTableServico.TODAS_COLUNAS_SERVICOS, null, null, BdTableServico.CAMPO_NOME
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

        Snackbar.make(recyclerViewServico, "Dados Exixtentes: " + data.getCount(), Snackbar.LENGTH_INDEFINITE).show();
        adaptadorServico.setCursor(data);
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

        adaptadorServico.setCursor(null);
    }
}