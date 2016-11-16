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
import android.widget.Toast;

import com.colinbradley.fwc.MainActivity;
import com.colinbradley.fwc.R;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by colinbradley on 11/5/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    //CONSTANTS FOR DATABASE
    public static final int DATABASE_VERSION = 8;
    public static final String DATABASE_NAME = "fwcgear.db";

    public static final String TABLE_NAME = "fwctable";

    public static final String COL_ID = "id";
    public static final String COL_IMAGE = "image_path";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_TYPE = "type";
    public static final String COL_PRICE = "price";

    public static final String[] NUMBERS_COLUMNS = {COL_ID, COL_NAME, COL_IMAGE, COL_DESCRIPTION, COL_TYPE, COL_PRICE};

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

    // GET CONTENTVALUES FOR AND ADD NEW GEAR TO DB
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

    //GATHER LIST FROM DATABASE
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

    //GET INDIVIDUAL ITEM BY ID
    public FWCGear getItembyID(int id) {
        SQLiteDatabase db = getReadableDatabase();

        String[] selectionArgs = new String[]{String.valueOf(id)};

        Cursor cursor = db.query(TABLE_NAME,
                null,
                COL_ID + " = ?",
                selectionArgs,
                null, null, null);

        if (cursor.moveToFirst()) {

            FWCGear gear = getGearFromCursor(cursor);
            return gear;
        }
        return null;
    }

    ///CHECKS IF SEARCH IS A NUMBER OR STRING TO DECIDE WHAT
    // QUERY TO USE OR IF USER TYPES "all" IT WILL SHOW ALL ITEMS FROM THE DB AGAIN
    public List<FWCGear> searchGear(String query){
        if (query.matches(".*\\d.*")){
            return searchPriceCheaperThan(query);
        }
        else{
            if (query.equalsIgnoreCase("all")){
                return getAllAsList();
            }
            else if (query.startsWith("is:")){
                return searchType(query.substring(3));
            }
            else {
                return searchByName(query);
            }
        }
    }

    //SEARCH FOR ITEMS BY TYPE (NEEDS "is:" PREFIX IN QUERY)
    public List<FWCGear> searchType(String query){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                NUMBERS_COLUMNS,
                COL_TYPE + " LIKE ?",
                new String[]{"%" + query + "%"},
                null, null, null, null);
        List<FWCGear> gearSearchByType = new LinkedList<>();

        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                FWCGear gear = getGearFromCursor(cursor);

                gearSearchByType.add(gear);
                cursor.moveToNext();
            }
        }
        return gearSearchByType;
    }

    //SEARCH FOR ITEMS LESS EXPENSIVE THAN INT INPUTED
    public List<FWCGear> searchPriceCheaperThan(String query) {
        SQLiteDatabase db = getReadableDatabase();

            Cursor cursor = db.query(TABLE_NAME,
                    NUMBERS_COLUMNS,
                    COL_PRICE + " <= ?",
                    new String[]{query},
                    null, null,
                    COL_PRICE,
                    null);
            List<FWCGear> gearSearchByPriceList = new LinkedList<>();

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    FWCGear gear = getGearFromCursor(cursor);

                    gearSearchByPriceList.add(gear);
                    cursor.moveToNext();
                }
            }
            return gearSearchByPriceList;
    }

    //SEARCH FOR ITEMS BY NAME AND WILL GET ANY ITERATION OF THE QUERY
    public List<FWCGear> searchByName(String query){
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                NUMBERS_COLUMNS,
                COL_NAME + " LIKE ?",
                new String[]{"%" + query + "%"},
                null,null,null,null);

        List<FWCGear> gearSearchByName = new LinkedList<>();

        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                FWCGear gear = getGearFromCursor(cursor);

                gearSearchByName.add(gear);
                cursor.moveToNext();
            }
        }
        return gearSearchByName;
    }

    //GATHERS INFO FROM CURSOR (SEPARATED TO CLEAN UP CODE A BIT INSTEAD OF HAVING IT INSIDE THE 5 METHODS ABOVE)
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

//POPULATE DATABASE IF NEEDED
    public void populateGearTable(){
        SQLiteDatabase db = getWritableDatabase();
        addGear(new FWCGear(1,"Eon Tracer Mask", R.drawable.helmet, "\"It's too much… Turn it off… Let me out!\" —RECORD-449-CHASM-6263", "Helmet", 120));
        addGear(new FWCGear(2,"Eon Tracer Gloves", R.drawable.gloves, "\"I can never find the beginning, the place where the path forks.\" —RECORD 448-CHASM-6808", "Gauntlet", 70));
        addGear(new FWCGear(3,"Eon Tracer Vest", R.drawable.chest, "\"Eternities streamed past me, like shooting stars…\" —RECORD-449-CHASM-6887", "Chest Armor", 120));
        addGear(new FWCGear(4,"Eon Tracer Boots", R.drawable.boots, "\"I feel like every time I find the thread it slips through my fingers.\" —RECORD-448-CHASM-6565", "Boots", 70));
        addGear(new FWCGear(5,"Eon Tracer Cloak", R.drawable.cloak, "\"We trace the history of war throughout the ages.\" —Lakshmi-2", "Class Item", 75));
        addGear(new FWCGear(6,"The Wail", R.drawable.handcannon, "A personal firearm, named and sanctified by the Future War Cult's leaders.", "Hand Cannon", 150));
        addGear(new FWCGear(7,"The Waltz", R.drawable.pulserifle, "A burst-fire weapon, named and sanctified by the Future War Cult's leaders.", "Pulse Rifle", 150));
        addGear(new FWCGear(8,"The Wounded", R.drawable.scoutrifle, "A marksman's weapon, named and sanctified by the Future War Cult's leaders.", "Scout Rifle", 150));
        addGear(new FWCGear(9,"The Waiting", R.drawable.fusionrifle, "An advanced energy weapon, named and sanctified by the Future War Cult's leaders.", "Fusion Rifle", 150));
        addGear(new FWCGear(10,"The Wormwood", R.drawable.sidearm, "A personal firearm, named and sanctified by the Future War Cult's leaders.", "Sidearm", 150));
        addGear(new FWCGear(11,"The Warpath", R.drawable.rocketlauncher, "A high-impact weapon, named and sanctified by the Future War Cult's leaders.", "Rocket Launcher", 150));
        addGear(new FWCGear(12,"Bellicose Shell", R.drawable.ghostshell, "For Ghosts who profess allegiance to the Future War Cult.", "Ghost Shell", 75));
        addGear(new FWCGear(13,"Eternally Assured Destruction", R.drawable.sparrow, "\"Technically, it's not a loss; it's a draw.\" —Lakshmi-2", "Sparrow", 120));
        addGear(new FWCGear(14,"The Climb", R.drawable.theclimbship, "There is nothing else.", "Ship", 500));
        addGear(new FWCGear(15,"The Teilhard War", R.drawable.ship2, "All things converge.", "Ship", 500));
        addGear(new FWCGear(16,"The Road Untraveled", R.drawable.theroaduntraveled, "Take out the stitches. Start over.", "Ship", 500));
        addGear(new FWCGear(17,"Babylon 9191", R.drawable.babylon9191, "Equip this shader to change the color of your armor.", "Shader", 180));
        addGear(new FWCGear(18,"Carthage 0100", R.drawable.carthage0100, "Equip this shader to change the color of your armor.", "Shader", 180));
        addGear(new FWCGear(19,"Tyre 4770", R.drawable.tyre4770, "Equip this shader to change the color of your armor.", "Shader", 180));
        addGear(new FWCGear(20,"Nineveh 8611", R.drawable.nineveh8611, "Equip this shader to change the color of your armor.", "Shader", 180));
    }
}
