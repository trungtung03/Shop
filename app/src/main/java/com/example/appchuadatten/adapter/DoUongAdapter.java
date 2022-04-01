package com.example.appchuadatten.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchuadatten.Oop.ThucPhamDangHot;
import com.example.appchuadatten.R;
import com.example.appchuadatten.activity.ChiTiet;
import com.example.appchuadatten.interfacee.ClickItemRecylerView2;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class DoUongAdapter extends RecyclerView.Adapter<DoUongAdapter.ViewHolder> {
    Context context;
    List<ThucPhamDangHot> list;

    public DoUongAdapter(Context context, List<ThucPhamDangHot> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.man_hinh_douong_adapter, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThucPhamDangHot thucPham = list.get(position);
        holder.ten.setText(thucPham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.gia.setText(decimalFormat.format(thucPham.getGiasanpham()) + " VNĐ");
        holder.diaChi.setText(thucPham.getDiachiquanan());
        Picasso.with(context).load(thucPham.getHinhanhsanpham()).placeholder(R.drawable.no_photos).error(R.drawable.error).into(holder.imageView);

        holder.setClickItemRecylerView2(new ClickItemRecylerView2() {
            @Override
            public void isClickItem(View view, int positon, boolean isClick) {
                if(!isClick) {
                    Toast.makeText(context, list.get(holder.getAdapterPosition()).getTensanpham(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, ChiTiet.class);
                    intent.putExtra("id", list.get(holder.getAdapterPosition()).getId());
                    intent.putExtra("idsp", list.get(holder.getAdapterPosition()).getIdsanpham());
                    intent.putExtra("tensp", list.get(holder.getAdapterPosition()).getTensanpham());
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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView imageView;
        TextView ten, gia, diaChi;

        ClickItemRecylerView2 clickItemRecylerView2;

        public void setClickItemRecylerView2(ClickItemRecylerView2 clickItemRecylerView2) {
            this.clickItemRecylerView2 = clickItemRecylerView2;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageThucPhamDoUong);
            ten = itemView.findViewById(R.id.textTenThucPhamDoUong);
            gia = itemView.findViewById(R.id.textGiaThucPhamDoUong);
            diaChi = itemView.findViewById(R.id.textDiaChiDoUong);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickItemRecylerView2.isClickItem(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            clickItemRecylerView2.isClickItem(v,getAdapterPosition(), true);
            return false;
        }
    }
}
