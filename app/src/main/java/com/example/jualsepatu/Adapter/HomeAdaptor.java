package com.example.jualsepatu.Adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jualsepatu.Fragment.HomeFragment;
import com.example.jualsepatu.MainActivity;
import com.example.jualsepatu.Model.Sepatu;
import com.example.jualsepatu.R;
import com.example.jualsepatu.UpdateSepatu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class HomeAdaptor extends RecyclerView.Adapter<HomeAdaptor.ViewHolder> {
    private ArrayList<Sepatu> daftarSepatus;
    private Context context;
    private dataListener listener;
    private String email;

    public HomeAdaptor(ArrayList<Sepatu> dafSepatus, Context ctx, String email) {
        this.daftarSepatus = dafSepatus;
        this.context = ctx;
        this.listener = (MainActivity) ctx;
        this.email = email;
    }

    public interface dataListener {
        void onDeleteData(Sepatu sepatu);
    }


    @NonNull
    @Override
    public HomeAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sepatu, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeAdaptor.ViewHolder holder, final int position) {
        final String emailPenjualSepatu = daftarSepatus.get(position).getEmailPenjual();
        String namaSepatu = daftarSepatus.get(position).getName();
        String brandSepatu = daftarSepatus.get(position).getBrand();

        holder.emailPenjual.setText(emailPenjualSepatu);
        holder.titeSepatu.setText(namaSepatu);
        holder.bodySepatu.setText(brandSepatu);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        holder.itemSepatu.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                AlertDialog.Builder aleBuilder = new AlertDialog.Builder(v.getContext());

                final String[] items = {"Update", "Delete"};

                aleBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Bundle bundle = new Bundle();
                                bundle.putString("keyS", daftarSepatus.get(position).getKey());
                                bundle.putString("IDS", daftarSepatus.get(position).getID());
                                bundle.putString("namaS", daftarSepatus.get(position).getName());
                                bundle.putString("brandS", daftarSepatus.get(position).getBrand());
                                bundle.putString("typeS", daftarSepatus.get(position).getType());
                                bundle.putString("noTelpS", daftarSepatus.get(position).getNoTeleponPenjual());
                                bundle.putStringArrayList("ukuranS", daftarSepatus.get(position).getSize());
                                bundle.putString("namaPenjualS", daftarSepatus.get(position).getNamaPenjual());
                                bundle.putString("idPenjualS", daftarSepatus.get(position).getIDPenjual());
                                bundle.putString("emailPenjualS", daftarSepatus.get(position).getEmailPenjual());

                                Intent intent = new Intent(v.getContext(), UpdateSepatu.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);

                                Toast.makeText(v.getContext(), "Update", Toast.LENGTH_SHORT).show();

                                break;
                            case 1:
                                Toast.makeText(v.getContext(), "Delete", Toast.LENGTH_SHORT).show();

                                listener.onDeleteData(daftarSepatus.get(position));
                                break;

                        }
                    }
                });

                if (email.equals(emailPenjualSepatu)) {
                    aleBuilder.create();
                    aleBuilder.show();
                }

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return daftarSepatus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView emailPenjual, titeSepatu, bodySepatu;
        CardView itemSepatu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            emailPenjual = (TextView) itemView.findViewById(R.id.Email_sepatu_penjual);
            titeSepatu = (TextView) itemView.findViewById(R.id.sepatu_title);
            bodySepatu = (TextView) itemView.findViewById(R.id.sepatu_body);
            itemSepatu = (CardView) itemView.findViewById(R.id.item_Sepatu_Card);

        }
    }
}
