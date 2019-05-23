package com.chengbo.notereg;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;

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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ID_CURSO_LOADER_MOVIMENTOS = 0;

    private RecyclerView recyclerViewMovimentos;
    private AdaptadorMovimentos adaptadorMovimentos;

    int selectedindex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportLoaderManager().initLoader(ID_CURSO_LOADER_MOVIMENTOS, null, this);

        recyclerViewMovimentos = (RecyclerView) findViewById(R.id.recyclerViewMovimentos);
        adaptadorMovimentos = new AdaptadorMovimentos(this);
        recyclerViewMovimentos.setAdapter(adaptadorMovimentos);
        recyclerViewMovimentos.setLayoutManager(new LinearLayoutManager(this));


        CircleMenu circleMenu = findViewById(R.id.circlemenu);

        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"),R.drawable.ic_home, R.drawable.ic_exit)
                .addSubMenu(Color.parseColor("#CD0000"), R.drawable.ic_movim)
                .addSubMenu(Color.parseColor("#00CD00"), R.drawable.ic_tips)
                .addSubMenu(Color.parseColor("#0000CD"), R.drawable.ic_graf)
                .addSubMenu(Color.parseColor("#0000CD"), R.drawable.ic_hist)
                .addSubMenu(Color.parseColor("#0000CD"), R.drawable.ic_servs);

        circleMenu.setOnMenuSelectedListener(new OnMenuSelectedListener() {
            @Override
            public void onMenuSelected(int i) {
                switch (i) {
                    case 0 :
                        selectedindex = 1;
                        break;
                    case 1 :
                        selectedindex = 2;
                        break;
                    case 2 :
                        selectedindex = 3;
                        break;
                    case 3 :
                        selectedindex = 4;
                        break;
                    case 4 :
                        selectedindex = 5;
                        break;
                }
            }
        });

        circleMenu.setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {
            @Override
            public void onMenuOpened() {
                selectedindex = 0;
            }

            @Override
            public void onMenuClosed() {

                switch (selectedindex){
                    case 1:
                        Intent intentMov = new Intent(MainActivity.this, WindMov.class);
                        Toast.makeText(MainActivity.this, (R.string.WindMov_click_noti), Toast.LENGTH_SHORT).show();
                        startActivity(intentMov);
                        break;
                    case 2:
                        Intent intentTip = new Intent(MainActivity.this, WindTy.class);
                        Toast.makeText(MainActivity.this, (R.string.WindTip_click_noti), Toast.LENGTH_SHORT).show();
                        startActivity(intentTip);
                        break;
                    case 3:
                        Intent intentGra = new Intent(MainActivity.this, WindGraf.class);
                        Toast.makeText(MainActivity.this, (R.string.WindGraf_click_noti), Toast.LENGTH_SHORT).show();
                        startActivity(intentGra);
                        break;
                    case 4:
                        Intent intentHist = new Intent(MainActivity.this, WindHist.class);
                        Toast.makeText(MainActivity.this, (R.string.WindHist_click_noti), Toast.LENGTH_SHORT).show();
                        startActivity(intentHist);
                        break;
                    case 5:

                        Intent intentServ = new Intent(MainActivity.this, WindServ.class);
                        Toast.makeText(MainActivity.this, (R.string.WindServ_click_noti), Toast.LENGTH_SHORT).show();
                        startActivity(intentServ);
                        break;
                }
            }
        });

    }

    @Override
    protected void onResume(){

        getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_MOVIMENTOS, null, this);

        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        }

        return super.onOptionsItemSelected(item);
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
