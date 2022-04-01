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
import com.example.appchuadatten.activity.TrangChinh;
import com.example.appchuadatten.interfacee.ClickItemRecyclerView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LuaChonDoTrangMiengAdapter extends RecyclerView.Adapter<LuaChonDoTrangMiengAdapter.ViewHolder> {
    Context context;
    List<LoaiThucPham> loaiThucPhamList;

    public LuaChonDoTrangMiengAdapter(Context context, List<LoaiThucPham> loaiThucPhamList) {
        this.context = context;
        this.loaiThucPhamList = loaiThucPhamList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lua_chon_dotrangmieng_adapter, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoaiThucPham loaiThucPham = loaiThucPhamList.get(position);
        holder.textView.setText(loaiThucPham.getTensanpham());
        Picasso.with(context).load(loaiThucPham.getHinhanhsanpham()).placeholder(R.drawable.no_photos).error(R.drawable.error).into(holder.imageView);

        holder.setClickItemRecyclerView(new ClickItemRecyclerView() {
            @Override
            public void onClick(View view, int position, boolean isClick) {
                if (!isClick) {
                    switch (position) {
                        case 0:
                            Toast.makeText(context,"Hiện tại không có món hàng nào đang được freeship", Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            Toast.makeText(context,"Chuẩn bị có event rồi nha", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Toast.makeText(context,"Hiện tại chưa có sản phẩm nào đang sale", Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            Toast.makeText(context,"Xin lỗi quý khách, dịch vụ này sẽ sớm được mở", Toast.LENGTH_SHORT).show();
                            break;
                        case 4:
                            Toast.makeText(context,"Quay trở lại trang chính", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, TrangChinh.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            break;
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return loaiThucPhamList.size();
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
            imageView = itemView.findViewById(R.id.imageViewLuaChonDoTrangMieng);
            textView = itemView.findViewById(R.id.textViewLuaChonDoTrangMieng);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickItemRecyclerView.onClick(v, getAdapterPosition(), false);
        }
    }
}
