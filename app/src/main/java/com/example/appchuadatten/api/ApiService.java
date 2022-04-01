package com.example.appchuadatten.api;

import com.example.appchuadatten.Oop.ThucPhamDangHot;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApiService {

    //<List<ThucPhamDangHot>> la doi tuong dinh nghia se nhan ve
    @GET("/Server%20App%20bán%20quần%20áo/getthucphamdanghot.php")
    Call<List<ThucPhamDangHot>>  getAllList();
}
