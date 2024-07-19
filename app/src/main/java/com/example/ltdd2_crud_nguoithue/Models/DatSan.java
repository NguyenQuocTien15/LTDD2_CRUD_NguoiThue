package com.example.ltdd2_crud_nguoithue.Models;

public class DatSan {
    String idDatSan, tenKH, soDienThoai, ngayThue, khungGio, tenSan, giaTien;
    boolean isConfirmed;


    public DatSan() {
    }

    public DatSan(String idDatSan, String tenKH, String soDienThoai, String ngayThue, String khungGio, String tenSan, String giaTien, boolean isConfirmed
    ) {
        this.idDatSan = idDatSan;
        this.tenKH = tenKH;
        this.soDienThoai = soDienThoai;
        this.ngayThue = ngayThue;
        this.khungGio = khungGio;
        this.tenSan = tenSan;
        this.giaTien = giaTien;
        this.isConfirmed = isConfirmed;
    }

    public String getIdDatSan() {
        return idDatSan;
    }

    public void setIdDatSan(String idDatSan) {
        this.idDatSan = idDatSan;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getNgayThue() {
        return ngayThue;
    }

    public void setNgayThue(String ngayThue) {
        this.ngayThue = ngayThue;
    }

    public String getKhungGio() {
        return khungGio;
    }

    public void setKhungGio(String khungGio) {
        this.khungGio = khungGio;
    }

    public String getTenSan() {
        return tenSan;
    }

    public void setTenSan(String tenSan) {
        this.tenSan = tenSan;
    }

    public String getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(String giaTien) {
        this.giaTien = giaTien;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }


    @Override
    public String toString() {
        return "DatSan{" +
                "idDatSan='" + idDatSan + '\'' +
                ", tenKH='" + tenKH + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", ngayThue='" + ngayThue + '\'' +
                ", khungGio='" + khungGio + '\'' +
                ", tenSan='" + tenSan + '\'' +
                ", giaTien='" + giaTien + '\'' +
                ", isConfirmed=" + isConfirmed +
                '}';
    }
}
