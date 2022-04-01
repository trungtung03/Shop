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

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ClipDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appchuadatten.Oop.LoaiThucPham;
import com.example.appchuadatten.Oop.ThucPhamDangHot;
import com.example.appchuadatten.R;
import com.example.appchuadatten.adapter.DoUongHienCoAdapter;
import com.example.appchuadatten.adapter.LuaChonAdapter;
import com.example.appchuadatten.adapter.MenuAdapter;
import com.example.appchuadatten.adapter.ThucPhamMoiNhatAdapter;
import com.example.appchuadatten.api.ApiService;
import com.example.appchuadatten.checkConnect.CheckConnect;
import com.example.appchuadatten.checkConnect.URL;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrangChinh extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    DrawerLayout drawerLayout;
    ImageView imageViewLogo, imageAddSanPham, imageTabs, imageLiveStream, imageThongBao, imageProfile;

    ListView listViewMenu;
    RecyclerView recyclerView, recyclerViewLuaChon, recyclerViewDoUong;

    List<ThucPhamDangHot> thucPhamDangHotList, listDoUong;
    ThucPhamMoiNhatAdapter thucPhamMoiNhatAdapter;
    MenuAdapter menuAadapter;

    List<LoaiThucPham> loaiThucPhamList;
    static List<LoaiThucPham> listMenu;
    LuaChonAdapter luaChonAdapter;

    DoUongHienCoAdapter doUongHienCoAdapter;

    int page = 1, idDoUong = 0, idAddSp = 0;

    TextView titleActionBar;

    CountDownTimer countDownTimer;

    int title[] = {
            R.string.title,
            R.string.title2,
            R.string.title3,
            R.string.title4,
            R.string.title5,
            R.string.title6,
            R.string.title7,
            R.string.title8,
            R.string.title9,
            R.string.title10,
    };

    int count = 0;

    EditText addTen, addHinhAnh, addGia, addDiaChi, addLoaiSp;
    Button themAdd, huyAdd;

    public static List<com.example.appchuadatten.Oop.GioHang> listGioHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchinh);
        if (CheckConnect.checkConnection(this)) {
            anhXa();
            ActionBar();
            QuangCao();
            GetLoaiThucPham();
            ThucPhamDangHot();
//            ThucPhamDangHot2();
            ClipLogo();
            GetThucPham(page);
            ClickItemMenu(getApplicationContext());
            ClickItemTaskBar();
        } else {
            showToast("không có kết nối internet");
            finish();
        }
    }

//    public static String hot_url = "http://trungtung.000webhostapp.com";
//
//    private void ThucPhamDangHot2() {
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(hot_url).client(client).addConverterFactory(GsonConverterFactory.create()).build();
//        ApiService apiService = retrofit.create(ApiService.class);
//        Call<List<ThucPhamDangHot>> listCall = apiService.getAllList();
//        listCall.enqueue(new Callback<List<ThucPhamDangHot>>() {
//
//            //Neu thanh cong no se roi vao callback nay
//            // response.body() no se la doi tuong em truyen vao nen se khong can ep kieu
//            @Override
//            public void onResponse(Call<List<ThucPhamDangHot>> call, retrofit2.Response<List<ThucPhamDangHot>> response) {
//                ArrayList<ThucPhamDangHot> list = (ArrayList<ThucPhamDangHot>) response.body();
//                for (int i = 0; i < list.size(); i++) {
//                    Log.d("AAAAA", list.get(i).toString());
//                    thucPhamDangHotList.add(new ThucPhamDangHot(
//                            list.get(i).getId()
//                            , list.get(i).getTensanpham()
//                            , list.get(i).getHinhanhsanpham()
//                            , list.get(i).getGiasanpham()
//                            , list.get(i).getDiachiquanan()
//                            , list.get(i).getIdsanpham()));
//                    thucPhamMoiNhatAdapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<ThucPhamDangHot>> call, Throwable t) {
//                showToast("Error");
//                Log.d("AAAAA", t + "");
//            }
//        });
//    }

    private void ClickItemTaskBar() {
        imageAddSanPham.setOnClickListener(this);
        imageTabs.setOnClickListener(this);
        imageLiveStream.setOnClickListener(this);
        imageThongBao.setOnClickListener(this);
        imageProfile.setOnClickListener(this);
    }

    private void ClickItemMenu(Context context) {
        listViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (CheckConnect.checkConnection(getApplicationContext())) {
                            showToast("Đang ở -> " + listMenu.get(position).getTensanpham());
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
                            intent.putExtra("idsanpham", listMenu.get(position).getId());
                            startActivity(intent);
                        } else {
                            showToast("không có kết nối internet");
                            finish();
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        showToast("Chuyển tiếp đến -> " + listMenu.get(position).getTensanpham());
                        if (CheckConnect.checkConnection(getApplicationContext())) {
                            Intent intent = new Intent(context, DoHop.class);
                            intent.putExtra("idsanpham", listMenu.get(position).getId());
                            startActivity(intent);
                        } else {
                            showToast("không có kết nối internet");
                            finish();
                        }
                        break;
                    case 3:
                        showToast("Chuyển tiếp đến -> " + listMenu.get(position).getTensanpham());
                        if (CheckConnect.checkConnection(getApplicationContext())) {
                            Intent intent = new Intent(context, DoUong.class);
                            intent.putExtra("idsanpham", listMenu.get(position).getId());
                            startActivity(intent);
                        } else {
                            showToast("không có kết nối internet");
                            finish();
                        }
                        break;
                    case 4:
                        showToast("Chuyển tiếp đến -> " + listMenu.get(position).getTensanpham());
                        Intent intentCall = new Intent();
                        intentCall.setAction(Intent.ACTION_CALL);
                        intentCall.setData(Uri.parse("tel:0862982143"));
                        startActivity(intentCall);
                        break;
                    case 5:
                        showToast("Chuyển tiếp đến -> " + listMenu.get(position).getTensanpham());
                        Intent intentSend = new Intent();
                        intentSend.setAction(Intent.ACTION_SENDTO);
                        intentSend.putExtra("sms_body", "Hello.....");
                        intentSend.setData(Uri.parse("sms:0862982143"));
                        startActivity(intentSend);
                        break;
                    case 6:
                        showToast("Chuyển tiếp đến -> " + listMenu.get(position).getTensanpham());
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.facebook.com/tung.nguyentrung.1428")));
                        break;
                }
            }
        });
    }

    private void GetLoaiThucPham() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL.DuongDanLoaiThucPham, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int id = 0;
                String tensanpham = "";
                String hinhanhsanpham = "";
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        id = jsonObject.getInt("id");
                        tensanpham = jsonObject.getString("tensanpham");
                        hinhanhsanpham = jsonObject.getString("hinhanhsanpham");

                        listMenu.add(new LoaiThucPham(id, tensanpham, hinhanhsanpham));
                        menuAadapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                listMenu.add(4, new LoaiThucPham(0, "Liên hệ", "https://cdn-icons-png.flaticon.com/128/950/950299.png"));
                listMenu.add(5, new LoaiThucPham(0, "Hỗ trợ", "https://cdn-icons-png.flaticon.com/128/868/868681.png"));
                listMenu.add(6, new LoaiThucPham(0, "Chi tiết", "https://cdn-icons-png.flaticon.com/128/6498/6498879.png"));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast("Server đang bảo trì");
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetThucPham(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = URL.DuongDanThucPham + Page;
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
                        listDoUong.add(new ThucPhamDangHot(ID, tensanpham, hinhanhsanpham, giasanpham, diachiquanan, idsanpham));
                        doUongHienCoAdapter.notifyDataSetChanged();
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
                idDoUong = 3;
                map.put("idsanpham", String.valueOf(idDoUong));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ClipLogo() {
        ClipDrawable clipDrawable = (ClipDrawable) imageViewLogo.getDrawable();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imageViewLogo.setImageLevel(clipDrawable.getLevel() + 30);
                if (clipDrawable.getLevel() > 10000) {
                    clipDrawable.setLevel(0);
                }
                handler.postDelayed(this, 1);
            }
        }, 1);
    }

    private void ThucPhamDangHot() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL.DuongDanThucPhamDangHot, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int ID = 0;
                    String tensanpham = "";
                    String hinhanhsanpham = "";
                    int giasanpham = 0;
                    String diachiquanan = "";
                    int idsanpham = 0;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            tensanpham = jsonObject.getString("tensanpham");
                            hinhanhsanpham = jsonObject.getString("hinhanhsanpham");
                            giasanpham = jsonObject.getInt("giasanpham");
                            diachiquanan = jsonObject.getString("diachiquanan");
                            idsanpham = jsonObject.getInt("idsanpham");

                            thucPhamDangHotList.add(new ThucPhamDangHot(ID, tensanpham, hinhanhsanpham, giasanpham, diachiquanan, idsanpham));
                            thucPhamMoiNhatAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    showToast("Không có dữ liệu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast("Server đang bảo trì");
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void QuangCao() {
        List<String> listQuangCao = new ArrayList<>();
        listQuangCao.add("https://intphcm.com/data/upload/poster-quang-cao-web-do-an.jpg");
        listQuangCao.add("https://intphcm.com/data/upload/poster-do-an-onl.jpg");
        listQuangCao.add("https://intphcm.com/data/upload/poster-do-an-chay.jpg");
        listQuangCao.add("https://intphcm.com/data/upload/poster-do-an-dep-mat.jpg");
        listQuangCao.add("https://taingay.net/wp-content/uploads/2021/07/poster-quang-cao-do-an-13.jpg");
        listQuangCao.add("https://taingay.net/wp-content/uploads/2021/07/poster-quang-cao-do-an-19.jpg");
        listQuangCao.add("https://taingay.net/wp-content/uploads/2021/07/poster-quang-cao-do-an-1.jpg");
        listQuangCao.add("https://taingay.net/wp-content/uploads/2021/07/poster-quang-cao-do-an-6.jpg");
        listQuangCao.add("https://taingay.net/wp-content/uploads/2021/07/poster-quang-cao-do-an-4-1.jpg");
        listQuangCao.add("https://taingay.net/wp-content/uploads/2021/07/poster-quang-cao-do-an-3.jpg");
        for (int i = 0; i < listQuangCao.size(); i++) {
            ImageView imageView = new ImageView(this);
            Picasso.with(this).load(listQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_quangcao);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.animation_quangcao_out);
        viewFlipper.setInAnimation(animation);
        viewFlipper.setOutAnimation(animation2);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);

        toolbar.setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count += 1;
                showToast("Open Menu");
                drawerLayout.openDrawer(GravityCompat.START);
                if (count > 1) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    count = 0;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar_trangchinh, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSearchTrangChinh:
                showToast("Search");
                break;
            case R.id.menuGioHangTrangChinh:
                showToast("Giỏ hàng");
                startActivity(new Intent(TrangChinh.this, GioHang.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void anhXa() {
        toolbar = findViewById(R.id.toolbar_activity);
        viewFlipper = findViewById(R.id.viewflipper_quangcao);
        drawerLayout = findViewById(R.id.drawerLayout);
        listViewMenu = findViewById(R.id.listViewMenu);
        recyclerView = findViewById(R.id.recyclerViewSpHot);
        recyclerViewLuaChon = findViewById(R.id.recyclerViewLuaChon);
        recyclerViewDoUong = findViewById(R.id.recyclerViewDoUongHienCo);
        imageViewLogo = findViewById(R.id.imageLogo);
        imageAddSanPham = findViewById(R.id.imageAdd);
        imageTabs = findViewById(R.id.imageTab);
        imageLiveStream = findViewById(R.id.imageLiveStream);
        imageThongBao = findViewById(R.id.imageThongBao);
        imageProfile = findViewById(R.id.imageProfile);
        titleActionBar = findViewById(R.id.titleActionBar);
        animationTitle();
        adapter();

        if (listGioHang != null) {

        } else {
            listGioHang = new ArrayList<>();
        }
    }

    private void animationTitle() {
        countDownTimer = new CountDownTimer(3000, 3000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_title);
                titleActionBar.setAnimation(animation);
                Random random = new Random();
                int a = 0 + random.nextInt(title.length);
                titleActionBar.setText(title[a]);
            }

            @Override
            public void onFinish() {
                countDownTimer.start();
            }
        }.start();
    }

    public void adapter() {

        thucPhamDangHotList = new ArrayList<>();
        thucPhamMoiNhatAdapter = new ThucPhamMoiNhatAdapter(getApplicationContext(), thucPhamDangHotList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(thucPhamMoiNhatAdapter);

        listMenu = new ArrayList<>();
        listMenu.add(0, new LoaiThucPham(0, "Trang chủ",
                "https://t3.ftcdn.net/jpg/00/70/24/16/240_F_70241613_jLOBn0YkQfLGe7AnwagKEuh1DSymFJpp.jpg"));
        menuAadapter = new MenuAdapter(getApplicationContext(), listMenu);
        listViewMenu.setAdapter(menuAadapter);

        loaiThucPhamList = new ArrayList<>();
        loaiThucPhamList.add(0, new LoaiThucPham(0, "Event",
                "https://cdn-icons-png.flaticon.com/128/3656/3656949.png"));
        loaiThucPhamList.add(1, new LoaiThucPham(0, "FreeShip",
                "https://cdn-icons.flaticon.com/png/128/5310/premium/5310883.png?token=exp=1641974808~hmac=fe304b3a4f1c38bbe55a840fb10a0480"));
        loaiThucPhamList.add(2, new LoaiThucPham(0, "Sale",
                "https://cdn-icons-png.flaticon.com/512/1685/1685230.png"));
        loaiThucPhamList.add(3, new LoaiThucPham(0, "Siêu Thị",
                "https://cdn-icons-png.flaticon.com/512/3737/3737173.png"));
        loaiThucPhamList.add(4, new LoaiThucPham(0, "Mini Game",
                "https://t4.ftcdn.net/jpg/02/66/84/71/240_F_266847163_V6bDeZUfJD44EY3Ufgq3XUrTBdiuLVfg.jpg"));
        loaiThucPhamList.add(5, new LoaiThucPham(0, "Xem Thêm",
                "https://cdn-icons-png.flaticon.com/512/633/633684.png"));
        luaChonAdapter = new LuaChonAdapter(getApplicationContext(), loaiThucPhamList);
        recyclerViewLuaChon.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerViewLuaChon.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewLuaChon.setAdapter(luaChonAdapter);

        listDoUong = new ArrayList<>();
        doUongHienCoAdapter = new DoUongHienCoAdapter(getApplicationContext(), listDoUong);
        recyclerViewDoUong.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerViewDoUong.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewDoUong.setAdapter(doUongHienCoAdapter);
    }

    public void showToast(String str) {
        Toast.makeText(TrangChinh.this, str, Toast.LENGTH_SHORT).show();
    }

    public void creatDialog_Va_AddSanPham() {

        Dialog dialog = new Dialog(TrangChinh.this);
        dialog.setContentView(R.layout.add_san_pham);
        dialog.setCanceledOnTouchOutside(false);
        addTen = dialog.findViewById(R.id.editTextTen);
        addHinhAnh = dialog.findViewById(R.id.editTextHinhAnh);
        addGia = dialog.findViewById(R.id.editTextGia);
        addDiaChi = dialog.findViewById(R.id.editTextDiaChi);
        addLoaiSp = dialog.findViewById(R.id.editTextTLoaiSanPham);
        themAdd = dialog.findViewById(R.id.btnXacNhanAdd);
        huyAdd = dialog.findViewById(R.id.btnHuyAdd);

        themAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(addTen.getText().toString().trim()) || TextUtils.isEmpty(addHinhAnh.getText().toString().trim()) ||
                        TextUtils.isEmpty(addGia.getText().toString().trim()) || TextUtils.isEmpty(addDiaChi.getText().toString().trim()) ||
                        TextUtils.isEmpty(addLoaiSp.getText().toString().trim())) {
                    showToast("Chưa nhập đủ thông tin");
                } else {
                    if (!(addLoaiSp.getText().toString().trim().equals("Đồ tráng miệng") || addLoaiSp.getText().toString().trim().equals("Đồ hộp") ||
                            addLoaiSp.getText().toString().trim().equals("Đồ uống"))) {
                        showToast("Sai lệnh\nGõ help để được hỗ trợ");
                        if (addLoaiSp.getText().toString().trim().equals("help")) {
                            showToast("Show bảng lệnh");
                            AlertDialog.Builder builder = new AlertDialog.Builder(TrangChinh.this);
                            builder.setTitle("Các câu lệnh được sử dụng");
                            builder.setMessage("1. Đồ tráng miệng\n2. Đồ hộp\n3. Đồ uống");
                            builder.create().show();
                        }
                    } else {
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.DuongDanAddSanPham, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.trim().equals("success")) {
                                    showToast("Update thành công\nVui lòng khởi động lại ứng dụng để lưu dữ liệu");
                                    dialog.dismiss();
                                } else {
                                    showToast("Update không thành công\nGõ help để được hỗ trợ");

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
                                switch (addLoaiSp.getText().toString().trim()) {
                                    case "Đồ tráng miệng":
                                        idAddSp = 1;
                                        map.put("tensanpham", addTen.getText().toString().trim());
                                        map.put("hinhanhsanpham", addHinhAnh.getText().toString().trim());
                                        map.put("giasanpham", addGia.getText().toString().trim());
                                        map.put("diachiquanan", addDiaChi.getText().toString().trim());
                                        map.put("idsanpham", String.valueOf(idAddSp));
                                        break;
                                    case "Đồ hộp":
                                        idAddSp = 2;
                                        map.put("tensanpham", addTen.getText().toString().trim());
                                        map.put("hinhanhsanpham", addHinhAnh.getText().toString().trim());
                                        map.put("giasanpham", addGia.getText().toString().trim());
                                        map.put("diachiquanan", addDiaChi.getText().toString().trim());
                                        map.put("idsanpham", String.valueOf(idAddSp));
                                        break;
                                    case "Đồ uống":
                                        idAddSp = 3;
                                        map.put("tensanpham", addTen.getText().toString().trim());
                                        map.put("hinhanhsanpham", addHinhAnh.getText().toString().trim());
                                        map.put("giasanpham", addGia.getText().toString().trim());
                                        map.put("diachiquanan", addDiaChi.getText().toString().trim());
                                        map.put("idsanpham", String.valueOf(idAddSp));
                                        break;
                                }
                                return map;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                }
            }
        });

        huyAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialog.dismiss();
                dialog.cancel();
            }
        });
        if (addLoaiSp.getText().toString().trim().equals("help")) {
            showToast("Show bảng lệnh");
        }
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageAdd:
                showToast("Add sản phẩm");
                creatDialog_Va_AddSanPham();
                break;
            case R.id.imageTab:
                showToast("Chức năng đang được cập nhật");
                break;
            case R.id.imageLiveStream:
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivity(intent);
                break;
            case R.id.imageThongBao:
                showToast("Hiện tại không có thông báo nào");
                break;
            case R.id.imageProfile:
                showToast("Đang cập nhật chức năng");
                break;
        }
    }
}