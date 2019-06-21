package com.chengbo.notereg;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

public class BdTableMovimento implements BaseColumns {

    public static final String NOME_TABELA = "movimentos";

    public static final String ALIAS_NOME_TIPO = "nome_tipo";
    public static final String ALIAS_NOME_SERVICO = "nome_servico";

    public static final String CAMPO_MONTANTE = "montante";
    public static final String CAMPO_DATA = "data";
    public static final String CAMPO_DESCRICAO = "descricao";
    public static final String CAMPO_TIPO = "tipo";
    public static final String CAMPO_SERVICO = "servico";

    public static final String CAMPO_NOME_TIPO = BdTableTipo.NOME_TABELA + "." + BdTableTipo.CAMPO_NOME + " AS " + ALIAS_NOME_TIPO; // tabela de categorias (só de leitura)
    public static final String CAMPO_NOME_SERVICO = BdTableServico.NOME_TABELA + "." + BdTableServico.CAMPO_NOME + " AS " + ALIAS_NOME_SERVICO; // tabela de categorias (só de leitura)

    public static final String[] TODAS_COLUNAS_MOVIMENTOS = new String[] { NOME_TABELA + "." + _ID, CAMPO_TIPO, CAMPO_SERVICO,CAMPO_DATA, CAMPO_MONTANTE, CAMPO_DESCRICAO, CAMPO_NOME_TIPO, CAMPO_NOME_SERVICO};

    private SQLiteDatabase db;

    public BdTableMovimento(SQLiteDatabase db){
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
                        "FOREIGN KEY (" + CAMPO_TIPO + ") REFERENCES " + BdTableTipo.NOME_TABELA + " ( " + BdTableTipo._ID + " ), " +
                        "FOREIGN KEY (" + CAMPO_SERVICO + ") REFERENCES " + BdTableServico.NOME_TABELA + " ( " + BdTableServico._ID + " ) " +
                        ")"
        );
    }

    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {

        String colunasSelect1 = TextUtils.join(",", columns);


        String sql = "SELECT " + colunasSelect1 + " From " +
                BdTableTipo.NOME_TABELA + " JOIN " + NOME_TABELA + " ON " + BdTableTipo.NOME_TABELA + "." + BdTableTipo._ID + "=" + NOME_TABELA + "." + CAMPO_TIPO +
                " JOIN " + BdTableServico.NOME_TABELA + " ON " + NOME_TABELA + "." + CAMPO_SERVICO + "=" + BdTableServico.NOME_TABELA + "." + BdTableServico._ID;

        if (selection != null) {
            sql += " AND " + selection;
        }

        Log.d("Tabela Movimentos", "query: " + sql);

        return db.rawQuery(sql, selectionArgs);



        /*String colunasSelect = TextUtils.join(",", columns);

        String sql = "SELECT " + colunasSelect + " FROM " +
                NOME_TABELA + " INNER JOIN " + BdTableTipo.NOME_TABELA+ " WHERE " + NOME_TABELA + "." + CAMPO_TIPO + "=" + BdTableTipo.NOME_TABELA + "." + BdTableTipo._ID
                ;

        if (selection != null) {
            sql += " AND " + selection;
        }

        Log.d("Tabela Movimentos", "query: " + sql);

        return db.rawQuery(sql, selectionArgs);
    }*/

    /*public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy);
    }*/
    }

    public long insert(ContentValues values) {
        return db.insert(NOME_TABELA, null, values);
    }

    public int update(ContentValues values, String whereClause, String [] whereArgs) {
        return db.update(NOME_TABELA, values, whereClause, whereArgs);
    }

    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(NOME_TABELA, whereClause, whereArgs);
    }
}
