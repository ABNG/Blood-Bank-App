package com.example.gamabubakar.bloodbankproject;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by gamAbubakar on 2/1/2018.
 */

public class BloodBank extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    View view;
    ArrayList<AdapterItem> list=new ArrayList<AdapterItem>();
    mycustomAdapter myadapter;
    DatabaseHelper dbhelper;
    ListView lv;
    Spinner sp1;
    String [] bloodgroup={"select...","A-","A+","AB-","AB+","B-","B+","O-","O+"};
    String spinnerstore;
    Button button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.blood_bank,container,false);
        dbhelper=new DatabaseHelper(getActivity());
        lv=view.findViewById(R.id.listview);
        list.clear();
        Cursor res=dbhelper.searchdataBlood();
        if(res.getCount()==0){

        }
        while(res.moveToNext()){

            list.add(new AdapterItem(res.getString(0),res.getString(1)));
        }
        myadapter=new mycustomAdapter(list);
        lv.setAdapter(myadapter);
        sp1=view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),R.layout.spinner_item,bloodgroup);
        sp1.setAdapter(adapter);
        sp1.setOnItemSelectedListener(this);
button=view.findViewById(R.id.requestButton);
button.setOnClickListener(this);
        return view;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerstore=bloodgroup[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onClick(View v) {
        Toast.makeText(getContext(),"Request Send for "+spinnerstore+" Donor",Toast.LENGTH_LONG).show();
    }
    class mycustomAdapter extends BaseAdapter {
        ArrayList<AdapterItem> list=new ArrayList<AdapterItem>();
        mycustomAdapter(ArrayList<AdapterItem> list){
            this.list=list;
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater=getLayoutInflater();
            View view1=inflater.inflate(R.layout.list_ticket,null);
            TextView bloodgroup=view1.findViewById(R.id.bloodgroup_id);
            TextView total=view1.findViewById(R.id.total_count);
            AdapterItem s=list.get(position);
            bloodgroup.setText(s.bloodgroup);
            total.setText(s.total);
            return view1;
        }
    }
}
