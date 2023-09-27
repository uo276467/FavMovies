package com.example.favmovies;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.favmovies.modelo.Categoria;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String POS_CATEGORIA_SELECCIONADA = " ";
    public static final int GESTION_CATEGORIA = 1;
    public static final String CATEGORIA_SELECCIONADA = "categoria_seleccionada";
    public static final String CATEGORIA_MODIFICADA = "categoria_modificada";

    private List<Categoria> listaCategorias;
    private Spinner spinner;
    private Snackbar msgCreaCategoria = null;

    boolean creandoCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Theme generado en styles no tiene actionBar. Vamos a ponerle action bar
        1.Modificamos el fichero themes.xml
        2.Modificamos el fichero manifiesto para añadirle esto
        android:theme="@style/AppTheme"
         */
        setTitle(R.string.tituloActivityEntrada);

        //Inicializa el modelo de datos
        listaCategorias = new ArrayList<Categoria>();
        listaCategorias.add(new Categoria("Acción", "Películas de acción"));
        listaCategorias.add(new Categoria("Comedia", "Películas de comedia"));

        //Inicializo el spinner
        spinner = findViewById(R.id.spCategoria);
        introListaSpinner(spinner, listaCategorias);

        //Recuperamos referencia al botón
        Button btnGuardar = findViewById(R.id.buttonGuardar);
        //Definimos listener
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Validación de campos
                if(validarCampos())
                    Snackbar.make(findViewById(R.id.layoutPrincipal), R.string.msg_guardado,
                            Snackbar.LENGTH_LONG)
                            .show();
            }
        });

        //Recuperamos referencia al botón
        Button btnEditar = findViewById(R.id.buttonEditar);
        //Definimos listener
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner categoria = findViewById(R.id.spCategoria);
                if(categoria.getSelectedItemPosition() == 0){
                    msgCreaCategoria = Snackbar.make(findViewById(R.id.layoutPrincipal), R.string.msg_nueva_categoria,
                                    Snackbar.LENGTH_LONG);
                }else{
                    msgCreaCategoria = Snackbar.make(findViewById(R.id.layoutPrincipal), R.string.msg_editar_categoria,
                                    Snackbar.LENGTH_LONG);
                }
            }
        });

        //Acción cancelar
        msgCreaCategoria.setAction(android.R.string.cancel, new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Snackbar.make(findViewById(R.id.layoutPrincipal), "Acción cancelada",
                                Snackbar.LENGTH_LONG)
                        .show();
            }
        });

        //Acción crear nueva categoria
        msgCreaCategoria.setAction(android.R.string.ok, new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Snackbar.make(findViewById(R.id.layoutPrincipal), "Acción realizada",
                        Snackbar.LENGTH_LONG)
                    .show();
                modificarCategoria();
            }
        });
        msgCreaCategoria.show(); //Necesario para que salga mensaje de acción realizada
    }

    private void introListaSpinner(Spinner spinner, List<Categoria> listaCategorias) {
        //Creamos un nuevo array solo con los nombres de las categorías
        ArrayList<String> nombres = new ArrayList<String>();
        nombres.add("Sin definir");
        for(Categoria cat : listaCategorias){
            nombres.add(cat.getNombre());
        }
        //Crea un ArrayAdapter usando un array de strings y el layout por defecto del spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, nombres);
        //Especifica el layout para usar cuando aparece la lista de elecciones
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        //Aplicar el adaptador al spinner
        spinner.setAdapter(adapter);
    }

    private void modificarCategoria(){
        Intent categoriaIntent = new Intent(MainActivity.this, CategoriaActivity.class);

        //Lanza activity categoría sin pasarle ninguna categoría (parámetro)
        //startActivity(categoriaIntent);

        //Log.i("Posición Categoría Seleccionada Spinner", spinner.getSelectedItemPosition()+ " ");
        categoriaIntent.putExtra(POS_CATEGORIA_SELECCIONADA, spinner.getSelectedItemPosition());

        creandoCategoria = true;
        if(spinner.getSelectedItemPosition()>0){
            creandoCategoria = false;
            categoriaIntent.putExtra(CATEGORIA_SELECCIONADA,
                    listaCategorias.get(spinner.getSelectedItemPosition()-1));
        }

        //Lanzamos activity para gestionar categoría esperando por un resultado
        startActivityForResult(categoriaIntent, GESTION_CATEGORIA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Comprobamos a qué petición se está respondiendo
        if(requestCode == GESTION_CATEGORIA){
            //Nos aseguramos que el resultado fue OK
            if(resultCode == RESULT_OK){
                Categoria cateAux = data.getParcelableExtra(CATEGORIA_MODIFICADA);
                //Log.d("FavMovies.MainActivity", cateAux.toString());

                if(creandoCategoria) {
                    //añadimos categoría a lista
                    listaCategorias.add(cateAux);
                    introListaSpinner(spinner, listaCategorias);
                }else{
                    //buscda la categoría del mismo nombre en la lista y cambia descripción
                    for(Categoria cat : listaCategorias){
                        if(cat.getNombre().equals(cateAux.getNombre())){
                            cat.setDescription(cateAux.getDescription());
                            //Log.d("FavMovies.MainActivity", "Modificada la descripción de: "+cat.getNombre().toString());
                            break;
                        }
                    }
                }
            }else if(resultCode==RESULT_CANCELED){
                //Log.d("FavMovie.MainActivity", "CategoriaActivity cancelada");
            }
        }
    }

    private boolean validarCampos(){
        EditText titulo = findViewById(R.id.txtTitulo);
        EditText sinopsis = findViewById(R.id.txtSinopsis);
        Spinner categoria = findViewById(R.id.spCategoria);
        EditText duracion = findViewById(R.id.txtDuracion);
        EditText fecha = findViewById(R.id.txtFecha);

        StringBuilder mensaje = new StringBuilder();
        mensaje.append(getString(R.string.error_campos_sin_rellenar));

        boolean errors = false;

        if(titulo.getText().toString().trim().length() == 0){
            mensaje.append("\n" + getString(R.string.titulo));
            errors = true;
        }
        if(sinopsis.getText().toString().length() == 0){
            mensaje.append("\n" + getString(R.string.argumento));
            errors = true;
        }
        if(categoria.getSelectedItemPosition() == 0){
            mensaje.append("\n" + getString(R.string.categoria));
            errors = true;
        }
        if(duracion.getText().toString().length() == 0){
            mensaje.append("\n" + getString(R.string.duracion));
            errors = true;
        }
        if(fecha.getText().toString().length() == 0){
            mensaje.append("\n" + getString(R.string.fecha));
            errors = true;
        }

        if(errors) {
            Snackbar.make(findViewById(R.id.layoutPrincipal), mensaje.toString(),
                            Snackbar.LENGTH_LONG)
                    .show();
            return false;
        } else {
            return true;
        }
    }

}