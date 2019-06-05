package com.chengbo.notereg;

import android.content.ContentValues;
import android.database.Cursor;

public class Tipo {

    private long id;
    private  String tiponome;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTiponome() {
        return tiponome;
    }

    public void setTiponome(String tiponome) {
        this.tiponome = tiponome;
    }

    public ContentValues getContentValues (){

        ContentValues values = new ContentValues();
        values.put(BdTableTipo.CAMPO_NOME, tiponome);
        return values;
    }

    public static Tipo fromCursor(Cursor cursor) {

        long id = cursor.getLong(
                cursor.getColumnIndex(BdTableTipo._ID)
        );

        String nome = cursor.getString(
                cursor.getColumnIndex(BdTableTipo.CAMPO_NOME)
        );

        Tipo tipo = new Tipo();

        tipo.setId(id);
        tipo.setTiponome(nome);

        return tipo;
    }
}
