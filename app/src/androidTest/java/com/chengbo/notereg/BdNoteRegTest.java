package com.chengbo.notereg;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BdNoteRegTest {

    @Before
    public  void apagaBaseDados (){
        getAppContext().deleteDatabase(BdNoteRegOpenHelper.NOME_BASE_DADOS);
    }

    @Test
    public void criaBdMovimentos() {
        // Context of the app under test.
        Context appContext = getAppContext();

        BdNoteRegOpenHelper openHelper = new BdNoteRegOpenHelper(appContext);

        SQLiteDatabase db = openHelper.getReadableDatabase();

        assertTrue(db.isOpen());
    }

    private Context getAppContext() {
        return InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testCRUD () {

        BdNoteRegOpenHelper openHelper = new BdNoteRegOpenHelper(getAppContext());
        SQLiteDatabase db = openHelper.getWritableDatabase();

        /*******************************
         * Operações da Tabela Tipo *
         *******************************/
        BdTableTipo tableTipo = new BdTableTipo(db);

        //Teste read Tipos
        Cursor cursorTipo = getTipo(tableTipo);
        assertEquals(0, cursorTipo.getCount());

        //Teste create/read tipos
        String nome = "Ganho";

        long idGanho = criaTipo(tableTipo, nome);
        cursorTipo = getTipo(tableTipo);
        assertEquals(1, cursorTipo.getCount());

        Tipo tipo = getTipoComID(cursorTipo, idGanho);
        assertEquals(nome, tipo.getTiponome());

        nome = "Despesa";

        long idDespesa = criaTipo(tableTipo, nome);
        cursorTipo = getTipo(tableTipo);
        assertEquals(2, cursorTipo.getCount());

        tipo = getTipoComID(cursorTipo, idDespesa);
        assertEquals(nome, tipo.getTiponome());

        //Teste Update/Read tipo
        nome = "GanhoDespesa";

        tipo.setTiponome(nome);

        int alterarTipo = tableTipo.update(tipo.getContentValues(), BdTableTipo._ID + "=?", new String[]{String.valueOf(idGanho)});

        assertEquals(1, alterarTipo);

        cursorTipo = getTipo(tableTipo);
        tipo = getTipoComID(cursorTipo, idGanho);

        assertEquals(nome, tipo.getTiponome());

        //Teste Create/delete/read tipo
        long id =  criaTipo(tableTipo, "TESTE DELETE");
        cursorTipo = getTipo(tableTipo);
        assertEquals(3, cursorTipo.getCount());

        tableTipo.delete(BdTableTipo._ID + "=?", new String[]{String.valueOf(id)});


        /*******************************
         * Operações da Tabela Serviço *
         *******************************/
        BdTableServico tableServico = new BdTableServico(db);

        //Teste read Serviço
        Cursor cursorServico = getServico(tableServico);
        assertEquals(0, cursorServico.getCount());

        //Teste create/read Serviço
        nome = "Alimentação";

        long idAlimentacao = criaServico(tableServico, nome);
        cursorServico = getServico(tableServico);
        assertEquals(1, cursorServico.getCount());

        Servico servico = getServicoComID(cursorServico, idAlimentacao);
        assertEquals(nome, servico.getServiconome());

        nome = "Salario";

        long idSalario = criaServico(tableServico, nome);
        cursorServico = getServico(tableServico);
        assertEquals(2, cursorServico.getCount());

        servico = getServicoComID(cursorServico, idSalario);
        assertEquals(nome, servico.getServiconome());

        //Teste Update/Read servico
        nome = "Transporte";

        servico.setServiconome(nome);

        int alterarServico = tableServico.update(servico.getContentValues(), BdTableServico._ID + "=?", new String[]{String.valueOf(idAlimentacao)});

        assertEquals(1, alterarServico);

        cursorServico = getServico(tableServico);
        servico = getServicoComID(cursorServico, idAlimentacao);

        assertEquals(nome, servico.getServiconome());

        //Teste Create/delete/read servico
        id =  criaServico(tableServico, "TESTE DELETE");
        cursorServico = getServico(tableServico);
        assertEquals(3, cursorServico.getCount());

        tableServico.delete(BdTableServico._ID + "=?", new String[]{String.valueOf(id)});


        /*******************************
         * Operações da Tabela Movimentos *
         *******************************/
        BdTableMovimento tableMovimento = new BdTableMovimento(db);

        //Teste Read movimentos
        Cursor cursorMovimento = getMovimento(tableMovimento);
        assertEquals(0, cursorMovimento.getCount());

        //Teste create/read movimento
        String data = "31/06/2019";
        double montante = 1580.95;
        String descricao = "Final do mês !!!";

        long idMov1 = criaMovimento(tableMovimento, data, montante, descricao, idGanho, idSalario);
        cursorMovimento = getMovimento(tableMovimento);
        assertEquals(1, cursorMovimento.getCount());

        Movimento movimento = getMovimentoComID(cursorMovimento, idMov1);
        assertEquals(data, movimento.getData());
        assertEquals(montante, movimento.getMontante(), 0.01);
        assertEquals(descricao, movimento.getDescricao());
        assertEquals(idGanho, movimento.getFktipo());
        assertEquals(idSalario, movimento.getFkservico());

        data = "01/07/2019";
        montante = 7.95;
        descricao = "Foi muito caro !!!";

        long idMov2 = criaMovimento(tableMovimento, data, montante, descricao, idDespesa, idAlimentacao);
        cursorMovimento = getMovimento(tableMovimento);
        assertEquals(2, cursorMovimento.getCount());

        movimento = getMovimentoComID(cursorMovimento, idMov2);
        assertEquals(data, movimento.getData());
        assertEquals(montante, movimento.getMontante(), 0.01);
        assertEquals(descricao, movimento.getDescricao());
        assertEquals(idDespesa, movimento.getFktipo());
        assertEquals(idAlimentacao, movimento.getFkservico());

        id = criaMovimento(tableMovimento, "02/07/2019", 0.01, "TESTE DELETE", idGanho, idAlimentacao);
        cursorMovimento = getMovimento(tableMovimento);
        assertEquals(3, cursorMovimento.getCount());

        //Teste read/Update movimentos
        movimento = getMovimentoComID(cursorMovimento, id);
        data = "31/06/2019";
        montante = 2300.86;
        descricao = "Subiu o salario";

        movimento.setData(data);
        movimento.setMontante(montante);
        movimento.setDescricao(descricao);
        movimento.setFktipo(idGanho);
        movimento.setFkservico(idSalario);

        tableMovimento.update(movimento.getContentValues(), BdTableMovimento._ID + "=?", new String[]{String.valueOf(id)});
        cursorMovimento = getMovimento(tableMovimento);

        movimento = getMovimentoComID(cursorMovimento, id);
        assertEquals(data, movimento.getData());
        assertEquals(montante, movimento.getMontante(), 0.01);
        assertEquals(descricao, movimento.getDescricao());
        assertEquals(idGanho, movimento.getFktipo());
        assertEquals(idSalario, movimento.getFkservico());

        //Teste read/delete Movimentos
        tableMovimento.delete(BdTableMovimento._ID + "=?", new String[]{String.valueOf(id)});
        cursorMovimento = getMovimento(tableMovimento);
        assertEquals(2, cursorMovimento.getCount());

    }

    //TIPO
    private long criaTipo (BdTableTipo tableTipo, String nome){

        Tipo tipo = new Tipo();

        tipo.setTiponome(nome);

        long id = tableTipo.insert(tipo.getContentValues());
        assertNotEquals(-1, id);

        return id;
    }

    private Cursor getTipo (BdTableTipo tableTipo){
        return  tableTipo.query(BdTableTipo.TODAS_COLUNAS_TIPOS, null, null, null, null, null);
    }

    private Tipo getTipoComID (Cursor cursor, long id){

        Tipo tipo = null;

        while (cursor.moveToNext()){
            tipo = Tipo.fromCursor(cursor);

            if (tipo.getId() == id){
                break;
            }
        }

        assertNotNull(tipo);

        return tipo;

    }

    //Serviço
    private long criaServico (BdTableServico tableServico, String nome){

        Servico servico = new Servico();

        servico.setServiconome(nome);

        long id = tableServico.insert(servico.getContentValues());
        assertNotEquals(-1, id);

        return id;
    }

    private Cursor getServico (BdTableServico tableServico){
        return  tableServico.query(BdTableServico.TODAS_COLUNAS_SERVICOS, null, null, null, null, null);
    }

    private Servico getServicoComID (Cursor cursor, long id){

        Servico servico = null;

        while (cursor.moveToNext()){
            servico = Servico.fromCursor(cursor);

            if (servico.getId() == id){
                break;
            }
        }

        assertNotNull(servico);

        return servico;
    }

    //Movimento
    private long criaMovimento (BdTableMovimento tableMovimento, String data, double montante, String descricao, long fktipo, long fkservico){

        Movimento movimento = new Movimento();

        movimento.setData(data);
        movimento.setMontante(montante);
        movimento.setDescricao(descricao);
        movimento.setFktipo(fktipo);
        movimento.setFkservico(fkservico);

        long id =  tableMovimento.insert(movimento.getContentValues());
        assertNotEquals(-1, id);

        return id;
    }

    private Cursor getMovimento (BdTableMovimento tableMovimento){
        return tableMovimento.query(BdTableMovimento.TODAS_COLUNAS_MOVIMENTOS, null, null, null, null, null);

    }

    private Movimento getMovimentoComID (Cursor cursor, long id){

        Movimento movimento = null;

        while (cursor.moveToNext()){

            movimento = Movimento.fromCursor(cursor);

            if (movimento.getId() == id){
                break;
            }
        }
        assertNotNull(movimento);

        return movimento;
    }


}
