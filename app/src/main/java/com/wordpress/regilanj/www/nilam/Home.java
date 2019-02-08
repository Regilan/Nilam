package com.wordpress.regilanj.www.nilam;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;



public class Home extends AppCompatActivity {

    TextView head, field, temp, humid, hindex, soilaval, soildval;
    Button logout, alert;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        head = (TextView) findViewById(R.id.headline);
        field = (TextView) findViewById(R.id.field);
        temp = (TextView) findViewById(R.id.temp);
        humid = (TextView) findViewById(R.id.humidity);
        hindex = (TextView) findViewById(R.id.heatindex);
        soilaval = (TextView) findViewById(R.id.soilanastate);
        soildval = (TextView) findViewById(R.id.soildigistate);
        logout = (Button) findViewById(R.id.logout);
        alert = (Button) findViewById(R.id.alert);

        Intent intent = getIntent();
        final String ID = intent.getStringExtra("ID");
        final String Field = intent.getStringExtra("Field");

        head.setText("Welcome, "+ID);
        field.setText(Field);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(ID);
        /*ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FarmData post = dataSnapshot.getValue(FarmData.class);
                Map<String, Object> values = post.toMap();
                hindex.setText(values.get("HeatI").toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(postListener);*/
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    if(data.getKey().equals("HeatIndex")) {
                        String value = data.getValue().toString();
                        hindex.setText(value);
                        Toast.makeText(getApplicationContext() , value, Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //do the operation

            }
        });

        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> newdata = new HashMap<>();
                newdata.put("Aadhar",Long.parseLong(ID));
                newdata.put("Field",Integer.valueOf(Field));
                newdata.put("Range",1000);
                newdata.put("Taken",false);
                db.collection("data").document(ID).set(newdata).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext() , "Your request successfully sent!", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext() , "Failed to send! Make sure you have Internet!", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });



    }
}
