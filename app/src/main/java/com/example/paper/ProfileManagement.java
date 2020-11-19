package com.example.paper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.paper.database.DBHandler;

public class ProfileManagement extends AppCompatActivity {

    EditText username,dob,password;
    Button btnupdate,btnadd;
    RadioButton male,female;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_management);

        username = findViewById(R.id.username1);
        dob = findViewById(R.id.dob1);
        password = findViewById(R.id.password);
        male = findViewById(R.id.radio11);
        female = findViewById(R.id.radio21);
        btnupdate = findViewById(R.id.btnupdate);
        btnadd = findViewById(R.id.btnadd);


        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),EditProfile.class);
                startActivity(i);

            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (male.isChecked()){
                    gender = "Male";

                }else
                    gender = "Female";

               DBHandler dbHandler = new DBHandler(getApplicationContext());
               long newID = dbHandler.addInfo(username.getText().toString(),dob.getText().toString(),password.getText().toString(),gender);
               Toast.makeText(ProfileManagement.this, "User added. UserID :"+newID, Toast.LENGTH_SHORT).show();

               Intent i = new Intent(getApplicationContext(),EditProfile.class);
                startActivity(i);

            }
        });

    }
}
