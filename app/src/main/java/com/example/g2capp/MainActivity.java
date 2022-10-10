package com.example.g2capp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    EditText editText;
    Button addButton;
    RecyclerView recyclerView;
   public CardView llitem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab);
        editText = findViewById(R.id.etext);
        addButton = findViewById(R.id.addbtn);
        recyclerView = findViewById(R.id.recyclerView);
        llitem = findViewById(R.id.llitem);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<String> dataArray = new ArrayList<>();
        RecyclerViewAdapter adapter =  new RecyclerViewAdapter(this,dataArray);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("message").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    dataArray.clear();
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        String i = snapshot1.getValue(String.class);
                        dataArray.add(i);
                    }
                    adapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(dataArray.size()-1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



        recyclerView.setAdapter(adapter);
//        recyclerView.setAdapter(mAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vision();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message");
                myRef.push().setValue(editText.getText().toString());
                editText.setText("");

                llitem.setVisibility(View.GONE);
            }
        });
    }

    void vision(){

        llitem.setVisibility(View.VISIBLE);

    }


    public static void onLongClicked() {

    }


}