package com.chengbo.notereg;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void OpenMovimento(View view) {

        Intent intentMov = new Intent(MainActivity.this, WindMov.class);
        Toast.makeText(MainActivity.this, (R.string.WindMov_click_noti), Toast.LENGTH_SHORT).show();
        startActivity(intentMov);
    }

    public void OpenTipo(View view) {

        Intent intentTip = new Intent(MainActivity.this, WindTy.class);
        Toast.makeText(MainActivity.this, (R.string.WindTip_click_noti), Toast.LENGTH_SHORT).show();
        startActivity(intentTip);

    }

    public void OpenServico(View view) {

        Intent intentServ = new Intent(MainActivity.this, WindServ.class);
        Toast.makeText(MainActivity.this, (R.string.WindServ_click_noti), Toast.LENGTH_SHORT).show();
        startActivity(intentServ);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
