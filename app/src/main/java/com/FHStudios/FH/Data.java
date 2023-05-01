package com.FHStudios.FH;



public class Data {

    private String component;
    private String quantity;
    private Integer grmg;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setGrmg(int grmg){ this.grmg=grmg;}
    public Integer getGrmg(){return grmg;}

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }


    public Data(String component, String quantity, int grmg) {
        this.component = component;
        this.quantity = quantity;
        this.grmg=grmg;
    }

    @Override
    public String toString() {
        return "Data{" +
                "component='" + component + '\'' +
                ", quantity='" + quantity + '\'' +
                ", grmg=" + grmg +
                '}';
    }
}
