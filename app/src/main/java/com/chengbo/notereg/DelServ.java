package com.chengbo.notereg;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DelServ extends AppCompatActivity {

    private Uri endrecoServicoApagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_serv);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textViewServico = (TextView) findViewById(R.id.DelServShow);

        Intent intent = getIntent();

        long idservico = intent.getLongExtra(WindServ.ID_SERVICO, -1);

        if(idservico == -1){
            Toast.makeText(this, (R.string.erroDelServ), Toast.LENGTH_LONG ).show();
            finish();
            return;
        }

        endrecoServicoApagar = Uri.withAppendedPath(NoteRegContentProvider.ENDERECO_SERVICO, String.valueOf(idservico));

        Cursor cursor = getContentResolver().query(endrecoServicoApagar, BdTableServico.TODAS_COLUNAS_SERVICOS, null, null, null);

        if(!cursor.moveToNext()){
            Toast.makeText(this,(R.string.erroDelServ), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Servico servico = Servico.fromCursor(cursor);

        textViewServico.setText(servico.getServiconome());

    }


    public void Cancel_Button (View view){

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delete, menu);
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
        } else if (id == R.id.action_delete) {
            delete();
            return true;
        } else if (id == R.id.action_cancel) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void delete() {

        int servicoApagados = getContentResolver().delete(endrecoServicoApagar, null, null);

        if (servicoApagados== 1) {
            Toast.makeText(this, (R.string.State_s_dele), Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, (R.string.erroDelType), Toast.LENGTH_LONG).show();
        }
    }

}
