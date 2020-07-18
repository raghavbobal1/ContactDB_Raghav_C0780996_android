package com.king.contactdb_raghav_c0780996_android.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.king.contactdb_raghav_c0780996_android.R;
import com.king.contactdb_raghav_c0780996_android.database.ContactDatabase;
import com.king.contactdb_raghav_c0780996_android.helperclasses.Contact;
import com.king.contactdb_raghav_c0780996_android.helperclasses.ContactAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView addPerson;
    EditText search;
    ContactAdapter contactAdapter;
    TextView count;

    List<Contact> contactArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        final ContactDatabase contactDb = ContactDatabase.getInstance( this );

        count = findViewById( R.id.countPerson);

        final Integer[] countvalue = {contactDb.daoObject().count()};

        contactDb.daoObject().countLive().observe( this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer countnew) {
                countvalue[0] = countnew;
                if(countnew == 1)
                {
                    count.setText( countnew.toString() + " contact");
                }
                else
                {
                    count.setText(countnew.toString() + " contacts");
                }

            }
        } );

        count.setText( countvalue[0].toString());

        addPerson = findViewById( R.id.add_person );
        addPerson.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent( getApplicationContext(), AddNewContactActivity.class );
                startActivity( myintent );
            }
        } );



        RecyclerView recyclerView = findViewById(R.id.recyclerPerson);
        contactAdapter = new ContactAdapter(this);


        contactArrayList = contactDb.daoObject().getDefault();

        contactDb.daoObject().getUserDetails().observe( this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable List<Contact> personlist) {
                contactArrayList = personlist;

                contactAdapter.setPersonsList(personlist);
                contactAdapter.notifyDataSetChanged();

            }
        } );

        contactAdapter.setPersonsList(contactArrayList);
        recyclerView.setAdapter(contactAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        contactAdapter.notifyDataSetChanged();


        search = findViewById(R.id.editSearch );


        search.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        } );


    }

    private void filter(String s) {
        List<Contact> filteredList = new ArrayList<>(  );
        for(Contact contact : contactArrayList)
        {
            if(contact.getFirstname().toLowerCase().contains( s.toLowerCase() )
                    || contact.getLastname().toLowerCase().contains( s.toLowerCase() )
                    || contact.getAddress().toLowerCase().contains( s.toLowerCase() )
                    || contact.getPhone().toLowerCase().contains( s.toLowerCase() )

            )
            {
                filteredList.add( contact );
            }
        }
        contactAdapter.filterList(filteredList);




    }
}
