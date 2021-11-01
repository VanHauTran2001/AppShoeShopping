package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sale{
    @SerializedName("id_sale")
    @Expose
    private String id_sale;
    @SerializedName("favStatus_Sale")
    @Expose
    private String favStatus_Sale;
    @SerializedName("imgSanPhamSale")
    @Expose
    private String imgSanPhamSale;
    @SerializedName("imgFavoriteSale")
    @Expose
    private String imgFavoriteSale;
    @SerializedName("imgLogoSale")
    @Expose
    private String imgLogoSale;
    @SerializedName("nameSale")
    @Expose
    private String nameSale;
    @SerializedName("dongSale")
    @Expose
    private String dongSale;
    @SerializedName("moneySale")
    @Expose
    private Double moneySale;
    @SerializedName("dongSaleBandau")
    @Expose
    private String dongSaleBandau;
    @SerializedName("moneySaleBandau")
    @Expose
    private Double moneySaleBandau;

    public String getId_sale() {
        return id_sale;
    }

    public void setId_sale(String id_sale) {
        this.id_sale = id_sale;
    }

    public String getFavStatus_Sale() {
        return favStatus_Sale;
    }

    public void setFavStatus_Sale(String favStatus_Sale) {
        this.favStatus_Sale = favStatus_Sale;
    }

    public String getImgSanPhamSale() {
        return imgSanPhamSale;
    }

    public void setImgSanPhamSale(String imgSanPhamSale) {
        this.imgSanPhamSale = imgSanPhamSale;
    }

    public String getImgFavoriteSale() {
        return imgFavoriteSale;
    }

    public void setImgFavoriteSale(String imgFavoriteSale) {
        this.imgFavoriteSale = imgFavoriteSale;
    }

    public String getImgLogoSale() {
        return imgLogoSale;
    }

    public void setImgLogoSale(String imgLogoSale) {
        this.imgLogoSale = imgLogoSale;
    }

    public String getNameSale() {
        return nameSale;
    }

    public void setNameSale(String nameSale) {
        this.nameSale = nameSale;
    }

    public String getDongSale() {
        return dongSale;
    }

    public void setDongSale(String dongSale) {
        this.dongSale = dongSale;
    }

    public Double getMoneySale() {
        return moneySale;
    }

    public void setMoneySale(Double moneySale) {
        this.moneySale = moneySale;
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
}

