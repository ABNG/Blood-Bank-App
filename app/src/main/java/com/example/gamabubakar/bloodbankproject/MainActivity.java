package com.example.gamabubakar.bloodbankproject;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper dbhelper;
    EditText username;
    EditText password;
    String name;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbhelper=new DatabaseHelper(this);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        try {
            Bundle b = getIntent().getExtras();
            name = b.getString("username");
            username.setText(name);
            if(username.getText().toString().length()==0)
                username.requestFocus();
            else
            password.requestFocus();
        }
        catch (Exception e){

        }
    }

    public void LoginButton(View view) {
        if((username.getText().toString().length()==0)||(password.getText().length()==0)){
            Snackbar.make(view,"Please Fill All Fields",Snackbar.LENGTH_LONG).show();
        }
        else {
            Cursor res = dbhelper.getalldata();
            while (res.moveToNext()) {
                if ((res.getString(5).equals(username.getText().toString())) && (res.getString(6).equals(password.getText().toString()))) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 1) {
                flag=0;
                finish();
                Intent intent = new Intent(MainActivity.this, TabActivity.class);
                intent.putExtra("Uname",username.getText().toString());
                intent.putExtra("Upassword",password.getText().toString());
                startActivity(intent);
            } else {
                Snackbar.make(view, "Name OR Password Incorrect", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    public void RegistrationButton(View view) {
        Intent intent=new Intent(MainActivity.this,Main2Activity.class);
        startActivity(intent);
    }
}
