package com.example.appchuadatten.Oop;

import com.google.gson.annotations.SerializedName;

public class ThucPhamDangHot {
    @SerializedName("id")
    int id;
    @SerializedName("tensanpham")
    String tensanpham;
    @SerializedName("hinhanhsanpham")
    String hinhanhsanpham;
    @SerializedName("giasanpham")
    int giasanpham;
    @SerializedName("diachiquanan")
    String diachiquanan;
    @SerializedName("idsanpham")
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

    @Override
    public String toString() {
        return "ThucPhamDangHot{" +
                "id=" + id +
                ", tensanpham='" + tensanpham + '\'' +
                ", hinhanhsanpham='" + hinhanhsanpham + '\'' +
                ", giasanpham=" + giasanpham +
                ", diachiquanan='" + diachiquanan + '\'' +
                ", idsanpham=" + idsanpham +
                '}';
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

    public ThucPhamDangHot(int id, String tensanpham, String hinhanhsanpham, int giasanpham, String diachiquanan, int idsanpham) {
        this.id = id;
        this.tensanpham = tensanpham;
        this.hinhanhsanpham = hinhanhsanpham;
        this.giasanpham = giasanpham;
        this.diachiquanan = diachiquanan;
        this.idsanpham = idsanpham;
    }
}
