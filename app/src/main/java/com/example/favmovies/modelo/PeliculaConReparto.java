package com.example.favmovies.modelo;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

/*
    Fíjate que no es una entidad.
 */
public class PeliculaConReparto {

    /*
        @Embebbed para indicar la clase padre -> Una película.

     */

    @Embedded
    private Pelicula pelicula;



    /*

    @Relation para establecer la relación:
        parentColumn -> El campo id de la clase Pelicula (que es la clase padre)
        entityColumn -> El campo id de la clase Interprete.
        En el associateBy hacemos el Join mediante:
        @Junction:
            value = La clase que representa el JOIN.

            parentColumn = Nombre del campo que representa la id de la Pelicula en InterpretePeliculaCrossRef
                                ¡Recuerda! En este caso establecimos clase padre a Pelicula.

            entityColumn = Nombre del campo que repesenta la id del Interprete en InterpretePeliculaCrossRef


     Así los objetos de esta clase tendrán UNA película y una LISTA de intérpretes (o reparto).

     Obviamente, este proceso podrías hacerlo al revés:
        Tener un intérprete y TODAS las películas en las que aparece.
    */

    @Relation(
            parentColumn = "id",
            entityColumn = "id",
            associateBy = @Junction(
                                value = InterpretePeliculaCrossRef.class,
                                parentColumn = "pelicula_id",
                                entityColumn = "interprete_id"
                            )
    )
    private List<Interprete> reparto;

    public PeliculaConReparto() {}


    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public List<Interprete> getReparto(){
        return reparto;
    }

    public void setReparto(List<Interprete> reparto){
        this.reparto = reparto;
    }

}
