package com.example.favmovies.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.favmovies.R;
import com.example.favmovies.modelo.Categoria;
import com.example.favmovies.util.Url;
import com.squareup.picasso.Picasso;

public class InformacionFragment extends Fragment {

    /* Las variables que utilizaremos */
    private static final String TITULO_PELI="Título";
    private String tituloPeli;
    private static final String CATEGORIA_PELI="Categoría";
    private Categoria categoriaPeli;
    private static final String ESTRENO_PELI="Estreno";
    private String estrenoPeli;
    private static final String DURACION_PELI="Duración";
    private String duracionPeli;
    private static final String CARATULA_PELI="Carátula";
    private String caratulaPeli;

    public static InformacionFragment newInstance(String tituloPeli, Categoria categoriaPeli, String estrenoPeli, String duracionPeli, String caratulaPeli) {
        InformacionFragment fragment = new InformacionFragment();
        Bundle args = new Bundle();
        //Esto no tiene mucha ciencia -> Clave, valor.
        args.putString(TITULO_PELI, tituloPeli);
        args.putParcelable(CATEGORIA_PELI, categoriaPeli);
        args.putString(ESTRENO_PELI, estrenoPeli);
        args.putString(DURACION_PELI, duracionPeli);
        args.putString(CARATULA_PELI, caratulaPeli);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tituloPeli = getArguments().getString(TITULO_PELI);
            categoriaPeli = getArguments().getParcelable(CATEGORIA_PELI);
            estrenoPeli = getArguments().getString(ESTRENO_PELI);
            duracionPeli = getArguments().getString(DURACION_PELI);
            caratulaPeli = getArguments().getString(CARATULA_PELI);
        }
    }

    /* Al crear la vista, cargamos los valores necesarios */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Mostramos el fragmento en el contenedor
        View root= inflater.inflate(R.layout.fragment_informacion, container, false);
        //TITULO
        TextView tvTitulo = root.findViewById(R.id.text_titulo);
        tvTitulo.setText(tituloPeli);
        //CATEGORÍA
        TextView tvCategoria = root.findViewById(R.id.text_categoria);
        tvCategoria.setText(categoriaPeli.getNombre());
        //ESTRENO
        TextView tvEstreno = root.findViewById(R.id.text_estreno);
        tvEstreno.setText(estrenoPeli);
        //DURACIÓN
        TextView tvDuracion = root.findViewById(R.id.text_duracion);
        tvDuracion.setText(duracionPeli);
        //CARATULA
        ImageView ivCaratula = root.findViewById(R.id.img_caratula);
        Picasso.get().load(Url.URL_IMAGEN_INTERPRETE + caratulaPeli).into(ivCaratula);
        return root;
    }
}
