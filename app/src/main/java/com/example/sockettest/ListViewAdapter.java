package com.example.sockettest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2019/4/26.
 */

public class ListViewAdapter extends BaseAdapter {
    Context mcontext;
    ArrayList<String>list;
    public ListViewAdapter(Context context, ArrayList<String>list){
        this.mcontext=context;
        this.list=list;
    }
    public void additem(ArrayList<String>list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    public  void refresh(){
        this.list.clear();
        for(int i=0;i<15;i++){
            this.list.add("数据"+i);
        }
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater=LayoutInflater.from(mcontext);
            convertView=inflater.inflate(R.layout.layout_listview_item,parent,false);
            viewHolder.text_item=convertView.findViewById(R.id.item_tv);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.text_item.setText(list.get(position));
        return convertView;
    }
    class ViewHolder {
        private TextView text_item;
    }
}
