package com.example.favmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.favmovies.modelo.Categoria;

public class CategoriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        //Recepción de datos
        Intent intent = getIntent();
        Log.i("MainActovity.POS_CATEGORIA_SELECCIONADA", MainActivity.POS_CATEGORIA_SELECCIONADA+" ");
        int posCategoria= intent.getIntExtra(MainActivity.POS_CATEGORIA_SELECCIONADA, 0);
        Log.i("posCategoria", posCategoria+ " ");
        Categoria categEntrada = null;

        if(posCategoria>0){
            categEntrada=intent.getParcelableExtra(MainActivity.CATEGORIA_SELECCIONADA);
        }

        TextView textViewCrea = (TextView)findViewById(R.id.textViewCat);
        EditText editNomCategoria = (EditText) findViewById(R.id.exitTxtNombreCategoria);
        EditText editDescripcion = (EditText) findViewById(R.id.editTxtDescriptcionCategoria);


        if(posCategoria==0){
            textViewCrea.setText(R.string.titulo_crear_categoria);
        }else{
            //textView e
            textViewCrea.setText(R.string.titulo_modificar_categoria);
            editNomCategoria.setText(categEntrada.getNombre());
            editDescripcion.setText((categEntrada.getDescription()));
            //No dejamos cambiar el nombre de la categoría
            editNomCategoria.setEnabled(false);
        }

        Button btnOk = findViewById(R.id.btnCatOk);
        Button btnCancel = findViewById(R.id.btnCatCancel);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Categoria categSalida = new Categoria(editNomCategoria.getText().toString(),
                        editDescripcion.getText().toString());
                Intent intentResultado = new Intent();
                intentResultado.putExtra(MainActivity.CATEGORIA_MODIFICADA, categSalida);
                setResult(RESULT_OK, intentResultado);
                Log.i("Paso por aquí", "Voy a por el finish()");
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}