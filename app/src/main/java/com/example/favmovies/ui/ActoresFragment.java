package com.example.favmovies.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.favmovies.R;
import com.example.favmovies.modelo.InterpretePeliculaCrossRef;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ActoresFragment extends Fragment {

    /* Las variables que utilizaremos */
    private static final String REPARTO_PELI="REPARTO";
    private String actoresPeli;


    /*

        Esto es un FactoryMethod.
        Los datos están siendo enviados ANTES del onCreate.
        El Bundle permanece cuando se tiene que recrear.
     */
    public static ActoresFragment newInstance(String actoresPeli) {
        ActoresFragment fragment = new ActoresFragment();
        Bundle args = new Bundle();
        //Esto no tiene mucha ciencia -> Clave, valor.
        args.putString(REPARTO_PELI, actoresPeli);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            actoresPeli = getArguments().getString(REPARTO_PELI);

        }
    }

    /* Al crear la vista, cargamos los valores necesarios */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Mostramos el fragmento en el contenedor
        View root= inflater.inflate(R.layout.fragment_actores, container, false);
        //TextView tvActores = root.findViewById(R.id.text_actores);
        //tvActores.setText(actoresPeli);
        return root;
    }
}
