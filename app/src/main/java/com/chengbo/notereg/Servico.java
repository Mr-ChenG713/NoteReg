package com.chengbo.notereg;

import android.content.ContentValues;
import android.database.Cursor;

public class Servico {

    private long id;
    private  String serviconome;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getServiconome() {
        return serviconome;
    }

    public void setServiconome(String serviconome) {
        this.serviconome = serviconome;
    }

    public ContentValues getContentValues (){

        ContentValues values = new ContentValues();
        values.put(BdTableServico.CAMPO_NOME, serviconome);
        return values;
    }

    public static Servico fromCursor(Cursor cursor) {

        long id = cursor.getLong(
                cursor.getColumnIndex(BdTableTipo._ID)
        );

        String nome = cursor.getString(
                cursor.getColumnIndex(BdTableServico.CAMPO_NOME)
        );

        Servico servico = new Servico();

        servico.setId(id);
        servico.setServiconome(nome);

        return servico;
    }
}
