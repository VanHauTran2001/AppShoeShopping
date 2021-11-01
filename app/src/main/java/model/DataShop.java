package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataShop {

    @SerializedName("categories")
    @Expose
    private List<Categories> categoriesList = null;

    @SerializedName("shoe")
    @Expose
    private List<Shoe> shoeList = null;

    @SerializedName("sale")
    @Expose
    private List<Sale> saleList = null;

    @SerializedName("nike")
    @Expose
    private List<Nike> nikeList = null;

    @SerializedName("adidas")
    @Expose
    private List<Adidas> adidasList = null;

    @SerializedName("gucci")
    @Expose
    private List<Gucci> gucciList = null;

    @SerializedName("puma")
    @Expose
    private List<Puma> pumaList = null;

    @SerializedName("vans")
    @Expose
    private List<Vans> vansList = null;

    public List<Categories> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<Categories> categoriesList) {
        this.categoriesList = categoriesList;
    }

    public List<Shoe> getShoeList() {
        return shoeList;
    }

    public void setShoeList(List<Shoe> shoeList) {
        this.shoeList = shoeList;
    }

    public List<Sale> getSaleList() {
        return saleList;
    }

    public void setSaleList(List<Sale> saleList) {
        this.saleList = saleList;
    }

    public List<Nike> getNikeList() {
        return nikeList;
    }

    public void setNikeList(List<Nike> nikeList) {
        this.nikeList = nikeList;
    }

    public List<Adidas> getAdidasList() {
        return adidasList;
    }

    public void setAdidasList(List<Adidas> adidasList) {
        this.adidasList = adidasList;
    }

    public List<Gucci> getGucciList() {
        return gucciList;
    }

    public void setGucciList(List<Gucci> gucciList) {
        this.gucciList = gucciList;
    }

    public List<Puma> getPumaList() {
        return pumaList;
    }

    public void setPumaList(List<Puma> pumaList) {
        this.pumaList = pumaList;
    }

    public List<Vans> getVansList() {
        return vansList;
    }

    public void setVansList(List<Vans> vansList) {
        this.vansList = vansList;
    }
}
