package com.example.appchuadatten.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appchuadatten.Oop.ThucPhamDangHot;
import com.example.appchuadatten.R;
import com.example.appchuadatten.adapter.DoHopAdapter;
import com.example.appchuadatten.adapter.DoHopMoiNhatAdapter;
import com.example.appchuadatten.adapter.MenuAdapter;
import com.example.appchuadatten.checkConnect.CheckConnect;
import com.example.appchuadatten.checkConnect.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoHop extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    RecyclerView recyclerViewDoHop, recyclerViewDoHopMoiNhat;
    VideoView videoViewQuangCao;
    ImageButton imageGioHang, imageTrangChinh, imageBack;
    DrawerLayout drawerLayout;

    ListView listViewMenu;
    MenuAdapter menuAdapter;

    List<ThucPhamDangHot> listDoHop, listDoHopMoiNhat;
    DoHopAdapter doHopAdapter;

    DoHopMoiNhatAdapter doHopMoiNhatAdapter;

    int count = 0, page = 1, idDoHop = 0;

    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_hop);
        if (CheckConnect.checkConnection(getApplicationContext())) {
            anhXa();
            actionToolBar();
            getIdSanPham();
            ClickItemMenu(getApplicationContext());
            getDataDoHop(page);
            runVideoQuangCao();
            getDataDoHopMoiNhat(page);
        } else {
            showToast("Không có kết nối internet");
            finish();
        }
    }

    private void getDataDoHopMoiNhat(int Page) {
        String url = URL.DuongDanDoHopMoiNhat + Page;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int ID = 0;
                String tensanpham = "";
                String hinhanhsanpham = "";
                int giasanpham = 0;
                String diachiquanan = "";
                int idsanpham = 0;
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ID = jsonObject.getInt("id");
                        tensanpham = jsonObject.getString("tensanpham");
                        hinhanhsanpham = jsonObject.getString("hinhanhsanpham");
                        giasanpham = jsonObject.getInt("giasanpham");
                        diachiquanan = jsonObject.getString("diachiquanan");
                        idsanpham = jsonObject.getInt("idsanpham");
                        listDoHopMoiNhat.add(new ThucPhamDangHot(ID, tensanpham, hinhanhsanpham, giasanpham, diachiquanan, idsanpham));
                        doHopMoiNhatAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
                map.put("iddohop", String.valueOf(idDoHop));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void runVideoQuangCao() {
        videoViewQuangCao.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.quangcaodoan2));
        countDownTimer = new CountDownTimer(62000, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                videoViewQuangCao.start();
            }

            @Override
            public void onFinish() {
                countDownTimer.start();
            }
        }.start();

    }

    private void getIdSanPham() {
        idDoHop = getIntent().getIntExtra("idsanpham", -1);
    }

    private void getDataDoHop(int Page) {
        String url = URL.DuongDanThucPham + Page;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int ID = 0;
                String tensanpham = "";
                String hinhanhsanpham = "";
                int giasanpham = 0;
                String diachiquanan = "";
                int idsanpham = 0;
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ID = jsonObject.getInt("id");
                        tensanpham = jsonObject.getString("tensanpham");
                        hinhanhsanpham = jsonObject.getString("hinhanhsanpham");
                        giasanpham = jsonObject.getInt("giasanpham");
                        diachiquanan = jsonObject.getString("diachiquanan");
                        idsanpham = jsonObject.getInt("idsanpham");
                        listDoHop.add(new ThucPhamDangHot(ID, tensanpham, hinhanhsanpham, giasanpham, diachiquanan, idsanpham));
                        doHopAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                HashMap<String, String> map = new HashMap<>();
                map.put("idsanpham", String.valueOf(idDoHop));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ClickItemMenu(Context context) {
        listViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        showToast("Chuyển tiếp đến -> " + TrangChinh.listMenu.get(position).getTensanpham());
                        if (CheckConnect.checkConnection(getApplicationContext())) {
                            startActivity(new Intent(context, TrangChinh.class));
                        } else {
                            showToast("không có kết nối internet");
                            finish();
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
//                        showToast("Chuyển tiếp đến -> " + listMenu.get(position).getTensanpham());
                        if (CheckConnect.checkConnection(getApplicationContext())) {
                            Intent intent = new Intent(context, DoTrangMieng.class);
                            intent.putExtra("idsanpham", TrangChinh.listMenu.get(position).getId());
                            startActivity(intent);
                        } else {
                            showToast("không có kết nối internet");
                            finish();
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:

                        if (CheckConnect.checkConnection(getApplicationContext())) {
                            showToast("Đang ở trang -> " + TrangChinh.listMenu.get(position).getTensanpham());
                        } else {
                            showToast("không có kết nối internet");
                            finish();
                        }
                        break;
                    case 3:
                        showToast("Chuyển tiếp đến -> " + TrangChinh.listMenu.get(position).getTensanpham());
                        if (CheckConnect.checkConnection(getApplicationContext())) {
                            Intent intent = new Intent(context, DoUong.class);
                            intent.putExtra("idsanpham", TrangChinh.listMenu.get(position).getId());
                            startActivity(intent);
                        } else {
                            showToast("không có kết nối internet");
                            finish();
                        }
                        break;
                    case 4:
                        showToast("Chuyển tiếp đến -> " + TrangChinh.listMenu.get(position).getTensanpham());
                        Intent intentCall = new Intent();
                        intentCall.setAction(Intent.ACTION_CALL);
                        intentCall.setData(Uri.parse("tel:0862982143"));
                        startActivity(intentCall);
                        break;
                    case 5:
                        showToast("Chuyển tiếp đến -> " + TrangChinh.listMenu.get(position).getTensanpham());
                        Intent intentSend = new Intent();
                        intentSend.setAction(Intent.ACTION_SENDTO);
                        intentSend.putExtra("sms_body", "Hello.....");
                        intentSend.setData(Uri.parse("sms:0862982143"));
                        startActivity(intentSend);
                        break;
                    case 6:
                        showToast("Chuyển tiếp đến -> " + TrangChinh.listMenu.get(position).getTensanpham());
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.facebook.com/tung.nguyentrung.1428")));
                        break;
                }
            }
        });
    }

    private void actionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Đồ hộp");
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count += 1;
                drawerLayout.openDrawer(GravityCompat.START);
                if (count > 1) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    count = 0;
                }
            }
        });
    }

    private void anhXa() {
        toolbar = findViewById(R.id.toolbar_doHop);
        recyclerViewDoHop = findViewById(R.id.recyclerViewDoHop);
        recyclerViewDoHopMoiNhat = findViewById(R.id.recyclerViewDoHopMoiNhat);
        videoViewQuangCao = findViewById(R.id.videoQuangCaoDoHop);
        imageGioHang = findViewById(R.id.imageGioHangDoHop);
        imageTrangChinh = findViewById(R.id.imageTrangChinhDoHop);
        imageBack = findViewById(R.id.imageBackDoHop);
        drawerLayout = findViewById(R.id.drawerLayout);
        listViewMenu = findViewById(R.id.listViewMenu);

        imageGioHang.setOnClickListener(this);
        imageTrangChinh.setOnClickListener(this);
        imageBack.setOnClickListener(this);

        menuAdapter = new MenuAdapter(getApplicationContext(), TrangChinh.listMenu);
        listViewMenu.setAdapter(menuAdapter);

        listDoHop = new ArrayList<>();
        doHopAdapter = new DoHopAdapter(getApplicationContext(), listDoHop);
        recyclerViewDoHop.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerViewDoHop.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewDoHop.setAdapter(doHopAdapter);

        listDoHopMoiNhat = new ArrayList<>();
        doHopMoiNhatAdapter = new DoHopMoiNhatAdapter(getApplicationContext(), listDoHopMoiNhat);
        recyclerViewDoHopMoiNhat.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        recyclerViewDoHopMoiNhat.setAdapter(doHopMoiNhatAdapter);
    }

    public void showToast(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageGioHangDoHop:
                showToast("Open giỏ hàng");
                startActivity(new Intent(DoHop.this, GioHang.class));
                break;
            case R.id.imageTrangChinhDoHop:
                showToast("Chuyển đến -> Trang chính");
                startActivity(new Intent(DoHop.this, TrangChinh.class));
                break;
            case R.id.imageBackDoHop:
                showToast("Quay lại trang trước");
                finish();
                break;
        }
    }
}