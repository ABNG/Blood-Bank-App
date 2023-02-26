package com.example.gamabubakar.bloodbankproject;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by gamAbubakar on 2/1/2018.
 */

public class MyDetail extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    View view;
    DatabaseHelper dbhelper;
    EditText name;
    RadioButton male;
    RadioButton female;
    EditText email;
    EditText contactno;
    EditText address;
    Spinner sp1;
    CheckBox checkBox;
    String radioButton;
    String spinnerstore;
    String checkbox;
    String [] bloodgroup={"select...","A-","A+","AB-","AB+","B-","B+","O-","O+"};
    Button button;
    String uname;
    String upassword;
    TextView tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.my_details,container,false);
        try {
            uname= getArguments().getString("uname");
            upassword=getArguments().getString("Upassword");
        }
        catch (Exception e){
        }
        dbhelper=new DatabaseHelper(getActivity());
        name=view.findViewById(R.id.my_username);
        male=view.findViewById(R.id.my_radiobutton_male);
        female=view.findViewById(R.id.my_radiobutton_female);
        email=view.findViewById(R.id.my_Email);
        contactno=view.findViewById(R.id.my_contactNo);
        address=view.findViewById(R.id.my_Address);
        sp1=view.findViewById(R.id.my_spinner);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),R.layout.spinner_item,bloodgroup);
        sp1.setAdapter(adapter);
        sp1.setOnItemSelectedListener(this);
        checkBox=view.findViewById(R.id.checkbox2);
        button=view.findViewById(R.id.my_updateButton);
        button.setOnClickListener(this);
        Cursor res = dbhelper.getalldata();
        while (res.moveToNext()) {
            if ((res.getString(5).equals(uname)) && (res.getString(6).equals(upassword))) {
                name.setText(res.getString(0));
                if(res.getString(1).equals("male")){
                    male.setChecked(true);
                }
                else{
                    female.setChecked(true);
                }
                email.setText(res.getString(2));
                contactno.setText(res.getString(3));
                address.setText(res.getString(4));
                for(int i=0;i<=8;i++) {
                    if(bloodgroup[i].equals(res.getString(7)))
                    sp1.setSelection(i);
                }
                if(res.getString(8).equals("checked"))
                    checkBox.setChecked(true);
                else{
                    checkBox.setChecked(false);
                }
            }
        }
        tv=view.findViewById(R.id.alertText);
        return view;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerstore=bloodgroup[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if(male.isChecked())
            radioButton="male";
        else
            radioButton="female";
        if(checkBox.isChecked())
            checkbox="checked";
        else
            checkbox="unchecked";
        try {
            if (name.getText().toString().length()==0 || email.getText().toString().length()==0 || contactno.getText().toString().length()==0 || address.getText().toString().length() == 0) {
                Snackbar.make(view, "Please Fill All Fields", Snackbar.LENGTH_LONG).show();
            } else {
                int result = dbhelper.updated(name.getText().toString(), radioButton, email.getText().toString(), Integer.parseInt(contactno.getText().toString()), address.getText().toString(), uname, upassword, spinnerstore, checkbox);
                if (result > 0) {
                    Snackbar.make(v, "Data Updated Successfully", Snackbar.LENGTH_LONG).show();
                }
                else
                    Snackbar.make(v, "Data Not Updated Successfully", Snackbar.LENGTH_LONG).show();
            }
        }
        catch (Exception e){
            Snackbar.make(v, "Please Fill All Fields", Snackbar.LENGTH_LONG).show();
        }
        tv.setVisibility(View.VISIBLE);
    }
}
