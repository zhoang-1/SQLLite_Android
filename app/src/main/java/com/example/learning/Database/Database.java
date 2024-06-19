package com.example.learning.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public static final String DB_NAME ="BeerProduct.sqlite";
    public static final int DB_VERSION = 1;
    public static final String TBL_NAME = "Beer";
    public static final String COL_CODE = "BeerCode";
    public static final String COL_NAME = "BeerName";
    public static final String COL_PRICE = "BeerPrice";

    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = " CREATE TABLE IF NOT EXISTS " + TBL_NAME + " ( "
                + COL_CODE + "INTEGER PRIMARY KEY AUTOINCREMENT,  "
                + COL_NAME + " VARCHAR(50) ,"
                + COL_PRICE + "REAL  ) ";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_NAME);
        onCreate(db);
    }
    // select...
    public Cursor queryData(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql , null);
    }

    // insert , update , delete
    public void execSql (String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }
    // IF EXISTS DATA
    public int getNumOfRows (){
        Cursor cursor =queryData("SELECT * FROM" + TBL_NAME  + ";");
        int nRows = cursor.getCount();
        cursor.close();
        return nRows;
    }
    public  void createFakeData ( ){
        if(getNumOfRows() == 0 ){
            try {
                execSql("INSERT INTO " + TBL_NAME + " VALUES(NULL, 'Henikein', '19500')");
                execSql("INSERT INTO " + TBL_NAME + " VALUES(NULL, 'Tiger', '19500')");
                execSql("INSERT INTO " + TBL_NAME + " VALUES(NULL, '333', '19500')");
                execSql("INSERT INTO " + TBL_NAME + " VALUES(NULL, 'SaiGon', '19500')");
                execSql("INSERT INTO " + TBL_NAME + " VALUES(NULL, '1664', '19500')");
                execSql("INSERT INTO " + TBL_NAME + " VALUES(NULL, 'Cocacola', '19500')");
                execSql("INSERT INTO " + TBL_NAME + " VALUES(NULL, 'Pepsi', '19500')");
                execSql("INSERT INTO " + TBL_NAME + " VALUES(NULL, 'Mirinda', '19500')");
            }catch (Exception e) {
                Log.e("Error : " , e.getMessage().toString());
            }
        }
    }
}
