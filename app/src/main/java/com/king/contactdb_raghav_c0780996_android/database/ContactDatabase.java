package com.king.contactdb_raghav_c0780996_android.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.king.contactdb_raghav_c0780996_android.helperclasses.Contact;


@Database(entities = Contact.class , exportSchema = false , version = 4)

public abstract class ContactDatabase extends RoomDatabase {


    public static final String DB_NAME = "user_db";

    private static ContactDatabase uInstance;


    public static ContactDatabase getInstance(Context context)
    {
        if(uInstance == null)
        {
            uInstance = Room.databaseBuilder(context.getApplicationContext(), ContactDatabase.class, ContactDatabase.DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return uInstance;
    }

    public abstract ContactDao daoObject();
}

