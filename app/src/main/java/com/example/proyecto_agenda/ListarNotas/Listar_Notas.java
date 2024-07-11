package com.example.proyecto_agenda.ListarNotas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_agenda.ActualizarNota.Actualizar_Nota;
import com.example.proyecto_agenda.Detalle.Detalle_Nota;
import com.example.proyecto_agenda.Objetos.Nota;
import com.example.proyecto_agenda.R;
import com.example.proyecto_agenda.ViewHolder.ViewHolder_Nota;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Listar_Notas extends AppCompatActivity {

    RecyclerView recyclerviewNotas;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference BASE_DE_DATOS;

    LinearLayoutManager linearLayoutManager;

    FirebaseRecyclerAdapter<Nota, ViewHolder_Nota> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Nota> options;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_notas);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Mis Notas");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        recyclerviewNotas = findViewById(R.id.recyclerviewNotas);
        recyclerviewNotas.setHasFixedSize(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        BASE_DE_DATOS = firebaseDatabase.getReference("Notas_Publicadas");
        dialog= new Dialog(Listar_Notas.this);
        ListarNotasUsuarios();
    }

    private void ListarNotasUsuarios(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference ROOT_REF = FirebaseDatabase.getInstance()
                .getReference()
                .child("Notas_Publicadas");

        Log.d("sharedNote", "Filtering notes: ");
        Log.d("sharedNote", "" + ROOT_REF.toString());

        BASE_DE_DATOS = BASE_DE_DATOS.child(user.getUid());

        Query query = ROOT_REF.orderByChild("uid_usuario")
                .equalTo(user.getUid());

        options = new FirebaseRecyclerOptions.Builder<Nota>().setQuery(query, Nota.class)
                .build();
        Log.d("sharedNote", "My notes: " + query.getRef().toString());


        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Nota, ViewHolder_Nota>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder_Nota viewHolder_nota, int i, @NonNull Nota nota) {
            viewHolder_nota.SetearDatos(
                    getApplicationContext(),
                    nota.getId_nota(),
                    nota.getUid_usuario(),
                    nota.getCorreo_usuario(),
                    nota.getFecha_hora_actual(),
                    nota.getTitulo(),
                    nota.getDescripcion(),
                    nota.getFecha_nota(),
                    nota.getEstado()
            );
            }

            @Override
            public ViewHolder_Nota onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nota,parent,
                        false);
                ViewHolder_Nota viewHolder_nota = new ViewHolder_Nota(view);
                viewHolder_nota.setOnClickListener(new ViewHolder_Nota.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //Obtener datos nota seleccionada
                        String id_nota = getItem(position).getId_nota();
                        String uid_usuario =getItem(position).getUid_usuario();
                        String correo_usuario = getItem(position). getCorreo_usuario();
                        String fecha_registro = getItem(position).getFecha_hora_actual();
                        String titulo = getItem(position).getTitulo();
                        String descripcion = getItem(position).getDescripcion();
                        String fecha_nota = getItem(position).getFecha_nota();
                        String estado = getItem(position).getEstado();

                        //Enviamos los datos a la siguiente actividad
                        Intent intent = new Intent(Listar_Notas.this, Detalle_Nota.class);
                        intent.putExtra("id_nota", id_nota);
                        intent.putExtra("uid_usuario", uid_usuario);
                        intent.putExtra("correo_usuario", correo_usuario);
                        intent.putExtra("fecha_registro", fecha_registro);
                        intent.putExtra("titulo", titulo);
                        intent.putExtra("descripcion", descripcion);
                        intent.putExtra("fecha_nota", fecha_nota);
                        intent.putExtra("estado", estado);
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                        //Obtener datos nota seleccionada
                        String id_nota = getItem(position).getId_nota();
                        String uid_usuario =getItem(position).getUid_usuario();
                        String correo_usuario = getItem(position). getCorreo_usuario();
                        String fecha_registro = getItem(position).getFecha_hora_actual();
                        String titulo = getItem(position).getTitulo();
                        String descripcion = getItem(position).getDescripcion();
                        String fecha_nota = getItem(position).getFecha_nota();
                        String estado = getItem(position).getEstado();


                    //Declarar vistas
                        Button CD_Eliminar, CD_Actualizar;

                        //Conoexion con el diseño
                        dialog.setContentView(R.layout.dialogo_opciones);
                        //inicair las vistas
                        CD_Eliminar= dialog.findViewById(R.id.CD_Eliminar);
                        CD_Actualizar = dialog.findViewById(R.id.CD_Actualizar);

                    CD_Eliminar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        EliminarNota(id_nota);
                        dialog.dismiss();
                        }
                    });

                    CD_Actualizar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(Listar_Notas.this, "Actualizar Nota", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(Listar_Notas.this, Actualizar_Nota.class));
                           Intent intent = new Intent(Listar_Notas.this, Actualizar_Nota.class);
                           intent.putExtra("id_nota", id_nota);
                           intent.putExtra("uid_usuario", uid_usuario);
                           intent.putExtra("correo_usuario", correo_usuario);
                           intent.putExtra("fecha_registro", fecha_registro);
                           intent.putExtra("titulo", titulo);
                           intent.putExtra("descripcion", descripcion);
                           intent.putExtra("fecha_nota", fecha_nota);
                           intent.putExtra("estado", estado);
                           startActivity(intent);
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                    }
                });
                return viewHolder_nota;
            }
        };
        linearLayoutManager = new LinearLayoutManager(Listar_Notas.this, LinearLayoutManager.VERTICAL,false);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerviewNotas.setLayoutManager(linearLayoutManager);
        recyclerviewNotas.setAdapter(firebaseRecyclerAdapter);
    }

    private void EliminarNota(String id_Nota) {

        AlertDialog.Builder builder = new AlertDialog.Builder(Listar_Notas.this);
        builder.setTitle("Eliminar Nota");
        builder.setMessage("¿Desea eliminar la nota?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Eliminar Nota en BD
                Query query = BASE_DE_DATOS.orderByChild("id_nota").equalTo(id_Nota);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(Listar_Notas.this, "Nota Eliminada", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Listar_Notas.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(Listar_Notas.this, "Cancelado por el usuario", Toast.LENGTH_SHORT).show();
            }
        });

        builder.create().show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseRecyclerAdapter!=null){
            firebaseRecyclerAdapter.startListening();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}