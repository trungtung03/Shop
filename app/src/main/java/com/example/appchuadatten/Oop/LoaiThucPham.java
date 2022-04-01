package com.example.appchuadatten.Oop;

public class LoaiThucPham {
    int id;
    String tensanpham;
    String hinhanhsanpham;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public String getHinhanhsanpham() {
        return hinhanhsanpham;
    }

    public void setHinhanhsanpham(String hinhanhsanpham) {
        this.hinhanhsanpham = hinhanhsanpham;
    }

    public LoaiThucPham(int id, String tensanpham, String hinhanhsanpham) {
        this.id = id;
        this.tensanpham = tensanpham;
        this.hinhanhsanpham = hinhanhsanpham;
    }
}
