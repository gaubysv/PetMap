package com.petmap.petmap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.petmap.petmap.Pet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2017.03.18.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    // Database name
    public static final String DATABASE_NAME = "pets.db";

    // Pets table name
    public static final String TABLE_NAME = "pets_table";

    // pets_table column names
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_TEL = "tel";     //Collar Telephone Number
    public static final String COL_CODE = "code";   //Collar Code
    public static final String COL_YEAR = "year";
    public static final String COL_MOHTH = "month";
    public static final String COL_DAY = "day";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PETS_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                COL_NAME + " TEXT, " + COL_TEL + " TEXT, " + COL_CODE + " TEXT, " + COL_YEAR + " INTEGER, " +
                COL_MOHTH + " INTEGER, " + COL_DAY + " INTEGER )";
        db.execSQL(CREATE_PETS_TABLE);
    }

    // Upgrading Database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
        onCreate(db);
    }

    // Add Pet
    public void addPet(Pet pet) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, pet.getName());
        values.put(COL_TEL, pet.getTelNo());
        values.put(COL_CODE, pet.getCode());
        values.put(COL_YEAR, pet.getYear());
        values.put(COL_MOHTH, pet.getMonth());
        values.put(COL_DAY, pet.getDay());

        database.insert(TABLE_NAME, null, values);
        database.close();
    }

    // Get Pet
    public Pet getPet(int id) {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(TABLE_NAME, new String[] { COL_ID, COL_NAME, COL_TEL, COL_CODE,
                        COL_YEAR, COL_MOHTH, COL_DAY},
                COL_ID + "=?", new String[] { String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        int pet_id = Integer.parseInt(cursor.getString(0));
        String pet_name = cursor.getString(1);
        String pet_telNo = cursor.getString(2);
        String pet_code = cursor.getString(3);
        int pet_year = Integer.parseInt(cursor.getString(4));
        int pet_month = Integer.parseInt(cursor.getString(5));
        int pet_day = Integer.parseInt(cursor.getString(6));

        Pet pet = new Pet(pet_name, pet_telNo, pet_code);

        pet.setId(pet_id);
        pet.setYear(pet_year);
        pet.setMonth(pet_month);
        pet.setDay(pet_day);

        return pet;
    }

    public List<Pet> getAllPets() {
        List<Pet> petList = new ArrayList<>();

        // SELECT ALL Query
        String selectAllQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectAllQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Pet pet = new Pet();
                pet.setId(Integer.parseInt(cursor.getString(0)));
                pet.setName(cursor.getString(1));
                pet.setTelNo(cursor.getString(2));
                pet.setCode(cursor.getString(3));
                pet.setYear(Integer.valueOf(cursor.getString(4)));
                pet.setMonth(Integer.valueOf(cursor.getString(5)));
                pet.setDay(Integer.valueOf(cursor.getString(6)));
                petList.add(pet);
            } while (cursor.moveToNext());
        }
        return petList;
    }

    public void deletePet(Pet pet) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, COL_ID + " = ?", new String[] {String.valueOf(pet.getId())});
        database.close();
    }

    public int getPetsCount() {
        int count = -1;

        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int updatePet(Pet pet) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, pet.getName());
        values.put(COL_TEL, pet.getTelNo());
        values.put(COL_CODE, pet.getCode());
        values.put(COL_YEAR, pet.getYear());
        values.put(COL_MOHTH, pet.getMonth());
        values.put(COL_DAY, pet.getDay());

        return db.update(TABLE_NAME, values, COL_ID + " = ?", new String[] {String.valueOf(pet.getId())});
    }

}
