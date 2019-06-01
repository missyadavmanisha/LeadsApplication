package com.codingblocks.leadsapplication;

import android.support.annotation.NonNull;

import java.util.Date;

public class LeadsDetails implements Comparable<LeadsDetails>{

    public String name, phone, gender, rent, room, facilities;
    public Date date;

    public LeadsDetails(String name, String phone, String gender, String rent, String room, String facilities, Date date) {
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.rent = rent;
        this.room = room;
        this.facilities = facilities;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public LeadsDetails() {

    }

    @Override
    public int compareTo(@NonNull LeadsDetails o) {
        try {
            return getDate().compareTo(o.getDate());
        }
        catch (Exception e){
            return 0;
        }
    }
}
