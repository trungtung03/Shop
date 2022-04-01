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

import com.example.appchuadatten.Oop.LoaiThucPham;
import com.example.appchuadatten.R;
import com.example.appchuadatten.activity.MiniGameTaiXiu;
import com.example.appchuadatten.interfacee.ClickItemRecyclerView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LuaChonAdapter extends RecyclerView.Adapter<LuaChonAdapter.ViewHolder> {
    Context context;
    List<LoaiThucPham> list;

    public LuaChonAdapter(Context context, List<LoaiThucPham> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lua_chon_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoaiThucPham loaiThucPham = list.get(position);
        Picasso.with(context).load(loaiThucPham.getHinhanhsanpham()).into(holder.imageView);
        holder.textView.setText(loaiThucPham.getTensanpham());

        holder.setClickItemRecyclerView(new ClickItemRecyclerView() {
            @Override
            public void onClick(View view, int position, boolean isClick) {
                if (!isClick) {
                    switch (position) {
                        case 0:
                            Toast.makeText(context, "Hiện tại không có event nào được tổ chức", Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            Toast.makeText(context, "Chưa có sản phẩm nào được freeship", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Toast.makeText(context, "Chuẩn bị đến đợt sale", Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            Toast.makeText(context, "Siêu thị chưa mở", Toast.LENGTH_SHORT).show();
                            break;
                        case 4:
                            Toast.makeText(context, "Đang cập nhật game", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(context, MiniGameTaiXiu.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            context.startActivity(intent);
                            break;
                        case 5:
                            Toast.makeText(context, "Tạo thời chưa có thêm lựa chọn", Toast.LENGTH_SHORT).show();
                            break;
                    }
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
        TextView textView;

        ClickItemRecyclerView clickItemRecyclerView;

        public void setClickItemRecyclerView(ClickItemRecyclerView clickItemRecyclerView) {
            this.clickItemRecyclerView = clickItemRecyclerView;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewLuaChon);
            textView = itemView.findViewById(R.id.textViewLuaChon);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickItemRecyclerView.onClick(v, getAdapterPosition(), false);
        }
    }
}
