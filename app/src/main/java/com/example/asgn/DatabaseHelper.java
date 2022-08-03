package com.example.asgn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Game.db";
    private static final String TABLE_NAME = "GamesLog";
    private static final String COL_1 = "gameID";
    private static final String COL_2 = "playDate";
    private static final String COL_3 = "playTime";
    private static final String COL_4 = "duration";
    private static final String COL_5 = "winningStatus";
    private static final int DATABASED_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASED_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +"(gameID INTEGER PRIMARY KEY AUTOINCREMENT, "+"playDate TEXT, playTime TEXT, duration INTEGER, winningStatus TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String pd, String pt, String dt, String vs){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,pd);
        contentValues.put(COL_3,pt);
        contentValues.put(COL_4,dt);
        contentValues.put(COL_5,vs);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result==-1){
            return false;
        } else{
            return true;
        }
    }
    public Cursor getAllData(){
        //get result order by gameID
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM GamesLog ORDER BY gameID DESC", null );
        return res;
    }
    public int getwinstatus(){
        //get record and count value
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT COUNT(winningStatus) FROM GamesLog WHERE winningStatus = 'Win'", null  );
        res.moveToFirst();
        String count = String.valueOf(res.getInt(0));
        res.close();
        return Integer.parseInt(count);
    }
    public int getlosestatus(){
        //get record and count value
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT COUNT(winningStatus) FROM GamesLog WHERE winningStatus = 'Lose'", null  );
        res.moveToFirst();
        String count = String.valueOf(res.getInt(0));
        res.close();
        return Integer.parseInt(count);
    }
    public int getDrawstatus(){
        //get record and count value
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT COUNT(winningStatus) FROM GamesLog WHERE winningStatus = 'Draw'", null  );
        res.moveToFirst();
        String count = String.valueOf(res.getInt(0));
        res.close();
        return Integer.parseInt(count);
    }

}
