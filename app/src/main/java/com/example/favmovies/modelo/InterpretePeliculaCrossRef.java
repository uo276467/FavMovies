package com.example.favmovies.modelo;


import androidx.room.ColumnInfo;
import androidx.room.Entity;

/*
    Aquí se establece la relación entre un Intérprete y una película
    Fíjate en cómo declaro las claves primarias.

 */
@Entity(tableName = "interprete_pelicula", primaryKeys = {"interprete_id", "pelicula_id"})
public class InterpretePeliculaCrossRef {

    @ColumnInfo(name = "pelicula_id")
    private int peliculaId;

    @ColumnInfo(name = "interprete_id")
    private int interpreteId;

    public InterpretePeliculaCrossRef() { }

    public InterpretePeliculaCrossRef(int interpreteId, int peliculaId)
    {
        this.peliculaId = peliculaId;
        this.interpreteId = interpreteId;
    }


    public int getPeliculaId() {
        return peliculaId;
    }

    public void setPeliculaId(int peliculaId) {
        this.peliculaId = peliculaId;
    }

    public int getInterpreteId() {
        return interpreteId;
    }

    public void setInterpreteId(int interpreteId) {
        this.interpreteId = interpreteId;
    }

    @Override
    public String toString() {
        return "InterpretePeliculaCrossRef{" +
                "peliculaId=" + peliculaId +
                ", interpreteId=" + interpreteId +
                '}';
    }
}