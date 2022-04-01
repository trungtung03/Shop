package com.example.appchuadatten.Oop;

public class Retrofit {
    int id;
    String tensanpham;
    String hinhanhsanpham;
    int giasanpham;
    String diachiquanan;
    int idsanpham;

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

    public int getGiasanpham() {
        return giasanpham;
    }

    public void setGiasanpham(int giasanpham) {
        this.giasanpham = giasanpham;
    }

    public String getDiachiquanan() {
        return diachiquanan;
    }

    public void setDiachiquanan(String diachiquanan) {
        this.diachiquanan = diachiquanan;
    }

    public int getIdsanpham() {
        return idsanpham;
    }

    public void setIdsanpham(int idsanpham) {
        this.idsanpham = idsanpham;
    }

    public Retrofit(int id, String tensanpham, String hinhanhsanpham, int giasanpham, String diachiquanan, int idsanpham) {
        this.id = id;
        this.tensanpham = tensanpham;
        this.hinhanhsanpham = hinhanhsanpham;
        this.giasanpham = giasanpham;
        this.diachiquanan = diachiquanan;
        this.idsanpham = idsanpham;
    }
}
