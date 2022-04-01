package com.example.appchuadatten.Oop;

public class GioHang {
    int idSp;
    String tensanpham;
    String hinhanhsanpham;
    int giasanpham;
    String diachiquanan;
    int soluongsanpham;

    public int getIdSp() {
        return idSp;
    }

    public void setIdSp(int idSp) {
        this.idSp = idSp;
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

    public int getSoluongsanpham() {
        return soluongsanpham;
    }

    public void setSoluongsanpham(int soluongsanpham) {
        this.soluongsanpham = soluongsanpham;
    }

    public GioHang(int idSp, String tensanpham, String hinhanhsanpham, int giasanpham, String diachiquanan, int soluongsanpham) {
        this.idSp = idSp;
        this.tensanpham = tensanpham;
        this.hinhanhsanpham = hinhanhsanpham;
        this.giasanpham = giasanpham;
        this.diachiquanan = diachiquanan;
        this.soluongsanpham = soluongsanpham;
    }
}
