package com.example.appchuadatten.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appchuadatten.R;
import com.example.appchuadatten.adapter.GioHangAdapter;
import com.example.appchuadatten.checkConnect.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class GioHang extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    CheckBox checkBoxAll;
    RecyclerView recyclerViewGioHang;
    public static TextView tongTien;
    static Button thanhToan;

    public static GioHangAdapter gioHangAdapter;

    public static int tongGia = 0, count = 0;

    public static int soluongsp = 0;

    public static int tinhTien = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        anhXa();
        actionToolBar();
        getDataGioHang(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar_giohang, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuTrangChinhGioHang:
                startActivity(new Intent(GioHang.this, TrangChinh.class));
                showToast("Quay về trang chính", getApplicationContext());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void EvenUntil() {
        tinhTien = 0;
        for (int i = 0; i < TrangChinh.listGioHang.size(); i++) {
            tinhTien += TrangChinh.listGioHang.get(i).getGiasanpham();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongTien.setText(decimalFormat.format(tinhTien) + " VNĐ");
    }

    static void getDataGioHang(Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL.DuongDanGioHang, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    int id = 0;
                    String ten = "";
                    String hinhanh = "";
                    int gia = 0;
                    String diachi = "";

                    TrangChinh.listGioHang.clear();
                    tongGia = 0;
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        id = jsonObject.getInt("id");
                        ten = jsonObject.getString("tensanpham");
                        hinhanh = jsonObject.getString("hinhanhsanpham");
                        gia = jsonObject.getInt("giasanpham");
                        diachi = jsonObject.getString("diachiquanan");
                        soluongsp = jsonObject.getInt("soluongsanpham");

                        TrangChinh.listGioHang.add(new com.example.appchuadatten.Oop.GioHang(id, ten, hinhanh, gia, diachi, soluongsp));
                        gioHangAdapter.notifyDataSetChanged();

                        tongGia += gia;
                    }
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    tongTien.setText(decimalFormat.format(tongGia) + " VNĐ");
                    thanhToan.setText("Thanh toán" + "(" + TrangChinh.listGioHang.size() + ")");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public static void delete(int position, Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.DuongDanDeleteGioHang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {
                    getDataGioHang(context);
                } else {
                    showToast("Erro", context);
                }
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
                map.put("delete", String.valueOf(position));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void actionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhXa() {
        toolbar = findViewById(R.id.toolbar_GioHang);
        recyclerViewGioHang = findViewById(R.id.recyclerViewGioHang);
        tongTien = findViewById(R.id.textViewTongTienGioHang);
        thanhToan = findViewById(R.id.btnThanhToanGioHang);

        thanhToan.setOnClickListener(this);
        gioHangAdapter = new GioHangAdapter(GioHang.this, TrangChinh.listGioHang);
        recyclerViewGioHang.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerViewGioHang.setAdapter(gioHangAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnThanhToanGioHang:
                showToast("Thanh toán", getApplicationContext());
                break;
        }
    }

    public static void showToast(String str, Context context) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }
}