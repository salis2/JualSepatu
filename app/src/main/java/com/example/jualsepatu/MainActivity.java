package com.example.jualsepatu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jualsepatu.Adapter.HomeAdaptor;
import com.example.jualsepatu.Fragment.AddSepatuFragment;
import com.example.jualsepatu.Fragment.HomeFragment;
import com.example.jualsepatu.Fragment.ProfileFragment;
import com.example.jualsepatu.Model.Pembeli;
import com.example.jualsepatu.Model.Penjual;
import com.example.jualsepatu.Model.Sepatu;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, HomeAdaptor.dataListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private TextView tvEmail;

    private List<AuthUI.IdpConfig> providers;
    private static final int MY_REQUEST_CODE = 123;
    private DatabaseReference mReference;
    private List<Pembeli> daftarPembeliList;

    private List<Penjual> daftarPenjualList;

    private String emailCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //        hooks Drawer
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbarL);

        //        toolbar
        setSupportActionBar(toolbar);

        //        Navigation drawer
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_logOut).setVisible(false);

        menu.findItem(R.id.nav_add_sepatu).setVisible(false);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        menu.findItem(R.id.nav_add_sepatu).setVisible(true);


        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        View headerView = navigationView.getHeaderView(0);
        tvEmail = (TextView) headerView.findViewById(R.id.textViewEmail);

        showSignInOptions();


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment(emailCurrent)).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment(emailCurrent)).commit();
                break;
            case R.id.nav_profil:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;
            case R.id.nav_category:
                Toast belum_jadi = Toast.makeText(this, "Belum Jadi", Toast.LENGTH_LONG);
                belum_jadi.show();
                break;
            case R.id.nav_logOut:
                AuthUI.getInstance().signOut(MainActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Menu menu = navigationView.getMenu();
                        menu.findItem(R.id.nav_logOut).setVisible(false);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment(emailCurrent)).commit();
                        navigationView.setCheckedItem(R.id.nav_home);
                        FirebaseAuth.getInstance().signOut();
                        showSignInOptions();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
                break;
            case R.id.nav_add_sepatu:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddSepatuFragment()).commit();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).setTheme(R.style.MyTheme).build(), MY_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {

                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                tvEmail.setText(user.getEmail());

                emailCurrent = user.getEmail();

                final Menu menu = navigationView.getMenu();
                navigationView.setCheckedItem(R.id.nav_home);
                menu.findItem(R.id.nav_logOut).setVisible(true);

                Toast.makeText(this, "" + user.getEmail(), Toast.LENGTH_SHORT).show();

                mReference = FirebaseDatabase.getInstance().getReference();
                mReference.child("Pembeli").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        daftarPembeliList = new ArrayList<>();
                        for (DataSnapshot notDataSnapshot : dataSnapshot.getChildren()) {

                            Pembeli pembeli = notDataSnapshot.getValue(Pembeli.class);
                            daftarPembeliList.add(pembeli);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        System.out.println(databaseError.getDetails() + "" + databaseError.getMessage());
                    }
                });

                mReference.child("Penjual").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        daftarPenjualList = new ArrayList<>();
                        for (DataSnapshot notDataSnapshot : dataSnapshot.getChildren()) {

                            Penjual penjual = notDataSnapshot.getValue(Penjual.class);
                            daftarPenjualList.add(penjual);

                        }

                        for (int a = 0; a < daftarPenjualList.size(); a++) {
                            if (user.getUid().equals(daftarPenjualList.get(a).getID())) {
                                menu.findItem(R.id.nav_add_sepatu).setVisible(true);
                                break;
                            } else if (!user.getUid().equals(daftarPenjualList.get(a).getID())) {
                                menu.findItem(R.id.nav_add_sepatu).setVisible(false);
                                writeNewPembeli(user.getUid(), user.getDisplayName(), user.getEmail());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {


                        System.out.println(databaseError.getDetails() + "" + databaseError.getMessage());
                    }
                });
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment(emailCurrent)).commit();
                navigationView.setCheckedItem(R.id.nav_home);


            } else {
                Toast.makeText(this, "" + response.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void writeNewPembeli(String uid, String displayName, String email) {
        Pembeli pembeli = new Pembeli(uid, displayName, email);

        mReference.child("Pembeli").child(uid).setValue(pembeli);

    }

    public void onDeleteData(Sepatu sepatu) {

        if (mReference != null) {
            mReference.child("Sepatu").child(sepatu.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(MainActivity.this, "Data berhasil di hapus", Toast.LENGTH_LONG).show();

                }
            });
        }
    }

}