package com.example.mad;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class woofadmin_org_update extends AppCompatActivity {

    EditText txtClinic, txtConNo, txtAddress, txtCity, txtDescription, txtOwner;
    Button btnUpdate;
    DatabaseReference upDbRef;
    DogCare updateClinic;

    //Method to clear all user inputs
    private void clearControls(){
        txtClinic.setText("");
        txtConNo.setText("");
        txtAddress.setText("");
        txtCity.setText("");
        txtDescription.setText("");
        txtOwner.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofadmin_org_update);

        txtClinic = findViewById(R.id.updateClinic);
        txtConNo = findViewById(R.id.updateConNo);
        txtAddress = findViewById(R.id.updateAddress);
        txtCity = findViewById(R.id.updateCity);
        txtDescription = findViewById(R.id.updateDescription);
        txtOwner = findViewById(R.id.updateOwner);
        btnUpdate = findViewById(R.id.woofadmin_org_update);

        updateClinic = new DogCare();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference updateRef = FirebaseDatabase.getInstance().getReference().child("DOgCare");
                updateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild("updateClinic1")){
                            try{
                                updateClinic.setClinicName(txtClinic.getText().toString().trim());
                                updateClinic.setContactNo(txtConNo.getText().toString().trim());
                                updateClinic.setAddress(txtAddress.getText().toString().trim());
                                updateClinic.setCity(txtCity.getText().toString().trim());
                                updateClinic.setDescription(txtDescription.getText().toString().trim());
                                updateClinic.setOwnerName(txtOwner.getText().toString().trim());
                                upDbRef = FirebaseDatabase.getInstance().getReference().child("DogCare").child("updateClinic1");
                                upDbRef.setValue(updateClinic);
                                clearControls();
                                //Feedback to the user via a Toast...
                                Toast.makeText(getApplicationContext(), "Data Updated Successfully...", Toast.LENGTH_SHORT).show();
                            }
                            catch(NumberFormatException e){
                                Toast.makeText(getApplicationContext(), "Invalid Contact Number...", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                            Toast.makeText(getApplicationContext(), "No Source to Update...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}