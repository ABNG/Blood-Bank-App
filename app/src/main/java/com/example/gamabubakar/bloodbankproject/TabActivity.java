package com.example.gamabubakar.bloodbankproject;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class TabActivity extends AppCompatActivity {
TabLayout tabLayout;
ViewPager pager;
String username;
String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        try {
            Bundle b = getIntent().getExtras();
            username = b.getString("Uname");
            password=b.getString("Upassword");
        }
        catch (Exception e){

        }
        //////////////////////////////////send data from activity to fragment///////////////////////////////////////////////////
        Bundle b=new Bundle();
        b.putString("uname",username);
        b.putString("Upassword",password);
        MyDetail myDetail=new MyDetail();
        myDetail.setArguments(b);
        //////////////////////////////////////////////////////////////////////////////////////
        pager=findViewById(R.id.viewpager);
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SearchDonor(),"SEARCH DONOR");
        adapter.addFragment(new BloodBank(),"BLOOD BANK");
        adapter.addFragment(myDetail,"MY DETAILS");
        pager.setAdapter(adapter);
        tabLayout=findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(pager);
        tabLayout.getTabAt(0).setIcon(R.drawable.magnify);
        tabLayout.getTabAt(1).setIcon(R.drawable.leaf);
        tabLayout.getTabAt(2).setIcon(R.drawable.carddetails);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                finish();
                Intent i=new Intent(TabActivity.this,MainActivity.class);
                startActivity(i);
                return  true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent i=new Intent(TabActivity.this,MainActivity.class);
        startActivity(i);
        super.onBackPressed();
    }
}
