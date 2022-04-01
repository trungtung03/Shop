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
import android.graphics.drawable.ClipDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appchuadatten.Oop.LoaiThucPham;
import com.example.appchuadatten.Oop.ThucPhamDangHot;
import com.example.appchuadatten.R;
import com.example.appchuadatten.adapter.DoUongAdapter;
import com.example.appchuadatten.adapter.DoUongMoiNhatAdapter;
import com.example.appchuadatten.adapter.MenuAdapter;
import com.example.appchuadatten.checkConnect.CheckConnect;
import com.example.appchuadatten.checkConnect.URL;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DoUong extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    ImageButton back, gioHang, trangChinh;
    DrawerLayout drawerLayout;
    ListView listViewMenu;
    VideoView videoView;
    ImageView imageViewQuangCao;
    ViewFlipper viewFlipperQuangCao;

    RecyclerView recyclerViewDoUong, recyclerViewDoUongMoiNhat;

    MenuAdapter menuAdapter;

    int countClick = 0, page = 1, idDoUong = 0;

    DoUongAdapter doUongAdapter;
    List<ThucPhamDangHot> listDoUong, listDoUongMoiNhat;

    DoUongMoiNhatAdapter doUongMoiNhatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_uong);
        anhXa();
        runViewFlipperQuangCao();
        clipImageQuangCao();
        runVideoQuangCao();
        getIdDoUong();
        actionToolBar();
        ClickItemMenu(getApplicationContext());
        getDataDoUong(page);
        getDataDoUongMoiNhat(page);
    }

    private void runViewFlipperQuangCao() {
        List<String> listQuangCao = new ArrayList<>();
        listQuangCao.add("https://thietkekhainguyen.com/wp-content/uploads/2018/12/poster-cafe-dep.jpg");
        listQuangCao.add("https://thietkekhainguyen.com/wp-content/uploads/2018/12/poster-tea-milk-tea.jpg");
        listQuangCao.add("https://thietkekhainguyen.com/wp-content/uploads/2018/12/poster-tea-coffee.jpg");
        listQuangCao.add("https://solution.com.vn/upload_images/images/2017-Bo-nhan-dien-thuong-hieu/TRIP-TEA/poster/thiet-ke-poster-quan-tra-sua-an-tuong.jpg");
        listQuangCao.add("https://thietkekhainguyen.com/wp-content/uploads/2018/12/poster-coffee-cafe.jpg");
        listQuangCao.add("https://thietkekhainguyen.com/wp-content/uploads/2018/12/poster-coffee-shop.jpg");
        listQuangCao.add("https://thietkekhainguyen.com/wp-content/uploads/2018/12/poster-quan-cafe.jpg");
        for (int i = 0; i < listQuangCao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(listQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipperQuangCao.addView(imageView);
        }
        viewFlipperQuangCao.setFlipInterval(5000);
        viewFlipperQuangCao.setAutoStart(true);
        viewFlipperQuangCao.setInAnimation(getApplicationContext(), R.anim.animation_quangcao_douong);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_quangcao_douong_out);
        viewFlipperQuangCao.setOutAnimation(animation);
    }

    private void clipImageQuangCao() {
        ClipDrawable clipDrawable = (ClipDrawable) imageViewQuangCao.getDrawable();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imageViewQuangCao.setImageLevel(clipDrawable.getLevel() + 30);
                if (clipDrawable.getLevel() >= 10000) {
                    clipDrawable.setLevel(0);
                }
                handler.postDelayed(this, 1);
            }
        }, 1);

    }

    private void getDataDoUongMoiNhat(int Page) {
        String url = URL.DuongDanDataDoUongMoiNhat + Page;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0, giasanpham = 0, idsanpham = 0;
                String tensanpham = "", hinhanhsanpham = "", diachiquanan = "";
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() > 2) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tensanpham = jsonObject.getString("tensanpham");
                            hinhanhsanpham = jsonObject.getString("hinhanhsanpham");
                            giasanpham = jsonObject.getInt("giasanpham");
                            diachiquanan = jsonObject.getString("diachiquanan");
                            idsanpham = jsonObject.getInt("idsanpham");

                            listDoUongMoiNhat.add(new ThucPhamDangHot(id, tensanpham, hinhanhsanpham, giasanpham, diachiquanan, idsanpham));
                            doUongMoiNhatAdapter.notifyDataSetChanged();
                        }
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
                map.put("iddouongmoinhat", String.valueOf(idDoUong));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void getIdDoUong() {
        idDoUong = getIntent().getIntExtra("idsanpham", -1);
    }

    private void getDataDoUong(int Page) {
        String url = URL.DuongDanDataDoUong + Page;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0, giasanpham = 0, idsanpham = 0;
                String tensanpham = "", hinhanhsanpham = "", diachiquanan = "";
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() > 2) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tensanpham = jsonObject.getString("tensanpham");
                            hinhanhsanpham = jsonObject.getString("hinhanhsanpham");
                            giasanpham = jsonObject.getInt("giasanpham");
                            diachiquanan = jsonObject.getString("diachiquanan");
                            idsanpham = jsonObject.getInt("idsanpham");

                            listDoUong.add(new ThucPhamDangHot(id, tensanpham, hinhanhsanpham, giasanpham, diachiquanan, idsanpham));
                            doUongAdapter.notifyDataSetChanged();
                        }
                    } else {
                        showToast("Không có dữ liệu");
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
                map.put("iddouong", String.valueOf(idDoUong));
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
                        if (CheckConnect.checkConnection(getApplicationContext())) {
                            showToast("Chuyển tiếp đến -> " + TrangChinh.listMenu.get(position).getTensanpham());
                            startActivity(new Intent(DoUong.this, TrangChinh.class));
                        } else {
                            showToast("không có kết nối internet");
                            finish();
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
//                        showToast("Chuyển tiếp đến -> " + TrangChinh.listMenu.get(position).getTensanpham());
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
                        showToast("Chuyển tiếp đến -> " + TrangChinh.listMenu.get(position).getTensanpham());
                        if (CheckConnect.checkConnection(getApplicationContext())) {
                            Intent intent = new Intent(context, DoHop.class);
                            intent.putExtra("idsanpham", TrangChinh.listMenu.get(position).getId());
                            startActivity(intent);
                        } else {
                            showToast("không có kết nối internet");
                            finish();
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConnect.checkConnection(getApplicationContext())) {
                            showToast("Đang ở -> " + TrangChinh.listMenu.get(position).getTensanpham());
                        } else {
                            showToast("không có kết nối internet");
                            finish();
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        showToast("Chuyển tiếp đến -> " + TrangChinh.listMenu.get(position).getTensanpham());
                        Intent intentCall = new Intent();
                        intentCall.setAction(Intent.ACTION_CALL);
                        intentCall.setData(Uri.parse("tel:0862982143"));
                        startActivity(intentCall);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        showToast("Chuyển tiếp đến -> " + TrangChinh.listMenu.get(position).getTensanpham());
                        Intent intentSend = new Intent();
                        intentSend.setAction(Intent.ACTION_SENDTO);
                        intentSend.putExtra("sms_body", "Hello.....");
                        intentSend.setData(Uri.parse("sms:0862982143"));
                        startActivity(intentSend);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 6:
                        showToast("Chuyển tiếp đến -> " + TrangChinh.listMenu.get(position).getTensanpham());
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.facebook.com/tung.nguyentrung.1428")));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void runVideoQuangCao() {
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.quangcaodouong));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                videoView.start();
                handler.postDelayed(this, 1);
            }
        }, 1);
    }

    private void actionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countClick += 1;
                drawerLayout.openDrawer(GravityCompat.START);
                if (countClick > 1) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    countClick = 0;
                }
            }
        });
    }

    private void anhXa() {
        toolbar = findViewById(R.id.toolbar_DoUong);
        back = findViewById(R.id.imageBackDoUong);
        gioHang = findViewById(R.id.imageGioHangDoUong);
        drawerLayout = findViewById(R.id.drawerLayout);
        listViewMenu = findViewById(R.id.listViewMenu);
        videoView = findViewById(R.id.videoViewDoUong);
        trangChinh = findViewById(R.id.imageTrangChinhDoUong);
        viewFlipperQuangCao = findViewById(R.id.viewflipper_quangcaoDoUong);
        imageViewQuangCao = findViewById(R.id.imageQuangCaoDoUong);
        recyclerViewDoUong = findViewById(R.id.recyclerViewDoUong);
        recyclerViewDoUongMoiNhat = findViewById(R.id.recyclerViewDoUongMoiNhat);
        back.setOnClickListener(this);
        gioHang.setOnClickListener(this);
        trangChinh.setOnClickListener(this);

        menuAdapter = new MenuAdapter(getApplicationContext(), TrangChinh.listMenu);
        listViewMenu.setAdapter(menuAdapter);

        listDoUong = new ArrayList<>();
        doUongAdapter = new DoUongAdapter(getApplicationContext(), listDoUong);
        recyclerViewDoUong.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerViewDoUong.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewDoUong.setAdapter(doUongAdapter);

        listDoUongMoiNhat = new ArrayList<>();
        doUongMoiNhatAdapter = new DoUongMoiNhatAdapter(getApplicationContext(), listDoUongMoiNhat);
        recyclerViewDoUongMoiNhat.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerViewDoUongMoiNhat.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewDoUongMoiNhat.setAdapter(doUongMoiNhatAdapter);
    }

    public void showToast(String str) {
        Toast.makeText(DoUong.this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageBackDoUong:
                showToast("Quay trờ về trang trước");
                finish();
                break;
            case R.id.imageGioHangDoUong:
                showToast("Mở giỏ hàng");
                startActivity(new Intent(DoUong.this, GioHang.class));
                break;
            case R.id.imageTrangChinhDoUong:
                showToast("Quay lại trang chính");
                startActivity(new Intent(DoUong.this, TrangChinh.class));
                break;
        }
    }
}