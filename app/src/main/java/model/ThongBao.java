package model;

public class ThongBao {
    private int idThongBao;
    private String txtThongBao;
    private String hour;
    private String day;

    public ThongBao(int idThongBao ,String txtThongBao, String hour, String day) {
        this.idThongBao = idThongBao;
        this.txtThongBao = txtThongBao;
        this.hour = hour;
        this.day = day;
    }

    public int getIdThongBao() {
        return idThongBao;
    }

    public void setIdThongBao(int idThongBao) {
        this.idThongBao = idThongBao;
    }

    public String getTxtThongBao() {
        return txtThongBao;
    }

    public void setTxtThongBao(String txtThongBao) {
        this.txtThongBao = txtThongBao;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
