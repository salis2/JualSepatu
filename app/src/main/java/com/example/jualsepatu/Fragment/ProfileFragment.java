package com.example.jualsepatu.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.example.jualsepatu.Model.Pembeli;
import com.example.jualsepatu.Model.Penjual;
import com.example.jualsepatu.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private View ProfileView;
    private TextView myName, myEmail;
    private Button buttonJadiPenjual;

    private DatabaseReference mReference;
    private List<Pembeli> daftarPembeliList;
    private List<Penjual> daftarPenjualList;

    public ProfileFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ProfileView = inflater.inflate(R.layout.fragment_profil, container, false);

        myName = (TextView) ProfileView.findViewById(R.id.namaProfil);
        myEmail = (TextView) ProfileView.findViewById(R.id.emailProfil);
        buttonJadiPenjual = (Button) ProfileView.findViewById(R.id.bttnJdPenjual);


        buttonJadiPenjual.setEnabled(false);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        myName.setText(user.getDisplayName());
        myEmail.setText(user.getEmail());

        mReference = FirebaseDatabase.getInstance().getReference();
        mReference.child("Pembeli").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                daftarPembeliList = new ArrayList<Pembeli>();

                for (DataSnapshot notSnapshot : dataSnapshot.getChildren()) {
                    Pembeli pembeli = notSnapshot.getValue(Pembeli.class);
                    daftarPembeliList.add(pembeli);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mReference.child("Penjual").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                daftarPenjualList = new ArrayList<Penjual>();

                for (DataSnapshot notSnapshot : dataSnapshot.getChildren()) {
                    Penjual penjual = notSnapshot.getValue(Penjual.class);
                    daftarPenjualList.add(penjual);

                }


                for (int a = 0; a < daftarPenjualList.size(); a++) {
                    if (!user.getUid().equals(daftarPenjualList.get(a).getID())) {
                        buttonJadiPenjual.setEnabled(true);
                    } else if (user.getUid().equals(daftarPenjualList.get(a).getID())) {
                        buttonJadiPenjual.setEnabled(false);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        buttonJadiPenjual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (daftarPembeliList.size() > 1) {
                    writeNewPenjual(user.getUid(), user.getDisplayName(), user.getEmail());
                } else {
                    Toast.makeText(getContext(), "Maaf Kami butuh pembeli", Toast.LENGTH_LONG).show();
                }

            }
        });
        return ProfileView;
    }

    private void writeNewPenjual(String uid, String displayName, String email) {
        Pembeli pembeli = new Pembeli(uid, displayName, email);

        mReference.child("Penjual").child(uid).setValue(pembeli);

        Toast.makeText(getContext(), "Anda berhasil Menjadi Penjual", Toast.LENGTH_LONG).show();

    }
}
