package com.example.lab5.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBManager extends SQLiteOpenHelper {
    public static final String TAG = "SQLite";
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "creditManagementDB";
    public static final String CREDIT_TABLE_NAME = "tblCredit";

    public static final String CREDIT_ID="id";
    public static final String CREDIT_TITLE="title";
    public static final String TRACK_TIME="trackTime";
    public static final String CASH="cash";
    public static final String TYPE_TRANSACTION="typeTransaction";
    public static final String DESCRIPTION="description";

    public DBManager(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        Log.d("DBManager","DBManager: OK");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlQuery="CREATE TABLE "+CREDIT_TABLE_NAME+" ("
                +CREDIT_ID+" TEXT PRIMARY KEY, "
                +CREDIT_TITLE+" TEXT,"
                +TRACK_TIME+" TEXT,"
                +CASH+" DOUBLE,"
                +TYPE_TRANSACTION+" INT,"
                +DESCRIPTION+" TEXT)";
        sqLiteDatabase.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+CREDIT_TABLE_NAME);
        onCreate(db);
    }
}
