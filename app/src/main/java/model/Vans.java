package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Vans{
    @SerializedName("idVans")
    @Expose
    private String idVans;
    @SerializedName("favVans")
    @Expose
    private String favVans;
    @SerializedName("imgVans")
    @Expose
    private String imgVans;
    @SerializedName("imgFavoriteVans")
    @Expose
    private String imgFavoriteVans;
    @SerializedName("nameVans")
    @Expose
    private String nameVans;
    @SerializedName("dongVans")
    @Expose
    private String dongVans;
    @SerializedName("moneyVans")
    @Expose
    private Double moneyVans;
    private String imgLogoSale;
    private String dongSaleBandau;
    private Double moneySaleBandau;

    public String getIdVans() {
        return idVans;
    }

    public void setIdVans(String idVans) {
        this.idVans = idVans;
    }

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

    public String getFavVans() {
        return favVans;
    }

    public void setFavVans(String favVans) {
        this.favVans = favVans;
    }

    public String getImgVans() {
        return imgVans;
    }

    public void setImgVans(String imgVans) {
        this.imgVans = imgVans;
    }

    public String getImgFavoriteVans() {
        return imgFavoriteVans;
    }

    public void setImgFavoriteVans(String imgFavoriteVans) {
        this.imgFavoriteVans = imgFavoriteVans;
    }

    public String getNameVans() {
        return nameVans;
    }

    public void setNameVans(String nameVans) {
        this.nameVans = nameVans;
    }

    public String getDongVans() {
        return dongVans;
    }

    public void setDongVans(String dongVans) {
        this.dongVans = dongVans;
    }

    public Double getMoneyVans() {
        return moneyVans;
    }

    public void setMoneyVans(Double moneyVans) {
        this.moneyVans = moneyVans;
    }
}
