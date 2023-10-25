package com.example.favmovies.datos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.favmovies.modelo.Interprete;

import java.util.List;

@Dao
public interface InterpreteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void add(Interprete interprete);

    @Query("SELECT * FROM interpretes")
    List<Interprete> getAll();


    @Query("SELECT * FROM interpretes WHERE id = (:interpreteId)")
    Interprete findById(int interpreteId);
}
