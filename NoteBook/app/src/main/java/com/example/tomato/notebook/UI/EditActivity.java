package com.example.tomato.notebook.UI;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.tomato.notebook.MyNotes;
import com.example.tomato.notebook.R;
import com.example.tomato.notebook.db.MyDBHelper;
import com.example.tomato.notebook.db.MyDataSource;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    private SQLiteDatabase database;
    private MyDBHelper dbHelper;
    private EditText editText;
    private MyDataSource myDataSource;
    private String beforeStr = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        editText = (EditText) findViewById(R.id.edit_note);

        Intent intent = getIntent();
        String s = intent.getStringExtra(MainActivity.CONTENT_ITEM);

        if(s!=null){
            editText.setText(s);
            beforeStr = editText.getText().toString();
        }



        //创建或打开数据库
        myDataSource=new MyDataSource(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        myDataSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();

        //插入数据
        if(beforeStr.length()==0 && !beforeStr.equals(editText.getText().toString())){
            insertData();
        }

        //更新数据
        if(beforeStr.length()>0 && !beforeStr.equals(editText.getText().toString())){
            updateData();

        }
        myDataSource.close();
    }

    //插入一条数据
    private void insertData() {
        MyNotes obj = getMyNotes();
        myDataSource.insert(obj);

    }

    //更新数据
    private void updateData() {
        MyNotes obj = getMyNotes();
        myDataSource.update(obj, beforeStr);

    }


    @NonNull
    private MyNotes getMyNotes() {
        MyNotes obj=new MyNotes();
        obj.setNote(editText.getText().toString());
        obj.getNote();
        return obj;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.delete_note:
                    myDataSource.deleteOne(editText.getText().toString());//单击删除按钮时删除当前笔记
                    Intent intent = new Intent(this,MainActivity.class);
                    startActivity(intent);
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
