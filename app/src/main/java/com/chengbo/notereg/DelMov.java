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

public class DelMov extends AppCompatActivity {

    private Uri endrecoMovimentoApagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_mov);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textViewTipo = (TextView) findViewById(R.id.ShowTip);
        TextView textViewServico = (TextView) findViewById(R.id.ShowSer);
        TextView textViewData = (TextView) findViewById(R.id.ShowData);
        TextView textViewAmt = (TextView) findViewById(R.id.ShowAmt);
        TextView textViewNote = (TextView) findViewById(R.id.ShowNot);

        Intent intent = getIntent();

        long idMovimento = intent.getLongExtra(WindMov.ID_MOVIMENTO, -1);

        if(idMovimento == -1){
            Toast.makeText(this, (R.string.erroDElMov), Toast.LENGTH_LONG ).show();
            finish();
            return;
        }

        endrecoMovimentoApagar = Uri.withAppendedPath(NoteRegContentProvider.ENDERECO_MOVIMENTO, String.valueOf(idMovimento));

        Cursor cursor = getContentResolver().query(endrecoMovimentoApagar, BdTableMovimento.TODAS_COLUNAS_MOVIMENTOS, null, null, null);

        if(!cursor.moveToNext()){
            Toast.makeText(this,(R.string.erroDElMov), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Movimento movimento = Movimento.fromCursor(cursor);

        textViewTipo.setText(movimento.getNomeTipo());
        textViewServico.setText(movimento.getNomeServico());
        textViewData.setText(movimento.getData());
        textViewAmt.setText(String.valueOf(movimento.getMontante()));
        textViewNote.setText(movimento.getDescricao());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

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

        int movimentoApagados = getContentResolver().delete(endrecoMovimentoApagar, null, null);

        if (movimentoApagados== 1) {
            Toast.makeText(this, (R.string.State_s_dele), Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, (R.string.erroDElMov), Toast.LENGTH_LONG).show();
        }
    }


}

