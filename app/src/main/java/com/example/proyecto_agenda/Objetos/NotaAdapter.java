//package com.example.proyecto_agenda.Objetos;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.proyecto_agenda.Detalle.Detalle_Nota;
//import com.example.proyecto_agenda.R;
//
//import java.util.List;
//
//public class NotaAdapter extends RecyclerView.Adapter<NotaAdapter.NotaViewHolder> {
//
//    private List<Nota> notas;
//    private Context context;
//
//    public NotaAdapter(Context context, List<Nota> notas) {
//        this.context = context;
//        this.notas = notas;
//    }
//
//    @NonNull
//    @Override
//    public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_nota, parent, false);
//        return new NotaViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull NotaViewHolder holder, int position) {
//        Nota nota = notas.get(position);
//        holder.bind(nota);
//    }
//
//    @Override
//    public int getItemCount() {
//        return notas.size();
//    }
//
//    public class NotaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        TextView tituloTextView, fechaTextView;
//
//        public NotaViewHolder(@NonNull View itemView) {
//            super(itemView);
//         //   tituloTextView = itemView.findViewById(R.id.tituloTextView); // Reemplaza con el ID correcto
//           // fechaTextView = itemView.findViewById(R.id.fechaTextView); // Reemplaza con el ID correcto
//            itemView.setOnClickListener(this);
//        }
//
//        public void bind(Nota nota) {
//            tituloTextView.setText(nota.getTitulo());
//            fechaTextView.setText(nota.getFecha_nota());
//
//            // Aquí puedes agregar más campos de la nota según sea necesario
//        }
//
//        @Override
//        public void onClick(View v) {
//            int position = getAdapterPosition();
//            if (position != RecyclerView.NO_POSITION) {
//                Nota nota = notas.get(position);
//                Intent intent = new Intent(context, Detalle_Nota.class);
//                intent.putExtra("id_nota", nota.getId_nota());
//                intent.putExtra("uid_usuario", nota.getUid_usuario());
//                intent.putExtra("correo_usuario", nota.getCorreo_usuario());
//                intent.putExtra("fecha_registro", nota.getFecha_hora_actual());
//                intent.putExtra("titulo", nota.getTitulo());
//                intent.putExtra("descripcion", nota.getDescripcion());
//                intent.putExtra("fecha_nota", nota.getFecha_nota());
//                intent.putExtra("estado", nota.getEstado());
//                context.startActivity(intent);
//            }
//        }
//    }
//}
