package com.example.jualsepatu;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.jualsepatu.Model.Sepatu;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UpdateSepatu extends AppCompatActivity {
    private EditText EnamaSepatu, EtypeSepatu, EphoneSepatu, EbrandSepatu;
    private RadioButton u42up, u43up, u44up, u45up;
    private Button bttnUpdate;
    private DatabaseReference databaseReference;

    private ArrayList<String> size = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sepatu);

        EnamaSepatu = (EditText) findViewById(R.id.ETnamaSepatuUpdate);
        EbrandSepatu = (EditText) findViewById(R.id.ETbrandSepatuUpdate);
        EtypeSepatu = (EditText) findViewById(R.id.ETtypeSepatuUpdate);
        EphoneSepatu = (EditText) findViewById(R.id.ETPhoneSepatuUpdate);
        u42up = (RadioButton) findViewById(R.id.Ukuran42Update);
        u43up = (RadioButton) findViewById(R.id.Ukuran43Update);
        u44up = (RadioButton) findViewById(R.id.Ukuran44Update);
        u45up = (RadioButton) findViewById(R.id.Ukuran45Update);

        u42up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (u42up.isSelected()) {
                    u42up.setSelected(false);
                    u42up.setChecked(false);
                } else {
                    u42up.setSelected(true);
                    u42up.setChecked(true);
                }

            }
        });

        u43up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (u43up.isSelected()) {
                    u43up.setSelected(false);
                    u43up.setChecked(false);
                } else {
                    u43up.setSelected(true);
                    u43up.setChecked(true);
                }

            }
        });

        u44up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (u44up.isSelected()) {
                    u44up.setSelected(false);
                    u44up.setChecked(false);
                } else {
                    u44up.setSelected(true);
                    u44up.setChecked(true);
                }

            }
        });

        u45up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (u45up.isSelected()) {
                    u45up.setSelected(false);
                    u45up.setChecked(false);
                } else {
                    u45up.setSelected(true);
                    u45up.setChecked(true);
                }

            }
        });


        getDataforUpdate();


    }

    public void onClickUp(View view) {
        Sepatu sepatu = new Sepatu();

        String key = getIntent().getExtras().getString("keyS");
        String id = getIntent().getExtras().getString("IDS");
        String name = EnamaSepatu.getText().toString();
        String brand = EbrandSepatu.getText().toString();
        String type = EtypeSepatu.getText().toString();
        String noTelphon = EphoneSepatu.getText().toString();
        String idPenjual = getIntent().getExtras().getString("idPenjualS");
        String namaPenjual = getIntent().getExtras().getString("namaPenjualS");
        String emailPenjual = getIntent().getExtras().getString("emailPenjualS");


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

        if (!u42up.isSelected() && !u43up.isSelected() && !u44up.isSelected() && !u45up.isSelected()) {
            Toast.makeText(getApplicationContext(), "Pilih Ukuran Sepatu", Toast.LENGTH_SHORT).show();
        }

        if (!name.equals("") && !brand.equals("") && !type.equals("") && !noTelphon.equals("")
                && u42up.isSelected() || u43up.isSelected() || u44up.isSelected() || u45up.isSelected()) {

            if (u42up.isSelected()) {
                size.add("42");
            }
            if (u43up.isSelected()) {
                size.add("43");
            }
            if (u44up.isSelected()) {
                size.add("44");
            }
            if (u45up.isSelected()) {
                size.add("45");
            }

            sepatu.setKey(key);
            sepatu.setID(id);
            sepatu.setName(name);
            sepatu.setBrand(brand);
            sepatu.setType(type);
            sepatu.setSize(size);
            sepatu.setNoTeleponPenjual(noTelphon);
            sepatu.setIDPenjual(idPenjual);
            sepatu.setNamaPenjual(namaPenjual);
            sepatu.setEmailPenjual(emailPenjual);

            updateData(sepatu);
            finish();
        }

    }

    private void updateData(Sepatu sepatu) {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        final String getKey = getIntent().getExtras().getString("keyS");

        databaseReference.child("Sepatu").child(getKey).setValue(sepatu).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                EnamaSepatu.setText("");
                EbrandSepatu.setText("");
                EtypeSepatu.setText("");
                EphoneSepatu.setText("");
                size.clear();

                u42up.setSelected(false);
                u42up.setChecked(false);
                u43up.setSelected(false);
                u43up.setChecked(false);
                u44up.setSelected(false);
                u44up.setChecked(false);
                u45up.setSelected(false);
                u45up.setChecked(false);

                Toast.makeText(UpdateSepatu.this, "Data Berhasil di Update", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getDataforUpdate() {
        final String getName = getIntent().getExtras().getString("namaS");
        final String getBrand = getIntent().getExtras().getString("brandS");
        final String getType = getIntent().getExtras().getString("typeS");
        final String getTelp = getIntent().getExtras().getString("noTelpS");
        final ArrayList<String> getUkuran = getIntent().getExtras().getStringArrayList("ukuranS");

        for (int i = 0; i < getUkuran.size(); i++) {

            switch (getUkuran.get(i)) {
                case "42":
                    u42up.setSelected(true);
                    u42up.setChecked(true);
                    break;
                case "43":
                    u43up.setSelected(true);
                    u43up.setChecked(true);
                    break;
                case "44":
                    u44up.setSelected(true);
                    u44up.setChecked(true);
                    break;
                case "45":
                    u45up.setSelected(true);
                    u45up.setChecked(true);
                    break;
            }
        }

        EnamaSepatu.setText(getName);
        EbrandSepatu.setText(getBrand);
        EtypeSepatu.setText(getType);
        EphoneSepatu.setText(getTelp);
        EphoneSepatu.setText(getTelp);

    }
}
