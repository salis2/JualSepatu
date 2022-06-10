package com.example.jualsepatu.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.jualsepatu.Adapter.HomeAdaptor;
import com.example.jualsepatu.MainActivity;
import com.example.jualsepatu.Model.Sepatu;
import com.example.jualsepatu.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private View HomeView;
    private DatabaseReference mDatabase;

    private RecyclerView.Adapter adapter;
    private RecyclerView rvView;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Sepatu> daftarSepatu;
    private String email;

    public HomeFragment(String email) {
        this.email = email;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        rvView = (RecyclerView) rootView.findViewById(R.id.sepatu_list);
        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rvView.setLayoutManager(layoutManager);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Sepatu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                daftarSepatu = new ArrayList<>();
                for (DataSnapshot notDataSnapshot : dataSnapshot.getChildren()) {

                    Sepatu sepatu = notDataSnapshot.getValue(Sepatu.class);
                    sepatu.setKey(notDataSnapshot.getKey());

                    daftarSepatu.add(sepatu);
                }

                adapter = new HomeAdaptor(daftarSepatu, getContext(), email);
                rvView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


                System.out.println(databaseError.getDetails() + "" + databaseError.getMessage());
            }
        });
        return rootView;
    }


}
