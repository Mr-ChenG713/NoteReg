package com.chengbo.notereg;

import android.content.Context;
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

        BdTabelaTipos tableTipos = new BdTabelaTipos(db);

        Tipo tipo = new Tipo();
        tipo.setNome("Ganho");
        long idGanho = tableTipos.insert(tipo.getContenteValues());

        assertNotEquals(-1, idGanho);
    }

}
