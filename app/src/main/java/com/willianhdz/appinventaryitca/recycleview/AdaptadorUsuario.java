package com.willianhdz.appinventaryitca.recycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.willianhdz.appinventaryitca.R;
import com.willianhdz.appinventaryitca.data.Dtos.Categorias2;
import com.willianhdz.appinventaryitca.data.Dtos.usuario;

import java.util.List;

public class AdaptadorUsuario extends RecyclerView.Adapter<AdaptadorUsuario.UsuarioViewHolder>{

    private Context mCtx;
    private List<usuario> usuarioList;
    private Object View;

    public AdaptadorUsuario(Context mCtx, List<usuario> usuarioList){
        this.mCtx = mCtx;
        this.usuarioList = usuarioList;
    }



    @Override
    public UsuarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recycleview_usuario, null);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UsuarioViewHolder holder, int position) {
        usuario usu = usuarioList.get(position);
        holder.textViewId1.setText(String.valueOf(usu.getId_usu()));
        holder.textViewnom1.setText(usu.getNombre());
        holder.textViewape1.setText(usu.getApellido());
        holder.textViewcorreo1.setText(usu.getCorreo());
        holder.textViewusuario1.setText(usu.getUsuario());
        holder.textViewclave1.setText(usu.getClave());
        holder.textViewtipo1.setText(String.valueOf(usu.getTipo()));
        holder.textViewestado1.setText(String.valueOf(usu.getEstado_us()));
        holder.textViewpregunta1.setText(usu.getPregunta());
        holder.textViewrespuesta1.setText(usu.getRespuesta());


        holder.textViewOtro.setText(String.valueOf("Registro #:" + (position + 1)) + "/" +
                getItemCount());
    }

    @Override
    public int getItemCount() {
        return usuarioList.size();
    }

    public class UsuarioViewHolder extends RecyclerView.ViewHolder {
        TextView textViewId1, textViewnom1, textViewape1, textViewcorreo1,textViewusuario1,textViewclave1,textViewestado1,textViewtipo1,textViewpregunta1,textViewrespuesta1, textViewOtro;
        public UsuarioViewHolder(View itemView) {
            super(itemView);
            textViewId1 = itemView.findViewById(R.id.textViewid1);
            textViewnom1= itemView.findViewById(R.id.textViewnom1);
            textViewape1 = itemView.findViewById(R.id.textViewape1);
            textViewcorreo1= itemView.findViewById(R.id.textViewcorreo1);
            textViewusuario1= itemView.findViewById(R.id.textViewusuario1);
            textViewclave1= itemView.findViewById(R.id.textViewclave1);
            textViewtipo1= itemView.findViewById(R.id.textViewtipo1);
            textViewestado1= itemView.findViewById(R.id.textViewestado1);
            textViewpregunta1= itemView.findViewById(R.id.textViewpregunt1);
            textViewrespuesta1= itemView.findViewById(R.id.textViewrespuest1);
            textViewOtro = itemView.findViewById(R.id.textViewOtro);

        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
