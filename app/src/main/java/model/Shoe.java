package model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Shoe{
    @SerializedName("key_id")
    @Expose
    private String key_id;
    @SerializedName("favStatus")
    @Expose
    private String favStatus;
    @SerializedName("imgShoe")
    @Expose
    private String imgShoe;
    @SerializedName("imgFavoriteShoe")
    @Expose
    private String imgFavoriteShoe;
    @SerializedName("nameShoe")
    @Expose
    private String nameShoe;
    @SerializedName("dong")
    @Expose
    private String dong;
    @SerializedName("moneyShoe")
    @Expose
    private Double moneyShoe;
    private String imgLogoSale;
    private String dongSaleBandau;
    private Double moneySaleBandau;

    public String getImgLogoSale() {
        return imgLogoSale;
    }

    public void setImgLogoSale(String imgLogoSale) {
        this.imgLogoSale = imgLogoSale;
    }

    public String getDongSaleBandau() {
        return dongSaleBandau;
    }

    public void setDongSaleBandau(String dongSaleBandau) {
        this.dongSaleBandau = dongSaleBandau;
    }

    public Double getMoneySaleBandau() {
        return moneySaleBandau;
    }

    public void setMoneySaleBandau(Double moneySaleBandau) {
        this.moneySaleBandau = moneySaleBandau;
    }

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    public String getFavStatus() {
        return favStatus;
    }

    public void setFavStatus(String favStatus) {
        this.favStatus = favStatus;
    }

    public String getImgShoe() {
        return imgShoe;
    }

    public void setImgShoe(String imgShoe) {
        this.imgShoe = imgShoe;
    }

    public String getImgFavoriteShoe() {
        return imgFavoriteShoe;
    }

    public void setImgFavoriteShoe(String imgFavoriteShoe) {
        this.imgFavoriteShoe = imgFavoriteShoe;
    }

    public String getNameShoe() {
        return nameShoe;
    }

    public void setNameShoe(String nameShoe) {
        this.nameShoe = nameShoe;
    }

    public String getDong() {
        return dong;
    }

    public void setDong(String dong) {
        this.dong = dong;
    }

    public Double getMoneyShoe() {
        return moneyShoe;
    }

    public void setMoneyShoe(Double moneyShoe) {
        this.moneyShoe = moneyShoe;
    }
}
