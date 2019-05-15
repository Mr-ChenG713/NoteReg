package com.chengbo.notereg;

import android.content.ContentValues;
import android.database.Cursor;

public class Movimento {

    private long id;
    private double montante;
    private String  data;
    private String descricao;
    private long tipos; //chave estrangeira
    private long servicos; //chave estrangeira

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getMontante() {
        return montante;
    }

    public void setMontante(double montante) {
        this.montante = montante;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public long getTipos() {
        return tipos;
    }

    public void setTipos(long tipos) {
        this.tipos = tipos;
    }

    public long getServicos() {
        return servicos;
    }

    public void setServicos(long servicos) {
        this.servicos = servicos;
    }

    public ContentValues getContentValues(){
        ContentValues valores =  new ContentValues ();

        valores.put(BdTabelaMovimentos.CAMPO_MONTANTE, montante);
        valores.put(BdTabelaMovimentos.CAMPO_DATA, data);
        valores.put(BdTabelaMovimentos.CAMPO_DESCRICAO, descricao);
        valores.put(BdTabelaMovimentos.CAMPO_TIPO, tipos);
        valores.put(BdTabelaMovimentos.CAMPO_SERVICO, servicos);//Todos Atributos sem ID

        return valores;
    }

    public static  Movimento fromCursor (Cursor cursor){

        long id = cursor.getLong(cursor.getColumnIndex(BdTabelaMovimentos._ID));

        float montante  = cursor.getFloat(cursor.getColumnIndex(BdTabelaMovimentos.CAMPO_MONTANTE));

        String data  = cursor.getString(cursor.getColumnIndex(BdTabelaMovimentos.CAMPO_DATA));

        String descricao = cursor.getString(cursor.getColumnIndex(BdTabelaMovimentos.CAMPO_DESCRICAO));

        long tipos = cursor.getLong(cursor.getColumnIndex(BdTabelaMovimentos.CAMPO_TIPO));

        long servico = cursor.getLong(cursor.getColumnIndex(BdTabelaMovimentos.CAMPO_SERVICO));

        Movimento movimento = new Movimento ();

        movimento.setId(id);
        movimento.setMontante(montante);
        movimento.setData(data);
        movimento.setDescricao(descricao);
        movimento.setTipos(tipos);
        movimento.setServicos(servico);

        return movimento;
    }
}
