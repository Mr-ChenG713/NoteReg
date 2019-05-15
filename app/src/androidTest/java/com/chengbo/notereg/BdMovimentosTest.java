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
public class BdMovimentosTest {

    @Before
    public  void apagaBaseDados (){

        getAppContext().deleteDatabase(BdMovimentosOpenHelper.NOME_BASE_DADOS);
    }

    @Test
    public void criaBdMovimentos() {
        // Context of the app under test.
        Context appContext = getAppContext();

        BdMovimentosOpenHelper openHelper = new BdMovimentosOpenHelper(appContext);

        SQLiteDatabase db = openHelper.getReadableDatabase();

        assertTrue(db.isOpen());
    }

    private Context getAppContext() {
        return InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testCRUD (){

        BdMovimentosOpenHelper openHelper = new BdMovimentosOpenHelper(getAppContext());
        SQLiteDatabase db = openHelper.getWritableDatabase();

        /*******************************
         * Operações da Tabela Tipo *
         *******************************/

        BdTabelaTipos tabelaTipos = new BdTabelaTipos(db);

        //Teste read Tipos
        Cursor cursorTipos = getTipos(tabelaTipos);
        assertEquals(0, cursorTipos.getCount());

        //Teste create/read tipos
        String nome_tipo = "Ganho";
        long idGanho = criaTipo(tabelaTipos, nome_tipo);

        cursorTipos = getTipos(tabelaTipos);
        assertEquals(1, cursorTipos.getCount());

        Tipo tipo = getTipoComID(cursorTipos, idGanho);

        assertEquals(nome_tipo, tipo.getNome());

        nome_tipo = "Despesa";
        long idDespesa = criaTipo(tabelaTipos, nome_tipo);

        cursorTipos = getTipos(tabelaTipos);
        assertEquals(2, cursorTipos.getCount());

        tipo = getTipoComID(cursorTipos, idDespesa);

        assertEquals(nome_tipo, tipo.getNome());

        //Teste Update/Read tipo

        nome_tipo = "Ganho / Ganho";
        tipo.setNome(nome_tipo);

        int reggistoAlteradoTipo = tabelaTipos.update(tipo.getContenteValues(), BdTabelaTipos._ID + "=?", new String[] {String.valueOf(idDespesa)});

        assertEquals(1, reggistoAlteradoTipo);

        cursorTipos = getTipos(tabelaTipos);
        tipo= getTipoComID(cursorTipos, idDespesa);

        assertEquals(nome_tipo, tipo.getNome());

        //Teste Create/delete/read tipo
        long id = criaTipo(tabelaTipos, "TESTE");
        cursorTipos = getTipos(tabelaTipos);
        assertEquals(3, cursorTipos.getCount());

        tabelaTipos.delete(BdTabelaTipos._ID + "=?", new String[] {String.valueOf(id)});
        cursorTipos = getTipos(tabelaTipos);
        assertEquals(2, cursorTipos.getCount());

        getTipoComID(cursorTipos, idGanho);
        getTipoComID(cursorTipos, idDespesa);

        /*******************************
         * Operações da Tabela Serviço *
         *******************************/

        BdTabelaServicos tabelaServicos = new BdTabelaServicos(db);

        //Teste read Serviço
        Cursor cursorServicos = getServicos(tabelaServicos);
        assertEquals(0, cursorServicos.getCount());

        //Teste create/read Serviço
        String nome_servico = "Alimentação";
        long idAlimentacao = criaServico(tabelaServicos, nome_servico);

        cursorServicos = getServicos(tabelaServicos);
        assertEquals(1, cursorServicos.getCount());

        Servico servico = getServicoComID(cursorServicos, idAlimentacao);

        assertEquals(nome_servico, servico.getNome());

        nome_servico = "Salario";
        long idSalario = criaServico(tabelaServicos, nome_servico);

        cursorServicos = getServicos(tabelaServicos);
        assertEquals(2, cursorServicos.getCount());

        servico = getServicoComID(cursorServicos, idSalario);

        assertEquals(nome_servico, servico.getNome());

        //Teste Update/Read servico
        nome_servico = "Alimentacao / Salario";
        servico.setNome(nome_servico);


        int reggistoAlteradoServico = tabelaServicos.update(servico.getContentValues(), BdTabelaServicos._ID + "=?", new String[] {String.valueOf(idSalario)});

        assertEquals(1, reggistoAlteradoServico);

        cursorServicos = getServicos(tabelaServicos);
        servico = getServicoComID(cursorServicos, idSalario);

        assertEquals(nome_servico, servico.getNome());

        //Teste Create/delete/read tipo
        id = criaServico(tabelaServicos, "TESTE");
        cursorServicos = getServicos(tabelaServicos);
        assertEquals(3, cursorServicos.getCount());

        tabelaServicos.delete(BdTabelaServicos._ID + "=?", new String[] {String.valueOf(id)});
        cursorServicos = getServicos(tabelaServicos);
        assertEquals(2, cursorServicos.getCount());

        getServicoComID(cursorServicos, idAlimentacao);
        getServicoComID(cursorServicos, idSalario);

        /*******************************
         * Operações da Tabela Movimentos *
         *******************************/

        BdTabelaMovimentos tabelaMovimentos = new BdTabelaMovimentos(db);

        //Teste Read movimentos
        Cursor cursorMovimentos = getMovimentos(tabelaMovimentos);
        assertEquals(0, cursorMovimentos.getCount());

        //Teste create/read movimento
        String data = "15/05/2019";
        double montante = 5.00;
        String descricao = " @ ";

        id = criaMovimento(tabelaMovimentos, data, montante, descricao, idGanho, idAlimentacao);
        cursorMovimentos = getMovimentos(tabelaMovimentos);
        assertEquals(1, cursorMovimentos.getCount());

        Movimento movimento = getMovimentoComID(cursorMovimentos, id);
        assertEquals(data, movimento.getData());
        assertEquals(montante, movimento.getMontante());
        assertEquals(descricao, movimento.getDescricao());
        assertEquals(idGanho, movimento.getTipos());
        assertEquals(idAlimentacao, movimento.getServicos());

        data = "19/05/2019";
        montante = 10.98;
        descricao = " @@@@ ";
        id = criaMovimento(tabelaMovimentos, data, montante, descricao, idDespesa, idSalario);
        cursorMovimentos = getMovimentos(tabelaMovimentos);
        assertEquals(2, cursorMovimentos.getCount());

        movimento = getMovimentoComID(cursorMovimentos, id);
        assertEquals(data, movimento.getData());
        assertEquals(montante, movimento.getMontante());
        assertEquals(descricao, movimento.getDescricao());
        assertEquals(idDespesa, movimento.getTipos());
        assertEquals(idSalario, movimento.getServicos());

        id = criaMovimento(tabelaMovimentos, "20/05/2019", 80.30, "@123", idGanho, idSalario);
        cursorMovimentos = getMovimentos(tabelaMovimentos);
        assertEquals(3, cursorMovimentos.getCount());

        //Teste read/Update movimentos
        movimento = getMovimentoComID(cursorMovimentos, id);
        data = "23/06/2020";
        montante = 160.81;
        descricao = "@123456678";

        movimento.setData(data);
        movimento.setMontante(montante);
        movimento.setDescricao(descricao);
        movimento.setTipos(idDespesa);
        movimento.setServicos(idSalario);

        tabelaMovimentos.update(movimento.getContentValues(), BdTabelaMovimentos._ID + "=?", new String[] {String.valueOf(id)});


        cursorMovimentos = getMovimentos(tabelaMovimentos);

        movimento = getMovimentoComID(cursorMovimentos, id);
        assertEquals(data, movimento.getData());
        assertEquals(montante, movimento.getMontante());
        assertEquals(descricao, movimento.getDescricao());
        assertEquals(idDespesa, movimento.getTipos());
        assertEquals(idSalario, movimento.getServicos());

        //Teste read/delete Movimentos
        tabelaMovimentos.delete(BdTabelaMovimentos._ID + "=?", new String[] {String.valueOf(id)});
        cursorMovimentos = getMovimentos(tabelaMovimentos);
        assertEquals(2, cursorMovimentos.getCount());

    }

    //Tabela tipos
    private long criaTipo(BdTabelaTipos tabelaTipos, String nome_tipo) {

        Tipo tipo = new Tipo();
        tipo.setNome(nome_tipo);

        long id = tabelaTipos.insert(tipo.getContenteValues());
        assertNotEquals(-1, id);

        return id;
    }

    private Cursor getTipos(BdTabelaTipos tabelaTipos) {

        return tabelaTipos.query(BdTabelaTipos.TODAS_COLUNAS_TIPOS, null, null, null, null, null);
    }

    private Tipo getTipoComID(Cursor cursor, long id) {

        Tipo tipo = null;

        while (cursor.moveToNext()) {
            tipo = Tipo.fromCursor(cursor);

            if (tipo.getId() == id) {
                break;
            }
        }

        assertNotNull(tipo);

        return tipo;
    }

    //Tabela servicos
    private long criaServico(BdTabelaServicos tabelaServicos, String nome_servico) {

        Servico servico = new Servico();
        servico.setNome(nome_servico);

        long id = tabelaServicos.insert(servico.getContentValues());
        assertNotEquals(-1, id);

        return id;
    }

    private Cursor getServicos (BdTabelaServicos tabelaServicos) {

        return tabelaServicos.query(BdTabelaServicos.TODAS_COLUNAS_SERVICOS, null, null, null, null, null);
    }

    private Servico getServicoComID(Cursor cursor, long id) {

        Servico servico = null;

        while (cursor.moveToNext()) {
            servico = Servico.fromCursor(cursor);

            if (servico.getId() == id) {
                break;
            }
        }

        assertNotNull(servico);

        return servico;
    }

    //Tabela Movimentos
    private long criaMovimento(BdTabelaMovimentos tabelaMovimentos, String data, double montante, String descricao, long tipos, long servicos) {
        Movimento movimento = new Movimento();

        movimento.setData(data);
        movimento.setMontante(montante);
        movimento.setDescricao(descricao);
        movimento.setTipos(tipos);
        movimento.setServicos(servicos);

        long id = tabelaMovimentos.insert(movimento.getContentValues());
        assertNotEquals(-1, id);

        return id;
    }

    private Cursor getMovimentos(BdTabelaMovimentos tabelaMovimentos) {
        return tabelaMovimentos.query(BdTabelaMovimentos.TODAS_COLUNAS_MOVIMENTOS, null, null, null, null, null);
    }

    private Movimento getMovimentoComID(Cursor cursor, long id) {
        Movimento movimento = null;

        while (cursor.moveToNext()) {
            movimento = Movimento.fromCursor(cursor);

            if (movimento.getId() == id) {
                break;
            }
        }

        assertNotNull(movimento);

        return movimento;
    }

}
