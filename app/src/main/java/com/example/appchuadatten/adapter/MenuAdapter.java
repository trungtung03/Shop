package com.example.appchuadatten.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appchuadatten.Oop.LoaiThucPham;
import com.example.appchuadatten.Oop.ThucPhamDangHot;
import com.example.appchuadatten.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MenuAdapter extends BaseAdapter {
    Context context;
    List<LoaiThucPham> list;

    public MenuAdapter(Context context, List<LoaiThucPham> list) {
        this.context = context;
        this.list = list;
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

    class ViewHolder {
        ImageView imageView;
        TextView textView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.menu_adapter, null);
            holder.imageView = convertView.findViewById(R.id.imageViewMenu);
            holder.textView = convertView.findViewById(R.id.textViewMenu);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        LoaiThucPham loaiThucPham = list.get(position);
        holder.textView.setText(loaiThucPham.getTensanpham());
        Picasso.with(context).load(loaiThucPham.getHinhanhsanpham()).into(holder.imageView);
        return convertView;
    }
}
