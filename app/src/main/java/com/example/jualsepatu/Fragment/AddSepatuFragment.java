package com.example.jualsepatu.Fragment;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jualsepatu.Model.Sepatu;
import com.example.jualsepatu.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class AddSepatuFragment extends Fragment {
    private DatabaseReference mReference;

    private View addSepatuView;
    private Button bttnAddSepatu;
    private EditText EnamaSepatu, EtypeSepatu, EphoneSepatu, EbrandSepatu;
    private RadioButton u42, u43, u44, u45;

    private ArrayList<String> size = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        addSepatuView = inflater.inflate(R.layout.fragment_add_sepatu, container, false);
        bttnAddSepatu = (Button) addSepatuView.findViewById(R.id.bttnInputSepatu);
        EnamaSepatu = (EditText) addSepatuView.findViewById(R.id.ETnamaSepatu);
        EbrandSepatu = (EditText) addSepatuView.findViewById(R.id.ETbrandSepatu);
        EtypeSepatu = (EditText) addSepatuView.findViewById(R.id.ETtypeSepatu);
        EphoneSepatu = (EditText) addSepatuView.findViewById(R.id.ETPhoneSepatu);
        u42 = (RadioButton) addSepatuView.findViewById(R.id.Ukuran42);
        u43 = (RadioButton) addSepatuView.findViewById(R.id.Ukuran43);
        u44 = (RadioButton) addSepatuView.findViewById(R.id.Ukuran44);
        u45 = (RadioButton) addSepatuView.findViewById(R.id.Ukuran45);

        u42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (u42.isSelected()) {
                    u42.setSelected(false);
                    u42.setChecked(false);
                } else {
                    u42.setSelected(true);
                    u42.setChecked(true);
                }

            }
        });

        u43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (u43.isSelected()) {
                    u43.setSelected(false);
                    u43.setChecked(false);
                } else {
                    u43.setSelected(true);
                    u43.setChecked(true);
                }

            }
        });

        u44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (u44.isSelected()) {
                    u44.setSelected(false);
                    u44.setChecked(false);
                } else {
                    u44.setSelected(true);
                    u44.setChecked(true);
                }

            }
        });

        u45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (u45.isSelected()) {
                    u45.setSelected(false);
                    u45.setChecked(false);
                } else {
                    u45.setSelected(true);
                    u45.setChecked(true);
                }

            }
        });

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mReference = FirebaseDatabase.getInstance().getReference();


        bttnAddSepatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random random = new Random();
                String id = String.valueOf(random.nextInt(10000));
                String name = EnamaSepatu.getText().toString();
                String brand = EbrandSepatu.getText().toString();
                String type = EtypeSepatu.getText().toString();
                String idPenjual = user.getUid();
                String namaPenjual = user.getDisplayName();
                String emailPenjual = user.getEmail();
                String noTelphon = EphoneSepatu.getText().toString();

                if (name.equals("") && brand.equals("") && type.equals("") && noTelphon.equals("")) {
                    EnamaSepatu.setError("Isi Nama Sepatu !");
                    EbrandSepatu.setError("Isi Brand Sepatu !");
                    EtypeSepatu.setError("Isi Type Sepatu !");
                    EphoneSepatu.setError("Isi No Telphone !");
                } else if (name.equals("")) {
                    EnamaSepatu.setError("Isi Nama Sepatu !");
                } else if (brand.equals("")) {
                    EbrandSepatu.setError("Isi Brand Sepatu !");
                } else if (type.equals("")) {
                    EtypeSepatu.setError("Isi Type Sepatu !");
                } else if (noTelphon.equals("")) {
                    EphoneSepatu.setError("Isi No Telphone !");
                }

                if (!u42.isSelected() && !u43.isSelected() && !u44.isSelected() && !u45.isSelected()) {
                    Toast.makeText(getContext(), "Pilih Ukuran Sepatu", Toast.LENGTH_SHORT).show();
                }

                if (!name.equals("") && !brand.equals("") && !type.equals("") && !noTelphon.equals("")
                        && u42.isSelected() || u43.isSelected() || u44.isSelected() || u45.isSelected()) {

                    if (u42.isSelected()) {
                        size.add("42");
                    }
                    if (u43.isSelected()) {
                        size.add("43");
                    }
                    if (u44.isSelected()) {
                        size.add("44");
                    }
                    if (u45.isSelected()) {
                        size.add("45");
                    }


                    insertSepatu(new Sepatu(id, name, brand, type, size, idPenjual, namaPenjual, emailPenjual, noTelphon));

                    EnamaSepatu.setText("");
                    EbrandSepatu.setText("");
                    EtypeSepatu.setText("");
                    EphoneSepatu.setText("");
                    size.clear();

                    u42.setSelected(false);
                    u42.setChecked(false);
                    u43.setSelected(false);
                    u43.setChecked(false);
                    u44.setSelected(false);
                    u44.setChecked(false);
                    u45.setSelected(false);
                    u45.setChecked(false);

                }
            }
        });

        return addSepatuView;
    }

    private void insertSepatu(Sepatu sepatu) {
        mReference.child("Sepatu").push().setValue(sepatu).addOnSuccessListener((Activity) getContext(), new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(), "Data Berhasil ditambah", Toast.LENGTH_LONG).show();
            }
        });
    }
}
