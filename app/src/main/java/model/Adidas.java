package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Adidas{
    @SerializedName("idAdidas")
    @Expose
    private String idAdidas;
    @SerializedName("favAdidas")
    @Expose
    private String favAdidas;
    @SerializedName("imgAdidas")
    @Expose
    private String imgAdidas;
    @SerializedName("imgFavoriteAdidas")
    @Expose
    private String imgFavoriteAdidas;
    @SerializedName("nameAdidas")
    @Expose
    private String nameAdidas;
    @SerializedName("dongAdidas")
    @Expose
    private String dongAdidas;
    @SerializedName("moneyAdidas")
    @Expose
    private Double moneyAdidas;
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

    public String getIdAdidas() {
        return idAdidas;
    }

    public void setIdAdidas(String idAdidas) {
        this.idAdidas = idAdidas;
    }

    public String getFavAdidas() {
        return favAdidas;
    }

    public void setFavAdidas(String favAdidas) {
        this.favAdidas = favAdidas;
    }

    public String getImgAdidas() {
        return imgAdidas;
    }

    public void setImgAdidas(String imgAdidas) {
        this.imgAdidas = imgAdidas;
    }

    public String getImgFavoriteAdidas() {
        return imgFavoriteAdidas;
    }

    public void setImgFavoriteAdidas(String imgFavoriteAdidas) {
        this.imgFavoriteAdidas = imgFavoriteAdidas;
    }

    public String getNameAdidas() {
        return nameAdidas;
    }

    public void setNameAdidas(String nameAdidas) {
        this.nameAdidas = nameAdidas;
    }

    public String getDongAdidas() {
        return dongAdidas;
    }

    public void setDongAdidas(String dongAdidas) {
        this.dongAdidas = dongAdidas;
    }

    public Double getMoneyAdidas() {
        return moneyAdidas;
    }

    public void setMoneyAdidas(Double moneyAdidas) {
        this.moneyAdidas = moneyAdidas;
    }
}
