package com.example.favmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.tituloActivityEntrada);

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
                    Snackbar.make(findViewById(R.id.layoutPrincipal), R.string.msg_nueva_categoria,
                                    Snackbar.LENGTH_LONG)
                            .show();
                }else{
                    Snackbar.make(findViewById(R.id.layoutPrincipal), R.string.msg_editar_categoria,
                                    Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });
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