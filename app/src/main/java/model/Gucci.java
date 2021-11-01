package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Gucci{
    @SerializedName("idGucci")
    @Expose
    private String idGucci;
    @SerializedName("favGucci")
    @Expose
    private String favGucci;
    @SerializedName("imgGucci")
    @Expose
    private String imgGucci;
    @SerializedName("imgFavoriteGucci")
    @Expose
    private String imgFavoriteGucci;
    @SerializedName("nameGucci")
    @Expose
    private String nameGucci;
    @SerializedName("dongGucci")
    @Expose
    private String dongGucci;
    @SerializedName("moneyGucci")
    @Expose
    private Double moneyGucci;
    private String imgLogoSale;
    private String dongSaleBandau;
    private Double moneySaleBandau;

    public String getIdGucci() {
        return idGucci;
    }

    public void setIdGucci(String idGucci) {
        this.idGucci = idGucci;
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

    public String getFavGucci() {
        return favGucci;
    }

    public void setFavGucci(String favGucci) {
        this.favGucci = favGucci;
    }

    public String getImgGucci() {
        return imgGucci;
    }

    public void setImgGucci(String imgGucci) {
        this.imgGucci = imgGucci;
    }

    public String getImgFavoriteGucci() {
        return imgFavoriteGucci;
    }

    public void setImgFavoriteGucci(String imgFavoriteGucci) {
        this.imgFavoriteGucci = imgFavoriteGucci;
    }

    public String getNameGucci() {
        return nameGucci;
    }

    public void setNameGucci(String nameGucci) {
        this.nameGucci = nameGucci;
    }

    public String getDongGucci() {
        return dongGucci;
    }

    public void setDongGucci(String dongGucci) {
        this.dongGucci = dongGucci;
    }

    public Double getMoneyGucci() {
        return moneyGucci;
    }

    public void setMoneyGucci(Double moneyGucci) {
        this.moneyGucci = moneyGucci;
    }
}
