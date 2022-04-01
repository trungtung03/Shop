package com.example.appchuadatten.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.example.appchuadatten.interfacee.ClickItemRecyclerView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class ThucPhamMoiNhatAdapter extends RecyclerView.Adapter<ThucPhamMoiNhatAdapter.ViewHolder> {

    Context context;
    List<ThucPhamDangHot> thucPhamList;

    public ThucPhamMoiNhatAdapter(Context context, List<ThucPhamDangHot> thucPhamList) {
        this.context = context;
        this.thucPhamList = thucPhamList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View thucPhamView = LayoutInflater.from(parent.getContext()).inflate(R.layout.man_hinh_chinh_adapter, parent, false);

        ViewHolder holder = new ViewHolder(thucPhamView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThucPhamDangHot thucPhamDangHot = thucPhamList.get(position);
        holder.textViewTen.setText(thucPhamDangHot.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.textViewGia.setText(decimalFormat.format(thucPhamDangHot.getGiasanpham()) + " VNĐ");
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.with(context).load(thucPhamDangHot.getHinhanhsanpham())
                .placeholder(R.drawable.not_image)
                .error(R.drawable.error)
                .into(holder.imageView);
        holder.textViewDiaChi.setText(thucPhamDangHot.getDiachiquanan());
        holder.setClickItemRecyclerView(new ClickItemRecyclerView() {
            @Override
            public void onClick(View view, int position, boolean isClick) {
                if (isClick) {
                    Intent intent = new Intent(context, ChiTiet.class);
                    intent.putExtra("id", thucPhamList.get(position).getId());
                    intent.putExtra("idsp", thucPhamList.get(position).getIdsanpham());
                    intent.putExtra("tensp", thucPhamList.get(position).getTensanpham());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return thucPhamList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textViewTen, textViewGia, textViewDiaChi;

        ClickItemRecyclerView clickItemRecyclerView;

        public void setClickItemRecyclerView(ClickItemRecyclerView clickItemRecyclerView) {
            this.clickItemRecyclerView = clickItemRecyclerView;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageThucPhamHot);
            textViewTen = itemView.findViewById(R.id.textTenThucPhamHot);
            textViewGia = itemView.findViewById(R.id.textGiaThucPhamHot);
            textViewDiaChi = itemView.findViewById(R.id.textDiaChi);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickItemRecyclerView.onClick(v, getAdapterPosition(), true);
                }
            });
        }
    }
}
