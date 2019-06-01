package com.codingblocks.leadsapplication;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class newLeadsDetailList extends RecyclerView.Adapter<newLeadsDetailList.MyHolder> {

    List<LeadsDetails> leadsDetailsList;
    Context context;
    String pgId, eazypgID, pgName, pgContact, ownerName;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    SharedPreferences sharedPreferences;
    int PGNumber;

    public static final String data = "";

    public newLeadsDetailList(List<LeadsDetails> leadsDetailsList, Context context, String eazypgID, String pgName, String pgContact, String ownerName) {
        this.leadsDetailsList= leadsDetailsList;
        this.context = context;
        this.eazypgID = eazypgID;
        this.pgName = pgName;
        this.pgContact = pgContact;

        this.ownerName = ownerName;

        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public newLeadsDetailList.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leads_row, parent, false);

        return new newLeadsDetailList.MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final newLeadsDetailList.MyHolder holder, final int position) {

   final LeadsDetails leadsDetails = leadsDetailsList.get(position);

        holder.nameTextView.setText(leadsDetails.name);
        holder.phoneTextView.setText(leadsDetails.phone);
        holder.genderTextView.setText(leadsDetails.gender);

        holder.callLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + leadsDetails.phone));
                    context.startActivity(callIntent);
                }
                catch (ActivityNotFoundException activityException) {
                    Toast.makeText(context, "Call failed", Toast.LENGTH_SHORT).show();
                }
                catch (SecurityException e) {
                    Toast.makeText(context, "Call failed!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.textLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Message Sent");
                builder.setMessage("Hi " + leadsDetailsList.get(position).name + ". Thanks for visiting " + pgName + ". Feel free to contact Mr./Ms. " + ownerName + " in case you finalize " + pgName + " for your stay. We look forward to host you :)");
                builder.setNeutralButton("Ok", null);
                builder.show();
            }
        });



        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = leadsDetails.date;
        holder.dateTextView.setText(df.format(date));


        if (leadsDetails.rent != null)
            holder.rentAmountTextView.setText(leadsDetails.rent);

        if (leadsDetails.room != null)
            holder.bedTypeTextView.setText(leadsDetails.room);

        if (leadsDetails.facilities != null) {
            if (leadsDetails.facilities.contains("AC")) {
                holder.nonAcTextView.setVisibility(View.VISIBLE);
                holder.nonAcTextView.setText("AC");
            } else {
                holder.nonAcTextView.setVisibility(View.VISIBLE);
                holder.nonAcTextView.setText("Non AC");
            }
            if (leadsDetails.facilities.contains("Wifi")) {
                holder.wifiTextView.setVisibility(View.VISIBLE);
                holder.wifiTextView.setText("Wifi");
            }
            if (leadsDetails.facilities.contains("Washroom")) {
                holder.washroomTextView.setVisibility(View.VISIBLE);
                holder.washroomTextView.setText("Washroom");
            }
            if (leadsDetails.facilities.contains("Mess")) {
                holder.messTextView.setVisibility(View.VISIBLE);
                holder.messTextView.setText("Mess");
            }

        }

    }

    @Override
    public int getItemCount() {
        return leadsDetailsList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        public TextView nameTextView, phoneTextView, genderTextView, dateTextView;
        public LinearLayout callLinearLayout, textLinearLayout, addLinearLayout;
        public Button callButton, remindButton;
        public TextView rentAmountTextView, bedTypeTextView, washroomTextView, wifiTextView, nonAcTextView, callTextView, remindTextView, addTenantTextView, messTextView;

        public MyHolder(View itemView){
            super(itemView);

            dateTextView = itemView.findViewById(R.id.dateTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            nonAcTextView = itemView.findViewById(R.id.nonAcTextView);
            genderTextView = itemView.findViewById(R.id.genderTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
            callButton=itemView.findViewById(R.id.tenant1CallButton);
            remindButton=itemView.findViewById(R.id.tenant1MessageButton);
            callTextView=itemView.findViewById(R.id.callTextView);
            remindTextView=itemView.findViewById(R.id.remindTextView);
            callLinearLayout = itemView.findViewById(R.id.callLinearLayout);
            textLinearLayout = itemView.findViewById(R.id.textLinearLayout);
            rentAmountTextView = itemView.findViewById(R.id.rentAmountTextView);
            bedTypeTextView = itemView.findViewById(R.id.bedTypeTextView);
            washroomTextView = itemView.findViewById(R.id.washroomTextView);
            wifiTextView = itemView.findViewById(R.id.wifiTextView);
            nonAcTextView = itemView.findViewById(R.id.nonAcTextView);
            messTextView = itemView.findViewById(R.id.messTextView);

        }
    }
}
