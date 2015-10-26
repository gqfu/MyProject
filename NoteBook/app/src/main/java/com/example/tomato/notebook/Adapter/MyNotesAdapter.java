package com.example.tomato.notebook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tomato.notebook.MyNotes;
import com.example.tomato.notebook.R;

import java.util.List;


public class MyNotesAdapter extends ArrayAdapter<MyNotes> {
    private Context mContext;
    private int mResourceId;

    public MyNotesAdapter(Context context,  List<MyNotes> objects) {
        super(context, R.layout.list_item, objects);
        mContext=context;
        mResourceId= R.layout.list_item;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= LayoutInflater.from(mContext);
        View root=inflater.inflate(mResourceId,parent,false);

        MyNotes item=getItem(position);
        if(item!=null) {
            TextView noteTextView = (TextView) root.findViewById(R.id.note_title);
            noteTextView.setText(String.valueOf(item.getNote()));

        }
        return root;
    }
}
