package com.chengbo.notereg;

import android.content.ContentValues;
import android.database.Cursor;

public class Tipo {

    private long id;
    private String nome;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ContentValues getContenteValues(){

        ContentValues valores =  new ContentValues();

        valores.put(BdTabelaTipos.CAMPO_NOME, nome);

        return valores;
    }

    public static Tipo fromCursor (Cursor cursor){
        Tipo tipo = new Tipo();

        long id = cursor.getLong(cursor.getColumnIndex(BdTabelaTipos._ID));

        String nome = cursor.getString(cursor.getColumnIndex(BdTabelaTipos.CAMPO_NOME));

        tipo.setId(id);
        tipo.setNome(nome);

        return tipo;
    }
}
