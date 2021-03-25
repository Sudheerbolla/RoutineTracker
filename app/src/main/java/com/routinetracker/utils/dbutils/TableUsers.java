package com.routinetracker.utils.dbutils;

public class TableUsers {

    public static String name = "zName";
    public static String emailId = "zEmailId";
    public static String password = "zPassword";
    public static String dob = "zDOB";
    public static String favNumber = "zFavNumber";
    public static String encryption = "zEncryption";
    public static String userType = "zUserType";
    public static String id = "zID";

    public static String TABLE_NAME = "TableUsers";
    public static String CREATE_TABLE = "create table if not exists " + TABLE_NAME + " ( " + id + " INTEGER PRIMARY KEY NOT NULL, " + name +
            " TEXT, " + favNumber + " INTEGER, " + dob + " TEXT, " + password + " BLOB, " + encryption + " BLOB, " + userType + " TEXT, " + emailId + " TEXT, UNIQUE (" + emailId + ") ON CONFLICT REPLACE);";
}
