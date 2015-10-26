package com.example.tomato.notebook.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tomato.notebook.Adapter.MyNotesAdapter;
import com.example.tomato.notebook.MyNotes;
import com.example.tomato.notebook.R;
import com.example.tomato.notebook.db.MyDataSource;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyDataSource myDataSource;
    private List<MyNotes> data;
    private ListView myNotesListView;
    private MyNotesAdapter adapter;
    private Button testButton;
    private TextView textView;

    private ImageView roundButton;

    public static final String CONTENT_ITEM = "Note";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //创建或打开数据库
        myDataSource=new MyDataSource(this);
        data=new ArrayList<>();




        //点击Item进入笔记编辑界面
        myNotesListView = (ListView) findViewById(R.id.note_list);
        myNotesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                textView = (TextView) findViewById(R.id.note_title);
                String note = data.get(position).getNote();
                intent.putExtra(CONTENT_ITEM,note.toString());
                startActivity(intent);
            }
        });

        //新建笔记按钮
        roundButton = (ImageView) findViewById(R.id.round_button);
        roundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.delete_notes:
                myDataSource.deleteAll();//删除所有笔记
                myNotesListView.setAdapter(null);

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    //更新显示
    private  void refreshDisplay(){
        adapter=new MyNotesAdapter(this,data);
        myNotesListView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        myDataSource.open();//打开表
        //显示数据
        if(myDataSource.isEmpty()){
            data=myDataSource.selectAll();
            refreshDisplay();;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        myDataSource.close();
    }

}
