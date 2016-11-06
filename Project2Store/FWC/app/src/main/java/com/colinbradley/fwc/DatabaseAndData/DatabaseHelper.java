package com.colinbradley.fwc.DatabaseAndData;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import com.colinbradley.fwc.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by colinbradley on 11/5/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "fwc.db";

    public static final String TABLE_NAME = "fwctable";

    public static final String COL_ID = "id";
    public static final String COL_IMAGE = "image_path";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_TYPE = "type";
    public static final String COL_PRICE = "price";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_ID + " INTEGER PRIMARY KEY, " +
                    COL_IMAGE + " INT, " +
                    COL_NAME + " TEXT, " +
                    COL_DESCRIPTION + " TEXT, " +
                    COL_TYPE + " TEXT, " +
                    COL_PRICE + " INTEGER" + ")";

    public static final String DROP_FWC_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static DatabaseHelper sInstance;

    public static DatabaseHelper getInstance(Context context){
        if (sInstance == null){
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DROP_FWC_TABLE);
        onCreate(db);

    }


/*
    private void populateGearTable(SQLiteDatabase db){


        db.insert(TABLE_NAME, null, contentValues("NAME", R.drawable.testpicture, "DESCRIPTION", "TYPE", 999));
        db.insert(TABLE_NAME, null, contentValues("NAME", R.drawable.testpicture, "DESCRIPTION", "TYPE", 999));
        db.insert(TABLE_NAME, null, contentValues("NAME", R.drawable.testpicture, "DESCRIPTION", "TYPE", 999));
        db.insert(TABLE_NAME, null, contentValues("NAME", R.drawable.testpicture, "DESCRIPTION", "TYPE", 999));
        db.insert(TABLE_NAME, null, contentValues("NAME", R.drawable.testpicture, "DESCRIPTION", "TYPE", 999));

    }
    */

    public void addGear(FWCGear gear){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, gear.getName());
        values.put(COL_IMAGE, gear.getImage());
        values.put(COL_DESCRIPTION, gear.getDescription());
        values.put(COL_TYPE, gear.getType());
        values.put(COL_PRICE, gear.getPrice());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }



    public List<FWCGear> getAllAsList(){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                null, null, null,
                null, null, null);

        List<FWCGear> gearList = new ArrayList<>();

        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                FWCGear gear = getGearFromCursor(cursor);

                gearList.add(gear);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return gearList;
    }

    private FWCGear getGearFromCursor(Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
        String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
        int imagePath = cursor.getInt(cursor.getColumnIndex(COL_IMAGE));
        String description = cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION));
        String type = cursor.getString(cursor.getColumnIndex(COL_TYPE));
        int price = cursor.getInt(cursor.getColumnIndex(COL_PRICE));

        FWCGear item = new FWCGear(id, name, imagePath, description, type, price);

        return item;
    }

    public FWCGear getItembyID(int id) {
        SQLiteDatabase db = getReadableDatabase();

        String[] selectionArgs = new String[]{String.valueOf(id)};

        Cursor cursor = db.query(TABLE_NAME,
                null,
                COL_ID + " = ?",
                selectionArgs,
                null, null, null);

        if (cursor.moveToFirst()) {

            String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
            int imagePath = cursor.getInt(cursor.getColumnIndex(COL_IMAGE));
            String description = cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION));
            String type = cursor.getString(cursor.getColumnIndex(COL_TYPE));
            int price = cursor.getInt(cursor.getColumnIndex(COL_PRICE));
            return new FWCGear(id, name, imagePath, description, type, price);
        }
        return null;
    }

}
