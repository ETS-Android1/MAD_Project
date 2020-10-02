package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class woofadmin_selectedProductView extends AppCompatActivity {

    String itemID;
    TextView name,price,desc,qty;
    DatabaseReference dbRef;
    Button delete,update;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woofadmin_selected_product_view);

        itemID =  getIntent().getStringExtra("itmID");

        name = findViewById(R.id.EtinputProductname);
        price = findViewById(R.id.EtinputUnitPrice);
        desc = findViewById(R.id.EtinputDescription);
        qty= findViewById(R.id.EtinputQty);
        image = findViewById(R.id.productImage);

        delete=findViewById(R.id.btnProdViewDel);
        update=findViewById(R.id.btnProdViewUpdate);


        dbRef =FirebaseDatabase.getInstance().getReference().child("ProductItem").child(itemID);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    ProductItem item = snapshot.getValue(ProductItem.class);

                    String itmName = item.getProductName();
                    float itmPrice = item.getUnitPrice();
                    int itmQty = item.getQty();
                    String itemDes = item.getDescription();

                    name.setText(itmName);
                    price.setText(String.valueOf(itmPrice));
                    desc.setText(itemDes);
                    qty.setText(String.valueOf(itmQty));
                    Picasso.get().load(item.getImage()).into(image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef=FirebaseDatabase.getInstance().getReference().child("ProductItem").child(itemID);
                dbRef.removeValue();
                Toast.makeText(getApplicationContext(),"Product deleted successfully",Toast.LENGTH_SHORT).show();


                navigateToViewProd();
            }


        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(qty.getText().toString()))
                    Toast.makeText(getApplicationContext(),"enter quantity",Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(price.getText().toString()))
                    Toast.makeText(getApplicationContext(),"enter price",Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(name.getText().toString()))
                    Toast.makeText(getApplicationContext(),"Enter product name",Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(desc.getText().toString()))
                    Toast.makeText(getApplicationContext(),"Enter description",Toast.LENGTH_SHORT).show();

                else {

                    dbRef = FirebaseDatabase.getInstance().getReference();
                    dbRef.child("ProductItem").child(itemID).child("productName").setValue(name.getText().toString().trim());
                    dbRef.child("ProductItem").child(itemID).child("description").setValue(desc.getText().toString());
                    dbRef.child("ProductItem").child(itemID).child("qty").setValue(Integer.parseInt(qty.getText().toString()));
                    dbRef.child("ProductItem").child(itemID).child("unitPrice").setValue(Float.parseFloat(price.getText().toString()));

                    Toast.makeText(getApplicationContext(), "Item Updated successfully", Toast.LENGTH_SHORT).show();
                    navigateToViewProd();

                }

            }
        });

        //bottom navigation bar begins
        BottomNavigationView bottomNavigationView = findViewById(R.id.app_admin_bottom_navigationbar);
        //set selected
        bottomNavigationView.setSelectedItemId(R.id.bottomNaviBar_adminItems);

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
                        startActivity(new Intent(getApplicationContext(), woofadmin_viewProduct.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.bottomNaviBar_adminOrders:
                        startActivity(new Intent(getApplicationContext(), woofadmin_orders.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.bottomNaviBar_adminProfile:
                        return true;

                }

                return false;
            }
        });
        }
    public void navigateToViewProd(){
        Intent intent = new Intent(this, woofadmin_viewProduct.class);
        startActivity(intent);





    }
}