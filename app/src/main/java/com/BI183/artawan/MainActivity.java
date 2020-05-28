package com.BI183.artawan;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bh183.deryhendra.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Film> dataFilm;
    private RecyclerView rvFilm;
    private FilmAdapter filmAdapter;
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvFilm = findViewById(R.id.rv_tampil_film);
        dbHandler = new DatabaseHandler(this);
        tampilDataFilm();
    }

    private void tampilDataFilm() {
        dataFilm = dbHandler.getAllFilm();
        filmAdapter = new FilmAdapter(this, dataFilm);
        RecyclerView.LayoutManager layManager = new LinearLayoutManager(MainActivity.this);
        rvFilm.setLayoutManager(layManager);
        rvFilm.setAdapter(filmAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id==R.id.item_menu_tambah){
            Intent bukaInput = new Intent(getApplicationContext(), InputActivity.class);
            bukaInput.putExtra("OPERASI", "insert");
            startActivity(bukaInput);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tampilDataFilm();
    }
}
