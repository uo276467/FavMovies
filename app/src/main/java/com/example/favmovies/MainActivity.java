package com.example.favmovies;

import androidx.annotation.NonNull;
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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.favmovies.modelo.Categoria;
import com.example.favmovies.modelo.Pelicula;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String POS_CATEGORIA_SELECCIONADA = " ";
    public static final int GESTION_CATEGORIA = 1;
    public static final String CATEGORIA_SELECCIONADA = "categoria_seleccionada";
    public static final String CATEGORIA_MODIFICADA = "categoria_modificada";

    private List<Categoria> listaCategorias = new ArrayList<Categoria>();
    private Spinner spinner;
    private Snackbar msgCreaCategoria = null;

    boolean creandoCategoria;

    private Pelicula pelicula;

    EditText editTitulo;
    EditText editSinopsis;
    EditText editDuracion;
    EditText editFecha;
    Button btnGuardar;
    Button btnModifCategoria;

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
        listaCategorias.add(new Categoria("Sin definir", ""));
        listaCategorias.add(new Categoria("Acción", "Peliculas de acción"));
        listaCategorias.add(new Categoria("Comedia", "Peliculas de comedia"));
        listaCategorias.add(new Categoria("Bélica", "Peliculas de guerra"));
        listaCategorias.add(new Categoria("Aventura", "Peliculas de aventura"));
        listaCategorias.add(new Categoria("Musicales", "Peliculas musicales"));
        listaCategorias.add(new Categoria("Drama", "Peliculas de drama"));


        editTitulo = (EditText) findViewById(R.id.txtTitulo);
        editSinopsis = (EditText) findViewById(R.id.txtSinopsis);
        editDuracion = (EditText) findViewById(R.id.txtDuracion);
        editFecha = (EditText) findViewById(R.id.txtFecha);

        //Inicializo el spinner
        spinner = findViewById(R.id.spCategoria);
        introListaSpinner(spinner, listaCategorias);

        //Recuperamos referencia al botón
        btnGuardar = findViewById(R.id.buttonGuardar);
        btnModifCategoria= findViewById(R.id.buttonEditar);

        //Ocultar el botón de guardar
        btnModifCategoria.setVisibility(View.GONE);

        //Recepción datos
        Intent intentPeli= getIntent();

        Pelicula pelicula=intentPeli.getParcelableExtra(MainRecycler.PELICULA_SELECCIONADA);
        if (pelicula!=null) //apertura en modo consulta
            abrirModoConsulta(pelicula);

        //Definimos listener
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Validación de campos
                if(validarCampos()) {
                    //guardarPeli();
                    Snackbar.make(findViewById(R.id.layoutPrincipal), R.string.msg_guardado,
                                    Snackbar.LENGTH_LONG)
                            .show();
                }
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

                //Acción cancelar
                msgCreaCategoria.setAction(android.R.string.cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(findViewById(R.id.layoutPrincipal), "Acción cancelada",
                                        Snackbar.LENGTH_LONG)
                                .show();
                    }
                });

                //Acción crear nueva categoría.
                msgCreaCategoria.setAction(android.R.string.ok, new View.OnClickListener() {
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
        });
    }

    private void introListaSpinner(Spinner spinner, List<Categoria> listaCategorias) {
        // Creamos un nuevo array sólo con los nombres de las categorías
        ArrayList<String> nombres= new ArrayList<String>();
        for (Categoria elemento : listaCategorias) {
            nombres.add(elemento.getNombre());
        }
        // Crea un ArrayAdapter usando un array de strings y el layout por defecto del spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,nombres);
        // Especifica el layout para usar cuando aparece la lista de elecciones
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        // Aplicar el adapter al spinner
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

    /*public void guardarPeli(){
        if (validarCampos()){
            pelicula=new Pelicula(editTitulo.getText().toString(), editSinopsis.getText().toString(),
                listaCategorias.get(spinner.getSelectedItemPosition()), editDuracion.getText().toString(),
                    editFecha.getText().toString()
                );

        Log.i ("guardarPeli",listaCategorias.get(spinner.getSelectedItemPosition()).getNombre() );
        }
        //Pasar pelicula al MainRecyclerView
        Intent intentResultado= new Intent();
        intentResultado.putExtra(MainRecycler.PELICULA_CREADA,pelicula);
        setResult(RESULT_OK,intentResultado);
        finish();
    }*/

    //Apertura de activity en modo consulta
    public void abrirModoConsulta(Pelicula pelicula){
        if (!pelicula.getTitulo().isEmpty()) { //apertura en modo consulta


            //Actualizar componentes con valores de la pelicula específica
            editTitulo.setText(pelicula.getTitulo());
            editSinopsis.setText(pelicula.getArgumento());
            editDuracion.setText(pelicula.getDuracion());
            editFecha.setText(pelicula.getFecha());

            //Busqueda en la lista de categoria para colocar la posición del spinner
            int i=0;
            int posicion=0;
            String nombreaccion=pelicula.getCategoria().getNombre();
            Log.i("CategoriaAbrirModoConsulta", nombreaccion);

            for (Categoria elemento : listaCategorias) {
                if (elemento.getNombre().equals(nombreaccion))
                    posicion = i;
                i++;
            }
            spinner.setSelection(posicion);

            //Inhabilitar edición de los conponentes
            editTitulo.setEnabled(false);
            editSinopsis.setEnabled(false);
            editFecha.setEnabled(false);
            editDuracion.setEnabled(false);
            btnGuardar.setEnabled(false);
            btnModifCategoria.setEnabled(false);
            spinner.setEnabled(false);
        }
    }

    private boolean validarCampos(){
        StringBuilder mensaje = new StringBuilder();
        mensaje.append(getString(R.string.error_campos_sin_rellenar));

        boolean errors = false;

        if(editTitulo.getText().toString().trim().length() == 0){
            mensaje.append("\n" + getString(R.string.titulo));
            errors = true;
        }
        if(editSinopsis.getText().toString().length() == 0){
            mensaje.append("\n" + getString(R.string.argumento));
            errors = true;
        }
        if(spinner.getSelectedItemPosition() == 0){
            mensaje.append("\n" + getString(R.string.categoria));
            errors = true;
        }
        if(editDuracion.getText().toString().length() == 0){
            mensaje.append("\n" + getString(R.string.duracion));
            errors = true;
        }
        if(editFecha.getText().toString().length() == 0){
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

    /*****MENU**************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.compartir){
            Conexion conexion=new Conexion(getApplicationContext());

            if (conexion.compruebaConexion()){
                compartirPeli();
            }
            else
                Toast.makeText(getApplicationContext(), R.string.comprueba_conexion, Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }


    private void compartirPeli() {
        /* es necesario hacer un intent con la constate ACTION_SEND */
        /*Llama a cualquier app que haga un envío*/
        Intent itSend = new Intent(Intent.ACTION_SEND);
        /* vamos a enviar texto plano */
        itSend.setType("text/plain");
        // itSend.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{para});


        EditText editTitulo= (EditText)findViewById(R.id.txtTitulo);
        EditText editContenido= (EditText) findViewById(R.id.txtSinopsis);

        itSend.putExtra(Intent.EXTRA_SUBJECT,
                getString(R.string.subject_compartir) + ": " + editTitulo.getText().toString());
        itSend.putExtra(Intent.EXTRA_TEXT, getString(R.string.titulo)
                +": "+editTitulo.getText().toString()+"\n"+
                getString(R.string.argumento)
                +": "+editContenido.getText().toString()
        );



        /* iniciamos la actividad */
                /* puede haber más de una aplicacion a la que hacer un ACTION_SEND,
                   nos sale un ventana que nos permite elegir una.
                   Si no lo pongo y no hay activity disponible, pueda dar un error */
        Intent shareIntent=Intent.createChooser(itSend, null);
        startActivity(shareIntent);
    }
}