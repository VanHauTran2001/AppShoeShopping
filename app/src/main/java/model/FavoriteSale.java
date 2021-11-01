package model;

public class FavoriteSale {
    private String idFavSale;
    private String imageFavSale;
    private String namFavSale;
    private Double moneyFavSale;
    private Double moneyFavSaleBD;

    public FavoriteSale(String idFavSale, String imageFavSale, String namFavSale, Double moneyFavSale, Double moneyFavSaleBD) {
        this.idFavSale = idFavSale;
        this.imageFavSale = imageFavSale;
        this.namFavSale = namFavSale;
        this.moneyFavSale = moneyFavSale;
        this.moneyFavSaleBD = moneyFavSaleBD;
    }

    public String getIdFavSale() {
        return idFavSale;
    }

    public void setIdFavSale(String idFavSale) {
        this.idFavSale = idFavSale;
    }

    public String getImageFavSale() {
        return imageFavSale;
    }

    public void setImageFavSale(String imageFavSale) {
        this.imageFavSale = imageFavSale;
    }

    public String getNamFavSale() {
        return namFavSale;
    }

    public void setNamFavSale(String namFavSale) {
        this.namFavSale = namFavSale;
    }

    public Double getMoneyFavSale() {
        return moneyFavSale;
    }

    public void setMoneyFavSale(Double moneyFavSale) {
        this.moneyFavSale = moneyFavSale;
    }

    public Double getMoneyFavSaleBD() {
        return moneyFavSaleBD;
    }

    public void setMoneyFavSaleBD(Double moneyFavSaleBD) {
        this.moneyFavSaleBD = moneyFavSaleBD;
    }
}
