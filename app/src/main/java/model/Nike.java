package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Nike{
    @SerializedName("idNike")
    @Expose
    private String idNike;
    @SerializedName("favNike")
    @Expose
    private String favNike;
    @SerializedName("imgNike")
    @Expose
    private String imgNike;
    @SerializedName("imgFavoriteNike")
    @Expose
    private String imgFavoriteNike;
    @SerializedName("nameNike")
    @Expose
    private String nameNike;
    @SerializedName("dongNike")
    @Expose
    private String dongNike;
    @SerializedName("moneyNike")
    @Expose
    private Double moneyNike;
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

    public String getIdNike() {
        return idNike;
    }

    public void setIdNike(String idNike) {
        this.idNike = idNike;
    }

    public String getFavNike() {
        return favNike;
    }

    public void setFavNike(String favNike) {
        this.favNike = favNike;
    }

    public String getImgNike() {
        return imgNike;
    }

    public void setImgNike(String imgNike) {
        this.imgNike = imgNike;
    }

    public String getImgFavoriteNike() {
        return imgFavoriteNike;
    }

    public void setImgFavoriteNike(String imgFavoriteNike) {
        this.imgFavoriteNike = imgFavoriteNike;
    }

    public String getNameNike() {
        return nameNike;
    }

    public void setNameNike(String nameNike) {
        this.nameNike = nameNike;
    }

    public String getDongNike() {
        return dongNike;
    }

    public void setDongNike(String dongNike) {
        this.dongNike = dongNike;
    }

    public Double getMoneyNike() {
        return moneyNike;
    }

    public void setMoneyNike(Double moneyNike) {
        this.moneyNike = moneyNike;
    }
}
