package com.chengbo.notereg;

import android.content.ContentValues;
import android.database.Cursor;

public class Movimento {

    private long id;
    private String data;
    private double montante;
    private String descricao;
    private long fktipo;
    private long fkservico;
    private String nomeTipo; // Campo "externo"
    private String nomeServico; // Campo "externo"

    public String getNomeTipo() {
        return nomeTipo;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getMontante() {
        return montante;
    }

    public void setMontante(double montante) {
        this.montante = montante;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public long getFktipo() {
        return fktipo;
    }

    public void setFktipo(long fktipo) {
        this.fktipo = fktipo;
    }

    public long getFkservico() {
        return fkservico;
    }

    public void setFkservico(long fkservico) {
        this.fkservico = fkservico;
    }



    public ContentValues getContentValues(){

        ContentValues values = new ContentValues();

        values.put(BdTableMovimento.CAMPO_TIPO, fktipo);
        values.put(BdTableMovimento.CAMPO_SERVICO, fkservico);
        values.put(BdTableMovimento.CAMPO_DATA, data);
        values.put(BdTableMovimento.CAMPO_MONTANTE, montante);
        values.put(BdTableMovimento.CAMPO_DESCRICAO, descricao);

        return values;
    }

    public static Movimento fromCursor (Cursor cursor){

        long id = cursor.getLong(cursor.getColumnIndex(BdTableMovimento._ID));
        long fktipo = cursor.getLong(cursor.getColumnIndex(BdTableMovimento.CAMPO_TIPO));
        long fkservico = cursor.getLong(cursor.getColumnIndex(BdTableMovimento.CAMPO_SERVICO));
        String data = cursor.getString(cursor.getColumnIndex(BdTableMovimento.CAMPO_DATA));
        double montante = cursor.getDouble(cursor.getColumnIndex(BdTableMovimento.CAMPO_MONTANTE));
        String descricao = cursor.getString(cursor.getColumnIndex(BdTableMovimento.CAMPO_DESCRICAO));
        //String nomeTipo = cursor.getString(cursor.getColumnIndex(BdTableMovimento.ALIAS_NOME_TIPO));
        //String nomeServico = cursor.getString(cursor.getColumnIndex(BdTableMovimento.ALIAS_NOME_SERVICO));

        Movimento movimento = new Movimento();

        movimento.setId(id);
        movimento.setFktipo(fktipo);
        movimento.setFkservico(fkservico);
        movimento.setData(data);
        movimento.setMontante(montante);
        movimento.setDescricao(descricao);
       // movimento.nomeTipo = nomeTipo;
        //movimento.nomeServico = nomeServico;

        return movimento;
    }

}
