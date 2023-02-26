package com.example.gamabubakar.bloodbankproject;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by gamAbubakar on 2/1/2018.
 */

public class SearchDonor extends Fragment implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    View view;
    DatabaseHelper dbhelper;
    Spinner sp1;
    String spinnerstore;
    String [] bloodgroup={"select...","A-","A+","AB-","AB+","B-","B+","O-","O+"};
    ListView lv;
    TextView tv;
    ArrayList<String>namelist;
    ArrayAdapter<String> listadapter;
    ArrayList<String> contactlist;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.search_donor,container,false);
        dbhelper=new DatabaseHelper(getActivity());
        tv=view.findViewById(R.id.listviewtext);
        sp1=view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),R.layout.spinner_item,bloodgroup);
        sp1.setAdapter(adapter);
        sp1.setOnItemSelectedListener(this);
        lv=view.findViewById(R.id.listview);
        namelist=new ArrayList<String>();
        contactlist=new ArrayList<String>();

        return view;

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            if (!(listadapter.isEmpty())) {
               // tv.setVisibility(View.GONE);
                listadapter.clear();
                namelist.clear();
                contactlist.clear();
            }
        }
        catch(Exception e){

            }
        spinnerstore=bloodgroup[position];
        Cursor res=dbhelper.searchdata(spinnerstore,"checked");
        if(res.getCount()==0){
            //Toast.makeText(getContext(),"No Data Found",Toast.LENGTH_LONG).show();
            tv.setText("No Data Found");
        }
        else {
            while (res.moveToNext()) {
                namelist.add(res.getString(0));
                contactlist.add(res.getString(3));
            }
           // tv.setVisibility(View.VISIBLE);
            tv.setText("Name");
            listadapter = new ArrayAdapter<String>(getContext(), R.layout.list_item, namelist);
            lv.setAdapter(listadapter);
            lv.setOnItemClickListener(this);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(namelist.get(position));
        builder.setMessage(contactlist.get(position));
                builder.show();
    }
}
