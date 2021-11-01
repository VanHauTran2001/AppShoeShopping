package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Puma{
    @SerializedName("idPuma")
    @Expose
    private String idPuma;
    @SerializedName("favPuma")
    @Expose
    private String favPuma;
    @SerializedName("imgPuma")
    @Expose
    private String imgPuma;
    @SerializedName("imgFavoritePuma")
    @Expose
    private String imgFavoritePuma;
    @SerializedName("namePuma")
    @Expose
    private String namePuma;
    @SerializedName("dongPuma")
    @Expose
    private String dongPuma;
    @SerializedName("moneyPuma")
    @Expose
    private Double moneyPuma;
    private String imgLogoSale;
    private String dongSaleBandau;
    private Double moneySaleBandau;

    public String getIdPuma() {
        return idPuma;
    }

    public void setIdPuma(String idPuma) {
        this.idPuma = idPuma;
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

    public String getFavPuma() {
        return favPuma;
    }

    public void setFavPuma(String favPuma) {
        this.favPuma = favPuma;
    }

    public String getImgPuma() {
        return imgPuma;
    }

    public void setImgPuma(String imgPuma) {
        this.imgPuma = imgPuma;
    }

    public String getImgFavoritePuma() {
        return imgFavoritePuma;
    }

    public void setImgFavoritePuma(String imgFavoritePuma) {
        this.imgFavoritePuma = imgFavoritePuma;
    }

    public String getNamePuma() {
        return namePuma;
    }

    public void setNamePuma(String namePuma) {
        this.namePuma = namePuma;
    }

    public String getDongPuma() {
        return dongPuma;
    }

    public void setDongPuma(String dongPuma) {
        this.dongPuma = dongPuma;
    }

    public Double getMoneyPuma() {
        return moneyPuma;
    }

    public void setMoneyPuma(Double moneyPuma) {
        this.moneyPuma = moneyPuma;
    }
}
