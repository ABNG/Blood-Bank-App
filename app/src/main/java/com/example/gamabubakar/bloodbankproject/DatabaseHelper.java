package com.example.gamabubakar.bloodbankproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gamAbubakar on 2/1/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    final static String dbname="BloodBank";
    final static String tablename="user_information";
    final static String col_1="name";
    final static String col_2="radiocheck";
    final static String col_3="email";
    final static String col_4="contactno";
    final static String col_5="address";
    final static String col_6="username";
    final static String col_7="password";
    final static String col_8="blood_group";
    final static String col_9="check_box";

    public DatabaseHelper(Context context) {
        super(context, dbname, null, 1);
        SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table IF NOT EXISTS "+tablename+"("+col_1+" text not null primary key,"+col_2+" text not null,"+col_3+" text not null,"+col_4+" integer not null,"+col_5+" text not null,"+col_6+" text not null,"+col_7+" text not null,"+col_8+" text not null,"+col_9+" text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP table IF EXISTS "+tablename+"");
        onCreate(db);
    }
    public boolean insertdata(String name,String radiocheck,String email,long contactno,String address,String username,String password,String bloodgroup,String checkbox){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(col_1,name);
        cv.put(col_2,radiocheck);
        cv.put(col_3,email);
        cv.put(col_4,contactno);
        cv.put(col_5,address);
        cv.put(col_6,username);
        cv.put(col_7,password);
        cv.put(col_8,bloodgroup);
        cv.put(col_9,checkbox);

        long check=db.insert(tablename,null,cv);
        if(check==-1)
            return false;
        else
            return true;
    }
    public Cursor getalldata(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+tablename+"",null);
        return res;
    }
    public int updated(String name,String radiocheck,String email,long contactno,String address,String username,String password,String bloodgroup,String checkbox){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(col_1,name);
        cv.put(col_2,radiocheck);
        cv.put(col_3,email);
        cv.put(col_4,contactno);
        cv.put(col_5,address);
        cv.put(col_6,username);
        cv.put(col_7,password);
        cv.put(col_8,bloodgroup);
        cv.put(col_9,checkbox);
        int check=db.update(tablename,cv,""+col_6+"=?",new String[]{username});
        return check;
    }
    public Cursor searchdata(String bloodgroup,String checkbox){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="select * from "+tablename+" where "+col_8+" = ? and "+col_9+" = ?";
        Cursor res=db.rawQuery(query,new String[]{bloodgroup,checkbox});
        return res;
    }
    public Cursor searchdataBlood(){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="select "+col_8+",count("+col_8+") as total from "+tablename+" group by "+col_8+"";
        Cursor res=db.rawQuery(query,null);
        return res;
    }
    public Cursor searchusername(){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="select "+col_6+" from "+tablename+"";
        Cursor res=db.rawQuery(query,null);
        return res;
    }
}
