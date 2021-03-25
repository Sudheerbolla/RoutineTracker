package com.routinetracker.utils.dbutils;

public class TableLocation {

    public static String userId = "zUserId";
    public static String latitude = "zLatitude";
    public static String longitude = "zLongitude";
    public static String timestamp = "zTimeStamp";
    public static String id = "zID";

    public static String TABLE_NAME = "TableLocation";
    public static String CREATE_TABLE = "create table if not exists " + TABLE_NAME + " ( " + id + " INTEGER PRIMARY KEY NOT NULL, " + userId +
            " TEXT, " + latitude + " TEXT, " + longitude + " TEXT, " + timestamp + " TEXT, UNIQUE (" + timestamp + ") ON CONFLICT REPLACE);";
}
