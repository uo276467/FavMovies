package com.example.favmovies.datos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.favmovies.modelo.InterpretePeliculaCrossRef;
import com.example.favmovies.modelo.Pelicula;

import java.util.List;

@Dao
public interface RepartoDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void add(InterpretePeliculaCrossRef reparto);

    @Query("SELECT * FROM interprete_pelicula")
    List<InterpretePeliculaCrossRef> getAll();
}
