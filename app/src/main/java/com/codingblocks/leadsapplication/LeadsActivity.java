package com.codingblocks.leadsapplication;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class LeadsActivity extends AppCompatActivity {

    EditText tenantNameEditText, tenantPhoneEditText;
    RadioGroup genderRadioGroup, rentRadioGroup, roomRadioGroup;
    RadioButton genderRadioButton, rentRadioButton;
    RadioButton roomRadioButton;
    CheckBox acLeadCheckBox, messLeadCheckBox, washroomLeadCheckBox, wifiLeadCheckBox;
    Button addLeadButton, cancelLeadButton;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();


    SharedPreferences sharedPreferences;
    int PGNumber;
    TextView tenantGenderTextView;

    String gender, pgName, ownerName, addressLine1, pincode, personalContact;
    ImageView backButton;

    boolean isLocation;
    String lat, lng;

    String pgAvailableFor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leads);

        tenantGenderTextView=findViewById(R.id.tenantGenderEditText);

        tenantNameEditText = findViewById(R.id.tenantNameEditText);
        tenantPhoneEditText = findViewById(R.id.tenantPhoneEditText);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        rentRadioGroup = findViewById(R.id.rentRadioGroup);
        roomRadioGroup = findViewById(R.id.roomRadioGroup);

        acLeadCheckBox = findViewById(R.id.acLeadCheckBox);
        messLeadCheckBox = findViewById(R.id.messLeadCheckBox);
        washroomLeadCheckBox = findViewById(R.id.washroomLeadCheckBox);
        wifiLeadCheckBox = findViewById(R.id.wifiLeadCheckBox);

        addLeadButton = findViewById(R.id.addLeadButton);
        cancelLeadButton = findViewById(R.id.cancelLeadButton);
        backButton = findViewById(R.id.backButton);

        firebaseDatabase = FirebaseDatabase.getInstance();


        databaseReference = firebaseDatabase.getReference("Leads");

        addLeadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tenantNameEditText.getText().toString().isEmpty()) {

                    tenantNameEditText.setError("Required");
                }

                if (tenantPhoneEditText.getText().toString().length() != 10) {

                    tenantPhoneEditText.setError("10 Digits Required");
                }
                if (!tenantNameEditText.getText().toString().isEmpty()  && tenantPhoneEditText.getText().toString().length() == 10) {

                    String name = tenantNameEditText.getText().toString();
                    String phone = tenantPhoneEditText.getText().toString();

                    if (genderRadioGroup.getVisibility() == View.INVISIBLE) {
                        int genderId = genderRadioGroup.getCheckedRadioButtonId();
                        genderRadioButton = findViewById(genderId);
                        gender = genderRadioButton.getText().toString();
                    }

                    String rent ="";

                    try
                    {
                        int rentId = rentRadioGroup.getCheckedRadioButtonId();
                        rentRadioButton = findViewById(rentId);

                        if(!rentRadioButton.isChecked())
                        {
                            rent="";
                        }
                        else{
                            rent=rentRadioButton.getText().toString();
                        }

                    }
                    catch (Exception e)
                    {
                        rent ="";
                    }
                    String room ="";
                    try
                    {
                        int roomId = roomRadioGroup.getCheckedRadioButtonId();
                        roomRadioButton = findViewById(roomId);

                        if(!roomRadioButton.isChecked())
                        {
                            room="";
                        }
                        else{
                            room=roomRadioButton.getText().toString();
                        }

                    }
                    catch (Exception e)
                    {
                        room = "" ;
                    }


                    String facilities = "";

                    if (acLeadCheckBox.isChecked()) {

                        facilities += "AC ";
                    }

                    if (messLeadCheckBox.isChecked()) {

                        facilities += "Mess ";
                    }
                    if (washroomLeadCheckBox.isChecked()) {

                        facilities += "Washroom ";
                    }
                    if (wifiLeadCheckBox.isChecked()) {

                        facilities += "Wifi ";
                    }



                    Date date = new Date();

                    LeadsDetails leadsDetails = new LeadsDetails(name, phone, gender, rent, room, facilities, date);
                    databaseReference.child(phone).setValue(leadsDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(LeadsActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    });


                }

            }
        });

        cancelLeadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();

    }
}
