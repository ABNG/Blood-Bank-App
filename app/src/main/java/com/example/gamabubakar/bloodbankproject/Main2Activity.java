package com.example.gamabubakar.bloodbankproject;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    DatabaseHelper dbhelper;
EditText name;
RadioButton male;
RadioButton female;
EditText email;
EditText contactno;
EditText address;
EditText username;
EditText password;
Spinner sp1;
CheckBox checkBox;
String radioButton;
String spinnerstore;
String checkbox;
String [] bloodgroup={"select...","A-","A+","AB-","AB+","B-","B+","O-","O+"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        dbhelper=new DatabaseHelper(this);
        name=findViewById(R.id.username);
        male=findViewById(R.id.radiobutton_male);
        female=findViewById(R.id.radiobutton_female);
        email=findViewById(R.id.Email);
        contactno=findViewById(R.id.contactNo);
        address=findViewById(R.id.Address);
        username=findViewById(R.id.usernamestored);
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Cursor res=dbhelper.searchusername();
                if(res.getCount()==0){

                }
                while(res.moveToNext()){
                    if(res.getString(0).equals(username.getText().toString())){
                        Toast.makeText(Main2Activity.this,"username Already Exist, Try Another One",Toast.LENGTH_SHORT).show();
                        username.setText("");

                    }
                }

            }
        });
        password=findViewById(R.id.passwordstored);
        sp1=findViewById(R.id.spinner);
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(Main2Activity.this,R.layout.spinner_item,bloodgroup);
        sp1.setAdapter(adapter);
        sp1.setOnItemSelectedListener(this);
        checkBox=findViewById(R.id.checkbox1);

    }

    public void RegisterButton(View view) {
        if(male.isChecked())
            radioButton="male";
        else
            radioButton="female";
        if(checkBox.isChecked())
            checkbox="checked";
        else
            checkbox="unchecked";
        try {
            if(name.getText().toString().length()==0 || email.getText().toString().length()==0 || contactno.getText().toString().length()==0 || address.getText().toString().length()==0 || username.getText().toString().length()==0 || password.getText().toString().length()==0){
                Snackbar.make(view, "Please Fill All Fields", Snackbar.LENGTH_LONG).show();
            }
            else {
                boolean check = dbhelper.insertdata(name.getText().toString(), radioButton, email.getText().toString(), Integer.parseInt(contactno.getText().toString()), address.getText().toString(), username.getText().toString(), password.getText().toString(), spinnerstore, checkbox);
                if (check == true) {
                    finish();
                    Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                    intent.putExtra("username", username.getText().toString());
                    startActivity(intent);
                } else
                    Toast.makeText(Main2Activity.this, "Data Not Inserted Successfully", Toast.LENGTH_LONG).show();
            }
            }
        catch(Exception e){
            Snackbar.make(view, "Please Fill All Fields", Snackbar.LENGTH_LONG).show();
        }


    }
////////////////////for spinner these two methods///////////////////////////////////////////////////////////////
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerstore=bloodgroup[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
