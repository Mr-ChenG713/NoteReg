package com.chengbo.notereg;

import android.content.ContentValues;
import android.database.Cursor;

public class Servico {

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

    public ContentValues getContentValues() {
        ContentValues valores = new ContentValues();

        valores.put(BdTabelaServicos.CAMPO_NOME, nome);

        return valores;
    }

    public static Servico fromCursor (Cursor cursor){

        Servico servico = new Servico();

        long id = cursor.getLong(cursor.getColumnIndex(BdTabelaServicos._ID));

        String nome = cursor.getString(cursor.getColumnIndex(BdTabelaServicos.CAMPO_NOME));

        servico.setId(id);
        servico.setNome(nome);

        return servico;

    }

}
