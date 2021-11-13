package com.example.lab5.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.lab5.Model.MyCredit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyCreditDAO {
    DBManager dbManager;

    public MyCreditDAO(Context context){
        dbManager=new DBManager(context);
    }

    public void create(MyCredit myCredit){
        Log.i(DBManager.TAG,"DBManager.create ... "+myCredit.getId());
        SQLiteDatabase db= dbManager.getWritableDatabase();
        int id =this.count()+1;

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Date date= new Date();
        String strCurrentDate = formatter.format(date);

        ContentValues values=new ContentValues();
        values.put(DBManager.CREDIT_ID, id+"");
        values.put(DBManager.CREDIT_TITLE, myCredit.getTitle());
        values.put(DBManager.TRACK_TIME, strCurrentDate);
        values.put(DBManager.CASH, myCredit.getCash());
        values.put(DBManager.TYPE_TRANSACTION, myCredit.getTypeTransaction());
        values.put(DBManager.DESCRIPTION, myCredit.getDescription());

        db.insert(DBManager.CREDIT_TABLE_NAME,null,values);
        db.close();
    }

    public List<MyCredit> readAll(){
        Log.i(DBManager.TAG,"DBManager.realAll ... ");

        List<MyCredit> listCredit =new ArrayList<>();

        String selectQuery="SELECT * from "+DBManager.CREDIT_TABLE_NAME;

        SQLiteDatabase db= dbManager.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                MyCredit credit=new MyCredit();
                credit.setId(cursor.getString(0));
                credit.setTitle(cursor.getString(1));
                credit.setTrackTime(cursor.getString(2));
                credit.setCash(cursor.getDouble(3));
                credit.setTypeTransaction(cursor.getInt(4));
                credit.setDescription(cursor.getString(5));

                listCredit.add(credit);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return listCredit;
    }

    public int update(MyCredit myCredit){
        Log.i(DBManager.TAG,"DBManager.update ... "+myCredit.getId());
        SQLiteDatabase db= dbManager.getWritableDatabase();
        ContentValues values=new ContentValues();

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Date date= new Date();
        String strCurrentDate = formatter.format(date);

        values.put(DBManager.CREDIT_TITLE, myCredit.getTitle());
        values.put(DBManager.CASH, myCredit.getCash());
        values.put(DBManager.TRACK_TIME, strCurrentDate);
        values.put(DBManager.TYPE_TRANSACTION, myCredit.getTypeTransaction());
        values.put(DBManager.DESCRIPTION, myCredit.getDescription());

        return db.update(DBManager.CREDIT_TABLE_NAME,values,DBManager.CREDIT_ID+" =?",new String[]{String.valueOf(myCredit.getId())});
    }

    public void delete(String id){
        SQLiteDatabase db= dbManager.getWritableDatabase();
        db.delete(DBManager.CREDIT_TABLE_NAME,DBManager.CREDIT_ID+" =?",new String[]{id});
    }

    public int count(){

        String countQuery = "SELECT * from "+DBManager.CREDIT_TABLE_NAME;
        SQLiteDatabase db=dbManager.getReadableDatabase();
        Cursor cursor=db.rawQuery(countQuery,null);
        int count=0;
        try {
            if(cursor.moveToFirst()){
                count=cursor.getCount();
            }
            return count;
        }
        finally {
            if(cursor!=null){
                cursor.close();
            }
        }
    }

    public void seed(){
        int count =this.count();
        if(count ==0){
            MyCredit credit1=new MyCredit("1", "Đóng học phí", "", 20000, 0, "Đóng học phí tháng 10");
            MyCredit credit2=new MyCredit("2", "Lãnh lương", "", 30000, 1, "Lãnh lương tháng 10");

            this.create(credit1);
            this.create(credit2);
        }
    }
}
