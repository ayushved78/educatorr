package com.example.education_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbhelp extends SQLiteOpenHelper {
    public static final String DBName = "registration.db";

    public dbhelp(Context context) {
        super(context, "registration.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table user(username TEXT primary key,password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists user");
    }

    public Boolean insertData(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        long result = db.insert("user",null,contentValues);
        if(result==-1){
            return false;
        }else
            return true;
    }

    public Boolean checkUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username = ?", new String[] {username});
        if (cursor.getCount() > 0){
            return true;
        }else
            return false;
    }

    public Boolean checkLogin(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username = ? and password = ?", new String[] {username,password});
        if (cursor.getCount() > 0){
            return true;
        }else
            return false;
    }
}
