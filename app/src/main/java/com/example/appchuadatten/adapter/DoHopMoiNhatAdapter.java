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

public class DoHopMoiNhatAdapter extends RecyclerView.Adapter<DoHopMoiNhatAdapter.Viewholder> {
    Context context;
    List<ThucPhamDangHot> list;

    public DoHopMoiNhatAdapter(Context context, List<ThucPhamDangHot> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.man_hinh_dohopmoinhat_adapter, null);
        Viewholder holder = new Viewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        ThucPhamDangHot doHopMoiNhat = list.get(position);
        holder.ten.setText(doHopMoiNhat.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.gia.setText(decimalFormat.format(doHopMoiNhat.getGiasanpham()) + " VNĐ");
        holder.diaChi.setText(doHopMoiNhat.getDiachiquanan());
        Picasso.with(context).load(doHopMoiNhat.getHinhanhsanpham()).placeholder(R.drawable.no_photos).error(R.drawable.error).into(holder.imageView);

        holder.setClickItemRecyclerView(new ClickItemRecyclerView() {
            @Override
            public void onClick(View view, int position, boolean isClick) {
                if(!isClick){
                    Intent intent = new Intent(context, ChiTiet.class);
                    intent.putExtra("id", list.get(position).getId());
                    intent.putExtra("idsp", list.get(position).getIdsanpham());
                    intent.putExtra("tensp", list.get(position).getTensanpham());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView ten, gia, diaChi;

        ClickItemRecyclerView clickItemRecyclerView;

        public void setClickItemRecyclerView(ClickItemRecyclerView clickItemRecyclerView) {
            this.clickItemRecyclerView = clickItemRecyclerView;
        }

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewDoHopMoiNhat);
            ten = itemView.findViewById(R.id.textViewNameDoHopMoiNhat);
            gia = itemView.findViewById(R.id.textViewGiaDoHopMoiNhat);
            diaChi = itemView.findViewById(R.id.textViewDiaChiDoHopMoiNhat);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickItemRecyclerView.onClick(v, getAdapterPosition(), false);
                }
            });
        }
    }
}
