package com.example.appchuadatten.activity;

import androidx.annotation.NonNull;
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
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
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
import com.example.appchuadatten.adapter.DoTrangMiengAdapter;
import com.example.appchuadatten.adapter.DoTrangMiengMoiNhatAdapter;
import com.example.appchuadatten.adapter.LuaChonDoTrangMiengAdapter;
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

public class DoTrangMieng extends AppCompatActivity {

    ImageView imageViewBack;
    Toolbar toolbar;
    RecyclerView recyclerViewLuaChon, recyclerViewDoTrangMieng, recyclerViewDoTrangMiengMoiNhat;

    int idDoTrangMieng = 0;

    int page = 1;

    List<ThucPhamDangHot> listDoTrangMieng, listDoTrangMiengMoiNhat;
    DoTrangMiengAdapter doTrangMiengAdapter;

    DoTrangMiengMoiNhatAdapter doTrangMiengMoiNhatAdapter;

    List<LoaiThucPham> listLuaChon;
    LuaChonDoTrangMiengAdapter luaChonDoTrangMiengAdapter;

    ViewFlipper viewFlipper;
    List<String> listQuangCao;

    VideoView videoView;

    View loadViewProgressBar;
    boolean isLoading = false, isLimit = false;

    ListView listViewMenu;
    MenuAdapter menuAdapter;
    DrawerLayout drawerLayout;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_trang_mieng);
        if (CheckConnect.checkConnection(getApplicationContext())) {
            anhXa();
            getIdDoTrangMieng();
            ClickItemMenu(getApplicationContext());
            quangCao();
            actionToolBar();
            getDataDoTrangMieng(page);
            getDataDoTrangMiengMoiNhat(page);
        } else {
            showToast("không có kết nối internet");
            finish();
        }
    }

    private void ClickItemMenu(Context context) {
        listViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (CheckConnect.checkConnection(getApplicationContext())) {
                            showToast("Chuyển tiếp đến -> " + TrangChinh.listMenu.get(position).getTensanpham());
                            startActivity(new Intent(context, TrangChinh.class));
                        } else {
                            showToast("không có kết nối internet");
                            finish();
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnect.checkConnection(getApplicationContext())) {
                            showToast("Đang ở trang -> " + TrangChinh.listMenu.get(position).getTensanpham());
                        } else {
                            showToast("không có kết nối internet");
                            finish();
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnect.checkConnection(getApplicationContext())) {
                            showToast("Chuyển tiếp đến -> " + TrangChinh.listMenu.get(position).getTensanpham());
                            Intent intent = new Intent(context, DoHop.class);
                            intent.putExtra("idsanpham", TrangChinh.listMenu.get(position).getId());
                            startActivity(intent);
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

    private void getDataDoTrangMiengMoiNhat(int Page) {
        String url = URL.DuongDanMonTrangMiengMoiNhat + Page;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
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

                        listDoTrangMiengMoiNhat.add(new ThucPhamDangHot(ID, tensanpham, hinhanhsanpham, giasanpham, diachiquanan, idsanpham));
                        doTrangMiengMoiNhatAdapter.notifyDataSetChanged();
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
                map.put("idsanpham", String.valueOf(idDoTrangMieng));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void quangCao() {
        listQuangCao = new ArrayList<>();
        listQuangCao.add("https://intphcm.com/data/upload/poster-tra-sua-dep-sang.jpg");
        listQuangCao.add("https://intphcm.com/data/upload/poster-tra-sua-ngon.jpg");
        listQuangCao.add("https://intphcm.com/data/upload/poster-tra-sua-cach-dieu.jpg");
        listQuangCao.add("https://tui-giay.com/poster-do-an/imager_1_6436_700.jpg");
        listQuangCao.add("https://intphcm.com/data/upload/1597829590-poster-tra-sua-dep-mat-3.jpg");
        listQuangCao.add("https://fedudesign.vn/wp-content/uploads/2020/07/unnamed-1.png");
        for (int i = 0; i < listQuangCao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(listQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animationIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_quangcao_dotrangmieng);
        Animation animationOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_quangcao_dotrangmieng_out);
        viewFlipper.setInAnimation(animationIn);
        viewFlipper.setOutAnimation(getApplicationContext(), R.anim.animation_quangcao_dotrangmieng_out);
    }

    private void getDataDoTrangMieng(int Page) {
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

                        listDoTrangMieng.add(new ThucPhamDangHot(ID, tensanpham, hinhanhsanpham, giasanpham, diachiquanan, idsanpham));
                        doTrangMiengAdapter.notifyDataSetChanged();

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
                map.put("idsanpham", String.valueOf(idDoTrangMieng));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void actionToolBar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count += 1;
                drawerLayout.openDrawer(GravityCompat.START);
                if(count > 1){
                    drawerLayout.closeDrawer(GravityCompat.START);
                    count = 0;
                }
            }
        });
    }

    private void getIdDoTrangMieng() {
        idDoTrangMieng = getIntent().getIntExtra("idsanpham", -1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar_dotrangmieng, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuGioHangDoTrangMieng:
                showToast("Open giỏ hàng");
                startActivity(new Intent(DoTrangMieng.this, GioHang.class));
                break;
            case R.id.menuCameraDoTrangMieng:
                showToast("Open camera");
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
                break;
            case R.id.menuSearchDoTrangMieng:
                showToast("search");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    CountDownTimer countDownTimer;

    private void anhXa() {
        imageViewBack = findViewById(R.id.imageBackDoTrangMieng);
        toolbar = findViewById(R.id.toolbar_DoTrangMieng);
        recyclerViewLuaChon = findViewById(R.id.recyclerViewLuaChonDoTrangMieng);
        recyclerViewDoTrangMieng = findViewById(R.id.recyclerViewDoTrangMieng);
        recyclerViewDoTrangMiengMoiNhat = findViewById(R.id.recyclerViewDoTrangMiengMoiNhat);
        viewFlipper = findViewById(R.id.viewflipper_quangcaoDoTrangMieng);
        videoView = findViewById(R.id.videoQuangCaoDoAn);
        listViewMenu = findViewById(R.id.listViewMenu);
        drawerLayout = findViewById(R.id.drawerLayout);

        menuAdapter = new MenuAdapter(getApplicationContext(), TrangChinh.listMenu);
        listViewMenu.setAdapter(menuAdapter);

        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.quangcaodoan));
        countDownTimer = new CountDownTimer(62000, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                videoView.start();
            }

            @Override
            public void onFinish() {
                countDownTimer.start();
            }
        }.start();

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Quay về trang trước");
                finish();
            }
        });

        listDoTrangMieng = new ArrayList<>();
        doTrangMiengAdapter = new DoTrangMiengAdapter(getApplicationContext(), listDoTrangMieng);
        recyclerViewDoTrangMieng.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerViewDoTrangMieng.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewDoTrangMieng.setAdapter(doTrangMiengAdapter);

        listLuaChon = new ArrayList<>();
        listLuaChon.add(0, new LoaiThucPham(0, "FreeShip",
                "https://cdn-icons.flaticon.com/png/128/3796/premium/3796201.png?token=exp=1641398754~hmac=bfab1e37ce7c73108bc89ffd55b05ab8"));
        listLuaChon.add(1, new LoaiThucPham(0, "Event",
                "https://cdn-icons-png.flaticon.com/128/3656/3656845.png"));
        listLuaChon.add(2, new LoaiThucPham(0, "Sale",
                "https://cdn-icons-png.flaticon.com/128/1029/1029023.png"));
        listLuaChon.add(3, new LoaiThucPham(0, "Hoàn tiền",
                "https://cdn-icons.flaticon.com/png/128/3211/premium/3211596.png?token=exp=1641398898~hmac=eb710152caf2eb6302189fc90564fd99"));
        listLuaChon.add(4, new LoaiThucPham(0, "Trang chính",
                "https://cdn-icons-png.flaticon.com/128/619/619034.png"));
        luaChonDoTrangMiengAdapter = new LuaChonDoTrangMiengAdapter(getApplicationContext(), listLuaChon);
        recyclerViewLuaChon.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerViewLuaChon.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewLuaChon.setAdapter(luaChonDoTrangMiengAdapter);

        listDoTrangMiengMoiNhat = new ArrayList<>();
        doTrangMiengMoiNhatAdapter = new DoTrangMiengMoiNhatAdapter(getApplicationContext(), listDoTrangMiengMoiNhat);
        recyclerViewDoTrangMiengMoiNhat.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerViewDoTrangMiengMoiNhat.setAdapter(doTrangMiengMoiNhatAdapter);

    }

    public void showToast(String str) {
        Toast.makeText(DoTrangMieng.this, str, Toast.LENGTH_SHORT).show();
    }
}