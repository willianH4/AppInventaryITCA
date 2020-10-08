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

import java.util.List;

public class AdaptadorCategorias extends RecyclerView.Adapter<AdaptadorCategorias.CategoriasViewHolder>{

 //Propiedad de la clase AdaptadorArticulos.
 private Context mCtx;
 private List<Categorias2> categoriaList;

//Añadimos un contructor a partir de las propiedades definidas en esta clase
//(AdaptorArticulos).
    public AdaptadorCategorias(Context mCtx, List<Categorias2> categoriaList) {
        this.mCtx = mCtx;
        this.categoriaList = categoriaList;
    }

    /***********************************************************************************************/
/*ReccylerView.Adapter tiene tres métodos abstractos a los que debemos aplicar el
modificador override.
/***********************************************************************************************/
/*
El siguiente método es llamado cuando el ViewHolder necesita ser inicializado.
Especificamos el layout que cada elemento de RecyclerView debería usar.
Ésto se hace al inflar el layout usando LayoutInflater, pasando el resultado
al constructor del ViewHolder
*/



    /***********************************************************************************************/
/*ReccylerView.Adapter tiene tres métodos abstractos a los que debemos aplicar el
modificador override.
/***********************************************************************************************/
/*
El siguiente método es llamado cuando el ViewHolder necesita ser inicializado.
Especificamos el layout que cada elemento de RecyclerView debería usar.
Ésto se hace al inflar el layout usando LayoutInflater, pasando el resultado
al constructor del ViewHolder
*/

    @Override
    public CategoriasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.categorias_recyclerview, null);
        return new CategoriasViewHolder(view);
    }

    /*
        En el mètodo onBindViewHolder se especifica el contenido de cada elemento del
        RecyclerView.
        Éste método es muy similar al método getView de un adaptador de ListView.
        En nuestro ejemplo, aquí es donde se tiene que establecer los valores de los campos de
        codigo, descripcion y precio del CardView.
        */

    @Override
    public void onBindViewHolder(CategoriasViewHolder holder, int position) {
    //Toast.makeText(mCtx, "Se encontraron: "+getItemCount()+ " Registros.",
   //     Toast.LENGTH_SHORT).show();
        Categorias2 dto = categoriaList.get(position);
        holder.textViewId.setText(String.valueOf(dto.getId_categoria()));
        holder.textViewNombre.setText(dto.getNombre());
        holder.textViewEstado.setText(String.valueOf(dto.getEstado()));
        holder.textViewOtro.setText(String.valueOf("Registro #:" + (position+1)) + "/" +
        getItemCount());
    }

    /*
    Éste mètodo regresara el número de elementos presentes en los datos. Como nuestro
    datos están en forma de una List,
    sólo necesitamos llamar al método size en el objeto List:
    */

    @Override
    public int getItemCount() {
        return categoriaList.size();
    }
 //Dentro de esta clase se define el mapeo o referencia de cada control
//en la vista (Layout o xml) Personalizado. Este es el diseño personalizado para pintar
//    cada registro en el recyclerview.

    public static class CategoriasViewHolder extends RecyclerView.ViewHolder {
        TextView textViewId, textViewNombre, textViewEstado, textViewOtro;
        //ImageView imageView;

        //Dentro del constructor de nuestro ViewHolder, inicializa las views (vistas)
        //que pertenezcan a los elementos de nuestro RecyclerView.

        public CategoriasViewHolder(View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(R.id.tvId);
            textViewNombre = itemView.findViewById(R.id.tvNombre);
            textViewEstado= itemView.findViewById(R.id.tvEstado);
            textViewOtro = itemView.findViewById(R.id.textViewOtro);
            //imageView = itemView.findViewById(R.id.imageView);
        }
    }

    /*
    Finalmente, necesitamos hacer el override en el método onAttachedToRecyclerView.
    Por ahora, simplemente podemos utilizar la implementación de la superclase de éste
    método
    */

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

