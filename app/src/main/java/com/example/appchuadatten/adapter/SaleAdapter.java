package com.example.appchuadatten.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchuadatten.Oop.RandomNumber;
import com.example.appchuadatten.R;

import java.util.List;

public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.ViewHolder> {
    Context context;
    List<RandomNumber> list;
    List<RandomNumber> list2;

    public SaleAdapter(Context context, List<RandomNumber> list,List<RandomNumber> list2) {
        this.context = context;
        this.list = list;
        this.list2 = list2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.man_hinh_sale_adapter, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RandomNumber randomNumber = list.get(position);
        RandomNumber randomNumber2 = list2.get(position);
        holder.gia.setText(randomNumber.getNumber() + "đ");
        holder.gia2.setText(randomNumber2.getNumber() + "đ");
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Chưa có gì để sưu tầm", Toast.LENGTH_SHORT).show();
            }
        });
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Chưa có gì để hiển thị thêm", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView gia, gia2;
        Button button;
        ImageView more;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gia = itemView.findViewById(R.id.tV);
            gia2 = itemView.findViewById(R.id.tV4);
            button = itemView.findViewById(R.id.btnSuuTam);
            more = itemView.findViewById(R.id.more);
        }
    }
}
