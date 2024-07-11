package com.example.proyecto_agenda.BaseDeDatos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserEntiti user);

    @Query("SELECT * FROM users WHERE uid = :uid")
    UserEntiti getUser(String uid);

    // Otros métodos de consulta y actualización según sea necesario
}
