package com.codingblocks.leadsapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    List<LeadsDetails> leadsDetailsList;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReference1;

    RecyclerView newLeadsRecyclerView;
    newLeadsDetailList newLeadsDetailList;

    String eazypgId, pgName, pgContact, ownerName;

    SharedPreferences sharedPreferences;
    ImageView backButton;
    int PGNumber;

    Button addLead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backButton = findViewById(R.id.backButton);

        leadsDetailsList = new ArrayList<>();

        newLeadsRecyclerView = findViewById(R.id.newLeadsRecyclerView);
        addLead = findViewById(R.id.addLeadButton);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        addLead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LeadsActivity.class);
                startActivity(intent);
            }
        });

        databaseReference = firebaseDatabase.getReference("Leads");

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                leadsDetailsList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    LeadsDetails tenantDetails = snapshot.getValue(LeadsDetails.class);
                    leadsDetailsList.add(tenantDetails);

                }
                Log.e("empty",leadsDetailsList.size()+"");

                newLeadsDetailList = new newLeadsDetailList(leadsDetailsList, MainActivity.this, eazypgId, pgName, pgContact, ownerName);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                newLeadsRecyclerView.setLayoutManager(layoutManager);
                newLeadsRecyclerView.setItemAnimator(new DefaultItemAnimator());
                //Collections.sort(tenantDetailsList, Collections.<UnderProcessTenantDetails>reverseOrder());
                newLeadsRecyclerView.setAdapter(newLeadsDetailList);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}