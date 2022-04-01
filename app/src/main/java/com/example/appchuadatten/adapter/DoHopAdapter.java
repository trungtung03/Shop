package com.example.appchuadatten.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchuadatten.Oop.ThucPhamDangHot;
import com.example.appchuadatten.R;
import com.example.appchuadatten.activity.ChiTiet;
import com.example.appchuadatten.interfacee.ClickItemRecyclerView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class DoHopAdapter extends RecyclerView.Adapter<DoHopAdapter.ViewHolder> {
    Context context;
    List<ThucPhamDangHot> list;

    public DoHopAdapter(Context context, List<ThucPhamDangHot> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.man_hinh_dohop_adapter, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThucPhamDangHot doHop = list.get(position);
        holder.textViewName.setText(doHop.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.textViewGia.setText(decimalFormat.format(doHop.getGiasanpham()) + " VNĐ");
        holder.textViewAddress.setText(doHop.getDiachiquanan());
        Picasso.with(context).load(doHop.getHinhanhsanpham()).placeholder(R.drawable.no_photos).error(R.drawable.error).into(holder.imageView);
        holder.setClickItemRecyclerView(new ClickItemRecyclerView() {
            @Override
            public void onClick(View view, int position, boolean isClick) {
                if(!isClick){
                    Intent intent = new Intent(context, ChiTiet.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("id", list.get(position).getId());
                    intent.putExtra("idsp", list.get(position).getIdsanpham());
                    intent.putExtra("tensp", list.get(position).getTensanpham());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewName, textViewGia, textViewAddress;

        ClickItemRecyclerView clickItemRecyclerView;

        public void setClickItemRecyclerView(ClickItemRecyclerView clickItemRecyclerView) {
            this.clickItemRecyclerView = clickItemRecyclerView;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
         imageView = itemView.findViewById(R.id.imageViewDoHop);
         textViewName = itemView.findViewById(R.id.textViewNameDoHop);
         textViewGia = itemView.findViewById(R.id.textViewGiaDoHop);
         textViewAddress = itemView.findViewById(R.id.textViewDiaChiDoHop);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickItemRecyclerView.onClick(v, getAdapterPosition(), false);
                }
            });
        }
    }

}
