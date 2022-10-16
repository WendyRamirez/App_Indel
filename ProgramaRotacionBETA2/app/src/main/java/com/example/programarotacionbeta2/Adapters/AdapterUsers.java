package com.example.programarotacionbeta2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programarotacionbeta2.R;

import java.util.ArrayList;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.ViewHolder> {

    ArrayList<DatosUsuarios> UserList;
    Context context;


    public AdapterUsers(ArrayList<DatosUsuarios> UserList, Context context) {

        this.UserList = UserList;

        this.context = context;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tabla_datos, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Nombre.setText(UserList.get(position).getUsername());
        holder.Id.setText(UserList.get(position).getId_usuario()+"");

    }



    @Override
    public int getItemCount() {
        return UserList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

       TextView Nombre,Id;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Nombre = itemView.findViewById(R.id.TVnombreusuario);
            Id = itemView.findViewById(R.id.TVidusuario);


        }
    }



}
