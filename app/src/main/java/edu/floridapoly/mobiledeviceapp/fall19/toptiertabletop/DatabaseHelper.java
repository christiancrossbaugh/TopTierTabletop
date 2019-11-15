package edu.floridapoly.mobiledeviceapp.fall19.toptiertabletop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper{

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "TopTierTabletop_db";

    private static final String KEY_ROWID = "_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";





    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create notes table
        db.execSQL("Create table user(email text primary key,password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
    }

    public boolean insert(String email,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("password",password);
        long ins = db.insert("user",null,contentValues);
        if(ins==-1)return  false;
        else return true;
    }

    //Check the email
    public Boolean checkEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where email=?",new String[]{email});
        if(cursor.getCount()>0) return false;
        else return true;
    }



}
