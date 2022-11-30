package com.example.lab_map_matveev493;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {
    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Settings (Api TEXT, colorCoastline TEXT, colorRiver TEXT, colorRoad TEXT, colorRailroad TEXT, cacheTime TEXT)";
        db.execSQL(sql);
        sql = "CREATE TABLE Cache (x TEXT, y TEXT, scale TEXT, bmp TEXT, cacheTime TEXT)";
        db.execSQL(sql);
        sql = "CREATE TABLE Position (x TEXT, y TEXT, level TEXT)";
        db.execSQL(sql);
    }

    public void setLastPosition(float x, float y, int level)
    {
        String sql = "DELETE FROM Position";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
        sql = "INSERT INTO Position (x, y, level) VALUES('" + x + "', '" + y + "', '" + level + "');";
        db = getWritableDatabase();
        db.execSQL(sql);
    }
    public void setDefaultSetting(String api, String colorCoastline, String colorRiver, String colorRoad, String colorRailroad, String cacheTime)
    {
        String sql = "DELETE FROM Settings";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
        sql = "INSERT INTO Settings (Api, colorCoastline, colorRiver, colorRoad, colorRailroad, cacheTime) VALUES('" + api + "', '" + colorCoastline + "', '" + colorRiver + "', '" + colorRoad + "', '" + colorRailroad + "', '" + cacheTime + "');";
        db = getWritableDatabase();
        db.execSQL(sql);
    }

    public void addCache(String x, String y, String scale, String bmp, String time)
    {
        String sql = "INSERT INTO Cache (x, y, scale, bmp, cacheTime) VALUES('" + x + "', '" + y + "', '" + scale + "', '" + bmp + "', '" + time + "');";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }
    public String getCache(String x, String y, String scale)
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT bmp FROM Cache WHERE x = '" + x + "' AND y = '" + y + "' AND scale = '" + scale + "';";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst() == true) return cur.getString(0);
        return "0.0f";
    }
    public String getCacheTime()
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT cacheTime FROM Settings;";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst() == true) return cur.getString(0);
        return "0.0f";
    }
    public String getTime()
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT cacheTime FROM Cache;";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst() == true) return cur.getString(0);
        return "0.0f";
    }
    public String getLastPositionX()
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT x FROM Position;";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst() == true) return cur.getString(0);
        return "0.0f";
    }
    public String getLastPositionY()
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT y FROM Position;";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst() == true) return cur.getString(0);
        return "0.0f";
    }
    public String getLastPositionLevel()
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT level FROM Position;";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst() == true) return cur.getString(0);
        return "0";
    }


    public String getApi()
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT Api FROM Settings;";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst() == true) return cur.getString(0);
        return "0";
    }
    public String getColorCoastline()
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT colorCoastline FROM Settings;";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst() == true) return cur.getString(0);
        return "0";
    }
    public String getColorRiver()
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT colorRiver FROM Settings;";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst() == true) return cur.getString(0);
        return "0";
    }
    public String getColorRoad()
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT colorRoad FROM Settings;";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst() == true) return cur.getString(0);
        return "0";
    }
    public String getColorRailroad()
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT colorRailroad FROM Settings;";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst() == true) return cur.getString(0);
        return "0";
    }

    public void clearCache()
    {
        String sql = "DELETE FROM Cache";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
