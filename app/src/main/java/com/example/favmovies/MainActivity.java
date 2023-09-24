package com.example.favmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    }

    private boolean validarCampos(){
        return true;
    }
}