package model;

import java.util.ArrayList;

public class Currency {
    private String shortCode;
    private CurrencyEntity current;
    private ArrayList<CurrencyEntity> historical;
    private Boolean isWatch;
    private Double watchRate;

    public Currency(String shortCode) {
        this.shortCode = shortCode;
        this.isWatch = false;
        this.watchRate = 0.0;
    }

    public void setHistorical(ArrayList<CurrencyEntity> historical) {
        this.historical = historical;
    }

    public void setCurrent(CurrencyEntity current) {
        this.current = current;
    }

    public void setWatchRate(Double watchRate) {
        this.watchRate = watchRate;
    }

    public void setWatch(Boolean isWatch) {
        this.isWatch = isWatch;
    }

    public String getShortCode() {
        return shortCode;
    }

    public CurrencyEntity getCurrent() {
        return current;
    }

    public ArrayList<CurrencyEntity> getHistorical() {
        return historical;
    }

    public Boolean getWatch() {
        return isWatch;
    }

    public Double getWatchRate() {
        return watchRate;
    }
}
