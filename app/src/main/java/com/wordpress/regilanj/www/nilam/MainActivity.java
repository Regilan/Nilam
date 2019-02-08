package com.wordpress.regilanj.www.nilam;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button register, login;
    EditText id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (Button) findViewById(R.id.register);
        login = (Button) findViewById(R.id.login);
        id = (EditText) findViewById(R.id.id);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Aadhar = id.getText().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                if (Aadhar.length() != 0) {
                    db.collection("farmerauth").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document: task.getResult()) {
                                    if (Aadhar.equals(document.getId())) {
                                        Map<String, Object> values = document.getData();
                                        Intent home = new Intent(MainActivity.this, Home.class).putExtra("ID", document.getId()).putExtra("Field", String.valueOf(values.get("Field")));
                                        startActivity(home);
                                        finish();
                                    }
                                }
                            }
                            else {
                                Toast.makeText(getApplicationContext() , "Sorry some error has occured!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent newactivity = new Intent(MainActivity.this, signup.class);
                startActivity(newactivity);
                finish();

            }
        });



    }
}
