package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mad.models.DogCare;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class woofadmin_org_details extends AppCompatActivity {

    TextView txtDelClinic, txtDelConNo, txtDelAddress, txtDelCity, txtDelDescription, txtDelOwner;
    Button buttonDelete, btnUpdate;
    DatabaseReference dbRef;
    DogCare deleteClinic;
    private String org_id = " ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofadmin_org_details);

        org_id = getIntent().getStringExtra("id");

        txtDelClinic = findViewById(R.id.deleteClinic);
        txtDelConNo = findViewById(R.id.deleteConNo);
        txtDelAddress = findViewById(R.id.deleteAddress);
        txtDelCity = findViewById(R.id.deleteCity);
        txtDelDescription = findViewById(R.id.deleteDescription);
        txtDelOwner = findViewById(R.id.deleteOwner);

        buttonDelete = findViewById(R.id.woofadmin_org_delete);
        btnUpdate = findViewById(R.id.woofadmin_org_update);

        deleteClinic = new DogCare();

        getOrgDetails(org_id);

        //DELETE ORG

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteOrgdata();
            }
        });


    }

    private void deleteOrgdata() {

        DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("DogCare");
        delRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(org_id)){
                    dbRef = FirebaseDatabase.getInstance().getReference().child("DogCare").child(org_id);
                    dbRef.removeValue();

                    Toast.makeText(getApplicationContext(), "Data Deleted Successfully...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(woofadmin_org_details.this, woofadmin_organization_view.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
                else{
                    Toast.makeText(getApplicationContext(), "No source to Delete...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getOrgDetails(final String org_id) {

        DatabaseReference orgRef = FirebaseDatabase.getInstance().getReference().child("DogCare");

        orgRef.child(org_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    DogCare clinics = snapshot.getValue(DogCare.class);

                    txtDelClinic.setText(clinics.getClinicName());
                    txtDelAddress.setText(clinics.getAddress());
                    txtDelConNo.setText(clinics.getContactNo());
                    txtDelCity.setText(clinics.getCity());
                    txtDelDescription.setText(clinics.getDescription());
                    txtDelOwner.setText(clinics.getOwnerName());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToviewDetails();
            }

            private void navigateToviewDetails() {
                Intent intent = new Intent(woofadmin_org_details.this, woofadmin_org_update.class);
                intent.putExtra("id", org_id);
                startActivity(intent);
            }
        });


        //bottom navigation bar begins
        BottomNavigationView bottomNavigationView = findViewById(R.id.app_admin_bottom_navigationbar);
        //set selected
        bottomNavigationView.setSelectedItemId(R.id.bottomNaviBar_adminProfile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.bottomNaviBar_adminOrganizations:
                        startActivity(new Intent(getApplicationContext(), woofadmin_organization_view.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.bottomNaviBar_adminItems:
                        startActivity(new Intent(getApplicationContext(), woofadmin_addItem.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.bottomNaviBar_adminOrders:
                        startActivity(new Intent(getApplicationContext(), woofadmin_orders.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.bottomNaviBar_adminProfile:
                        startActivity(new Intent(getApplicationContext(), admin_account.class));
                        overridePendingTransition(0,0);
                        return true;

                }

                return false;
            }
        });
    }
}