package com.example.tomato.notebook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.tomato.notebook.MyNotes;
import com.example.tomato.notebook.db.DBSchema;
import com.example.tomato.notebook.db.MyDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于实现CRUD
 */
public class MyDataSource {
    private static final String TAG ="MyDataSource";
    private SQLiteDatabase mDatabase;
    private MyDBHelper myDBHelper;
    private Context mContext;

    public MyDataSource(Context context){
        mContext=context;
        myDBHelper=new MyDBHelper(context);
    }

    //open
    public void open(){
        Log.d(TAG, "database created and opened.");
        mDatabase=myDBHelper.getWritableDatabase();
    }
    //close
    public void close(){
        Log.d(TAG, "database closed.");
        mDatabase.close();
    }

    //insert,成功返回新记录的ID，不成功返回-1
    public  long insert(MyNotes obj){
        if(obj==null){
            return -1;
        }
        ContentValues values=new ContentValues();
        values.put(DBSchema.MyDataClassTable.NOTE_TEXT, obj.getNote());
        long resultId=mDatabase.insert(DBSchema.MyDataClassTable.TABLE_NAME,null,values);
        return resultId;
    }

    //更新数据
    public  long update(MyNotes obj,String str){
        if(obj==null){
            return -1;
        }
        ContentValues values=new ContentValues();
        values.put(DBSchema.MyDataClassTable.NOTE_TEXT, obj.getNote());
        long resultId=mDatabase.update(DBSchema.MyDataClassTable.TABLE_NAME, values,
                DBSchema.MyDataClassTable.NOTE_TEXT + "=" + '?', new String[]{str});
        return resultId;
    }



    //提取所有的记录
    public List<MyNotes> selectAll(){
        List<MyNotes> objs;
        Cursor cursor=mDatabase.query(
                DBSchema.MyDataClassTable.TABLE_NAME,   //表名
                new String[]{               //要提取的字段名
                        DBSchema.MyDataClassTable.NOTE_ID,
                        DBSchema.MyDataClassTable.NOTE_TEXT},
                null,   //where
                null,   //where params
                null,   //groupby
                null,   //having
                null    //orderby
        );
        objs=cursorToList(cursor);
        return objs;
    }


    //将Cursor所引用的所有数据全部读取出来
    private List<MyNotes> cursorToList(Cursor cursor){
        if(cursor==null){
            return null;
        }
        List<MyNotes> objs=new ArrayList<>();
        MyNotes obj=null;
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                obj=readFromCursor(cursor);
                if(obj!=null){
                    objs.add(obj);
                }
                cursor.moveToNext();
            }
        }
        return  objs;
    }

    //删除所有数据
    public int deleteAll(){
        return mDatabase.delete(DBSchema.MyDataClassTable.TABLE_NAME,null,null);
    }

    //删除一条记录
    public int deleteOne(String item){
        return mDatabase.delete(DBSchema.MyDataClassTable.TABLE_NAME,
                DBSchema.MyDataClassTable.NOTE_TEXT + "=" + '?',new String[]{item});
    }

    private MyNotes readFromCursor(Cursor cursor){
        if(cursor==null){
            return null;
        }
        MyNotes obj=new MyNotes();

        obj.setNote(cursor.getString(cursor.getColumnIndex(DBSchema.MyDataClassTable.NOTE_TEXT)));
        return obj;
    }

    //判断数据库是否为空
    public boolean isEmpty(){
        Cursor query=mDatabase.rawQuery("SELECT * from notes",null);
        if(query != null){
            return true;
        }else {
            return false;
        }
    }
}
