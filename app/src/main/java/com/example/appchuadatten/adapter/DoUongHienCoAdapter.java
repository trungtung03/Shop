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

public class DoUongHienCoAdapter extends RecyclerView.Adapter<DoUongHienCoAdapter.ViewHolder> {
    Context context;
    List<ThucPhamDangHot> list;

    public DoUongHienCoAdapter(Context context, List<ThucPhamDangHot> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.man_hinh_chinh_adapter_2, parent, false);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThucPhamDangHot thucPham = list.get(position);
        holder.textViewTen.setText(thucPham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.textViewGia.setText(decimalFormat.format(thucPham.getGiasanpham()) + " VNĐ");
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.with(context).load(thucPham.getHinhanhsanpham())
                .placeholder(R.drawable.not_image)
                .error(R.drawable.error)
                .into(holder.imageView);
        holder.textViewDiaChi.setText(thucPham.getDiachiquanan());

        holder.setClickItemRecyclerView(new ClickItemRecyclerView() {
            @Override
            public void onClick(View view, int position, boolean isClick) {
                if (!isClick) {
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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        public TextView textViewTen, textViewGia, textViewDiaChi;

        ClickItemRecyclerView clickItemRecyclerView;

        public void setClickItemRecyclerView(ClickItemRecyclerView clickItemRecyclerView) {
            this.clickItemRecyclerView = clickItemRecyclerView;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageThucPhamDoUongHienCo);
            textViewTen = itemView.findViewById(R.id.textTenThucPhamDoUongHienCo);
            textViewGia = itemView.findViewById(R.id.textGiaThucPhamDoUongHienCo);
            textViewDiaChi = itemView.findViewById(R.id.textDiaChiDoUongHienCo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickItemRecyclerView.onClick(v, getAdapterPosition(), false);
        }
    }
}
