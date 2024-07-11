package com.example.proyecto_agenda;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_agenda.AgregarNota.Agregar_Nota;
import com.example.proyecto_agenda.ListarNotas.Listar_Notas;
import com.example.proyecto_agenda.NotasImportantes.Notas_Importantes;
import com.example.proyecto_agenda.Perfil.Perfil_Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuPrincipal extends AppCompatActivity {

    Button AgregarNotas, ListarNotas, Importantes, Perfil, AcercaDe, CerrarSesion;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    TextView UidPrincipal, nombresPrincipal, correoPrincipal;
    ProgressBar progressBarDatos;

    DatabaseReference Usuarios;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Proyecto Agenda");

        UidPrincipal = findViewById(R.id.UidPrincipal);
        nombresPrincipal = findViewById(R.id.nombresPrincipal);
        correoPrincipal = findViewById(R.id.correoPrincipal);
        progressBarDatos = findViewById(R.id.progressBarDatos);

        Usuarios = FirebaseDatabase.getInstance().getReference("Usuarios");

        AgregarNotas = findViewById(R.id.AgregarNotas);
        ListarNotas = findViewById(R.id.ListarNotas);
        Importantes = findViewById(R.id.Importantes);
        Perfil = findViewById(R.id.Perfil);
        AcercaDe = findViewById(R.id.AcercaDe);
        CerrarSesion = findViewById(R.id.CerrarSesion);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        sharedPreferences = getSharedPreferences("agenda_prefs", MODE_PRIVATE);

        AgregarNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid_usuario = UidPrincipal.getText().toString();
                String correo_usuario = correoPrincipal.getText().toString();
                Intent intent = new Intent(MenuPrincipal.this, Agregar_Nota.class);
                intent.putExtra("Uid", uid_usuario);
                intent.putExtra("Correo", correo_usuario);
                startActivity(intent);
            }
        });

        ListarNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuPrincipal.this, Listar_Notas.class));
                Toast.makeText(MenuPrincipal.this, "Listar Notas", Toast.LENGTH_SHORT).show();
            }
        });

        Importantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuPrincipal.this, Notas_Importantes.class));
                Toast.makeText(MenuPrincipal.this, "Notas Archivadas", Toast.LENGTH_SHORT).show();
            }
        });

        Perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuPrincipal.this, Perfil_Usuario.class));
                Toast.makeText(MenuPrincipal.this, "Notas compartidas", Toast.LENGTH_SHORT).show();
            }
        });

        AcercaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MenuPrincipal.this, "Acerca De", Toast.LENGTH_SHORT).show();
            }
        });

        CerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalirAplicacion();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        comprobarInicioSesion();
    }

    private void comprobarInicioSesion() {
        if (user != null) {
            cargaDeDatosOffline();
            cargaDeDatosFirebase();
        } else {
            startActivity(new Intent(MenuPrincipal.this, MainActivity.class));
            finish();
        }
    }

    private void cargaDeDatosOffline() {
        String uid = sharedPreferences.getString("uid", "");
        String nombres = sharedPreferences.getString("nombres", "");
        String correo = sharedPreferences.getString("correo", "");

        if (!uid.isEmpty() && !nombres.isEmpty() && !correo.isEmpty()) {
            progressBarDatos.setVisibility(View.GONE);
            nombresPrincipal.setVisibility(View.VISIBLE);

            UidPrincipal.setText(uid);
            nombresPrincipal.setText(nombres);
            correoPrincipal.setText(correo);

            habilitarBotones();
        }
    }

    private void cargaDeDatosFirebase() {
        Usuarios.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String uid = snapshot.child("uid").getValue(String.class);
                    String nombres = snapshot.child("nombres").getValue(String.class);
                    String correo = snapshot.child("correo").getValue(String.class);

                    if (uid != null && nombres != null && correo != null) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("uid", uid);
                        editor.putString("nombres", nombres);
                        editor.putString("correo", correo);
                        editor.apply();

                        progressBarDatos.setVisibility(View.GONE);
                        nombresPrincipal.setVisibility(View.VISIBLE);

                        UidPrincipal.setText(uid);
                        nombresPrincipal.setText(nombres);
                        correoPrincipal.setText(correo);

                        habilitarBotones();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MenuPrincipal.this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void habilitarBotones() {
        AgregarNotas.setEnabled(true);
        ListarNotas.setEnabled(true);
        Importantes.setEnabled(true);
        Perfil.setEnabled(true);
        AcercaDe.setEnabled(true);
        CerrarSesion.setEnabled(true);
    }

    private void SalirAplicacion() {
        firebaseAuth.signOut();
        startActivity(new Intent(MenuPrincipal.this, MainActivity.class));
        Toast.makeText(this, "Se cerró la sesión", Toast.LENGTH_SHORT).show();
    }
}
