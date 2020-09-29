package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mad.models.DogCare;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class woofcare_clinc_details extends AppCompatActivity {

    TextView txtClinic, txtConNo, txtAddress, txtCity, txtDescription, txtOwner;
    DatabaseReference dbRef;
    DogCare careClinic;
    private String org_id = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofcare_clinc_details);

        org_id = getIntent().getStringExtra("id");

        txtClinic = findViewById(R.id.careClinic);
        txtConNo = findViewById(R.id.careConNo);
        txtAddress = findViewById(R.id.careAddress);
        txtCity = findViewById(R.id.careCity);
        txtDescription = findViewById(R.id.careDescription);
        txtOwner = findViewById(R.id.careowner);

        careClinic = new DogCare();

        getOrgDetails(org_id);

        //bottom navigation bar begins
                BottomNavigationView bottomNavigationView = findViewById(R.id.app_bottom_navigationbar);
                //set selected
                bottomNavigationView.setSelectedItemId(R.id.bottomNaviBar_woofCare);

                bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch(item.getItemId())
                        {
                            case R.id.bottomNaviBar_woofCorner:
                                startActivity(new Intent(getApplicationContext(), woofcorner_show_ads.class));
                                overridePendingTransition(0,0);
                                return true;

                            case R.id.bottomNaviBar_woofCare:
                                return true;

                            case R.id.bottomNaviBar_woofShop:
                                startActivity(new Intent(getApplicationContext(), woofshop_show_products.class));
                                overridePendingTransition(0,0);
                                return true;

                            case R.id.bottomNaviBar_woofProfile:
                                startActivity(new Intent(getApplicationContext(), app_woofprofile_menu.class));
                                overridePendingTransition(0,0);
                                return true;

                        }

                        return false;
                    }
                });
                //bottom navigation bar ends


    }

    private void getOrgDetails(String org_id) {
        DatabaseReference orgRef = FirebaseDatabase.getInstance().getReference().child("DogCare");

        orgRef.child(org_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    DogCare clinics = snapshot.getValue(DogCare.class);

                    txtClinic.setText(clinics.getClinicName());
                    txtAddress.setText(clinics.getAddress());
                    txtConNo.setText(clinics.getContactNo());
                    txtCity.setText(clinics.getCity());
                    txtDescription.setText(clinics.getDescription());
                    txtOwner.setText(clinics.getOwnerName());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}