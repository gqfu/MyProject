package com.example.tomato.notebook.db;

/**
 * 用于封装数据库相关的信息
 */
public class DBSchema {

    public static final String DB_NAME="mydata.db";

    public static final int DB_VERSION=1;

    public static class MyDataClassTable{
        public static final String TABLE_NAME = "notes";
        public static final String NOTE_ID = "_id";
        public static final String NOTE_TEXT = "noteText";
        //建表语句
        public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME+" ("+NOTE_ID
                +" INTEGER PRIMARY KEY AUTOINCREMENT, "+NOTE_TEXT+ " TEXT)";
    }
}
