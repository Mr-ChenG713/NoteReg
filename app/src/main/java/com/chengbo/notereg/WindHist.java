package com.chengbo.notereg;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class WindHist extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>  {

    private static final int ID_CURSO_LOADER_MOVIMENTOS = 0;

    private RecyclerView recyclerViewMovimentos;
    private AdaptadorMovimentos adaptadorMovimentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wind_hist);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_MOVIMENTOS, null, this);

        recyclerViewMovimentos = (RecyclerView) findViewById(R.id.recyclerViewMovimentos);
        adaptadorMovimentos = new AdaptadorMovimentos(this);
        recyclerViewMovimentos.setAdapter(adaptadorMovimentos);
        recyclerViewMovimentos.setLayoutManager(new LinearLayoutManager(this));

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

    @Override
    protected void onResume(){

        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_MOVIMENTOS, null, this);

        super.onResume();

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        CursorLoader cursorLoader = new CursorLoader(this, NoteRegContentProvider.ENDERECO_MOVIMENTOS, BdTabelaMovimentos.TODAS_COLUNAS_MOVIMENTOS, null, null, BdTabelaMovimentos.CAMPO_TIPO);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        adaptadorMovimentos.setCursor(data);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
