package com.example.appchuadatten.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appchuadatten.Oop.ThucPhamDangHot;
import com.example.appchuadatten.R;
import com.example.appchuadatten.activity.GioHang;
import com.example.appchuadatten.activity.TrangChinh;
import com.example.appchuadatten.checkConnect.URL;
import com.example.appchuadatten.interfacee.ClickItemRecyclerView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder> {
    GioHang context;
    List<com.example.appchuadatten.Oop.GioHang> list;

    public GioHangAdapter(GioHang context, List<com.example.appchuadatten.Oop.GioHang> list) {
        this.context = context;
        this.list = list;
    }

    int soluongspplus = 0, soluongspminus = 0;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.man_hinh_gio_hang_adapter, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        com.example.appchuadatten.Oop.GioHang thucPhamGioHang = list.get(position);
        holder.ten.setText(thucPhamGioHang.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.gia.setText(decimalFormat.format(thucPhamGioHang.getGiasanpham()) + " VNĐ");
        holder.diaChi.setText(thucPhamGioHang.getDiachiquanan());
        holder.soLuong.setText(TrangChinh.listGioHang.get(position).getSoluongsanpham() + "");
        Picasso.with(context).load(thucPhamGioHang.getHinhanhsanpham()).placeholder(R.drawable.no_photos).error(R.drawable.error).into(holder.hinh);
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soluongspminus = Integer.parseInt(holder.soLuong.getText().toString().trim());
                soluongspminus -= 1;
                if (soluongspminus == 0) {
                    soluongspminus = 1;
                }
                holder.soLuong.setText(String.valueOf(soluongspminus));
                int soluonghientai = TrangChinh.listGioHang.get(holder.getAdapterPosition()).getSoluongsanpham();
                long giahientai = TrangChinh.listGioHang.get(holder.getAdapterPosition()).getGiasanpham();

                TrangChinh.listGioHang.get(holder.getAdapterPosition()).setSoluongsanpham(soluongspminus);

                long giamoi = (giahientai * soluongspminus) / soluonghientai;
                DecimalFormat decimalFormat1 = new DecimalFormat("###,###,###");
                holder.gia.setText(decimalFormat1.format(giamoi) + " VNĐ");

                DecimalFormat decimalFormat2 = new DecimalFormat("###,###,###");
                GioHang.tongTien.setText(decimalFormat2.format(GioHang.tongGia) + " VNĐ");

                TrangChinh.listGioHang.get(holder.getAdapterPosition()).setGiasanpham((int) giamoi);

                GioHang.EvenUntil();

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.DuongDanUpdateGiaGioHang, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put("giamoi", String.valueOf(giamoi));
                        map.put("id", String.valueOf(list.get(holder.getAdapterPosition()).getIdSp()));
                        if (soluongspminus == 0) {
                            soluongspminus = 1;
                        }
                        map.put("soluong", String.valueOf(soluongspminus));
                        return map;
                    }
                };
                requestQueue.add(stringRequest);

                if (soluongspminus < 10) {
                    holder.plus.setVisibility(View.VISIBLE);
                }
                if (soluongspminus < 2) {
                    Toast.makeText(context, "Số sản phẩm không được nhỏ hơn 1", Toast.LENGTH_SHORT).show();
                    holder.soLuong.setText(String.valueOf(soluongspminus));
                    holder.minus.setVisibility(View.GONE);
                }
            }
        });
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soluongspplus = Integer.parseInt(holder.soLuong.getText().toString().trim());
                soluongspplus += 1;
                int soluonghientai = TrangChinh.listGioHang.get(holder.getAdapterPosition()).getSoluongsanpham();
                long giahientai = TrangChinh.listGioHang.get(holder.getAdapterPosition()).getGiasanpham();

                TrangChinh.listGioHang.get(holder.getAdapterPosition()).setSoluongsanpham(soluongspplus);

                long giamoi = (giahientai * soluongspplus) / soluonghientai;

                DecimalFormat decimalFormat2 = new DecimalFormat("###,###,###");
                holder.gia.setText(decimalFormat2.format(giamoi) + " VNĐ");

                DecimalFormat decimalFormat1 = new DecimalFormat("###,###,###");
                GioHang.tongTien.setText(decimalFormat1.format(GioHang.tongGia) + " VNĐ");

                TrangChinh.listGioHang.get(holder.getAdapterPosition()).setGiasanpham((int) giamoi);

                GioHang.EvenUntil();

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.DuongDanUpdateGiaGioHang, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put("giamoi", String.valueOf(giamoi));
                        map.put("id", String.valueOf(list.get(holder.getAdapterPosition()).getIdSp()));
                        map.put("soluong", String.valueOf(soluongspplus));
                        return map;
                    }
                };
                requestQueue.add(stringRequest);

                holder.soLuong.setText(String.valueOf(soluongspplus));
                if (soluongspplus > 1) {
                    holder.minus.setVisibility(View.VISIBLE);
                }
                if (soluongspplus >= 10) {
                    Toast.makeText(context, "Số sản phẩm không được lớn hơn 10", Toast.LENGTH_SHORT).show();
                    soluongspplus = 10;
                    holder.soLuong.setText(String.valueOf(soluongspplus));
                    holder.plus.setVisibility(View.INVISIBLE);
                }
            }
        });

        holder.setClickItemRecyclerView(new ClickItemRecyclerView() {
            @Override
            public void onClick(View view, int position, boolean isClick) {
                if (isClick) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn có chắc chắn muốn xóa " + list.get(position).getTensanpham() + " khỏi giỏ hàng không?");
                    builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            GioHang.delete(list.get(position).getIdSp(), context);

                            Toast.makeText(context, "Đã xóa " + list.get(position).getTensanpham() + " khỏi giỏ hàng", Toast.LENGTH_SHORT);

                            GioHang.tongGia -= TrangChinh.listGioHang.get(position).getGiasanpham();
                            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                            GioHang.tongTien.setText(decimalFormat.format(GioHang.tongGia) + " VNĐ");

                            TrangChinh.listGioHang.remove(position);
                            if (TrangChinh.listGioHang != null) {
                                GioHang.gioHangAdapter.notifyDataSetChanged();
                            } else {

                            }
                        }
                    });
                    builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView ten, gia, diaChi, soLuong;
        ImageView hinh;
        ImageButton minus, plus;

        ClickItemRecyclerView clickItemRecyclerView;

        public void setClickItemRecyclerView(ClickItemRecyclerView clickItemRecyclerView) {
            this.clickItemRecyclerView = clickItemRecyclerView;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ten = itemView.findViewById(R.id.textViewNameGioHang);
            gia = itemView.findViewById(R.id.textViewGiaGioHang);
            diaChi = itemView.findViewById(R.id.textViewDiaChiGioHang);
            soLuong = itemView.findViewById(R.id.textViewSoLuongGioHang);
            hinh = itemView.findViewById(R.id.imageHinhGioHang);
            minus = itemView.findViewById(R.id.minusGioHang);
            plus = itemView.findViewById(R.id.plusGioHang);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickItemRecyclerView.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            clickItemRecyclerView.onClick(v, getAdapterPosition(), true);
            return false;
        }
    }
}
