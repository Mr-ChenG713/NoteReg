package com.chengbo.notereg;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTabelaMovimentos implements BaseColumns {

    public static final String NOME_TABELA = "movimentos";
    public static final String CAMPO_MONTANTE = "montante";
    public static final String CAMPO_DATA = "data";
    public static final String CAMPO_DESCRICAO = "descricao";
    public static final String CAMPO_TIPO = "tipo";
    public static final String CAMPO_SERVICO = "servico";

    public static final String[] TODAS_COLUNAS_MOVIMENTOS = new String[] {_ID, CAMPO_TIPO, CAMPO_SERVICO,CAMPO_DATA, CAMPO_MONTANTE,CAMPO_DESCRICAO};


    private SQLiteDatabase db;

    public BdTabelaMovimentos(SQLiteDatabase db){
        this.db = db;
    }

    public void cria(){
        db.execSQL(
                "CREATE TABLE " + NOME_TABELA + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        CAMPO_MONTANTE + " DOUBLE NOT NULL, " +
                        CAMPO_DATA + " TEXT NOT NULL, " +
                        CAMPO_DESCRICAO + " TEXT, " +
                        CAMPO_TIPO + " INTEGER NOT NULL, " +
                        CAMPO_SERVICO + " INTEGER NOT NULL, " +
                        "FOREIGN KEY (" + CAMPO_TIPO + ") REFERENCES " + BdTabelaTipos.NOME_TABELA + " ( " + BdTabelaTipos._ID + " ), " +
                        "FOREIGN KEY (" + CAMPO_SERVICO + ") REFERENCES " + BdTabelaServicos.NOME_TABELA + " ( " + BdTabelaServicos._ID + " ) " +
                        ")"
        );
    }

    public Cursor query (String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        return db.query(NOME_TABELA,columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public long insert (ContentValues values){

        return db.insert(NOME_TABELA, null, values);
    }

    public int update (ContentValues values, String whereClause, String[] whereArgs){

        return db.update(NOME_TABELA, values, whereClause, whereArgs);
    }

    public int delete (String whereClause, String[] whereArgs){
        return  db.delete(NOME_TABELA, whereClause, whereArgs);
    }

}
