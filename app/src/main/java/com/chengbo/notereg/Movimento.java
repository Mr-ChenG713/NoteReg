package com.chengbo.notereg;

import android.content.ContentValues;
import android.database.Cursor;

public class Movimento {

    private long id;
    private String data;
    private double montante;
    private String descricao;
    private long tipo;
    private long servico;
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

    public long getTipo() {
        return tipo;
    }

    public void setTipo(long tipo) {
        this.tipo = tipo;
    }

    public long getServico() {
        return servico;
    }

    public void setServico(long servico) {
        this.servico = servico;
    }

    public ContentValues getContentValues(){

        ContentValues values = new ContentValues();

        values.put(BdTableMovimento.CAMPO_TIPO, tipo);
        values.put(BdTableMovimento.CAMPO_SERVICO, servico);
        values.put(BdTableMovimento.CAMPO_DATA, data);
        values.put(BdTableMovimento.CAMPO_MONTANTE, montante);
        values.put(BdTableMovimento.CAMPO_DESCRICAO, descricao);

        return values;
    }

    public static Movimento fromCursor (Cursor cursor){

        long id = cursor.getLong(cursor.getColumnIndex(BdTableMovimento._ID));
        long tipo = cursor.getLong(cursor.getColumnIndex(BdTableMovimento.CAMPO_TIPO));
        long servico = cursor.getLong(cursor.getColumnIndex(BdTableMovimento.CAMPO_SERVICO));
        String data = cursor.getString(cursor.getColumnIndex(BdTableMovimento.CAMPO_DATA));
        double montante = cursor.getDouble(cursor.getColumnIndex(BdTableMovimento.CAMPO_MONTANTE));
        String descricao = cursor.getString(cursor.getColumnIndex(BdTableMovimento.CAMPO_DESCRICAO));
        String nomeTipo = cursor.getString(cursor.getColumnIndex(BdTableMovimento.ALIAS_NOME_TIPO));
        String nomeServico = cursor.getString(cursor.getColumnIndex(BdTableMovimento.ALIAS_NOME_SERVICO));


        Movimento movimento = new Movimento();

        movimento.setId(id);
        movimento.setTipo(tipo);
        movimento.setServico(servico);
        movimento.setData(data);
        movimento.setMontante(montante);
        movimento.setDescricao(descricao);
        movimento.nomeTipo = nomeTipo;
        movimento.nomeServico = nomeServico;

        return movimento;
    }

}
