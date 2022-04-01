package com.example.appchuadatten.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appchuadatten.Oop.RandomNumber;
import com.example.appchuadatten.Oop.ThucPhamDangHot;
import com.example.appchuadatten.R;
import com.example.appchuadatten.adapter.ChiTietThucPhamAdapter;
import com.example.appchuadatten.adapter.SaleAdapter;
import com.example.appchuadatten.checkConnect.CheckConnect;
import com.example.appchuadatten.checkConnect.URL;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ChiTiet extends AppCompatActivity implements View.OnClickListener {

    ImageView back, hinhAnhSanPham, imageViewTrangChinh, imageViewChat;
    Toolbar toolbar;
    TextView textViewTen, textViewGia, textViewDiaChi;
    RecyclerView recyclerViewSale, recyclerViewThucPham;
    Button buttonThemGioHang, buttonMuaNgay;

    int idchitiet = 0, idthucphamchitiet = 0, page = 1;

    List<ThucPhamDangHot> listThucPham;
    ChiTietThucPhamAdapter chiTietThucPhamAdapter;

    List<RandomNumber> randomNumberList, randomNumberList2;
    SaleAdapter saleAdapter;

    int soluongsanpham = 0, gia = 0;
    String ten = "", hinh = "", diachi = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        if (CheckConnect.checkConnection(getApplicationContext())) {
            anhXa();
            getIdThucPham();
            actionToolBar();
            getDataChiTiet(page);
            getThucPhamLienQuan(page);
        } else {
            showToast("không có kết nối internet");
            finish();
        }
    }

    private void getThucPhamLienQuan(int Page) {
        String url = URL.DuongDanThucPham + Page;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String tensanpham = "";
                String hinhanhsanpham = "";
                int giasanpham = 0;
                String diachiquanan = "";
                int idsanpham = 0;
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        id = jsonObject.getInt("id");
                        tensanpham = jsonObject.getString("tensanpham");
                        hinhanhsanpham = jsonObject.getString("hinhanhsanpham");
                        giasanpham = jsonObject.getInt("giasanpham");
                        diachiquanan = jsonObject.getString("diachiquanan");
                        idsanpham = jsonObject.getInt("idsanpham");
                        listThucPham.add(new ThucPhamDangHot(id, tensanpham, hinhanhsanpham, giasanpham, diachiquanan, idsanpham));
                        chiTietThucPhamAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
                map.put("idsanpham", String.valueOf(idthucphamchitiet));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void getDataChiTiet(int Page) {
        String url = URL.DuongDanChiTietSanPham + Page;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String tensanpham = "";
                String hinhanhsanpham = "";
                int giasanpham = 0;
                String diachiquanan = "";
                int idsanpham = 0;
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        id = jsonObject.getInt("id");
                        tensanpham = jsonObject.getString("tensanpham");
                        hinhanhsanpham = jsonObject.getString("hinhanhsanpham");
                        giasanpham = jsonObject.getInt("giasanpham");
                        diachiquanan = jsonObject.getString("diachiquanan");
                        idsanpham = jsonObject.getInt("idsanpham");

                        ten = jsonObject.getString("tensanpham");
                        hinh = jsonObject.getString("hinhanhsanpham");
                        gia = jsonObject.getInt("giasanpham");
                        diachi = jsonObject.getString("diachiquanan");
                        soluongsanpham = 1;

                        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                        textViewTen.setText(tensanpham);
                        Picasso.with(getApplicationContext()).load(hinhanhsanpham).placeholder(R.drawable.no_photos)
                                .error(R.drawable.error).into(hinhAnhSanPham);
                        textViewGia.setText(decimalFormat.format(giasanpham) + " VNĐ");
                        textViewDiaChi.setText(diachiquanan);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
                map.put("idchitiet", String.valueOf(idchitiet));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void actionToolBar() {
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar_chitiet, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuGioHangChiTiet:
                showToast("Open giỏ hàng");
                startActivity(new Intent(ChiTiet.this, GioHang.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getIdThucPham() {
        idchitiet = getIntent().getIntExtra("id", -1);

        idthucphamchitiet = getIntent().getIntExtra("idsp", -1);
    }

    private void anhXa() {
        back = findViewById(R.id.imageBackChiTiet);
        hinhAnhSanPham = findViewById(R.id.imageViewChiTiet);
        imageViewTrangChinh = findViewById(R.id.imageTrangChinhChiTiet);
        imageViewChat = findViewById(R.id.imageChatChiTiet);
        toolbar = findViewById(R.id.toolbar_chitiet);
        textViewTen = findViewById(R.id.textViewTenChiTiet);
        textViewGia = findViewById(R.id.textViewGiaChiTiet);
        textViewDiaChi = findViewById(R.id.textViewDiaChiChiTiet);
        recyclerViewSale = findViewById(R.id.recyclerViewSaleChiTiet);
        recyclerViewThucPham = findViewById(R.id.recyclerViewSanPhamChiTiet);
        buttonThemGioHang = findViewById(R.id.btnThemGioHangChiTiet);
        buttonMuaNgay = findViewById(R.id.btnMuaNgayChiTiet);

        back.setOnClickListener(this);
        imageViewTrangChinh.setOnClickListener(this);
        imageViewChat.setOnClickListener(this);
        buttonThemGioHang.setOnClickListener(this);
        buttonMuaNgay.setOnClickListener(this);

        listThucPham = new ArrayList<>();
        chiTietThucPhamAdapter = new ChiTietThucPhamAdapter(getApplicationContext(), listThucPham);
        recyclerViewThucPham.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerViewThucPham.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewThucPham.setAdapter(chiTietThucPhamAdapter);

        randomNumberList = new ArrayList<>();
        randomNumberList2 = new ArrayList<>();
        addListRandomNumber();
        saleAdapter = new SaleAdapter(getApplicationContext(), randomNumberList, randomNumberList2);
        recyclerViewSale.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerViewSale.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewSale.setAdapter(saleAdapter);
    }

    public void addListRandomNumber() {
        Random random = new Random();
        int a1 = 1000 + random.nextInt(100000 - 1000) + 1;
        int a2 = 1000 + random.nextInt(100000 - 1000) + 1;
        int a3 = 1000 + random.nextInt(100000 - 1000) + 1;
        int a4 = 1000 + random.nextInt(100000 - 1000) + 1;
        int a5 = 1000 + random.nextInt(100000 - 1000) + 1;
        int a6 = 100000 + random.nextInt(1000000);
        int a7 = 100000 + random.nextInt(1000000);
        int a8 = 100000 + random.nextInt(1000000);
        int a9 = 100000 + random.nextInt(1000000);
        int a10 = 100000 + random.nextInt(1000000);
        randomNumberList.add(new RandomNumber(a1));
        randomNumberList.add(new RandomNumber(a2));
        randomNumberList.add(new RandomNumber(a3));
        randomNumberList.add(new RandomNumber(a4));
        randomNumberList.add(new RandomNumber(a5));
        randomNumberList2.add(new RandomNumber(a6));
        randomNumberList2.add(new RandomNumber(a7));
        randomNumberList2.add(new RandomNumber(a8));
        randomNumberList2.add(new RandomNumber(a9));
        randomNumberList2.add(new RandomNumber(a10));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageBackChiTiet:
                showToast("Quay lại trang trước");
                finish();
                break;
            case R.id.imageTrangChinhChiTiet:
                showToast("Quay lại trang chính");
                startActivity(new Intent(getApplicationContext(), TrangChinh.class));
                break;
            case R.id.imageChatChiTiet:
                showToast("Chức năng tạm thời chưa ra mắt");
                break;
            case R.id.btnThemGioHangChiTiet:
                Snackbar snackbar = Snackbar.make(v, "Thêm đồ vào giỏ hàng", BaseTransientBottomBar.LENGTH_LONG);
                snackbar.setAction("Chấp nhận", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("Đã thêm");
                        insertGioHang();
                    }
                });
                snackbar.show();
                break;
            case R.id.btnMuaNgayChiTiet:
                showToast("Mua");
                break;
        }
    }

    private void insertGioHang() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.DuongDanInsertGioHangSanPham, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast("Server đang bảo trì");
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("tensanpham", ten);
                map.put("hinhanhsanpham", hinh);
                map.put("giasanpham", String.valueOf(gia));
                map.put("diachiquanan", diachi);
                map.put("soluongsanpham", String.valueOf(soluongsanpham));

                return map;
            }
        };
        requestQueue.add(stringRequest);

    }

    public void showToast(String str) {
        Toast.makeText(ChiTiet.this, str, Toast.LENGTH_SHORT).show();
    }
}