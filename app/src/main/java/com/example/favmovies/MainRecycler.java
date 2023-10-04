package com.example.favmovies;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.favmovies.modelo.Categoria;
import com.example.favmovies.modelo.Pelicula;

import java.util.ArrayList;
import java.util.List;

public class MainRecycler extends AppCompatActivity {

    // identificador de intent
    public static final String PELICULA_SELECCIONADA = "pelicula_seleccionada";

    public static final String PELICULA_CREADA = "pelicula_creada";

    // identificador de activity
    private static final int GESTION_ACTIVITY = 1;


    //Modelo datos
    List<Pelicula> listaPeli;
    Pelicula peli;
    RecyclerView listaPeliView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recycler);

        //Rellenar lista de peliculas

        rellenarLista();

        // Recuperamos referencia y configuramos recyclerView con la lista de usuarios
        listaPeliView = (RecyclerView)findViewById(R.id.recyclerView);
        listaPeliView.setHasFixedSize(true);

        /* Un RecyclerView necesita un Layout Manager para manejar el posicionamiento de los
           elementos en cada línea. Se podría definir un LayoutManager propio extendendiendo la clase
           RecyclerView.LayoutManager. Sin embargo, en la mayoría de los casos, simplemente se usa
           una de las subclases LayoutManager predefinidas: LinearLayoutManager, GridLayoutManager,
           StaggeredGridLayoutManager*/
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listaPeliView.setLayoutManager(layoutManager);

        //Pasamos la lista de peliculas al RecyclerView con el ListaPeliculaAdapter
        // Instanciamos el adapter con los datos de la petición y lo asignamos a RecyclerView
        // Generar el adaptador, le pasamos la lista de peliculas
        // y el manejador para el evento click sobre un elemento
        ListaPeliculasAdapter lpAdapter= new ListaPeliculasAdapter(listaPeli,
                new ListaPeliculasAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Pelicula peli) {
                        clickOnItem(peli);
                    }
                });
        listaPeliView.setAdapter(lpAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Comprobamos a qué petición se está respondiendo
        if (requestCode == GESTION_ACTIVITY) {
            // Nos aseguramos que el resultado fue OK
            if (resultCode == RESULT_OK) {
                peli=data.getParcelableExtra(PELICULA_CREADA);
                //Log.d("PeliCreadaRecicler", peli.getTitulo());

                // Refrescar el ReciclerView
                //Añadimos a la lista de peliculas la peli nueva
                listaPeli.add(peli);

                //creamos un nuevo adapter que le pasamos al recyclerView
                ListaPeliculasAdapter listaPeliculasAdapter=new ListaPeliculasAdapter(listaPeli,
                        new ListaPeliculasAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Pelicula peli) {
                                clickOnItem(peli);
                            }
                        });
                listaPeliView.setAdapter(listaPeliculasAdapter);
            }
        }

    }

    public void clickOnItem (Pelicula peli){
        Log.i("Click adapter","Item Clicked "+peli.getCategoria().getNombre());
        //Toast.makeText(MainActivity.this, "Item Clicked "+user.getId(), Toast.LENGTH_LONG).show();

        //Paso el modo de apertura
        Intent intent=new Intent (MainRecycler.this, MainActivity.class);
        intent.putExtra(PELICULA_SELECCIONADA, peli);

        startActivity(intent);
    }

    private void rellenarLista(){
        listaPeli = new ArrayList<Pelicula>();
        Categoria cataccion=new Categoria ("Acción", "PelisAccion");
        Pelicula peli1= new Pelicula("Tenet","Una acción épica que gira en " +
                "torno al espionaje internacional, los viajes en el tiempo y la evolución, " +
                "en la que un agente secreto debe prevenir la Tercera Guerra Mundial.",
                cataccion,"150","26/8/2020");
        Pelicula peli2= new Pelicula("Tenet","Una acción épica que gira en " +
                "torno al espionaje internacional, los viajes en el tiempo y la evolución, " +
                "en la que un agente secreto debe prevenir la Tercera Guerra Mundial.",
                cataccion,"150","26/8/2020");
        listaPeli.add(peli1);
        listaPeli.add(peli2);
    }

    /*Listener sobre el Fab**/
    public void crearPeliNuevaFab(View v){
        Log.d("CrearPeli", "crearPeil");
        Intent intent=new Intent(MainRecycler.this, MainActivity.class);
        startActivityForResult(intent, GESTION_ACTIVITY);

    }
}