package com.example.appchuadatten.adapter;

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

public class DoTrangMiengMoiNhatAdapter extends RecyclerView.Adapter<DoTrangMiengMoiNhatAdapter.ViewHolder> {
    Context context;
    List<ThucPhamDangHot> list;

    public DoTrangMiengMoiNhatAdapter(Context context, List<ThucPhamDangHot> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.man_hinh_dotrangmiengmoinhat_adapter, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThucPhamDangHot doTrangMieng = list.get(position);
        holder.textViewName.setText(doTrangMieng.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.textViewGia.setText(decimalFormat.format(doTrangMieng.getGiasanpham()) + " VNĐ");
        holder.textViewAddress.setText(doTrangMieng.getDiachiquanan());
        Picasso.with(context).load(doTrangMieng.getHinhanhsanpham()).placeholder(R.drawable.not_image).error(R.drawable.error).into(holder.imageView);

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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textViewName, textViewGia, textViewAddress;

        ClickItemRecyclerView clickItemRecyclerView;

        public void setClickItemRecyclerView(ClickItemRecyclerView clickItemRecyclerView) {
            this.clickItemRecyclerView = clickItemRecyclerView;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageDoTrangMiengMoiNhat);
            textViewName = itemView.findViewById(R.id.textViewNameDoTrangMiengMoiNhat);
            textViewGia = itemView.findViewById(R.id.textViewGiaDoTrangMiengMoiNhat);
            textViewAddress = itemView.findViewById(R.id.textViewDiaChiDoTrangMiengMoiNhat);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickItemRecyclerView.onClick(v, getAdapterPosition(), false);
        }
    }
}
