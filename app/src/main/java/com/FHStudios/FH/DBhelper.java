package com.FHStudios.FH;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME= "Food_data.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_FD";
    private static final String COLUMN_NUTRIENT="component";
    private static final String COLUMN_QUANTITY="quantity";
    private static final String COLUMN_GRMG="type";

    public DBhelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query=
                "CREATE TABLE " + TABLE_NAME +
                        " (" +COLUMN_NUTRIENT + " TEXT, " +
                        COLUMN_QUANTITY + " TEXT, " + COLUMN_GRMG +" INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(db);
    }
    public void addData(String nutr, String qntt, int grmg){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put(COLUMN_NUTRIENT, nutr);
        cv.put(COLUMN_QUANTITY, qntt);
        cv.put(COLUMN_GRMG, grmg);
        long result=db.insert(TABLE_NAME, null,cv);
        if(result==-1) Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
        else Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();

    }
}
