package com.example.proyecto_agenda.ListarNotas;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.proyecto_agenda.Objetos.Nota;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesHelper {

    private static final String PREF_NAME = "NotasPref";
    private static final String KEY_NOTAS = "Notas";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
    }

    public void saveNotas(List<Nota> notas) {
        String json = gson.toJson(notas);
        editor.putString(KEY_NOTAS, json);
        editor.apply();
    }

    public List<Nota> getNotas() {
        String json = sharedPreferences.getString(KEY_NOTAS, null);
        Type type = new TypeToken<ArrayList<Nota>>() {}.getType();
        return json != null ? gson.fromJson(json, type) : new ArrayList<>();
    }

    public void clearNotas() {
        editor.remove(KEY_NOTAS);
        editor.apply();
    }
}
