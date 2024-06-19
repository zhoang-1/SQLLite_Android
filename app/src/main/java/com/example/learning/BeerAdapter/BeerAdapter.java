package com.example.learning.BeerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.learning.MainActivity;
import com.example.learning.Model.Beer;
import com.example.learning.R;

import java.util.List;

public class BeerAdapter extends BaseAdapter {
    MainActivity context;
    int item_layout;
    List<Beer>beers;
    //constructor
    public BeerAdapter(MainActivity context, int item_layout, List<Beer> beers) {
        this.context = context;
        this.item_layout = item_layout;
        this.beers = beers;
    }

    @Override
    public int getCount() {
        return beers.size();
    }

    @Override
    public Object getItem(int position) {
        return beers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(item_layout , null);
            holder.txtNamebeer = convertView.findViewById(R.id.txtNamebeer);
            holder.imvEdit = convertView.findViewById(R.id.imvEdit);
            holder.imvDelete = convertView.findViewById(R.id.imvDelete);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        // binding data
        Beer b =beers.get(position);
        holder.txtNamebeer.setText(b.getBeerName() + " - " +  String.format("%.0f VND", b.getBeerPrice() ));
        holder.imvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.openEditDialog(b);
            }
        });
        holder.imvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.openDeleteDialog(b);
            }
        });
        return convertView;
    }
    public static class ViewHolder{
        TextView txtNamebeer;
        ImageView imvEdit;
        ImageView imvDelete;
    }

}
