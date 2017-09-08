package com.example.lingyi.seacher_op;

/**
 * Created by lingyi on 2017/9/6.
 */

public class Goods {
    private String barcode;
    private String name;
    private String origincountry;
    private String brand;
    private String price;
    protected String pic;
    private String netcontent;

    public Goods(String barcode,String name,String origincountry){
        this.barcode=barcode;
        this.name = name;
        this.origincountry = origincountry;
    }

    public String getName(){
        return name;
    }
    public String getBarcode(){
        return barcode;
    }
    public String getOrigincountry(){
        return origincountry;
    }
    public String getPic(){return pic;}
    public String getBrand(){
        return brand;
    }
    public String getPrice(){return price;}
    public String getNetcontent(){return netcontent;}

}
