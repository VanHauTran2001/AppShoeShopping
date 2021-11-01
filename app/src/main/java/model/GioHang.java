package model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class GioHang {

    public int id;
    public String hinhAnhSp;
    public String tenSp;
    public double giaSp;
    public double TongGiaSp;
    public int soLuongSp;
    public int size;

    public GioHang(String hinhAnhSp, String tenSp, double giaSp, double tongGiaSp, int soLuongSp, int size) {
        this.hinhAnhSp = hinhAnhSp;
        this.tenSp = tenSp;
        this.giaSp = giaSp;
        TongGiaSp = tongGiaSp;
        this.soLuongSp = soLuongSp;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHinhAnhSp() {
        return hinhAnhSp;
    }

    public void setHinhAnhSp(String hinhAnhSp) {
        this.hinhAnhSp = hinhAnhSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public double getGiaSp() {
        return giaSp;
    }

    public void setGiaSp(double giaSp) {
        this.giaSp = giaSp;
    }

    public double getTongGiaSp() {
        return TongGiaSp;
    }

    public void setTongGiaSp(double tongGiaSp) {
        TongGiaSp = tongGiaSp;
    }

    public int getSoLuongSp() {
        return soLuongSp;
    }

    public void setSoLuongSp(int soLuongSp) {
        this.soLuongSp = soLuongSp;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "GioHang{" +
                "hinhAnhSp='" + hinhAnhSp + '\'' +
                ", tenSp='" + tenSp + '\'' +
                ", giaSp=" + giaSp +
                ", TongGiaSp=" + TongGiaSp +
                ", soLuongSp=" + soLuongSp +
                ", size=" + size +
                '}';
    }
}
