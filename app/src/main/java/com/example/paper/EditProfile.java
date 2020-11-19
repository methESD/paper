package com.example.paper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.paper.database.DBHandler;

import java.util.List;

public class EditProfile extends AppCompatActivity {


    EditText username1,dob1,password1;
    Button btnedit,btndelete,btnsearch;
    RadioButton male,female;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        username1 = findViewById(R.id.username1);
        dob1 = findViewById(R.id.dob1);
        password1 = findViewById(R.id.password1);
        male = findViewById(R.id.radio11);
        female = findViewById(R.id.radio21);
        btnedit = findViewById(R.id.btnedit);
        btndelete = findViewById(R.id.btndelete);
        btnsearch = findViewById(R.id.btnsearch);

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHandler dbHandler = new DBHandler(getApplicationContext());
                List user = dbHandler.readAllInfor(username1.getText().toString());

                if(user.isEmpty()){
                    Toast.makeText(EditProfile.this, "No User", Toast.LENGTH_SHORT).show();
                    username1.setText(null);
                    }
                else {
                    Toast.makeText(EditProfile.this, "User Found! user:"+user.get(0).toString(), Toast.LENGTH_SHORT).show();
                    username1.setText(user.get(0).toString());
                    dob1.setText(user.get(1).toString());
                    password1.setText(user.get(2).toString());
                    if (user.get(3).toString().equals("Male")){
                        male.setChecked(true);
                    }else{
                        female.setChecked(true);
                    }
                }



            }
        });
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (male.isChecked()) {
                    gender = "Male";

                } else
                    gender = "Female";

                DBHandler dbHandler = new DBHandler(getApplicationContext());

                Boolean status = dbHandler.updateInfor(username1.getText().toString(), dob1.getText().toString(), password1.getText().toString(), gender);
                if (status)
                {
                    Toast.makeText(EditProfile.this, "Updated", Toast.LENGTH_SHORT).show();

                }else
                {
                    Toast.makeText(EditProfile.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBHandler dbHandler = new DBHandler(getApplicationContext());
                dbHandler.deleteInfo(username1.getText().toString());

                Toast.makeText(EditProfile.this, "Deleted", Toast.LENGTH_SHORT).show();



            }
        });

    }
}