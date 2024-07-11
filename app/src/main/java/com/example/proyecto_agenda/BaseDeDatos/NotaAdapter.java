//package com.example.proyecto_agenda.BaseDeDatos;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.proyecto_agenda.Objetos.Nota;
//import com.example.proyecto_agenda.R;
//
//import java.util.List;
//
//public class NotaAdapter extends RecyclerView.Adapter<NotaAdapter.NotaViewHolder> {
//
//    private List<Nota> notas;
//    private OnItemClickListener listener;
//
//    public interface OnItemClickListener {
//        void onItemClick(Nota nota);
//    }
//
//    public NotaAdapter(List<Nota> notas, OnItemClickListener listener) {
//        this.notas = notas;
//        this.listener = listener;
//    }
//
//    @NonNull
//    @Override
//    public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nota, parent, false);
//        return new NotaViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull NotaViewHolder holder, int position) {
//        Nota nota = notas.get(position);
//        holder.bind(nota, listener);
//    }
//
//    @Override
//    public int getItemCount() {
//        return notas.size();
//    }
//
//    public static class NotaViewHolder extends RecyclerView.ViewHolder {
//        TextView titulo, descripcion, fecha;
//
//        public NotaViewHolder(@NonNull View itemView) {
//            super(itemView);
//            titulo = itemView.findViewById(R.id.tituloNota);
//            descripcion = itemView.findViewById(R.id.descripcionNota);
//            fecha = itemView.findViewById(R.id.fechaNota);
//        }
//
//        public void bind(final Nota nota, final OnItemClickListener listener) {
//            titulo.setText(nota.getTitulo());
//            descripcion.setText(nota.getDescripcion());
//            fecha.setText(nota.getFecha_nota());
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listener.onItemClick(nota);
//                }
//            });
//        }
//    }
//}
